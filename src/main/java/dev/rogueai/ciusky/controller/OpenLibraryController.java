package dev.rogueai.ciusky.controller;

import dev.rogueai.ciusky.service.OpenLibrary;
import dev.rogueai.ciusky.service.Throttled;
import dev.rogueai.ciusky.service.model.openlibrary.Root;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/library")
public class OpenLibraryController {

    private static final Log logger = LogFactory.getLog(OpenLibraryController.class);

    @Autowired
    private OpenLibrary openLibrary;

    @GetMapping
    public String root(@RequestParam String title, @RequestParam(required = false, defaultValue = "5") int limit, Model model) {
        search(title, limit, model);
        return "page/library :: default";
    }

    @HxRequest
    @GetMapping
    public String htmxRoot(@RequestParam String title, @RequestParam(required = false, defaultValue = "5") int limit, Model model) {
        Throttled<Root> res = search(title, limit, model);
        if (res.model.isPresent()) {
            return "page/library :: complete";
        } else {
            return "page/library :: loading";
        }
    }

    public Throttled<Root> search(String title, int limit, Model model) {
        Throttled<Root> res = openLibrary.search(title, limit);
        model.addAttribute("title", title);
        model.addAttribute("timeToWaitPercent", res.timeToWaitPercent);
        model.addAttribute("docs", res.model.orElse(new Root()).docs);
        return res;
    }

}

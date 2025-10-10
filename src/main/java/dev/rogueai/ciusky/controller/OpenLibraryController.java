package dev.rogueai.ciusky.controller;

import dev.rogueai.ciusky.service.OpenLibrary;
import dev.rogueai.ciusky.service.Throttled;
import dev.rogueai.ciusky.service.ext.Root;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/library")
public class OpenLibraryController {

    private static final Log logger = LogFactory.getLog(OpenLibraryController.class);

    @Autowired
    private OpenLibrary openLibrary;

    @GetMapping
    public String library(@RequestParam String title, @RequestParam(required = false, defaultValue = "10") int limit, Model model) {
        searchByTitle(title, limit, model);
        return "page/library :: default";
    }

    @HxRequest
    @GetMapping
    public String htmxLibrary(@RequestParam String title, @RequestParam(required = false, defaultValue = "10") int limit, Model model) {
        Throttled<Root> res = searchByTitle(title, limit, model);
        if (res.model.isPresent()) {
            return "page/library :: complete";
        } else {
            return "page/library :: loading";
        }
    }

    @GetMapping(value = { "/cover/{olid}.jpg" }, produces = { MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody byte[] image(@PathVariable String olid) throws IOException {
        return openLibrary.getImage(olid);
    }

    public Throttled<Root> searchByTitle(String title, int limit, Model model) {
        Throttled<Root> res = openLibrary.searchByTitle(title, limit);
        model.addAttribute("title", title);
        model.addAttribute("limit", limit);
        model.addAttribute("timeToWaitPercent", res.timeToWaitPercent);
        model.addAttribute("docs", res.model.orElse(new Root()).docs);
        return res;
    }

}

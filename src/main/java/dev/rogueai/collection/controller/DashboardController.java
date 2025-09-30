package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.OptionService;
import dev.rogueai.collection.service.model.Option;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxReselect;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final List<DashboardMenu> menus = new ArrayList<>();

    @Autowired
    private OptionService optionService;

    @PostConstruct
    public void init() {
        menus.add(new DashboardMenu("Dashboard", "dashboard", "/dashboard"));
        menus.add(new DashboardMenu("Ciusky Types", null, "/dashboard/types"));
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping()
    public String dashboard(Model model) {
        model.addAttribute("menus", menus);
        return "page/dashboard";
    }

    @GetMapping("/types")
    public String ciuskyTypes(Model model) {
        List<Option> types = optionService.types();
        model.addAttribute("menus", menus);
        model.addAttribute("optionView", new OptionView(types));
        return "page/ciusky-types";
    }

    @HxRequest
    @HxReselect("form")
    @PostMapping("/types")
    public String addTypes(@ModelAttribute OptionView optionView, Model model) {
        optionView.getOptions().add(new Option());
        return "page/ciusky-types";
    }

    @HxRequest
    @HxReselect("form")
    @PutMapping("/types")
    public String updateTypes(@ModelAttribute OptionView optionView, Model model) {
        // TODO
        return "page/ciusky-types";
    }

    @HxRequest
    @HxReselect("form")
    @PutMapping("/types/{index}")
    public String deleteTypes(@ModelAttribute OptionView optionView, @PathVariable int index, Model model) {
        // TODO
        return "page/ciusky-types";
    }

}

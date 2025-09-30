package dev.rogueai.collection.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final List<DashboardMenu> menus = new ArrayList<>();

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
        return "page/admin";
    }

    @GetMapping("/types")
    public String ciuskyTypes(Model model) {
        model.addAttribute("menus", menus);
        return "page/ciusky-types";
    }

}

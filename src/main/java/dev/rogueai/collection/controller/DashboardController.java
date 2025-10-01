package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.OptionService;
import dev.rogueai.collection.service.model.OptionEdit;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxReselect;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
        List<OptionEdit> types = optionService.typesForEdit();
        model.addAttribute("menus", menus);
        model.addAttribute("optionEditorView", new OptionEditorView(types));
        return "page/ciusky-types";
    }

    /**
     * Add row
     *
     * @param optionEditorView
     * @param model
     * @return
     */
    @HxRequest
    @HxReselect("form")
    @PostMapping("/type/add")
    public String addType(@ModelAttribute OptionEditorView optionEditorView, Model model) {
        optionEditorView.getOptions().add(new OptionEdit());
        optionEditorView.setDirty(true);
        return "page/ciusky-types";
    }

    /**
     * Save changes
     *
     * @param optionEditorView
     * @param model
     * @return
     */
    @HxRequest
    @HxReselect("form")
    @PutMapping("/types/save")
    public String saveTypes(@ModelAttribute @Valid OptionEditorView optionEditorView, Model model) {
        optionService.saveTypes(optionEditorView.getOptions());
        return "redirect:htmx:/dashboard/types";
    }

    /**
     * Save changes
     *
     * @param optionEditorView
     * @param model
     * @return
     */
    @HxRequest
    @HxReselect("#actions")
    @PutMapping("/type/change")
    public String updateType(@ModelAttribute @Valid OptionEditorView optionEditorView, Model model) {
        optionEditorView.setDirty(true);
        return "page/ciusky-types";
    }

    /**
     * Delete row
     *
     * @param optionEditorView
     * @param index
     * @return
     */
    @HxRequest
    @HxReselect("form")
    @PostMapping("/type/delete/{index}")
    public String deleteTypes(@ModelAttribute OptionEditorView optionEditorView, @PathVariable int index) {
        OptionEdit deleted = optionEditorView.getOptions().get(index);
        if (deleted.getId() != null) {
            deleted.setDeleted(true);
        } else {
            optionEditorView.getOptions().remove(deleted);
        }
        optionEditorView.setDirty(true);
        return "page/ciusky-types";
    }

}

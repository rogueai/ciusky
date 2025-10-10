package dev.rogueai.ciusky.controller;

import dev.rogueai.ciusky.controller.view.DashboardMenu;
import dev.rogueai.ciusky.controller.view.OptionEditorView;
import dev.rogueai.ciusky.service.OptionService;
import dev.rogueai.ciusky.service.model.OptionEdit;
import dev.rogueai.ciusky.util.TemplateUtils;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final List<DashboardMenu> menus = new ArrayList<>();

    @Autowired
    private OptionService optionService;

    @Autowired
    private TemplateUtils templateUtils;

    @Autowired
    public CacheManager cacheManager;

    @PostConstruct
    public void init() {
        menus.add(new DashboardMenu("Dashboard", "dashboard", "/dashboard"));
        menus.add(new DashboardMenu("Ciusky Types", null, "/dashboard/types"));
    }

    @ModelAttribute("menus")
    public List<DashboardMenu> menus() {
        return menus;
    }

    @GetMapping()
    public String dashboard(Model model) {

        // TODO: move and generalize this code
        CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;

        Collection<String> cacheNames = caffeineCacheManager.getCacheNames();
        Collection<Long> series = new ArrayList<>();

        for (String cacheName : cacheNames) {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
            if (cache == null) {
                continue;
            }
            long cacheSize = cache.getNativeCache().estimatedSize();
            series.add(cacheSize);
        }

        model.addAttribute("series", series);
        model.addAttribute("labels", cacheNames);

        return "page/dashboard";
    }

    @GetMapping("/types")
    public String types(Model model) {
        List<OptionEdit> types = optionService.typesForEdit();
        model.addAttribute("optionEditorView", new OptionEditorView(types));
        return "page/ciusky-types";
    }

    /**
     * Add row
     */
    @HxRequest
    @PostMapping("/type/add")
    public String typeAdd(@ModelAttribute OptionEditorView optionEditorView, Model model) {
        optionEditorView.getOptions().add(new OptionEdit());
        optionEditorView.setDirty(true);
        return "page/ciusky-types :: form";
    }

    /**
     * Save changes
     */
    @HxRequest
    @PutMapping("/types/save")
    public String typesSave(@ModelAttribute @Valid OptionEditorView optionEditorView, Model model, HtmxResponse htmxResponse) {
        optionService.saveTypes(optionEditorView.getOptions());
        templateUtils.toast(htmxResponse, true, "Saved");

        List<OptionEdit> types = optionService.typesForEdit();
        model.addAttribute("optionEditorView", new OptionEditorView(types));

        return "page/ciusky-types :: form";
    }

    /**
     * Save changes
     */
    @HxRequest
    @PutMapping("/type/change")
    public String typeDelete(@ModelAttribute @Valid OptionEditorView optionEditorView) {
        optionEditorView.setDirty(true);
        return "page/ciusky-types :: actions";
    }

    /**
     * Delete row
     */
    @HxRequest
    @PostMapping("/type/toggle/{index}")
    public String typeToggle(@ModelAttribute OptionEditorView optionEditorView, @PathVariable int index) {
        OptionEdit option = optionEditorView.getOptions().get(index);
        if (option.getId() != null) {
            option.setDeleted(!option.isDeleted());
        } else {
            optionEditorView.getOptions().remove(option);
        }
        optionEditorView.setDirty(true);
        return "page/ciusky-types :: form";
    }
}

package dev.rogueai.ciusky.controller;

import dev.rogueai.ciusky.service.CiuskySearchService;
import dev.rogueai.ciusky.service.OptionService;
import dev.rogueai.ciusky.service.model.CiuskyFilter;
import dev.rogueai.ciusky.service.model.CiuskySearch;
import dev.rogueai.ciusky.service.model.Option;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.util.List;

@Controller
@SessionAttributes("filter")
public class SearchController {

    @Autowired
    private CiuskySearchService ciuskySearchService;

    @Autowired
    private OptionService optionService;

    @ModelAttribute(name = "filter")
    public CiuskyFilter filter() {
        List<Option> types = optionService.types();
        return new CiuskyFilter("", "", types);
    }

    @GetMapping({ "/" })
    public String list(@ModelAttribute("filter") CiuskyFilter filter, Model model) {
        List<Option> types = optionService.types();
        List<CiuskySearch> listCiusky = ciuskySearchService.findAll(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("ciuskyTypes", types);
        model.addAttribute("listCiusky", listCiusky);
        return "page/index :: default";
    }

    @HxRequest()
    @PostMapping({ "/search" })
    public View search(@ModelAttribute("filter") @Valid CiuskyFilter filter, BindingResult bindingResult, Model model, HtmxResponse htmxResponse) {

        if (bindingResult.hasErrors()) {
            htmxResponse.setRetarget("#search-inputs");
            htmxResponse.setReselect("#search-inputs");
            htmxResponse.setReswap(HtmxReswap.outerHtml());
            return FragmentsRendering.with("page/index :: search-inputs").build();
        }

        List<CiuskySearch> listCiusky = ciuskySearchService.findAll(filter);
        model.addAttribute("listCiusky", listCiusky);

        return FragmentsRendering //
                .with("page/index :: table") //
                .fragment("page/index :: search-inputs") //
                .build();
    }

    @GetMapping({ "/about" })
    public String about() {
        return "page/about :: default";
    }

}

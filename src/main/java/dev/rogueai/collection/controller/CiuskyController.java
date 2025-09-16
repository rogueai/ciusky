package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.CiuskySearchService;
import dev.rogueai.collection.service.CiuskyService;
import dev.rogueai.collection.service.OptionService;
import dev.rogueai.collection.service.ImageService;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.CiuskySearch;
import dev.rogueai.collection.service.model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class CiuskyController {

    @Autowired
    private CiuskySearchService ciuskySearchService;

    @Autowired
    private CiuskyService ciuskyService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ImageService imageService;

    @GetMapping({"/"})
    public String list(Model model) {
        List<CiuskySearch> listCiusky = ciuskySearchService.findAll();
        model.addAttribute("listCiusky", listCiusky);
        return "index";
    }

    @GetMapping({"/ciusky", "/ciusky/{id}"})
    public String create(@PathVariable(required = false) Long id, Model model) {
        List<Option> types = optionService.types();
        model.addAttribute("types", types);
        if (id == null) {
            model.addAttribute("ciusky", new Ciusky());
        } else {
            model.addAttribute("ciusky", ciuskyService.get(id));
        }

        return "create";
    }

    @PostMapping({"/ciusky"})
    public String save(@ModelAttribute Ciusky ciusky, Model model) {
        ciuskyService.save(ciusky);
        return "forward:/ciusky/" + ciusky.id;
    }

    @GetMapping({"/image/{uuid}"})
    public @ResponseBody byte[] image(@PathVariable String uuid, @RequestParam(required = false, defaultValue = "false") boolean thumbnail) throws IOException {
        return imageService.getResource(uuid, thumbnail);
    }



}

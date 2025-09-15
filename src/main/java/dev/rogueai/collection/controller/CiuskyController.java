package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.CiuskyService;
import dev.rogueai.collection.service.ImageService;
import dev.rogueai.collection.service.model.Ciusky;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class CiuskyController {

    @Autowired
    private CiuskyService ciuskyService;

    @Autowired
    private ImageService imageService;

    @GetMapping({"/"})
    public String list(Model model) {
        List<Ciusky> listCiusky = ciuskyService.getAll();
        model.addAttribute("listCiusky", listCiusky);
        return "index";
    }

    @GetMapping({"/image/{uuid}"})
    public @ResponseBody byte[] image(@PathVariable String uuid, @RequestParam(required = false, defaultValue = "false") boolean thumbnail) throws IOException {
        return imageService.getResource(uuid, thumbnail);
    }



}

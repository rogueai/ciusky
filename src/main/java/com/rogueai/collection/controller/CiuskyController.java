package com.rogueai.collection.controller;

import com.rogueai.collection.service.CiuskyService;
import com.rogueai.collection.service.TagService;
import com.rogueai.collection.service.model.Ciusky;
import com.rogueai.collection.service.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class CiuskyController {

    @Autowired
    private TagService tagService;

    @Autowired
    private CiuskyService ciuskyService;

    @GetMapping({"/"})
    public String list(Model model) {
        List<Tag> list1 = tagService.searchTag("p");

        List<Ciusky> list2 = ciuskyService.getAll();

        model.addAttribute("listTag", list1);
        model.addAttribute("listCiusky", list2);
        return "index";
    }
}

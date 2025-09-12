package com.rogueai.collection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class CiuskyController {

    @GetMapping({"/"})
    public String list(Model model) {
        List<String> list = Arrays.asList("a", "b", "c");
        model.addAttribute("list", list);
        return "index";
    }
}

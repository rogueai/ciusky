package com.rogueai.collection.controller;

import com.rogueai.collection.service.CiuskyService;
import com.rogueai.collection.service.model.Ciusky;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CiuskyController {

    @Autowired
    private CiuskyService ciuskyService;

    @GetMapping({"/"})
    public String list(Model model) {
        List<Ciusky> listCiusky = ciuskyService.getAll();
        model.addAttribute("listCiusky", listCiusky);
        return "index";
    }
}

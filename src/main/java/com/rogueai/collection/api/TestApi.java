package com.rogueai.collection.api;

import com.rogueai.collection.service.CiuskyService;
import com.rogueai.collection.service.model.Ciusky;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/api", produces = "application/json")
public class TestApi {

    private final CiuskyService ciuskyService;

    public TestApi(CiuskyService ciuskyService) {
        this.ciuskyService = ciuskyService;
    }

    @GetMapping(value = "/person")
    @ResponseBody
    public List<Ciusky> getAll() {
        return ciuskyService.getAll();
    }

    @GetMapping(value = "/person/{id}")
    @ResponseBody
    public Ciusky getPerson(@PathVariable long id) {
        return ciuskyService.get(id);
    }

}

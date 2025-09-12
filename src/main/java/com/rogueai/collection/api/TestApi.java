package com.rogueai.collection.api;

import com.rogueai.collection.db.dto.PersonDto;
import com.rogueai.collection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/api")
public class TestApi {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/person", produces = "application/json")
    @ResponseBody
    public List<PersonDto> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/person/{id}", produces = "application/json")
    @ResponseBody
    public PersonDto getPerson(@PathVariable long id) {
        return userService.get(id);
    }

    @PostMapping(value = "/person", produces = "application/json")
    @ResponseBody
    public int insert(@RequestBody PersonDto person) {
        return userService.insert(person);
    }

    @PutMapping(value = "/person", produces = "application/json")
    @ResponseBody
    public int update(@RequestBody PersonDto person) {
        return userService.update(person);
    }

    @DeleteMapping(value = "/person/{id}", produces = "application/json")
    @ResponseBody
    public boolean delete(@PathVariable long id) {
        return userService.delete(id);

    }

}

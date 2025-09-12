package com.rogueai.collection.api;

import com.rogueai.collection.db.dto.PersonEntity;
import com.rogueai.collection.service.UserService;
import com.rogueai.collection.service.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/api", produces = "application/json")
public class TestApi {

    private final UserService userService;

    public TestApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/person")
    @ResponseBody
    public List<Person> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/person/{id}")
    @ResponseBody
    public Person getPerson(@PathVariable long id) {
        return userService.get(id);
    }

    @PostMapping(value = "/person")
    @ResponseBody
    public int insert(@RequestBody PersonEntity person) {
        return userService.insert(person);
    }

    @PutMapping(value = "/person")
    @ResponseBody
    public int update(@RequestBody PersonEntity person) {
        return userService.update(person);
    }

    @DeleteMapping(value = "/person/{id}")
    @ResponseBody
    public boolean delete(@PathVariable long id) {
        return userService.delete(id);

    }

}

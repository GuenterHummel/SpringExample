package com.gh.demoserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting") // maps to http://localhost:8080/hello?name=Gue
    public String greeting(@RequestParam(name="name", defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting"; // forward to /src/main/resources/templates/greeting.html
    }
}
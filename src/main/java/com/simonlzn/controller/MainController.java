package com.simonlzn.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("home")
    public ModelAndView showHomePage() {
        ModelAndView m = new ModelAndView("index");
        m.addObject("name", "Hello");
        return m;
    }
}
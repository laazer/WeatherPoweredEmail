package com.laazer.wpe.controller;

import com.laazer.wpe.model.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Laazer
 */
@RestController
public class HomeController {

    @GetMapping("/index")
    public ModelAndView home(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("index");
    }
}

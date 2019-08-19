package com.training.RegistrationForm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
    @GetMapping("/")
    public String getStartPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "index.html";
    }
}

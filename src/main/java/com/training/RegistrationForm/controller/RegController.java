package com.training.RegistrationForm.controller;

import com.training.RegistrationForm.dto.UserDTO;
import com.training.RegistrationForm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class RegController {

    @Autowired
    UserService userService;

    @GetMapping("/reg")
    public String getRegistrationPage(UserDTO userDTO){
        return "reg.html";
    }

    @PostMapping("/reg")
    public String validateRegistrationInfo(Model model, @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reg.html";
        }
        userService.saveNewUser(userDTO);
        return "redirect:/login";
    }
}

package com.training.RegistrationForm.controller;

import com.training.RegistrationForm.dto.UserDTO;
import com.training.RegistrationForm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Slf4j
@Controller
public class UsersController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin/usersList", method = RequestMethod.GET)
    public String showUsersList(Model model) {
        model.addAttribute("users", userService.getAllUsers().getUsers());
        return "users.html";
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePassword(Model model) {
        return "change_password.html";
    }

    @PostMapping(value = "/user/changePassword")
    public String ChangePassword(@RequestParam String password, Model model) {
        if(password.length() <3) {
            return "change_password.html";
        }
        UserDTO userDTO = new UserDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDTO.setEmail(auth.getName());
        userDTO.setPassword(password);
        userService.updatePassword(userDTO);
        return "redirect:/";
    }
}

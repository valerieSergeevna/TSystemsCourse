package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;



    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "redirect:/users";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult,
                          Model model, HttpServletRequest request) throws DataBaseException {

        if (bindingResult.hasErrors()) {
            return "redirect:/users";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "redirect:/users";
        }

        if (!userService.saveUser(userForm, request)) {
            model.addAttribute("usernameError", "User name is not uniq");
            return "redirect:/users";
        }

        return "redirect:/users";
    }


}
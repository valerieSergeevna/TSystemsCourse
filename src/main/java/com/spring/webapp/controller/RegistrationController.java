package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;



//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "redirect:/users";
//    }

    @RequestMapping("/registration")
    public String addUser(Model model, HttpServletRequest request) throws DataBaseException, ServerException {
//crutch
//        String username = userService.loadUserByUsername(userForm.getUsername()).getUsername();
//        userService.deleteUser(((User)userService.loadUserByUsername(userForm.getUsername())).getId());

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        User user = new User();
        user.setGoogleUsername(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);
//        if (bindingResult.hasErrors()) {
//            return "redirect:/users";
//        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "redirect:/users";
        }

        if (!userService.saveUser(user, request)) {
            model.addAttribute("usernameError", "User name is not uniq");
            return "redirect:/users";
        }

        return "redirect:/users";
    }


}
package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.webapp.dto.AbstractDTOUser;
import com.spring.webapp.dto.AdminDTOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.NurseDTOImpl;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.DoctorServiceImpl;
import com.spring.webapp.service.NurseServiceImpl;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private NurseServiceImpl nurseService;

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "admin/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult,
                          Model model, HttpServletRequest request) throws DataBaseException {

        if (bindingResult.hasErrors()) {
            return "admin/registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "admin/registration";
        }
        String role = "";
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String userRole = request.getParameter("role");
        String userName = userForm.getUsername();

        switch (userRole) {
            case "doctor":
                role = "ROLE_DOCTOR";
                doctorService.save((DoctorDTOImpl)setFields(new DoctorDTOImpl(),
                        name,surname,position,userName));
                break;
            case "nurse":
                role = "ROLE_NURSE";
                nurseService.save((NurseDTOImpl) setFields(new NurseDTOImpl(),
                        name,surname,position,userName));
                break;
            case "admin":
                role = "ROLE_ADMIN";
                adminService.save((AdminDTOImpl) setFields(new AdminDTOImpl(),
                        name,surname,position,userForm.getUsername()));
                break;
            default:
        }
        if (!userService.saveUser(userForm, role)) {
            model.addAttribute("usernameError", "User name is not uniq");
            return "admin/registration";
        }

        return "redirect:/";
    }

    private AbstractDTOUser setFields(AbstractDTOUser abstractDTOUser,String name, String surname,
                           String position, String username){
        abstractDTOUser.setName(name);
        abstractDTOUser.setSurname(surname);
        abstractDTOUser.setPosition(position);
        abstractDTOUser.setUsername(username);
        return abstractDTOUser;
    }
}
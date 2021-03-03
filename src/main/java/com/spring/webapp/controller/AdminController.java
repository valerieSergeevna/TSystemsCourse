package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dto.AllDTOUser;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.DoctorUserServiceImpl;
import com.spring.webapp.service.NurseUserServiceImpl;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AdminController {


    private DoctorUserServiceImpl doctorService;

    private UserService userService;

    private NurseUserServiceImpl nurseService;

    private AdminServiceImpl adminService;

    @Autowired
    public void setDoctorService(DoctorUserServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNurseService(NurseUserServiceImpl nurseService) {
        this.nurseService = nurseService;
    }

    @Autowired
    public void setAdminService(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }


    @RequestMapping("/addNewDoctor")
    public String addNewDoctor(Model model) {
        DoctorDTOImpl doctor = new DoctorDTOImpl();
        model.addAttribute("doctor", doctor);
        return "doctor/doctor-info";
    }

    @RequestMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute("doctor") DoctorDTOImpl doctor) throws DataBaseException {
        doctorService.save(doctor);
        return "redirect:/";////!!!
    }

    @RequestMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("docId") int id) throws DataBaseException {
        doctorService.delete(id);
        return "redirect:/";//////!!!
    }


    @RequestMapping("/updateDoctorInfo")
    public String updateDoctorInfo(@RequestParam("docId") int id, Model model) throws DataBaseException {
        DoctorDTOImpl doctorDTO = doctorService.get(id);
        model.addAttribute("doctor", doctorDTO);
        return "doctor/doctor-info";
    }

    @GetMapping("/users")
    public String userList(Model model) throws ServerException, DataBaseException {
        model.addAttribute("allUsers", userService.allUsersWithInfo());
        model.addAttribute("userForm", new User());
        return "admin/users";
    }

    @RequestMapping("/updateUser")
    public String updateUserInfo(@RequestParam("userId") Long userId, Model model) throws DataBaseException, ServerException {
        User user = userService.findUserById(userId);

        AllDTOUser userDTO;
        String roles = ((Role) userService.findUserById(userId).getRoles().toArray()[0]).getAuthority();
        if ("ROLE_DOCTOR".equals(roles)) {
            userDTO = doctorService.getByUserName(user.getUsername());
        } else if ("ROLE_NURSE".equals(roles)) {
            userDTO = nurseService.getByUserName(user.getUsername());
        } else {
            userDTO = adminService.getByUserName(user.getUsername());
        }
        model.addAttribute("userInfo", userDTO);
        model.addAttribute("user", user);
        return "admin/user-info";
    }

    @RequestMapping(value = "/saveUpdatedUser")
    public String editUserInfo(@Validated @ModelAttribute("userForm") AllDTOUser userForm, BindingResult bindingResult,
                               Model model, HttpServletRequest request) throws DataBaseException, ServerException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return "admin/user-info";
        }
        User user = userService.findByUsername(request.getParameter("username"));
        User thisUser = (User) userService.loadUserByUsername(request.getParameter("hiddenUsername"));

        if (user != null && !thisUser.getId().equals(user.getId())) {
            model.addAttribute("usernameError", "This username already exists");
            model.addAttribute("userId", thisUser.getId());
            return "redirect:/updateUser";
        }

        userService.updateUser(thisUser, request);

        return "redirect:/users";
    }

    @RequestMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult,
                          Model model, HttpServletRequest request) throws DataBaseException, ServerException {

        if (bindingResult.hasErrors()) {
            return "redirect:/users";
        }
        model.addAttribute("allUsers", userService.allUsersWithInfo());

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "admin/users";
        }

        if (!userService.saveUser(userForm, request)) {
            model.addAttribute("usernameError", "User name is not uniq");
            return "admin/users";
        }

        return "redirect:/users";
    }




    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) throws DataBaseException, ServerException {
        userService.deleteUser(userId);
   //     usernameError = null;
        return "redirect:/users";
    }


}
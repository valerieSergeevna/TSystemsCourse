package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dto.AdminDTOImpl;
import com.spring.webapp.dto.AllDTOUser;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.NurseDTOImpl;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.*;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private DoctorUserServiceImpl doctorService;

    @Autowired
    private UserService userService;

    @Autowired
    private NurseUserServiceImpl nurseService;


    @Autowired
    private AdminServiceImpl adminService;

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
        String roles = ((Role)userService.findUserById(userId).getRoles().toArray()[0]).getAuthority();
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
    public String editUserInfo(@Validated  @ModelAttribute("userForm") AllDTOUser userForm, BindingResult bindingResult,
                               Model model, HttpServletRequest request) throws DataBaseException, ServerException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return  "admin/user-info";
        }


        String roles =((Role)(userService.loadUserByUsername(userForm.getUsername()).getAuthorities().toArray()[0])).getAuthority();
        if ("ROLE_DOCTOR".equals(roles)) {
            doctorService.update(new DoctorDTOImpl(userForm.getId(),userForm.getName(),
                    userForm.getSurname(),userForm.getPosition(),userForm.getUsername()));
        } else if ("ROLE_NURSE".equals(roles)) {
            nurseService.update(new NurseDTOImpl(userForm.getId(),userForm.getName(),
                    userForm.getSurname(),userForm.getPosition(),userForm.getUsername()));
        } else {
            adminService.update(new AdminDTOImpl(userForm.getId(),userForm.getName(),
                    userForm.getSurname(),userForm.getPosition(),userForm.getUsername()));
        }
        User user = (User) userService.loadUserByUsername(userForm.getUsername());
        userService.saveUser(user,request);


        return "redirect:/users";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) throws DataBaseException, ServerException {
        userService.deleteUser(userId);
        return "redirect:/users";
    }


//
//    @PostMapping("/users")
//    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "redirect:/admin";
//    }

//    @GetMapping("/admin/gt/{userId}")
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin";
//    }

}
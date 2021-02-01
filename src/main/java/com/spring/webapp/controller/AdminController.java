package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dto.AdminDTOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.DoctorServiceImpl;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private UserService userService;

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
        return "admin/users";
    }


    @PostMapping("/users")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

}
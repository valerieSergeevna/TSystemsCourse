package com.spring.webapp.controller;


import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private EntityService doctorService;

    @RequestMapping("/")
    public String showAllDoctors(Model model){
        List<Doctor> allDoctors = doctorService.getAll();
        model.addAttribute("allDocs", allDoctors);
        return "all-doctors";
    }

    @RequestMapping("/addNewDoctor")
    public String addNewDoctor(Model model){
        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        return "doctor-info";
    }

    @RequestMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor){
        doctorService.save(doctor);
        return "redirect:/";
    }
}

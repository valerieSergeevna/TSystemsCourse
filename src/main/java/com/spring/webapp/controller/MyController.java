package com.spring.webapp.controller;

import com.spring.webapp.dao.DoctorDAO;
import com.spring.webapp.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private DoctorDAO doctorDAO;

    @RequestMapping("/")
    public String showAllDoctors(Model model){
        List<Doctor> allDoctors = doctorDAO.getAllDoctors();
        model.addAttribute("allDocs", allDoctors);
        return "all-doctors";
    }
}

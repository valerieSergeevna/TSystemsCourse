package com.spring.webapp.controller;


import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("docId") int id){
        doctorService.delete(id);
        return "redirect:/";
    }


    @RequestMapping("/updateDoctorInfo")
    public String updateInfo(@RequestParam("docId") int id, Model model){
        Doctor doctor = (Doctor) doctorService.get(id);
        model.addAttribute("doctor", doctor);
        return "doctor-info";
    }
}

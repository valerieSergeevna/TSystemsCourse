package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.service.DoctorServiceImpl;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import com.spring.webapp.service.TreatmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private DoctorServiceImpl doctorService;

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

}
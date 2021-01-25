package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.utils.TimeParser;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.service.DoctorServiceImpl;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import com.spring.webapp.service.TreatmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private TreatmentServiceImpl treatmentService;

    @Autowired
    private TreatmentEventServiceImpl treatmentEventService;


    @RequestMapping("/deletePatient")
    public String deletePatient(@RequestParam("patientId") int id) throws DataBaseException {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

    @RequestMapping("/addNewPatient")
    public String addNewPatient(Model model) {
        model.addAttribute("patient", patientService.createNewPatient());
        return "doctor/treatment-info";
    }

    @RequestMapping(value = "/saveTreatment", method = RequestMethod.POST)
    public String saveTreatment(@Validated @ModelAttribute("patient") PatientDTOImpl patientDTO,
                                BindingResult bindingResult,
                                HttpServletRequest request,Authentication authentication, Model model) throws DataBaseException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patientDTO);
            return "doctor/treatment-info";
        }
        patientService.saveTreatmentInfo(patientDTO,request,authentication);
        return "redirect:/patients";
    }

    @RequestMapping("/deleteTreatment") // delete list's item
    public String deleteTreatment(@RequestParam("treatmentId") int id,
                                  Model model) throws DataBaseException {
        PatientDTOImpl patientDTO = treatmentService.getPatient(id);
        treatmentService.delete(id);
        model.addAttribute("patientId", patientDTO.getId());
        return "redirect:doctor/updateTreatmentInfo";
    }


    @RequestMapping("/doctor/updateTreatmentInfo")
    public String updateTreatmentInfo(@RequestParam("patientId") int id, Model model) throws DataBaseException {
        PatientDTOImpl patientDTO = patientService.get(id);
        model.addAttribute("patient", patientDTO);
        return "doctor/treatment-info";
    }

}

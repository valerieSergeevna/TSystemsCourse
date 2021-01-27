package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.webapp.dto.*;
import com.spring.webapp.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class GeneralController {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private ProcedureMedicineServiceImpl procedureMedicineService;


    private static final Logger logger = Logger.getLogger(GeneralController.class);

    @RequestMapping("/")
    public String greet(Model model, Authentication authentication) {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        String role;
        for (int i = 0; i < roles.size(); i++) {
            role = roles.toArray()[i] + "";
            if (role.equals("ROLE_DOCTOR")) {
                logger.info("role verified" + i + " is -> " + role);
                logger.info("name verified" + i + " is -> " + authentication.getName());
                model.addAttribute("role", "Doctor");
                return "redirect:/patients";
            } else if (role.equals("ROLE_NURSE")) {
                logger.info("role verified" + i + " is -> " + role);
                logger.info("name verified" + i + " is -> " + authentication.getName());
                model.addAttribute("role", "Nurse");
                return "redirect:/nurse/";
            }
        }
        return "greeting";
    }

    @RequestMapping("/patients")
    public String showAllPatients(Model model, Authentication authentication) throws DataBaseException {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        String role;
        List<PatientDTOImpl> allPatient = new ArrayList<>();
        String name = authentication.getName();
        for (int i = 0; i < roles.size(); i++) {
            role = roles.toArray()[i] + "";
            if (role.equals("ROLE_DOCTOR")) {
                allPatient = patientService.getAllByDoctorUserName(name);
            } else if (role.equals("ROLE_NURSE")) {
                model.addAttribute("role", "Nurse");
                allPatient = patientService.getAll();
            }
        }
        model.addAttribute("allPatient", allPatient);
        return "all-patient";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "general/login";
    }

    @RequestMapping("/viewPatient")
    public String viewPatient(@RequestParam("patientId") int id, Model model) throws DataBaseException {
        model.addAttribute("patient", patientService.get(id));
        return "patient-view";
    }

    @RequestMapping("/medicineProcedure")
    public String viewMedicineProcedure( Model model) throws DataBaseException {
        model.addAttribute("allMedicineProcedure", procedureMedicineService.getAll());
        return "medicine-procedure";
    }


    @RequestMapping("/403")
    public String _403() {
        return "errors/403";
    }

    @RequestMapping("/400")
    public String _400() {
        return "errors/400";
    }

}
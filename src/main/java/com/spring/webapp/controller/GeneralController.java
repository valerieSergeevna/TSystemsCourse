package com.spring.webapp.controller;

import com.spring.SpringBootRehaApplication;
import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.jms.JmsProducer;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.ProcedureMedicineServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
public class GeneralController {
    JmsProducer jmsProducer;

    private PatientServiceImpl patientService;

    private ProcedureMedicineServiceImpl procedureMedicineService;

    boolean wakeUpFlag = false;

    @Autowired
    public void setJmsProducer(JmsProducer jmsProducer) {
        this.jmsProducer = jmsProducer;
    }

    @Autowired
    public void setPatientService(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setProcedureMedicineService(ProcedureMedicineServiceImpl procedureMedicineService) {
        this.procedureMedicineService = procedureMedicineService;
    }

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(SpringBootRehaApplication.class);


    @RequestMapping("/")
    public String greet(Model model, Authentication authentication, Principal principal) {
        String name = principal.getName();
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        String role;
        for (int i = 0; i < roles.size(); i++) {
            role = ((Role) roles.toArray()[i]).getAuthority() + "";
            log.info("role verified" + i + " is -> " + role);
            log.info("name verified" + i + " is -> " + authentication.getName());
            switch (role) {
                case "ROLE_DOCTOR":
                    model.addAttribute("role", "Doctor");
                    return "redirect:/patients";
                case "ROLE_NURSE":
                    model.addAttribute("role", "Nurse");
                    return "redirect:/nurse/";
                case "ROLE_ADMIN":
                    model.addAttribute("role", "Admin");
                    return "redirect:/users";
            }
        }
        return "greeting";
    }

    @RequestMapping("/patients")
    public String showAllPatients(Model model, Authentication authentication) throws DataBaseException, ServerException {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        String role;
        List<PatientDTOImpl> allPatient = new ArrayList<>();
        String name = authentication.getName();
        for (int i = 0; i < roles.size(); i++) {
            role = ((Role) roles.toArray()[i]).getAuthority() + "";
            if (role.equals("ROLE_DOCTOR")) {
                allPatient = patientService.getAllByDoctorUserName(name);
            } else if (role.equals("ROLE_NURSE")) {
                model.addAttribute("role", "Nurse");
                allPatient = patientService.getAll();
            } else if (role.equals("ROLE_ADMIN")) {
                model.addAttribute("role", "Admin");
                allPatient = patientService.getAll();
            }
        }
        model.addAttribute("allPatient", allPatient);
        return "all-patient";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() throws NamingException {
        if (!wakeUpFlag) {
            wakeUpFlag = true;
            jmsProducer.send("UPDATE");
        }

        return "general/login";
    }


    @RequestMapping("/viewPatient")
    public String viewPatient(@RequestParam("patientId") int id, Model model) throws DataBaseException {
        model.addAttribute("patient", patientService.get(id));
        return "patient-view";
    }

    @RequestMapping("/medicineProcedure")
    public String viewMedicineProcedure(Model model) throws DataBaseException {
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

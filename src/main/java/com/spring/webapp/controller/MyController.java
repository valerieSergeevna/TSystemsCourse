package com.spring.webapp.controller;

import com.spring.webapp.dao.TreatmentDAOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.EntityDTO;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.service.EntityService;
import com.spring.webapp.service.TreatmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    @Qualifier("doctorServiceImpl")
    private EntityService doctorService;

    @Autowired
    @Qualifier("patientServiceImpl")
    private EntityService patientService;

    @Autowired
    @Qualifier("treatmentServiceImpl")
    private EntityService treatmentService;

    @RequestMapping("/")
    public String showAllDoctors(Model model){
       /* List<DoctorDTOImpl> allDoctors = doctorService.getAll();

        model.addAttribute("allDocs", allDoctors);
        return "all-doctors";*/
        List<PatientDTOImpl> allPatient = patientService.getAll();

        model.addAttribute("allPatient", allPatient);
        return "all-patient";
    }

    @RequestMapping("/addNewDoctor")
    public String addNewDoctor(Model model){
        DoctorDTOImpl doctor = new DoctorDTOImpl();
        model.addAttribute("doctor", doctor);
        return "doctor-info";
    }

    @RequestMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute("doctor") DoctorDTOImpl doctor){
        doctorService.save(doctor);
        return "redirect:/";
    }

    @RequestMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("docId") int id){
        doctorService.delete(id);
        return "redirect:/";
    }


    @RequestMapping("/updateDoctorInfo")
    public String updateDoctorInfo(@RequestParam("docId") int id, Model model){
        EntityDTO doctorDTO = (DoctorDTOImpl) doctorService.get(id);
        model.addAttribute("doctor", doctorDTO);
        return "doctor-info";
    }

  /*  @RequestMapping("/addNewPatient")
    public String addNewPatient(Model model){
        EntityDTO patientDTO = new PatientDTOImpl();
        model.addAttribute("doctor", patientDTO);
        return "patient-info";
    }

    @RequestMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") PatientDTOImpl patientDTO){
        patientService.save(patientDTO);
        return "redirect:/";
    }

    @RequestMapping("/deletePatient")
    public String deletePatient(@RequestParam("patientId") int id){
        patientService.delete(id);
        return "redirect:/";
    }


    @RequestMapping("/updatePatientInfo")
    public String updatePatientInfo(@RequestParam("patientId") int id, Model model){
        EntityDTO patientDTO = (PatientDTOImpl) patientService.get(id);
        model.addAttribute("patient", patientDTO);
        return "patient-info";
    }*/


    @RequestMapping("/addNewTreatment")
    public String addNewTreatment(Model model){
        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        model.addAttribute("treatment", treatmentDTO);
        return "treatment-info";
    }

    @RequestMapping("/saveTreatment")
    public String saveTreatment(@ModelAttribute("treatment") TreatmentDTOImpl treatmentDTO){
        treatmentService.save(treatmentDTO);
        return "redirect:/";
    }

    @RequestMapping("/deleteTreatment")
    public String deleteTreatment(@RequestParam("treatmentId") int id){
        treatmentService.delete(id);
        return "redirect:/";
    }


    @RequestMapping("/updateTreatmentInfo")
    public String updateTreatmentInfo(@RequestParam("treatmentId") int id, Model model){
        TreatmentDTOImpl treatmentDTO = (TreatmentDTOImpl) treatmentService.get(id);
        PatientDTOImpl patientDTO = (((TreatmentServiceImpl)treatmentService).getPatient(id));
        model.addAttribute("treatment", treatmentDTO);
        model.addAttribute("patient", patientDTO);
        return "treatment-info";
    }

}

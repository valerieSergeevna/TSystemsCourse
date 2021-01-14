package com.spring.webapp.controller;

import com.spring.webapp.dao.PatientDAOImpl;
import com.spring.webapp.dao.TreatmentDAOImpl;
import com.spring.webapp.dto.*;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.TreatmentEvent;
import com.spring.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private TreatmentServiceImpl treatmentService;

    @Autowired
    private TreatmentEventServiceImpl treatmentEventService;

    @RequestMapping("/")
    public String showAllDoctors(Model model) {
       /* List<DoctorDTOImpl> allDoctors = doctorService.getAll();

        model.addAttribute("allDocs", allDoctors);
        return "all-doctors";*/
        List<PatientDTOImpl> allPatient = patientService.getAll();

        model.addAttribute("allPatient", allPatient);
        return "all-patient";
    }

    @RequestMapping("/addNewDoctor")
    public String addNewDoctor(Model model) {
        DoctorDTOImpl doctor = new DoctorDTOImpl();
        model.addAttribute("doctor", doctor);
        return "doctor-info";
    }

    @RequestMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute("doctor") DoctorDTOImpl doctor) {
        doctorService.save(doctor);
        return "redirect:/";
    }

    @RequestMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("docId") int id) {
        doctorService.delete(id);
        return "redirect:/";
    }


    @RequestMapping("/updateDoctorInfo")
    public String updateDoctorInfo(@RequestParam("docId") int id, Model model) {
        DoctorDTOImpl doctorDTO = doctorService.get(id);
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

    @RequestMapping("/updatePatientInfo")
    public String updatePatientInfo(@RequestParam("patientId") int id, Model model){
        EntityDTO patientDTO = (PatientDTOImpl) patientService.get(id);
        model.addAttribute("patient", patientDTO);
        return "patient-info";
    }*/

//TODO: MAKE CANCEL FUNCTIONAL

    @RequestMapping("/deletePatient")
    public String deletePatient(@RequestParam("patientId") int id) {
        patientService.deleteTreatments(id);
        patientService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/addNewPatient")
    public String addNewPatient(Model model) {
        // TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        //   PatientDTOImpl patientDTO = patientService.createEmpty();
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        List<TreatmentDTOImpl> treatmentDTO = new ArrayList<>();
        //  treatmentDTO.add(treatmentService.createEmpty(patientDTO));
        patientDTO.setTreatments(treatmentDTO);
        //   model.addAttribute("treatment", treatmentDTO);
        model.addAttribute("patient", patientDTO);
        return "treatment-info";
    }

    @RequestMapping("/addNewTreatment")
    public String addNewTreatment(@RequestParam("patientId") int id, Model model) {
        PatientDTOImpl patientDTO = patientService.get(id);
        List<TreatmentDTOImpl> treatmentDTO = new ArrayList<>();
        treatmentDTO.add(treatmentService.createEmpty(patientDTO));
        patientDTO.setTreatments(treatmentDTO);
        model.addAttribute("patient", patientDTO);
        return "treatment-info";
    }

    @RequestMapping(value = "/saveTreatment", method = RequestMethod.POST)
    public String saveTreatment(@ModelAttribute("patient") PatientDTOImpl patientDTO,
                                HttpServletRequest request) {

        List<TreatmentDTOImpl> treatmentDTOList = new ArrayList<>();
        String[] itemValues = request.getParameterValues("treatment");
        String[] typeValues = request.getParameterValues("treatmentType");
        String[] typeNameValues = request.getParameterValues("treatmentName");
        String[] patternValues = request.getParameterValues("treatmentPattern");
        String[] doseValues = request.getParameterValues("treatmentDose");
        String[] periodValues = request.getParameterValues("treatmentPeriod");

        int treatmentIdCount = 0;
        if (itemValues != null) {
            for (int i = 0; i < itemValues.length; i++) {
                TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl(Integer.parseInt(itemValues[i]),
                        typeValues[i], Integer.parseInt(patternValues[i]), periodValues[i], Double.parseDouble(doseValues[i]));
                treatmentDTO.setTypeName(typeNameValues[i]);
                treatmentDTOList.add(treatmentDTO);
            }
            treatmentIdCount = itemValues.length;
        }

        if (typeValues.length > treatmentIdCount) {
            for (int i = treatmentIdCount; i < typeValues.length; i++) {
                TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
                treatmentDTO.setType(typeValues[i]);
                treatmentDTO.setTypeName(typeNameValues[i]);
                treatmentDTO.setTimePattern(Integer.parseInt(patternValues[i]));
                treatmentDTO.setDose(Double.parseDouble(doseValues[i]));
                treatmentDTO.setPeriod(periodValues[i]);
                treatmentDTOList.add(treatmentDTO);
            }
        }

        patientService.saveOrUpdateTreatments(treatmentDTOList, patientDTO);
        return "redirect:/";
    }

    @RequestMapping("/deleteTreatment") // delete list's item
    public String deleteTreatment(@RequestParam("treatmentId") int id,
                                  Model model) {
        PatientDTOImpl patientDTO = treatmentService.getPatient(id);
        treatmentService.delete(id);
        model.addAttribute("patientId", patientDTO.getId());

        return "redirect:/updateTreatmentInfo";
    }


    @RequestMapping("/updateTreatmentInfo")
    public String updateTreatmentInfo(@RequestParam("patientId") int id, Model model) {
        List<TreatmentDTOImpl> allTreatments = patientService.getTreatments(id);

        PatientDTOImpl patientDTO = patientService.get(id);
        model.addAttribute("patient", patientDTO);
        return "treatment-info";
    }

    @RequestMapping("/nurse/")
    public String showAllEvents(Model model) {
        List<TreatmentEventDTOImpl> treatmentEventDTOList = treatmentEventService.getAll();

        model.addAttribute("allEvents", treatmentEventDTOList);
        return "all-patient";
    }



}

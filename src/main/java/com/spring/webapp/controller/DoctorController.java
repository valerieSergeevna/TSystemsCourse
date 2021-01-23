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

        List<TreatmentEventDTOImpl> treatmentEventDTOList = treatmentEventService.getByPatient(id);
        for (TreatmentEventDTOImpl treatmentEventDTO : treatmentEventDTOList) {
            treatmentEventService.delete(treatmentEventDTO.getId());
        }
        patientService.deleteTreatments(id);
        patientService.delete(id);
        return "redirect:/patients";
    }

    @RequestMapping("/addNewPatient")
    public String addNewPatient(Model model) {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        List<TreatmentDTOImpl> treatmentDTO = new ArrayList<>();
        patientDTO.setTreatments(treatmentDTO);
        model.addAttribute("patient", patientDTO);
        return "doctor/treatment-info";
    }

    @RequestMapping(value = "/saveTreatment", method = RequestMethod.POST)
    public String saveTreatment(@Validated @ModelAttribute("patient") PatientDTOImpl patientDTO,
                                BindingResult bindingResult,
                                HttpServletRequest request,Authentication authentication, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patientDTO);
            return "doctor/treatment-info";
        }
        List<TreatmentDTOImpl> treatmentDTOList = new ArrayList<>();
        String[] itemValues = request.getParameterValues("treatment");
        String[] typeValues = request.getParameterValues("treatmentType");
        String[] typeNameValues = request.getParameterValues("treatmentName");
        String[] patternValues = request.getParameterValues("treatmentPattern");
        String[] doseValues = request.getParameterValues("treatmentDose");
        // String[] periodValues = request.getParameterValues("treatmentPeriod");
        String[] startDate = request.getParameterValues("startDate");
        String[] endDate = request.getParameterValues("endDate");

        int treatmentIdCount = 0;
        if (itemValues != null) {
            for (int i = 0; i < itemValues.length; i++) {
                TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl(Integer.parseInt(itemValues[i]),
                        typeValues[i], Integer.parseInt(patternValues[i]), Double.parseDouble(doseValues[i]));
                treatmentDTO.setTypeName(typeNameValues[i]);
                treatmentDTO.setStartDate(TimeParser.parseToLocalDate(startDate[i]));
                treatmentDTO.setEndDate(TimeParser.parseToLocalDate(endDate[i]));
                treatmentDTOList.add(treatmentDTO);
            }
            treatmentIdCount = itemValues.length;
        }

        if (typeValues != null) {
            if (typeValues.length > treatmentIdCount) {
                for (int i = treatmentIdCount; i < typeValues.length; i++) {
                    TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
                    //DUPLICATE CODE^
                    treatmentDTO.setType(typeValues[i]);
                    treatmentDTO.setTypeName(typeNameValues[i]);
                    treatmentDTO.setTimePattern(Integer.parseInt(patternValues[i]));
                    treatmentDTO.setDose(Double.parseDouble(doseValues[i]));
                    treatmentDTO.setStartDate(TimeParser.parseToLocalDate(startDate[i]));
                    treatmentDTO.setEndDate(TimeParser.parseToLocalDate(endDate[i]));
                    treatmentDTOList.add(treatmentDTO);
                }
            }
        }
        if (patientDTO.getStatus().equals("discharged")) {
            patientService.update(patientDTO);
            for (TreatmentDTOImpl treatmentDTO : treatmentDTOList) {
                treatmentService.delete(treatmentDTO.getTreatmentId());
            }
        } else {
            String doctorName = authentication.getName();
            patientService.saveOrUpdateTreatments(treatmentDTOList, patientDTO, doctorService.getByUserName(doctorName));
        }
        return "redirect:/patients";
    }

    @RequestMapping("/deleteTreatment") // delete list's item
    public String deleteTreatment(@RequestParam("treatmentId") int id,
                                  Model model) {
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

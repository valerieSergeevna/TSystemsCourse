package com.spring.webapp.controller;

import com.spring.webapp.dto.*;
import com.spring.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    public String greet(Model model, Authentication authentication) {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        String role;
        for (int i = 0; i < roles.size(); i++) {
            role = roles.toArray()[i] + "";
            //  logger.info("role verified" + i + " is -> " + role);
            //verify if user contain role to view dashboard page default
            if (role.equals("ROLE_DOCTOR")) {
                //    logger.warn("IDENTIFIED: ROLE_DASHBOARD = " + role );
                //   mav.setViewName("dasboard");
                model.addAttribute("role", "Doctor");
                return "redirect:/patients";
            } else if (role.equals("ROLE_NURSE")) {
                model.addAttribute("role", "Nurse");
                return "redirect:/nurse/";
            }
        }
        //   model.addAttribute("role", authentication.getPrincipal());

        return "greeting";
    }

    @RequestMapping("/patients")
    public String showAllDoctors(Model model, Authentication authentication) {
       // Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
      //  String role;
        List<PatientDTOImpl> allPatient;
      /*  String name = authentication.getName();
        for (int i = 0; i < roles.size(); i++) {
            role = roles.toArray()[i] + "";
            if (role.equals("ROLE_DOCTOR")) {
                allPatient = patientService.getAllByDoctorUserName(name);
                return "redirect:/patients";
            } else if (role.equals("ROLE_NURSE")) {
                model.addAttribute("role", "Nurse");
                return "redirect:/nurse/";
            }
        }*/
        allPatient = patientService.getAll();

        model.addAttribute("allPatient", allPatient);
        return "all-patient";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "general/login";
    }

    @RequestMapping("/addNewDoctor")
    public String addNewDoctor(Model model) {
        DoctorDTOImpl doctor = new DoctorDTOImpl();
        model.addAttribute("doctor", doctor);
        return "doctor/doctor-info";
    }

    @RequestMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute("doctor") DoctorDTOImpl doctor) {
        doctorService.save(doctor);
        return "redirect:/";////!!!
    }

    @RequestMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("docId") int id) {
        doctorService.delete(id);
        return "redirect:/";//////!!!
    }


    @RequestMapping("/updateDoctorInfo")
    public String updateDoctorInfo(@RequestParam("docId") int id, Model model) {
        DoctorDTOImpl doctorDTO = doctorService.get(id);
        model.addAttribute("doctor", doctorDTO);
        return "doctor/doctor-info";
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
        // TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        //   PatientDTOImpl patientDTO = patientService.createEmpty();
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        List<TreatmentDTOImpl> treatmentDTO = new ArrayList<>();
        //  treatmentDTO.add(treatmentService.createEmpty(patientDTO));
        patientDTO.setTreatments(treatmentDTO);
        //   model.addAttribute("treatment", treatmentDTO);
        model.addAttribute("patient", patientDTO);
        return "doctor/treatment-info";
    }

   /* @RequestMapping("/addNewTreatment")
    public String addNewTreatment(@RequestParam("patientId") int id, Model model) {
        PatientDTOImpl patientDTO = patientService.get(id);
        List<TreatmentDTOImpl> treatmentDTO = new ArrayList<>();
        treatmentDTO.add(treatmentService.createEmpty(patientDTO));
        patientDTO.setTreatments(treatmentDTO);
        model.addAttribute("patient", patientDTO);
        return "treatment-info";
    }*/

    @RequestMapping(value = "/saveTreatment", method = RequestMethod.POST)
    public String saveTreatment(@Validated @ModelAttribute("patient") PatientDTOImpl patientDTO,
                                BindingResult bindingResult,
                                HttpServletRequest request, Model model) {
        //костыль

        patientDTO.setAges(request.getParameter("ages").length()==0?0:patientDTO.getAges());
        patientDTO.setInsuranceNumber(request.getParameter("insuranceNumber").length()==0?0:patientDTO.getInsuranceNumber());

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
        if (typeValues != null) {
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
        }
        if (patientDTO.getStatus().equals("discharged")) {
            patientService.update(patientDTO);
            for (TreatmentDTOImpl treatmentDTO : treatmentDTOList) {
                treatmentService.delete(treatmentDTO.getTreatmentId());
            }
        } else {
            patientService.saveOrUpdateTreatments(treatmentDTOList, patientDTO);
        }
        return "redirect:/patients";
    }

    @RequestMapping("/viewPatient")
    public String viewPatient(@RequestParam("patientId") int id, Model model) {
        PatientDTOImpl patientDTO = patientService.get(id);
        model.addAttribute("patient", patientDTO);
        return "patient-view";
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
        return "doctor/treatment-info";
    }

    @RequestMapping("/nurse/showAllTreatments")
    public String showAllEvents(Model model) {
        List<TreatmentEventDTOImpl> allEvents = treatmentEventService.getAll();
        model.addAttribute("allEvents", allEvents);
        return "nurse/all-treatmentEvents";
    }

  /*  @RequestMapping("/nurse/updateEvent")
    public String updateEvent(@ModelAttribute("event") TreatmentEventDTOImpl treatmentEventDTO,
                              HttpServletRequest request,Model model) {
        TreatmentEventDTOImpl treatmentEventDTO1 = model.getAttribute("event");
        String status = request.getParameter("status"+treatmentEventDTO.getId());
        treatmentEventDTO.setStatus(status);
        treatmentEventService.update(treatmentEventDTO);
        return "redirect:/nurse/";
    }*/

    @RequestMapping("nurse/updateStatus")
    public String updateStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status,
                               HttpServletRequest request) {
        // String[] id = request.getParameterValues("eventId");
        //     String[] status = request.getParameterValues("status");
        //     String status = request.getParameter("status"+id);
        TreatmentEventDTOImpl treatmentEventDTO = treatmentEventService.get(id);
        treatmentEventDTO.setStatus(status);
        treatmentEventService.update(treatmentEventDTO);
        //    model.addAttribute("event", treatmentEventDTO);
        return "redirect:/nurse/";
    }

    @RequestMapping("nurse/cancelStatus")
    public String cancelStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status,
                               Model model) {
        // String[] id = request.getParameterValues("eventId");
        //     String[] status = request.getParameterValues("status");
        //     String status = request.getParameter("status"+id);
        TreatmentEventDTOImpl treatmentEventDTO = treatmentEventService.get(id);
        //   treatmentEventDTO.setStatus(status);
        //  treatmentEventService.update(treatmentEventDTO);
        model.addAttribute("cancelEvent", treatmentEventDTO);
        return "nurse/cancel-info";
    }

    @RequestMapping(value = "nurse/setCancelInfo", method = RequestMethod.POST)
    public String setCancelReason(@ModelAttribute("cancelEvent") TreatmentEventDTOImpl treatmentEventDTO) {
        //bad code
        TreatmentEventDTOImpl newTreatmentEventDTO = treatmentEventService.get(treatmentEventDTO.getId());
        newTreatmentEventDTO.setStatus("canceled");
        newTreatmentEventDTO.setCancelReason(treatmentEventDTO.getCancelReason());
        treatmentEventService.update(newTreatmentEventDTO);
        return "redirect:/nurse/";
    }

    @RequestMapping("/nurse/")
    public String showTodayTreatments(Model model) {
        LocalDateTime nowDay = LocalDateTime.now();
        List<TreatmentEventDTOImpl> allEvents = treatmentEventService.getTodayEvents(LocalDateTime.of(nowDay.getYear(), nowDay.getMonth(),
                nowDay.plusDays(2).getDayOfMonth(), 8, 0, 0));
        model.addAttribute("allEvents", allEvents);
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/showNearestHourTreatments")
    public String showNearestHourTreatments(Model model) {
        LocalDateTime nowDay = LocalDateTime.now();
        List<TreatmentEventDTOImpl> allEvents = treatmentEventService.getNearestEvents(LocalDateTime.of(nowDay.getYear(), nowDay.getMonth(),
                nowDay.plusDays(2).getDayOfMonth(), 8, 0, 0));
        model.addAttribute("allEvents", allEvents);
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/findBySurname")
    public String findBySurname(@RequestParam("patientSurname") String surname, Model model, HttpServletRequest request) {
        // String surname = request.getParameter("patientSurname");
        List<PatientDTOImpl> patientDTOList = patientService.getBySurname(surname);
        List<TreatmentEventDTOImpl> allEvents = new ArrayList<>();
        for (PatientDTOImpl patientDTO : patientDTOList) {
            //O(n^2)???????????
            allEvents.addAll(treatmentEventService.getByPatient(patientDTO.getId()));
        }
        model.addAttribute("allEvents", allEvents);
        return "nurse/all-treatmentEvents";
    }

   /* @RequestMapping("nurse/setCancelInfo")
    public String cancelInfo(@ModelAttribute("cancel") TreatmentEventDTOImpl treatmentEventDTO,
                                HttpServletRequest request) {
        String reason = request.getParameter("cancelReason");
        treatmentEventDTO.setCancelReason(reason);
        return "redirect:/nurse/";
    }*/
}

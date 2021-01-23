package com.spring.webapp.controller;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.service.DoctorServiceImpl;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import com.spring.webapp.service.TreatmentServiceImpl;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NurseController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private TreatmentServiceImpl treatmentService;

    @Autowired
    private TreatmentEventServiceImpl treatmentEventService;

    @RequestMapping("/nurse/showAllTreatments")
    public String showAllEvents(Model model) {
        List<TreatmentEventDTOImpl> allEvents = treatmentEventService.getAll();
        model.addAttribute("allEvents", allEvents);
        return "nurse/all-treatmentEvents";
    }


    @RequestMapping("nurse/updateStatus")
    public String updateStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status,
                               HttpServletRequest request) {
            TreatmentEventDTOImpl treatmentEventDTO = treatmentEventService.get(id);
            treatmentEventDTO.setStatus(status);
            treatmentEventService.update(treatmentEventDTO);
            return "redirect:/nurse/";
    }

    @RequestMapping("nurse/cancelStatus")
    public String cancelStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status,
                               Model model) {
            TreatmentEventDTOImpl treatmentEventDTO = treatmentEventService.get(id);
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
    public String findBySurname(@RequestParam("patientSurname") String surname, Model model, HttpServletRequest request) throws DataBaseException {
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

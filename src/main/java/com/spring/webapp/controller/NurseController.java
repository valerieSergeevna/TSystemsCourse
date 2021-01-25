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
import org.springframework.http.HttpRequest;
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
    private TreatmentEventServiceImpl treatmentEventService;

    @RequestMapping("/nurse/showAllTreatments")
    public String showAllEvents(Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.getAll());
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("nurse/updateStatus")
    public String updateStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status,
                               HttpServletRequest request) throws DataBaseException {
        treatmentEventService.updateStatus(id, status);
        return "redirect:/nurse/";
    }

    @RequestMapping("nurse/cancelStatus")
    public String cancelStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status,
                               Model model) throws DataBaseException {
        model.addAttribute("cancelEvent", treatmentEventService.get(id));
        return "nurse/cancel-info";
    }

    @RequestMapping(value = "nurse/setCancelInfo", method = RequestMethod.POST)
    public String setCancelReason(@ModelAttribute("cancelEvent") TreatmentEventDTOImpl treatmentEventDTO) throws DataBaseException {
        treatmentEventService.setCancelReason(treatmentEventDTO);
        return "redirect:/nurse/";
    }

    @RequestMapping("/nurse/")
    public String showTodayTreatments(Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.showTodayTreatments());
        return "nurse/all-treatmentEvents";

    }

    @RequestMapping("/nurse/showNearestHourTreatments")
    public String showNearestHourTreatments(Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.showNearestHourTreatments());
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/findBySurname")
    public String findBySurname(@RequestParam("patientSurname") String surname, Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.findBySurname(surname));
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/findByDate")
    public String findByDate(@RequestParam("date") String date, Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.showTreatmentsByDate(date));
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/getByType")
    public String getByType(@RequestParam("treatmentType") String type, Model model) throws DataBaseException {
        model.addAttribute("allEvents",
                treatmentEventService.getByType(type));
        return "nurse/all-treatmentEvents";
    }
}

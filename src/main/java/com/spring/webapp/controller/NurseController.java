package com.spring.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.exception.ClientException;
import com.spring.exception.DataBaseException;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.ws.Response;
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
        model.addAttribute("currentTime", LocalDateTime.now());
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("nurse/updateStatus")
    public String updateStatus(@RequestParam("eventId") int id,
                               @RequestParam("eventStatus") String status) throws DataBaseException {
        treatmentEventService.updateStatus(id, status);
        return "redirect:/nurse/";
    }

    @RequestMapping("nurse/cancelStatus")
    public String cancelStatus(@RequestParam("eventId") int id,
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
        model.addAttribute("currentTime", LocalDateTime.now());
        return "nurse/all-treatmentEvents";

    }

    @RequestMapping("/nurse/showNearestHourTreatments")
    public String showNearestHourTreatments(Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.showNearestHourTreatments());
        model.addAttribute("currentTime", LocalDateTime.now());
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/findBySurname")
    public String findBySurname(@RequestParam("patientSurname") String surname, Model model) throws DataBaseException {
        model.addAttribute("allEvents", treatmentEventService.findBySurname(surname));
        model.addAttribute("currentTime", LocalDateTime.now());
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/findByDate")
    public String findByDate(@RequestParam("date") String date, Model model) throws DataBaseException, ClientException {
        model.addAttribute("allEvents", treatmentEventService.showTreatmentsByDate(date));
        model.addAttribute("currentTime", LocalDateTime.now());
        return "nurse/all-treatmentEvents";
    }

    @RequestMapping("/nurse/getByType")
    public String getByType(@RequestParam("treatmentType") String type, Model model) throws DataBaseException {
        model.addAttribute("allEvents",
                treatmentEventService.getByType(type));
        model.addAttribute("currentTime", LocalDateTime.now());
        return "nurse/all-treatmentEvents";
    }

    @GetMapping(value = "/todaysTreatmentsEvents", produces = "application/json")
    @ResponseBody
    public String getEventsRest() throws JsonProcessingException, DataBaseException {
        ObjectMapper objectMapper = new ObjectMapper();
 //       objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(treatmentEventService.getAllForTodayRest());
    }

    //rest


//
//    @RequestMapping("nurse/updateAjaxStatus")
//    @ResponseBody
//    public List<TreatmentEventDTOImpl> updateAjaxStatus(@RequestParam("eventId") int id,
//                               @RequestParam("eventStatus") String status) throws DataBaseException {
//        List<TreatmentEventDTOImpl> treatmentEventDTOList;
//        treatmentEventService.updateStatus(id, status);
//        treatmentEventDTOList = treatmentEventService.getAll();
//        return treatmentEventDTOList;
//
//    }
//
//    @RequestMapping("nurse/updateAjaxStatus")
//    @ResponseBody
//    public List<TreatmentEventDTOImpl> ajaxToday() throws DataBaseException {
//        return treatmentEventService.showTodayTreatments();
//    }

}

package com.spring.webapp.controller;

import com.spring.exception.ClientException;
import com.spring.exception.DataBaseException;
import com.spring.exception.MyException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger logger = Logger.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(DataBaseException.class)
    public String handleHibernateException(DataBaseException ex) {
        //Do something additional if required
      /*  ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;*/
        return "/errors/server-error";
    }

//    @ExceptionHandler(Exception.class)
//    public String handleAllException(Exception ex) {
//        //Do something additional if required
//      /*  ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("message", ex.getMessage());
//        return modelAndView;*/
//        logger.error(ex.getMessage());
//        return "/errors/server-error";
//    }

    @ExceptionHandler(ClientException.class)
    public String handleNumberFormatException(ClientException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "/errors/client-error";
    }




  //  @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleResourceNotFoundException() {
        return "/errors/404";
    }



   /* @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String showCustomMessage(){
        return "/errors/400";
    }*/

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String showServerError(Exception ex){
        logger.error(ex.getMessage());
        return "/errors/server-error";
    }

}

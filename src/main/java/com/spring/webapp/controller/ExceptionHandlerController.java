package com.spring.webapp.controller;

import com.spring.exception.ClientException;
import com.spring.exception.DataBaseException;
import com.spring.exception.MyException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(DataBaseException.class)
    public String handleHibernateException(DataBaseException ex) {
        return "/errors/server-error";
    }


    @ExceptionHandler(ClientException.class)
    public String handleNumberFormatException(ClientException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        logger.error(ex.getMessage());
        return "/errors/client-error";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleResourceNotFoundException() {
        return "/errors/404";
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String showServerError(Exception ex){
        System.out.println(ex.getMessage());
        System.out.println(Arrays.toString(ex.getStackTrace()));
        logger.error(ex.getMessage());
        return "/errors/server-error";
    }

}

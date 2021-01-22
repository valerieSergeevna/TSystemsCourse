package com.spring.exception;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Component;


public class DataBaseException extends MyException {
    public DataBaseException(Exception ex){
        super(ex);
    }
}

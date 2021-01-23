package com.spring.exception;

import org.springframework.stereotype.Component;

public class MyException extends Exception {
    public MyException() {
    }
    public MyException(String message) {
        super(message);
    }

    public MyException(Exception e) {
        super(e.getMessage(),e);
    }

    public MyException(String message, Exception e) {
        super(message, e);
    }
}
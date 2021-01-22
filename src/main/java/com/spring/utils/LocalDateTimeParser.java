package com.spring.utils;

import com.spring.exception.ClientException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeParser {
    public static LocalDateTime parse(String date) {
        try {

            ///????
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate ld = LocalDate.parse(date, format);
                return LocalDateTime.of(ld, LocalTime.of(0, 0, 0));

            } catch (DateTimeParseException ex) {
                return LocalDateTime.parse(date);
            }
        }catch (DateTimeParseException ex){
            throw new ClientException("Incorrect input date");
        }
    }
}

package com.spring.utils;

import com.spring.exception.ClientException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeParser {
   /* public static LocalDateTime parseToLocalDateTime(String date) {
        try {

            ///????
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate ld = LocalDate.parse(date, format);
                return LocalDateTime.of(ld, LocalTime.of(0, 0, 0));

            } catch (DateTimeParseException ex) {
                return LocalDateTime.parse(date);
            }
        } catch (DateTimeParseException ex) {
            throw new ClientException("Incorrect input date");
        }
    }*/

    public static LocalDate parseToLocalDate(String date) {
      //  try {
            return LocalDate.parse(date);
//        } catch (DateTimeParseException ex) {
//            //log
//            throw new ClientException("Incorrect input date");
//        }
    }

    public static LocalDateTime fromLocalDateToLocalDateTime(LocalDate date) {
        return LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), 0, 0, 0);
    }

    public static LocalDate fromLocalDateTimeToLocalDate(LocalDateTime date) {
        return LocalDate.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth());
    }
}

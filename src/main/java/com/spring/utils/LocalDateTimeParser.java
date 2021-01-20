package com.spring.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
    public static LocalDateTime parse(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse(date, format);
        LocalDateTime dateTime = LocalDateTime.of(ld, LocalTime.of(0,0,0));
      //  LocalDateTime dateTime = LocalDateTime.parse(date, format);
       // return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(),
         //       dateTime.getDayOfMonth(), 0, 0, 0);
        return dateTime;
    }
}

package com.motel.motel.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DateTimeFormatService {

    public static final String MATCH_PATTERN = " ";
    public static final String MATCH_PATTERN_DATE = "/";
    public static final String MATCH_PATTERN_TIME = ":";

    public static LocalDateTime toLocalDateTime(String localDateTime){

        if(!isValidDateTime(localDateTime)) return null;

        return LocalDateTime.of(
                Integer.parseInt(localDateTime.split(MATCH_PATTERN)[0].split(MATCH_PATTERN_DATE)[2]),
                Integer.parseInt(localDateTime.split(MATCH_PATTERN)[0].split(MATCH_PATTERN_DATE)[1]),
                Integer.parseInt(localDateTime.split(MATCH_PATTERN)[0].split(MATCH_PATTERN_DATE)[0]),
                Integer.parseInt(localDateTime.split(MATCH_PATTERN)[1].split(MATCH_PATTERN_TIME)[0]),
                Integer.parseInt(localDateTime.split(MATCH_PATTERN)[1].split(MATCH_PATTERN_TIME)[1]),
                Integer.parseInt(localDateTime.split(MATCH_PATTERN)[1].split(MATCH_PATTERN_TIME)[2])
        );
    }

    private static boolean isValidDateTime(String localDateTime) {
        return isValidDate(extractDate(localDateTime)) &&
                isValidTime(extractTime(localDateTime));
    }

    private static boolean isValidTime(String time) {
        try{
            LocalTime.of(Integer.parseInt(time.split(MATCH_PATTERN_TIME)[0]),
                    Integer.parseInt(time.split(MATCH_PATTERN_TIME)[1]),
                    Integer.parseInt(time.split(MATCH_PATTERN_TIME)[2]));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private static boolean isValidDate(String date) {
        try{
            LocalDate.of(Integer.parseInt(date.split(MATCH_PATTERN_DATE)[2]),
                    Integer.parseInt(date.split(MATCH_PATTERN_DATE)[1]),
                    Integer.parseInt(date.split(MATCH_PATTERN_DATE)[0]));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private static String extractTime(String localDateTime) {
        return localDateTime.split(MATCH_PATTERN)[1];
    }

    private static String extractDate(String localDateTime) {
        return localDateTime.split(MATCH_PATTERN)[0];
    }


    public static String toDateTimeString(LocalDateTime dateTime){

        if(dateTime == null) return "";

        return String.format("%s/%s/%s %s:%s:%s",
                dateTime.getDayOfMonth() < 10 ? "0"+dateTime.getDayOfMonth() : dateTime.getDayOfMonth(),
                dateTime.getMonthValue() < 10 ? "0"+dateTime.getMonthValue() : dateTime.getMonthValue(),
                dateTime.getYear(),
                dateTime.getHour() < 10 ? "0"+dateTime.getHour() : dateTime.getHour(),
                dateTime.getMinute() < 10 ? "0"+dateTime.getMinute() : dateTime.getMinute(),
                dateTime.getSecond() < 10 ? "0"+dateTime.getSecond() : dateTime.getSecond());
    }


}

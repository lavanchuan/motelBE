package com.motel.motel.controllers.demo;

import com.motel.motel.services.DateTimeFormatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateTimeRequest {
    private String dateTime;
    private LocalDate date;

    public String getDateTime() {
        return DateTimeFormatService.toDateTimeString(DateTimeFormatService.toLocalDateTime(dateTime));
    }
}

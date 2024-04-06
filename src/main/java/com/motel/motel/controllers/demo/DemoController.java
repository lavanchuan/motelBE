package com.motel.motel.controllers.demo;

import com.motel.motel.controllers.demo.DateTimeRequest;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController("/demo")
public class DemoController {

    @Autowired
    DateTimeFormatService dateTimeFormatService;

    @Data
    static class Interval{
        private String name;
        private LocalDateTime meetTime;
        private LocalDateTime daoTime;

        public void setMeetTime(String meetTime) {
            this.meetTime = DateTimeFormatService.toLocalDateTime(meetTime);
        }

        public void setMeetTime(LocalDateTime meetTime) {
            this.meetTime = meetTime;
        }

        public void setDaoTime(String daoTime) {
            this.daoTime = DateTimeFormatService.toLocalDateTime(daoTime);
        }

        public void setDaoTime(LocalDateTime daoTime) {
            this.daoTime = daoTime;
        }

        public LocalDateTime getMeetTime() {
            return meetTime;
        }

        public LocalDateTime getDaoTime() {
            return daoTime;
        }
    }
    static final int INTERVAL_TIME_APPOINT_MINUTES = 60;

    private boolean isValidMeetTime(LocalDateTime timeDTO, LocalDateTime timeDAO) {
        return timeDAO.isBefore(timeDTO.plusMinutes(-1 * INTERVAL_TIME_APPOINT_MINUTES))
                || timeDAO.isAfter(timeDTO.plusMinutes(INTERVAL_TIME_APPOINT_MINUTES));
    }

    @GetMapping("/valid-meet-time")
    public ResponseEntity<?> isValidMeetTime(@RequestBody Interval interval){
        return ResponseEntity.ok(isValidMeetTime(interval.getMeetTime() , interval.getDaoTime()));
    }

    @GetMapping("/to-datetime")
    public ResponseEntity<?> toDateTime(@RequestBody DateTimeRequest request){
        return ResponseEntity.ok(
                request
        );
    }

    @GetMapping("/to-datetime-string")
    public ResponseEntity<?> toDateTimeString(){
        return ResponseEntity.ok(dateTimeFormatService.toDateTimeString(LocalDateTime.now()));
    }

    @GetMapping("/account")
    public ResponseEntity<?> account(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountDTO);
    }

}

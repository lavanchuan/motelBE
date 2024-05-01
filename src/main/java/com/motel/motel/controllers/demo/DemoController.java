package com.motel.motel.controllers.demo;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.controllers.demo.DateTimeRequest;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.entities.ImageDAO;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController("/demo")
public class DemoController {

    @Autowired
    DateTimeFormatService dateTimeFormatService;

    @Autowired
    DbContext dbContext;

    @Data
    static class Interval {
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
    public ResponseEntity<?> isValidMeetTime(@RequestBody Interval interval) {
        return ResponseEntity.ok(isValidMeetTime(interval.getMeetTime(), interval.getDaoTime()));
    }

    @GetMapping("/to-datetime")
    public ResponseEntity<?> toDateTime(@RequestBody DateTimeRequest request) {
        return ResponseEntity.ok(
                request
        );
    }

    @GetMapping("/to-datetime-string")
    public ResponseEntity<?> toDateTimeString() {
        return ResponseEntity.ok(dateTimeFormatService.toDateTimeString(LocalDateTime.now()));
    }

    @GetMapping("/account")
    public ResponseEntity<?> account(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountDTO);
    }

    //TODO load image
    int[] motels = {1, 2};
    int[][] motelRooms = {{1, 2, 3, 4, 5}, {1, 2, 3, 4}};
    int[][] idRooms = {{1, 2, 6, 10, 16}, {3, 4, 5, 14}};
    int[][] countImages = {{2, 12, 4, 6, 0}, {9, 3, 0, 0}};

    @PostMapping("/load-room-images")
    public ResponseEntity<?> loadImages() {
        String url;
        String res = "";
        for (int mIdx = 0; mIdx < motels.length; mIdx++) {
            for (int rIdx = 0; rIdx < motelRooms[mIdx].length; rIdx++) {
                for (int i = 1; i <= countImages[mIdx][rIdx]; i++) {
                    url = numFormat(motels[mIdx]) + "-" +
                            numFormat(motelRooms[mIdx][rIdx]) + "-" +
                            (i > 9 ? i : "0" + i);

                    System.out.println(url);
                    res += url + "\n";

                    saveImage(url, idRooms[mIdx][rIdx]);
                }
            }
        }
        return ResponseEntity.ok(res);
    }

    private void saveImage(String url, int roomId) {
        ImageDAO dao = new ImageDAO();
        dao.setUrl(url);
        dao.setMotelRoomDAO(dbContext.motelRoomRepository.findById(roomId).orElseThrow());
        dbContext.imageRepository.save(dao);
    }

    private String numFormat(int n) {
        return "" + (n > 99 ? n : (n > 9 ? "0" + n : "00" + n));
    }
}

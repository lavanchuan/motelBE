package com.motel.motel.models.response;

import com.motel.motel.models.dtos.*;
import lombok.Data;

public class ObjResponse {

    @Data
    public static class AppointDetail{
        private MakeAppointDTO appoint;
        private AccountDTO user;
        private AccountDTO owner;
        private MotelRoomDTO room;
        private MotelDTO motel;
    }

    @Data
    public static class BookingDetail{
        private BookRoomDTO booking;
        private AccountDTO user;
        private AccountDTO owner;
        private MotelRoomDTO room;
        private MotelDTO motel;
    }

    @Data
    public static class CountMotelActive{
        private int countMotelActivate;
        private int count;

        public CountMotelActive(int countMotelActivate, int count) {
            this.countMotelActivate = countMotelActivate;
            this.count = count;
        }

        public CountMotelActive() {
            this.count = 0;
            this.countMotelActivate = 0;
        }
    }

    @Data
    public static class MotelOwnerDetail{
        private MotelDTO motel;
        private AccountDTO owner;
    }
}

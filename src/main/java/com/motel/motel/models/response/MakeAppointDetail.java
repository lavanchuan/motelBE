package com.motel.motel.models.response;

import com.motel.motel.models.dtos.MakeAppointDTO;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.dtos.MotelRoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeAppointDetail {
    MakeAppointDTO makeAppoint;
    MotelRoomDTO room;
    MotelDTO motel;
}

package com.motel.motel.models.dtos;

import com.motel.motel.models.e.MotelStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MotelDTO {
    private int id;
    private LocalDate createAt;
    private String name;
    private String address;
    private String electric_price;
    private String water_price;
    private MotelStatus status;
    private int ownerId;

}

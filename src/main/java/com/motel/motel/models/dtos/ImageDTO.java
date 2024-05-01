package com.motel.motel.models.dtos;

import lombok.Data;

@Data
public class ImageDTO {
    private int id;
    private int motelRoomId;
    private String url;
}

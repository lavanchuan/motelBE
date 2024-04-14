package com.motel.motel.models.response;

import com.motel.motel.models.dtos.ReviewDTO;
import lombok.Data;

import java.util.List;

public class ReviewResponse extends BaseResponse<List<ReviewDTO>>{
    public ReviewResponse(int status) {
        super(status);
    }

    public ReviewResponse(List<ReviewDTO> reviewDTOS) {
        super(reviewDTOS);
    }
}

package com.motel.motel.models.response;

import lombok.Data;

@Data
public abstract class BaseResponse<DATA> {

    public static final int SUCCESS = 200;
    public static final int ERROR = 400;
    public static final int CONFLICT = 409;

    private int status;
    private DATA data;

    public BaseResponse(int status){
        this.status = status;
        this.data = null;
    }

    public BaseResponse(DATA data){
        this.status = SUCCESS;
        this.data = data;
    }
}

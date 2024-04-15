package com.motel.motel.models.response;

public class OtherResponse<DATA> extends BaseResponse<DATA>{
    public OtherResponse(int status) {
        super(status);
    }

    public OtherResponse(DATA data) {
        super(data);
    }
}

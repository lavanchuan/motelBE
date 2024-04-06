package com.motel.motel.models.response;

import com.motel.motel.models.dtos.AccountDTO;

public class AccountResponse extends BaseResponse<AccountDTO>{
    public AccountResponse(int status) {
        super(status);
    }

    public AccountResponse(AccountDTO accountDTO) {
        super(accountDTO);
    }
}

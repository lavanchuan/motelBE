package com.motel.motel.models.dtos;

import com.motel.motel.services.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String to;
    private String subject;
    private String content;
    private String from = MailSenderService.FROM;
    private String personal = MailSenderService.PERSONAL;
}

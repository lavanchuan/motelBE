package com.motel.motel.services;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MailDTO;
import com.motel.motel.models.entities.AccountDAO;
import com.motel.motel.services.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppSystemService {

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    AccountServiceImpl accountService;

    public void sendMailRegisOwner(String userEmail){
        MailDTO mailDTO = new MailDTO();
        mailDTO.setSubject("Regis owner");

        AccountDTO dto = accountService.findByMail(userEmail);
        String birthday = dto.getDateOfBirth().getDayOfMonth() + "/" +
                dto.getDateOfBirth().getMonthValue() + "/" +
                dto.getDateOfBirth().getYear();

        mailDTO.setContent(String.format("Regis owner form %s\n" +
                "Name: %s\n" +
                "Phone: %s\n" +
                "Address: %s\n" +
                "Birthday: %s\n", userEmail, dto.getName(), dto.getPhone(), dto.getAddress(), birthday));

        accountService.findAllAdmin().forEach(admin -> {
            mailDTO.setTo(admin.getMail());
            mailSenderService.sendMail(mailDTO);
        });
    }

    public void sendMailOwnerForBookRoom(){}

    public void sendMailOwnerForBookAppoint(){}
}

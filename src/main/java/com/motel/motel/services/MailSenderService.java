package com.motel.motel.services;

import com.motel.motel.models.dtos.MailDTO;
import com.motel.motel.models.response.BaseResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailSenderService {
    @Autowired
    JavaMailSender mailSender;

    public static final String FROM = "motel@account.com";
    public static final String PERSONAL = "Free Motel Support";

    private MimeMessage createMimeMessage(MailDTO mailDTO) throws
            UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(mailDTO.getFrom(), mailDTO.getPersonal());
        mimeMessageHelper.setTo(mailDTO.getTo());
        mimeMessageHelper.setSubject(mailDTO.getSubject());
        mimeMessageHelper.setText(mailDTO.getContent());

        return mimeMessage;
    }

    public boolean sendMail(MailDTO mailDTO){
        try{
            mailSender.send(createMimeMessage(mailDTO));
        } catch (MessagingException e){
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static class MailSenderResponse extends BaseResponse<String> {

        public MailSenderResponse(int status) {
            super(status);
        }

        public MailSenderResponse(String s) {
            super(s);
        }
    }
}

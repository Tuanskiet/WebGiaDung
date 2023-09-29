package com.poly.WebGiaDung.service;


import com.poly.WebGiaDung.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Locale;

@Service
public class SendEmailService {

    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    public TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    String FORM_EMAIL;
    final String SUBJECT = "Yasuki - Đơn hàng của bạn đã được xác nhận!";
    public static final String BODY_HTML = "ss";
    final String BODY_TEXT = "";

    public void sendMailHtml(String subject, String toEmail, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom( new InternetAddress(FORM_EMAIL));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, false));
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(body, "text/html; charset=utf-8");

        javaMailSender.send(mimeMessage);
    }

    public void sendMail(String subject, String toEmail, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        javaMailSender.send(mimeMessage);
    }

    public void sendMailWithInline(final String recipientEmail,final OrderDto orderDto)
            throws MessagingException {
        //total payment
//        BigDecimal totalPayment = orderDto.getCartDtoList().stream()
//                .map(CartDto::get)
//                .reduce(BigDecimal.ZERO,BigDecimal::add);

        // Prepare the evaluation context
        Locale locale = new Locale("vi-VN");
        final Context ctx = new Context();
        ctx.setVariable("dateOrder", new Date());
        ctx.setVariable("orderDto", orderDto);
//        ctx.setVariable("totalPayment", totalPayment);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true = multipart
        message.setSubject(SUBJECT);
        message.setFrom(FORM_EMAIL);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("mail_order.html", ctx);
        message.setText(htmlContent, true); // true = isHtml

        // Send mail
        this.javaMailSender.send(mimeMessage);

    }


}

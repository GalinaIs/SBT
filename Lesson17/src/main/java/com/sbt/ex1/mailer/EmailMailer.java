package com.sbt.ex1.mailer;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailMailer implements Mailer {
    @Override
    public void send(String sender, String recipient, String message) throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // we're going to use google mail to send this message
        mailSender.setHost(sender);
        // construct the message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(recipient);

        // setting message text, last parameter 'true' says that it is HTML format
        helper.setText(message, true);
        helper.setSubject("Monthly department salary report");
        // send the message
        mailSender.send(mimeMessage);
    }
}

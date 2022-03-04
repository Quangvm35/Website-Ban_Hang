package com.poly.SOF3021.service;

import javax.mail.MessagingException;

import com.poly.SOF3021.model.Mail;

public interface SuportService  {
    void sendEmail(Mail mail) throws MessagingException;
}

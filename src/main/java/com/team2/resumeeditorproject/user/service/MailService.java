package com.team2.resumeeditorproject.user.service;

public interface MailService {
    boolean checkAuthNum(String email,String authCode);
    String createUuid();
    void sendEmail(String email);
    void sendEmailEnd(String setFrom, String toMail, String title, String content);
}

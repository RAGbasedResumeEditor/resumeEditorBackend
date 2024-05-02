package com.team2.resumeeditorproject.user.service;

public interface MailService {

    boolean CheckAuthNum(String email,String authNum);

    int makeRandomNumber();

    String joinEmail(String email);

    void mailSend(String setFrom, String toMail, String title, String content);

}

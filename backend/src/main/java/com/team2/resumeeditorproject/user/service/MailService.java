package com.team2.resumeeditorproject.user.service;

public interface MailService {

    boolean CheckAuthNum(String email,String authNum);
<<<<<<< HEAD
    String createUuid();
=======
    //int makeRandomNumber();
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
    String joinEmail(String email);
    void mailSend(String setFrom, String toMail, String title, String content);

}

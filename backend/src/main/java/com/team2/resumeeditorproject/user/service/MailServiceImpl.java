package com.team2.resumeeditorproject.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{ // 인증코드를 생성하고 이메일을 보내는 서비스

    private final JavaMailSender mailSender; // 메일을 보내기 위한 인터페이스
    private final RedisComponent redisComp;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private String toEmail;
    private static String AUTHNUM;

    @Override
    public boolean checkAuthNum(String email,String authnum){
        if(redisComp.getValues(email)==null){ // redisUtill에 저장된 인증코드가 없다면 false를 반환
            return false;
        }else if(redisComp.getValues(email).equals(authnum)){ // redisUtill에 저장한 인증코드와 일치하면 true를 반환
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String createUuid(){
        UUID temp = UUID.randomUUID();
        String strTemp = temp.toString();
        return strTemp.substring(0,11);
    }

    @Override
    public void sendEmail(String email) { //메일을 어디서 어디로 보내고 인증 번호를 어떤 html 형식으로 보내는지 작성한다.
        AUTHNUM=createUuid(); // 인증 코드 생성
        toEmail = email; // 인증번호 받을 이메일 주소
        String title = "[Resume Editor] verify your email"; // 이메일 제목
        String content =                                    //html 형식으로 작성
                        "<h5>본 이메일 인증은 Resume Editor 회원가입을 위한 필수사항입니다.<br>" +
                        "아래 인증 코드를 입력하여 홈페이지에서 남은 회원가입 절차를 완료하여 주시기 바랍니다.</h5>"+
                        "<h3 style='color:green'><b>" + AUTHNUM + "</b></h3>" +
                        "<h6>인증 코드는 5분간 유효합니다.</h6>";
        sendEmailEnd(fromEmail, toEmail, title, content); // 인증 코드를 담은 메일 전송
    }

    @Override
    public void sendEmailEnd(String fromM, String toM, String title, String content) { //이메일을 전송하는 메서드
        if(redisComp.existData(toM)){ // key에 해당하는 value값 존재 확인. Redis에 해당 수신 메일이 있다면 삭제
            redisComp.deleteData((toM)); // key에 해당하는 value값 삭제.
        }
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용해 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정
                                                // true를 전달해 multipart 형식의 메시지를 지원, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(fromM);// 송신자 주소 설정
            helper.setTo(toM);// 수신자 주소 설정
            helper.setSubject(title);//이메일 제목 설정
            helper.setText(content,true);//이메일의 내용 설정. 두 번째 매개 변수에 true를 전달해 html 설정.
            mailSender.send(message); // 인증 메일 전송
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redisComp.setDataExpire(toM, AUTHNUM,60*5L); // Redis에 저장 (5분간 유효)
    }
}

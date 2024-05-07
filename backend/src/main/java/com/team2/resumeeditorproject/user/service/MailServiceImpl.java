package com.team2.resumeeditorproject.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{ // 인증번호를 생성하고 이메일을 보내는 서비스

    private final JavaMailSender mailSender; // 메일을 보내기 위한 인터페이스
    private final RedisComponent redisComp;
    @Value("${spring.mail.username}")
    private String fromMail; // 송신할 이메일 주소
    private static String AUTHNUM;

    public boolean CheckAuthNum(String email,String authNum){
        if(redisComp.getValues(authNum)==null){ // redisUtill에 저장된 인증번호가 없다면 false를 반환
            return false;
        }else if(redisComp.getValues(authNum).equals(email)){ // redisUtill에 저장한 값과 일치하면 true를 반환
            return true;
        }else{
            return false;
        }
    }

    public String createUuid(){
        UUID temp = UUID.randomUUID();
        String strTemp = temp.toString();
        return strTemp.substring(0,11);
    }

    public String joinEmail(String email) { //메일을 어디서 어디로 보내고 인증 번호를 어떤 html 형식으로 보내는지 작성한다.
        String num = createUuid(); // 난수 생성
        String setFrom = fromMail; // MailConfig에 설정한 이메일 주소
        String toMail = email; // 인증번호 받을 이메일 주소
        String title = "Resume Editor 회원 가입 인증 이메일입니다."; // 이메일 제목
        String content ="<h3>Resume Editor를 방문해주셔서 감사합니다.</h3>" + //html 형식으로 작성
                        "<hr><br>" +
                        "이메일 인증을 위한 인증 코드를 발급했습니다.<br>" +
                        "아래 인증 코드를 입력하여 회원가입을 완료해주세요."+
                        "<h3><b>" + num + "</b></h3>" +
                        "<h6>인증 코드는 5분간 유효합니다.</h6>";
        mailSend(setFrom, toMail, title, content);
        return num;
    }

    public void mailSend(String setFrom, String toMail, String title, String content) {//이메일을 전송하는 메서드
        if(redisComp.existData(toMail)){ //Redis에 해당 수신 메일이 있다면 삭제
            redisComp.deleteData((toMail));
        }

        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용해 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정
                                                // true를 전달해 multipart 형식의 메시지를 지원, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);// 송신자 주소 설정
            helper.setTo(toMail);// 수신자 주소 설정
            helper.setSubject(title);//이메일 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정. 두 번째 매개 변수에 true를 전달해 html 설정.
            mailSender.send(message); // 인증 메일 전송
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류 발생 => 이러한 경우 MessagingException 발생
            e.printStackTrace();
        }
        redisComp.setDataExpire(AUTHNUM,toMail,60*5L); // Redis에 저장 (5분간 유효)
    }
}

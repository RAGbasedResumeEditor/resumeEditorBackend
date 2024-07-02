package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.Verification;
import com.team2.resumeeditorproject.user.repository.VerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService { // 인증코드를 생성하고 이메일을 보내는 서비스

    private final VerificationRepository verificationRepository;
    private final JavaMailSender mailSender; // 메일을 보내기 위한 인터페이스
    @Value("${spring.mail.username}")
    private String fromEmail;
    private String toEmail;
    private static String AUTHNUM;

    @Override
    public boolean checkAuthNum(String email,String authCode) {
        Verification verification = verificationRepository.findByEmail(email);
        if (verification != null && verification.getCode().equals(authCode) && new Date().before(verification.getExpiresAt())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String createUuid() {
        UUID temp = UUID.randomUUID();
        String strTemp = temp.toString();
        return strTemp.substring(0,11);
    }

    @Override
    public void sendEmail(String email) { //메일을 어디서 어디로 보내고 인증 번호를 어떤 html 형식으로 보내는지 작성한다.
        AUTHNUM = createUuid(); // 인증 코드 생성
        toEmail = email; // 인증번호 받을 이메일 주소
        String title = "[Reditor] verify your email"; // 이메일 제목
        String content =                                    //html 형식으로 작성
                        "<h5>본 이메일 인증은 Reditor 회원가입을 위한 필수사항입니다.<br>" +
                        "아래 인증 코드를 입력하여 홈페이지에서 남은 회원가입 절차를 완료하여 주시기 바랍니다.</h5>"+
                        "<h3 style='color:green'><b>" + AUTHNUM + "</b></h3>" +
                        "<h6>인증 코드는 5분간 유효합니다.</h6>";
        sendEmailEnd(fromEmail, toEmail, title, content); // 인증 코드를 담은 메일 전송
    }

    @Override
    public void sendEmailEnd(String fromM, String toM, String title, String content) { //이메일을 전송하는 메서드
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
        // 현재 시간을 가져옵니다.
        Date currentTime = new Date();
        // Calendar 인스턴스를 생성하고 현재 시간을 설정합니다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        // 5분을 더합니다.
        calendar.add(Calendar.MINUTE, 5);
        // 5분 후의 시간을 가져옵니다.
        Date futureTime = calendar.getTime();

        if (verificationRepository.findByEmail(toM)!=null) {
            Verification verification= verificationRepository.findByEmail(toM);
            verification.setCode(AUTHNUM);
            verification.setCreatedAt(currentTime);
            verification.setExpiresAt(futureTime);
            verificationRepository.save(verification);
            return;
        }

        Verification verification=Verification.builder()
                .email(toM)
                .code(AUTHNUM)
                .createdAt(currentTime)
                .expiresAt(futureTime)
                .build();
        verificationRepository.save(verification);
    }
}

package com.test.utill;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Utill {


	
	@Autowired
	private JavaMailSender sender;
	
	
	public  String generateUniqueFileName() {
		String filename = "";

		long millis = System.currentTimeMillis();
		String DATE_FORMAT = "yyyyMMdd_HHmmss_SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(new Date());
		System.out.println("date " + date);
		String num = String.valueOf(new Random().nextInt(9999));
		filename = date + "_" + millis + "_" + num;
		return filename;

	}
	
	public String generateToken() {
		return UUID.randomUUID().toString();
	}
	
	
	public void sendMail( String email ,String subject ,String body) {
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText( body);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            e.printStackTrace();
          
        }
        sender.send(message);
		
	}
}

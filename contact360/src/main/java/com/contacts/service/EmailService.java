package com.contacts.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String to, String from, String subject, String message) {

		boolean flag = false;
		// Gmail Host
		String host = "smtp.gmail.com";

		// Get System Property
		Properties prop = System.getProperties();
		System.out.println("Properties: " + prop);

		// Store Information in properties object

		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.auth", "true");

		// Step 1: Get Session

		Session session = Session.getInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(from, "emailkey");
			}

		});

		session.setDebug(true);

		// Step 2: Compose the message
		MimeMessage mime = new MimeMessage(session);

		try {

			// From Email Address
			mime.setFrom(from);

			// To Email Address
			mime.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Adding Subject
			mime.setSubject(subject);

			// Adding body text
//			mime.setText(message);
			mime.setContent(message, "text/html");

			// Step 3: Send the message via Transport

			Transport.send(mime);
			flag = true;
			System.out.println("Email Sent...");
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}

		return flag;

	}

}

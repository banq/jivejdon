/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.spi.component.email;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.naming.InitialContext;

import com.google.common.eventbus.Subscribe;
import com.jdon.container.pico.Startable;

public class EmailSender implements Startable {
	public final static String NOHTML_FORMAT = "text/plain";
	public final static String HTML_FORMAT = "text/html";

	// Session used by the javamail classes
	private Session session;
	private String JAVAMAIL_JNDINAME;

	public EmailSender(String JAVAMAIL_JNDINAME) {
		this.JAVAMAIL_JNDINAME = JAVAMAIL_JNDINAME;
	}

	private void init() {
		// jboss JAVAMAIL_JNDINAME in server/default/deploy/mail-service.xml
		// such as : java:/Mail
		try {
			InitialContext ic = new InitialContext();
			session = (Session) ic.lookup(JAVAMAIL_JNDINAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	public void send(EmailVO emailVO) {
		try {
			Message message = createMessage(emailVO);
			if (message != null)
				Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * Factory method to add a message by specifying its fields.
	 * <p>
	 * 
	 * To use more advanced message features, use the
	 * <code>addMessage(Message message)</code> method.
	 * <p>
	 * 
	 * If parts of the message are invalid (ie, the toEmail is null) the message
	 * won't be sent.
	 * 

	 */
	public Message createMessage(EmailVO emailVO) {
		// Check for errors in the given fields:
		if (emailVO.isEmpty()) {
			System.err.println("Error sending email in EmailTask.java: " + "Invalid fields.");
			return null;
		}
		if (session == null) {
			System.err.println("session is null no jndi");
			return null;

		}
		try {
			MimeMessage message = new MimeMessage(session);
			Address to = null;
			Address from = null;
			if (emailVO.getToEmail() != null) {
				if (emailVO.getToName() != null) {
					to = new InternetAddress(emailVO.getToEmail(), emailVO.getToName());
				} else {
					to = new InternetAddress(emailVO.getToEmail());
				}
			}
			// formEmail configured in context.xml
			if (emailVO.getFromEmail() != null && emailVO.getFromEmail().length() != 0) {
				if (emailVO.getFromName() != null) {
					from = new InternetAddress(emailVO.getFromEmail(), emailVO.getFromName());
				} else {
					from = new InternetAddress(emailVO.getFromEmail());
				}
			}
			message.setRecipient(Message.RecipientType.TO, to);
			message.setReplyTo(new Address[] { from });
			;
			message.setSubject(MimeUtility.encodeText(emailVO.getSubject(), "utf-8", "B"));

			Multipart mp = new MimeMultipart();
			BodyPart bp = new MimeBodyPart();
			bp.setHeader("Content-Type", "text/html;charset=utf-8");
			bp.setContent(emailVO.getBody(), "text/html;charset=utf-8");
			mp.addBodyPart(bp);
			message.setContent(mp);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private class SMTPAuthenticator extends Authenticator {
		private String SMTP_AUTH_USER;
		private String SMTP_AUTH_PWD;

		private SMTPAuthenticator(String SMTP_AUTH_USER, String SMTP_AUTH_PWD) {
			this.SMTP_AUTH_USER = SMTP_AUTH_USER;
			this.SMTP_AUTH_PWD = SMTP_AUTH_PWD;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		}
	}

	@Override
	public void start() {
		init();

	}

	@Override
	public void stop() {
		session = null;

	}

}

package com.jdon.jivejdon.util;

import com.jdon.jivejdon.component.email.EmailHelper;

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * A task to send email.
 * 
 * @see EmailHelper
 */
public class EmailTask extends Thread {
	public final static String NOHTML_FORMAT = "text/plain";
	public final static String HTML_FORMAT = "text/html";

	// Session used by the javamail classes
	private Session session;
	private String JAVAMAIL_JNDINAME;
	// List of messages
	private List messages = null;

	public EmailTask(String JAVAMAIL_JNDINAME) {
		this();
		this.JAVAMAIL_JNDINAME = JAVAMAIL_JNDINAME;
	}

	/**
	 * Reads mail properties creates a JavaMail session that will be used to
	 * send all mail.
	 */
	public EmailTask(String host, String port, String debug, String user, String password, String from) {
		this();
		Properties mailProps = new Properties();
		if (host != null) {
			mailProps.setProperty("mail.smtp.host", host);
		}
		// check the port for errors (if specified)
		if (port != null && !port.equals("")) {
			try {
				// no errors at this point, so add the port as a property
				mailProps.setProperty("mail.smtp.port", port);
			} catch (Exception e) {
			}
		}
		// optional mail debug (output is written to standard out)
		if ("true".equals(debug)) {
			mailProps.setProperty("mail.debug", "true");
		}
		mailProps.put("mail.smtp.auth", "true");
		mailProps.put("mail.smtp.from", from);

		// Create the mail session
		Authenticator auth = new SMTPAuthenticator(user, password);
		session = Session.getDefaultInstance(mailProps, auth);
	}

	/**
	 * Creates a new EmailTask.
	 */
	public EmailTask() {
		messages = new LinkedList();
	}

	public static void main(String[] args) {
		EmailTask emailTask = new EmailTask("smtp.163.net", "25", "true", "banq@163.net", "XX",
				"banq@163.net");

		emailTask.addMessage("banq", "banq@163.com", "banq", "banq@sina.com", "水水水 ", "pp【4楼 " +
				"水水水】:ss... www.sina.com", EmailTask.HTML_FORMAT);

		Thread thread = new Thread(emailTask);
		thread.start();

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

	/**
	 * Runs the task, which sends all email messages that have been queued.
	 */
	public void run() {
		try {
			Iterator messageIterator = messages.iterator();
			while (messageIterator.hasNext()) {

				try {
					Thread.sleep(6000);
					Message message = (Message) messageIterator.next();
					Transport.send(message);
				} catch (Exception me) {
					me.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.messages.clear();
		}
	}

	/**
	 * Factory method to add a JavaMail message object to the internal list of
	 * messages.
	 *
	 * @param message
	 *            a message to send.
	 */
	public void addMessage(Message message) {
		messages.add(message);
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
	 * @param toName
	 *            the name of the recipient of this email.
	 * @param toEmail
	 *            the email address of the recipient of this email.
	 * @param fromName
	 *            the name of the sender of this email.
	 * @param fromEmail
	 *            the email address of the sender of this email.
	 * @param subject
	 *            the subject of the email.
	 * @param body
	 *            the body of the email.
	 * @param format
	 *            text/html or text/plain
	 */
	public void addMessage(String toName, String toEmail, String fromName, String fromEmail, String subject, String body, String format) {
		// Check for errors in the given fields:
		if (toEmail == null || fromEmail == null || subject == null || body == null) {
			System.err.println("Error sending email in EmailTask.java: " + "Invalid fields.");
		} else {
			try {
				MimeMessage message = createMessage();
				Address to = null;
				Address from = null;
				if (toEmail != null) {
					if (toName != null) {
						to = new InternetAddress(toEmail, toName);
					} else {
						to = new InternetAddress(toEmail);
					}
				}
				// formEmail configured in context.xml
				if (fromEmail != null && fromEmail.length() != 0) {
					if (fromName != null) {
						from = new InternetAddress(fromEmail, fromName);
					} else {
						from = new InternetAddress(fromEmail);
					}
				}
				message.setRecipient(Message.RecipientType.TO, to);
				message.setReplyTo(new Address[] { from });
				;
				message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));

				Multipart mp = new MimeMultipart();
				BodyPart bp = new MimeBodyPart();
				bp.setHeader("Content-Type", "text/html;charset=utf-8");
				bp.setContent(body, "text/html;charset=utf-8");
				mp.addBodyPart(bp);
				message.setContent(mp);
				addMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Factory method to return a blank JavaMail message. You should use the
	 * object returned and set desired message properties. When done, pass the
	 * object to the addMessage(Message) method.
	 *
	 * @return A new JavaMail message.
	 */
	public MimeMessage createMessage() {
		if (session == null)
			init();
		return new MimeMessage(session);
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

}
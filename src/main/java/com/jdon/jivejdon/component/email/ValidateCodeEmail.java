package com.jdon.jivejdon.manager.email;

import java.util.Random;

import com.jdon.annotation.Component;
import com.jdon.cache.UtilCache;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.util.EmailTask;
import com.jdon.util.Debug;

@Component("validateCodeEmail")
public class ValidateCodeEmail implements Startable {
	private final static String module = ValidateCodeEmail.class.getName();

	private final UtilCache validatecodes;

	private ValidateCodeEmailParams validateCodeEmailParams;

	private EmailHelper emailHelper;

	public ValidateCodeEmail(ValidateCodeEmailParams validateCodeEmailParams, EmailHelper emailHelper) {
		super();

		this.validateCodeEmailParams = validateCodeEmailParams;
		this.emailHelper = emailHelper;
		this.validatecodes = new UtilCache(20, 30 * 60 * 1000, false);
	}

	public void send(Account account) {
		Debug.logVerbose("sendValidateCodeEmail  ", module);

		Random r = new Random();
		int validateCode = r.nextInt();
		validatecodes.put(account.getUserId(), validateCode);

		String body = createValidateEmailBody(account, validateCode);

		String subject = validateCodeEmailParams.getTitle();
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = validateCodeEmailParams.getFromName();
		String fromEmail = validateCodeEmailParams.getFromEmail();
		EmailVO emailVO = new EmailVO(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.HTML_FORMAT);
		emailHelper.send(emailVO);
		Debug.logVerbose("email is over", module);

	}

	private String createValidateEmailBody(Account account, int validateCode) {
		StringBuilder buffer = new StringBuilder(validateCodeEmailParams.getBody());
		buffer.append("\n\n");
		String url = validateCodeEmailParams.getUrl() + "&validateCode=" + validateCode + "&userId=" + account.getUserId();
		buffer.append("<a href='").append(url).append("' target=_blank>").append(url).append("</a>");
		buffer.append("\n\n");
		return buffer.toString();
	}

	public boolean emailValidate(String userId, int validateCode) {
		if (validatecodes.containsKey(userId)) {
			Integer validateCodeold = (Integer) validatecodes.get(userId);
			if (validateCodeold.intValue() == validateCode) {
				validatecodes.remove(userId);
				return true;
			}
		}
		return false;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		this.validatecodes.stop();
		this.emailHelper.stop();

	}

}

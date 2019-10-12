package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.property.Property;

public class AccountProfileForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private Account account;

	private String signature;

	private Collection propertys;

	private int maxSize = 20;

	private int validateCode;

	public AccountProfileForm() {
		account = new Account();
		propertys = new ArrayList();
		for (int i = 0; i < maxSize; i++) {
			propertys.add(new Property());
		}
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Collection getPropertys() {
		return propertys;
	}

	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public Property getProperty(int index) {
		Property prop = (Property) ((List) propertys).get(index);
		if (prop == null)
			return null;
		if (prop.getValue() == null)
			return prop;

		if (!prop.getValue().contains("<a href=")) {
			String newString = prop.getValue().replaceAll("(?:https?|ftps?)://[\\w/%.-]+", "<a href='$0'>$0</a>");
			prop.setValue(newString);
		}
		return prop;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (getPropertys().size() > maxSize) {
			errors.add("max length is " + maxSize);
			return;
		}

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(int validateCode) {
		this.validateCode = validateCode;
	}

}

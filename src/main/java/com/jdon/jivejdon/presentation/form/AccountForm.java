/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.presentation.form;

import com.jdon.model.ModelForm;
import org.apache.commons.validator.EmailValidator;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author <a href="mailto:banq@163.com">banq </a>
 */
public class AccountForm extends BaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String email;

	private String password;

	private String password2;

	private String username;

	private String registerCode;

	private String randstr;

	private boolean emailVisible;

	private int ans;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	/**
	 * @return Returns the emailVisible.
	 */
	public boolean isEmailVisible() {
		return emailVisible;
	}

	/**
	 * @param emailVisible The emailVisible to set.
	 */
	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	public int getAns() {
		return ans;
	}

	public void setAns(int ans) {
		this.ans = ans;
	}

	public String getRandstr() {
		return randstr;
	}

	public void setRandstr(String randstr) {
		this.randstr = randstr;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
        if(ModelForm.CREATE_STR.equals(getAction())){
			String randstr = (String)request.getSession().getAttribute("randstr");
			if(randstr == null){
				return;
			}
		}

		if ((getAction() == null) || getAction().length() == 0 || ModelForm
				.EDIT_STR.equals(getAction())
				|| ModelForm.CREATE_STR.equals(getAction())) {


//            if (!SkinUtils.verifyQQRegisterCode(registerCode, randstr,
//                    request.getRemoteAddr())) {
//                errors.add("registerCode  dismatch");
//                return;
//            }

			if (ModelForm.CREATE_STR.equals(getAction())) {
				if (!SkinUtils.verifyQQMobileNumber(request, registerCode)) {
					errors.add("registerCode.dismatch");
					return;
				}
			} else if (ModelForm.EDIT_STR.equals(getAction())) {
				if (!SkinUtils.verifyRegisterCode(registerCode, request)) {
					errors.add("registerCode.dismatch");
					return;
				}
			}


			if (ModelForm.CREATE_STR.equals(getAction())) {
				// evening shut up
//				Calendar startingCalendar = Calendar.getInstance();
//				startingCalendar.setTime(new Date());
//				if (startingCalendar.get(Calendar.HOUR_OF_DAY) < 8 ||
//                        startingCalendar.get(Calendar.HOUR_OF_DAY) > 23) {
//					errors.add("close...");
//				}
			}

			if (addErrorIfStringEmpty(errors, "username.required", getUsername
					()))
				return;

			if (addErrorIfStringEmpty(errors, "password.required",
					getPassword()))
				return;

			if (addErrorIfStringEmpty(errors, "email.required", getEmail()))
				return;

			if (getPassword() != null && getPassword().length() > 0) {
				if (!getPassword().equals(getPassword2())) {
					errors.add("password.check");
					return;
				}
			}
			if (addErrorIfStringEmpty(errors, "email.emailcheck", getEmail()))
				return;

			if (this.getUsername() != null && this.getUsername().length() > 20) {
				errors.add("username.toolong");
				return;
			}

			if (!getUsername().matches("(\\w+|\\d+)")) {
				errors.add("username.alphanumeric");
				return;
			}

			if (!EmailValidator.getInstance().isValid(this.getEmail())) {
				errors.add("email.emailcheck");
				return;
			}


//			if (ModelForm.CREATE_STR.equals(getAction())) {
//				if (!SkinUtils.verifyProblemAnswer(getAns(), request)) {
//					errors.add("answer is not correct");
//					return;
//
//				}
//			}

		}

	}
}

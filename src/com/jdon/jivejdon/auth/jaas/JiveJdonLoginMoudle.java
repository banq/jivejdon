package com.jdon.jivejdon.auth.jaas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * the implements of JAAS, authenticated by container
 * 
 * @author oojdon
 * 
 */

public class JiveJdonLoginMoudle implements LoginModule {

	private Subject _subject;
	private CallbackHandler _callbackHandler;

	private boolean succeeded = false;

	private String username;
	private List<String> roles = new ArrayList<String>();

	private RolesProvider rolesProvider = new DefaultRolesProvider();

	public boolean abort() {
		return true;
	}

	public boolean commit() {
		if (succeeded) {
			_subject.getPrincipals().add(new User(username));

			for (String role : roles)
				_subject.getPrincipals().add(new Role(role));
			return true;
		} else {
			return false;
		}
	}

	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		_subject = subject;
		_callbackHandler = callbackHandler;
	}

	public boolean login() throws LoginException {
		try {
			authenticate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LoginException();
		}
		return succeeded;
	}

	public boolean logout() {
		_subject.getPrincipals().clear();
		return true;
	}

	private void authenticate() throws IOException, UnsupportedCallbackException {

		NameCallback nameCallback = new NameCallback("name: ");
		PasswordCallback passwordCallback = new PasswordCallback("password: ", false);

		_callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });

		username = nameCallback.getName();

		String password = null;
		char[] passwordChar = passwordCallback.getPassword();

		if (passwordChar != null) {
			password = new String(passwordChar);
			roles = rolesProvider.provideRoles(username, DigestUtil.hash(password));
		}

		if (roles.size() > 0)
			succeeded = true;

	}

}
package com.jdon.jivejdon.model.auth;

import java.util.HashSet;
import java.util.Set;

/**
 * contain all operations and them method that must be Authenticated accessed.
 * its scope is application.
 * 
 * @author banq
 * 
 */
public class PermissionRule {

	/**
	 * contain all operations and them method, this operations are all
	 * Authenticated, but not contain they are authenticated by who what role.
	 * 
	 * this method can be used for anonymous.
	 * 
	 * key is the service + method
	 */
	private Set operationAuthenticated;

	/**
	 * contain all operations are authenticated by who? a list of all maps that
	 * key is service + method + role
	 */
	private Set operationAuthenticatedByRoles;

	public PermissionRule() {
		operationAuthenticated = new HashSet();
		operationAuthenticatedByRoles = new HashSet();
	}

	public boolean isOperationAuthenticated(String serviceName, String methodName) {
		StringBuilder bf = new StringBuilder(serviceName);
		bf.append(methodName);
		return operationAuthenticated.contains(bf.toString().intern());
	}

	public boolean isOperationAuthenticatedByRole(String serviceName, String methodName, String roleName) {
		StringBuilder bf = new StringBuilder(serviceName);
		bf.append(methodName);
		bf.append(roleName);
		return operationAuthenticatedByRoles.contains(bf.toString().intern());
	}

	public void putRule(String serviceName, String methodName, String roleName) {
		StringBuilder bf = new StringBuilder(serviceName);
		bf.append(methodName);
		operationAuthenticated.add(bf.toString().intern());
		bf.append(roleName);
		operationAuthenticatedByRoles.add(bf.toString().intern());
	}

}

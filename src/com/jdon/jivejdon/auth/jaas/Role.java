package com.jdon.jivejdon.auth.jaas;

import java.security.Principal;

public class Role implements Principal {

	private String rolename;

	public Role(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * @param object
	 *            Object
	 * @return boolean
	 * @todo Implement this java.security.Principal method
	 */
	public boolean equals(Object object) {
		boolean flag = false;
		if (object == null)
			flag = false;
		if (this == object)
			flag = true;
		if (!(object instanceof Role))
			flag = false;
		if (object instanceof Role) {
			Role that = (Role) object;
			if (this.getName().equals(that.getName())) {
				flag = true;
			}
		}
		// System.out.println("flag="+flag);
		return flag;
	}

	/**
	 * @return String
	 * @todo Implement this java.security.Principal method
	 */
	public String toString() {
		return this.getName();
	}

	/**
	 * @return int
	 * @todo Implement this java.security.Principal method
	 */
	public int hashCode() {
		return rolename.hashCode();
	}

	/**
	 * @return String
	 * @todo Implement this java.security.Principal method
	 */
	public String getName() {
		return this.rolename;
	}
}

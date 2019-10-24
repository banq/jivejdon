package com.jdon.jivejdon.auth;

import com.jdon.jivejdon.domain.model.auth.PermissionRule;

public class OperationAuthorization {
	private PermissionRule permissionRule;

	private PermissionXmlParser permissionXmlParser;

	public OperationAuthorization(PermissionXmlParser permissionXmlParser) {
		this.permissionXmlParser = permissionXmlParser;
	}

	public PermissionRule getPermissionRule() {
		if (permissionRule == null) {
			permissionRule = permissionXmlParser.parse();
		}
		return permissionRule;
	}

}

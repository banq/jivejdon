package com.jdon.jivejdon.auth.jaas;

import java.util.List;

public interface RolesProvider {
	
	List<String> provideRoles(String username,String password);

}

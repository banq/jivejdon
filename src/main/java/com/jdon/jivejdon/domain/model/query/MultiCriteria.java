package com.jdon.jivejdon.domain.model.query;

import com.jdon.util.UtilDateTime;
import com.jdon.util.UtilValidate;

public class MultiCriteria extends QueryCriteria{
	
	private String username;
	private String userID;
	
	public MultiCriteria(){
		
	}
	/**
	 * setup fromdate, toDate is the now Date. 
	 * @param fromDate   format: yyyy/mm/dd
	 */
	public MultiCriteria(String fromDate){
		this.fromDate = UtilDateTime.toDate(fromDate, "00:00");
	}

	public String getUserID() {
		if (UtilValidate.isEmpty(userID)) return null;
		return userID;
	}

	public void setUserID(String userID) {
		if (UtilValidate.isEmpty(userID))
		   this.userID = null;
		else			
		   this.userID = userID;
	}

	public String getUsername() {
		if (UtilValidate.isEmpty(username)) return null;
		return username;
	}

	public void setUsername(String username) {
		if (UtilValidate.isEmpty(username))
			this.username = null;
		else
		    this.username = username;
	}
	
}

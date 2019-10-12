package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.property.Property;
import com.jdon.model.ModelForm;

public class QueryForm extends ModelForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String queryType;
	private String dateRange;

	private Collection dateRanges;

	private String forumId;
	private String username;
	private String userID;

	// yyyy-mm-dd
	private String fromDate;

	// yyyy-mm-dd
	private String toDate;

	private int messageReplyCountWindow; // reply count

	private Collection forums;

	// save queryCriteria for html:link multi params
	Map paramMaps = new HashMap();

	/**
	 * <option value="1">近一天</option> <option value="3">近三天</option> <option
	 * value="5">近一周</option> <option value="30">近一月</option> <option
	 * value="365">近一年</option> <option value="3650">所有</option>
	 * 
	 */
	public QueryForm() {
		forums = new ArrayList();
		dateRanges = new ArrayList();
		Property prop = new Property();
		prop.setName("近一天");
		prop.setValue("1");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("近三天");
		prop.setValue("3");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("近一周");
		prop.setValue("5");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("近一月");
		prop.setValue("30");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("近三月");
		prop.setValue("90");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("近半年");
		prop.setValue("190");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("近一年");
		prop.setValue("365");
		dateRanges.add(prop);

		prop = new Property();
		prop.setName("所有");
		prop.setValue("3650");
		dateRanges.add(prop);

	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
		paramMaps.put("dateRange", dateRange);
	}

	public Collection getDateRanges() {
		return dateRanges;
	}

	public void setDateRanges(Collection dateRanges) {
		this.dateRanges = dateRanges;
	}

	public String getForumId() {
		return forumId;
	}

	public void setForumId(String forumId) {
		this.forumId = forumId;
		paramMaps.put("forumId", forumId);
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
		paramMaps.put("userID", userID);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		paramMaps.put("username", username);
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
		paramMaps.put("queryType", queryType);
	}

	public Collection getForums() {
		return forums;
	}

	public void setForums(Collection forums) {
		this.forums = forums;
	}

	/**
	 * //yyyy-mm-dd
	 * 
	 * @return
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * //yyyy-mm-dd
	 * 
	 * @param fromDate
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
		paramMaps.put("fromDate", fromDate);
	}

	public String getToDate() {
		return toDate;
	}

	/**
	 * //yyyy-mm-dd
	 * 
	 * @param toDate
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
		paramMaps.put("toDate", toDate);
	}

	public Map getParamMaps() {
		return paramMaps;
	}

	public int getMessageReplyCountWindow() {
		return messageReplyCountWindow;
	}

	public void setMessageReplyCountWindow(int messageReplyCountWindow) {
		this.messageReplyCountWindow = messageReplyCountWindow;
		paramMaps.put("messageReplyCountWindow", messageReplyCountWindow);
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.forumId = null;
		this.username = null;
		this.userID = null;
		this.messageReplyCountWindow = 0;
		this.paramMaps.clear();

	}

}

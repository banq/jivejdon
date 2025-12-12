/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.domain.model.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jdon.controller.cache.StringKey;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

/**
 * Query Object P of EAA http://www.martinfowler.com/eaaCatalog/queryObject.html
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class QueryCriteria implements StringKey {
	private static final String DATE_SPLIT = "-";

	protected java.util.Date fromDate;
	protected java.util.Date toDate;

	private String forumId;

	private int messageReplyCountWindow;
	private int digCountWindow ; 
	protected ResultSort resultSort;

	public QueryCriteria() {

		try {
			this.fromDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-01-01 00:00:00");
			this.toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2100-01-01 00:00:00");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.resultSort = new ResultSort();
	}

	public java.util.Date getFromDate() {
		return fromDate;
	}

	/**
	 * 
	 * @return yyyy-mm-dd
	 */
	public String getFromDateString() {
		return ToolsUtil.toDateString(fromDate, DATE_SPLIT);
	}

	/**
	 * 
	 * @return a long Millis String with no hour/min/ss/mills
	 */
	public String getFromDateNoMillisString() {
		return ToolsUtil.dateToNoMillis(fromDate);
	}

	public void setFromDate(String fromYear, String fromMonth, String fromDay) {
		Calendar cal = Calendar.getInstance();
		if (UtilValidate.isEmpty(fromYear))
			fromYear = Integer.toString(cal.get(Calendar.YEAR));
		if (UtilValidate.isEmpty(fromMonth)) {
			int month = cal.get(Calendar.MONTH) + 1;
			if (month < 10) {
				fromMonth = "0" + month;
			} else {
				fromMonth = "" + month;
			}
		}
		if (UtilValidate.isEmpty(fromDay)) {
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (day < 10) {
				fromDay = "0" + day;
			} else {
				fromDay = "" + day;
			}
		}

		String fromDate = fromYear + DATE_SPLIT + fromMonth + DATE_SPLIT + fromDay;
		setFromDate(fromDate);
		// this.fromDate= UtilDateTime.toDate(fromMonth, fromDay, fromYear,
		// "00", "00", "00");

	}

	/**
	 * 
	 * @param fromDate
	 *            yyyy-mm-dd
	 */
	public void setFromDate(String fromDate) {
		try {
			this.fromDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fromDate + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public java.util.Date getToDate() {
		return toDate;
	}

	public String getToDateString() {
		return ToolsUtil.toDateString(toDate, DATE_SPLIT);
	}

	/**
	 * 
	 * @return a long Millis String with no hour/min/ss/mills
	 */
	public String getToDateNoMillisString() {
		return ToolsUtil.dateToNoMillis(toDate);
	}

	public void setToDate(String toYear, String toMonth, String toDay) {
		Calendar cal = Calendar.getInstance();
		if (UtilValidate.isEmpty(toYear))
			toYear = Integer.toString(cal.get(Calendar.YEAR));
		if (UtilValidate.isEmpty(toMonth)) {
			int month = cal.get(Calendar.MONTH) + 1;
			if (month < 10) {
				toMonth = "0" + month;
			} else {
				toMonth = "" + month;
			}
		}

		if (UtilValidate.isEmpty(toDay)) {
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (day < 10) {
				toDay = "0" + day;
			} else {
				toDay = "" + day;
			}
		}
		String toDate = toYear + DATE_SPLIT + toMonth + DATE_SPLIT + toDay;
		setToDate(toDate);
	}

	/**
	 * 
	 * @param toDate
	 *            yyyy-mm-dd
	 */
	public void setToDate(String toDate) {
		try {
			this.toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(toDate + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getForumId() {
		return forumId;
	}

	public void setForumId(String forumId) {
		if (UtilValidate.isEmpty(forumId))
			this.forumId = null;
		else
			this.forumId = forumId;
	}

	public ResultSort getResultSort() {
		return resultSort;
	}

	public void setResultSort(ResultSort resultSort) {
		this.resultSort = resultSort;
	}

	public int getMessageReplyCountWindow() {
		return messageReplyCountWindow;
	}

	public void setMessageReplyCountWindow(int messageReplyCountWindow) {
		this.messageReplyCountWindow = messageReplyCountWindow;
	}

	public int getDigCountWindow() {
		return digCountWindow;
	}

	public void setDigCountWindow(int digCountWindow) {
		this.digCountWindow = digCountWindow;
	}

	/**
	 * this is about cache,
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("forumId=");
		if (toDate == null || fromDate == null) {
			return "";
		}
		long l = toDate.getTime() - fromDate.getTime();
		long dateRange = l / (1000 * 60 * 60 * 24);
		if (dateRange > 1) { // great than 1 day
			sb.append(forumId);
			sb.append("fromDate=");
			sb.append(ToolsUtil.toDateString(fromDate, DATE_SPLIT));
			sb.append("toDate=");
			sb.append(ToolsUtil.toDateString(toDate, DATE_SPLIT));
		} else {// less 1 day, one hour is cache key
			sb.append(forumId);
			sb.append("fromDate=");
			sb.append(ToolsUtil.toDateHourString(fromDate));
			sb.append("toDate=");
			sb.append(ToolsUtil.toDateHourString(toDate));

		}
		sb.append(" messageReplyCountWindow=").append(messageReplyCountWindow);
		return sb.toString();
	}

	public String getKey() {
		return toString();
	}

	@Override
	public String getDataKey() {
		return toString();
	}

}

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
package com.jdon.jivejdon;

import com.ocpsoft.pretty.time.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class Constants {

	public static final int FORUM = 0;

	public static final int THREAD = 1;

	public static final int MESSAGE = 2;

	public static final int USER = 3;

	public static final int OTHERS = 4;

	public static final int SHORTMESSAGE = 5;

	public static final int SUBSCRIPTION = 6;

	public static final String ERRORS = "system.error";

	public static final String NOPERMISSIONS = "nopermission.error";

	public static final String NOPERMISSIONS2 = "hasChildern.nopermission.error";

	public static final String USERNAME_EXISTED = "username.existed";

	public static final String EMAIL_EXISTED = "email.existed";

	public static final String NOT_FOUND = "id.notfound";

	public static final String EXCEED_MAX_UPLOADS = "upload.error.exceed.max";

	public static final String INPUT_PERMITTED = "Input.Permitted";

	public static final String IP_PERMITTED = "IP.Permitted";

	public static final String EXISTED_ERROR = "USER.CREATE.ERROR";

	private static String date_format = "yyyy-MM-dd HH:mm";

	private static PrettyTime t = new PrettyTime();

	public Constants(String format, String mylocale) {
		Constants.date_format = format;
		Constants.t = new PrettyTime(new Locale(mylocale));
	}

	public static String getDateTimeDisp(String datetime) {
		if ((datetime == null) || (datetime.equals("")))
			return "";
		long datel = Long.parseLong(datetime);
		return new SimpleDateFormat(date_format).format(new Date(datel));
	}

	public static String getDefaultDateTimeDisp(String datetime) {
		if ((datetime == null) || (datetime.equals("")))
			return "";
		long datel = Long.parseLong(datetime);
		return new SimpleDateFormat(date_format).format(new Date(datel));
	}

	public static String getDefaultDateTimeDisp(long datetime) {
		return new SimpleDateFormat(date_format).format(new Date(datetime));
	}

	public static Date parseDateTime(String datetime) {
		if (datetime == null || datetime.length() == 0)
			return new Date();
		try {
			return new SimpleDateFormat(date_format).parse(datetime);
		} catch (ParseException e) {
		}
		return new Date();
	}

	public static boolean timeAfter(int hour, Date olddate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(olddate);
		calendar.add(Calendar.HOUR, 1);
		Date nowD = new Date();
		return nowD.after(calendar.getTime());
	}

	public static String convertDataToPretty(String dateTime) {

		Date messageCreateDate = Constants.parseDateTime(dateTime);
		t.setReference(new Date());
		return t.format(messageCreateDate);

	}

	public static String convertDataToPretty(long dateTime) {
		Date messageCreateDate = new Date(dateTime);
		t.setReference(new Date());
		return t.format(messageCreateDate);

	}

	public Date date_parse(String dateS) {
		try {
			return new SimpleDateFormat(date_format).parse(dateS);
		} catch (ParseException e) {
			return null;
		}
	}

}

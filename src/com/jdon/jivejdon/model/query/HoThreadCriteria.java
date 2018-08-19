package com.jdon.jivejdon.model.query;

import java.util.Date;
import java.util.GregorianCalendar;

import com.jdon.util.UtilValidate;

public class HoThreadCriteria extends QueryCriteria{
	
	
	private int hotThreadsNumber = 4;
	private String dateRange;
	
	public void setDateRange(String dateRange) {
		if (UtilValidate.isEmpty(dateRange))  {
			dateRange = "1";
		}
		int dateRangeInt = Integer.parseInt("-" + dateRange);
        GregorianCalendar worldTour = new GregorianCalendar();
        worldTour.add(GregorianCalendar.DATE, dateRangeInt);
        Date d = worldTour.getTime();           
        this.fromDate = d;
        this.toDate = new Date();
        this.dateRange = dateRange;
	}
    
	public String getDateRange(){
		return dateRange;
	}
	

	public int getHotThreadsNumber() {
		return hotThreadsNumber;
	}


	public void setHotThreadsNumber(int hotThreadsNumber) {
		this.hotThreadsNumber = hotThreadsNumber;
	}

}

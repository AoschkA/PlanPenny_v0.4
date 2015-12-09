package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.util.ArrayList;

public class Plan {
    private Date startDate;
    private Date endDate;
    private String color;

    public Plan(Date startDate, Date endDate, String color) {
    	this.startDate=startDate;
    	this.endDate=endDate;
    	this.color=color;
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public ArrayList<Date> getContainingDates() {
		// Not working over 2 years, if 1 year is leap year
		int februaryDays = 28;
		if (isLeapYear(startDate.getYear())) 
			februaryDays++;
		ArrayList<Date> dateList = new ArrayList<Date>();
		// January, February, March, April, May, June, July, August, September, October, November, December
		int[] dayLimit = {31, februaryDays, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int currentYear = startDate.getYear();
		int currentMonth = startDate.getMonth();
		int currentDay = startDate.getDay();
		boolean run = true;
		
		do{
			Date currentDate = new Date(currentYear, currentMonth, currentDay);
			dateList.add(currentDate);
			
			if (dayLimit[currentMonth-1] >= currentDay) {
				currentDay=1;
				if (currentMonth==12) {
					currentMonth=1;
					currentYear++;
				} else
					currentMonth++;
			} else
				currentDay++;
			
			Date newDate = new Date(currentYear, currentMonth, currentDay);
			if (newDate.equals(endDate)) {
				dateList.add(newDate);
				run=false;
			}
		} while(run);
		
		return dateList;
		
	}
	private boolean isLeapYear(int year) {
		if (year%4==0){
			if (year%100==0){
				if(year%400==0)
					return true;
				else
					return false;
			}
			else
				return true;
		} else
			return false;
	}


}
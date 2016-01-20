package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Plan implements Serializable {
    private Date startDate;
    private Date endDate;
    private String color;
	private String title;

    public Plan(Date startDate, Date endDate, String color, String title) {
    	this.startDate=startDate;
    	this.endDate=endDate;
    	this.color=color;
		this.title = title;
    }
	public Plan(){

	}
	public String getTitle() {return this.title;}
	public void setTitle(String title) {this.title = title;}
	public Date getStartDate() {return this.startDate;}

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
			
			if (dayLimit[currentMonth-1] <= currentDay) {
				currentDay=1;
				if (currentMonth==12) {
					currentMonth=1;
					currentYear++;
				} else
					currentMonth++;
			} else
				currentDay++;
			
			Date newDate = new Date(currentYear, currentMonth, currentDay);
			if (newDate.getYear() == endDate.getYear() && newDate.getMonth() == endDate.getMonth() && newDate.getDay() == endDate.getDay()) {
				dateList.add(newDate);
				run=false;
			}
		} while(run);
		
		return dateList;
		
	}
	public static int maxDaysInMonth(int year, int month){
		int februaryDays = 28;
		if (isLeapYear(year))
			februaryDays++;
		// January, February, March, April, May, June, July, August, September, October, November, December
		int[] dayLimit = {31, februaryDays, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		return dayLimit[month-1];
	}
	private static boolean isLeapYear(int year) {
		if (year%4==0){
			if (year%100==0){
				return year % 400 == 0;
			}
			else
				return true;
		} else
			return false;
	}
	public String toString() {
		return this.title + ": " + this.startDate.getYear() + "/" + this.startDate.getMonth() + "/" + this.startDate.getDay() + "  - " + this.endDate.getYear() +"/"+this.endDate.getMonth() + "/" + this.endDate.getDay();
	}

}
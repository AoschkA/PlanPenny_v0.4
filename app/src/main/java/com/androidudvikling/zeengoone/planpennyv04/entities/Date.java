package com.androidudvikling.zeengoone.planpennyv04.entities;

public class Date {
	private int year;
	private int month;
	private int day;
	
	public Date(int year, int month, int day) {
		this.year=year;
		this.month=month;
		this.day=day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public boolean before(Date date){
		if (year<date.getYear())
			return true;
		else if (year>date.getYear())
			return false;
		else if (month<date.getMonth())
			return true;
		else if (month>date.getMonth())
			return false;
		else if (day<date.getDay())
			return true;
		else 
			return false;
	}
	
	public boolean after(Date date) {
		if (year>date.getYear())
			return true;
		else if (year<date.getYear())
			return false;
		else if (month>date.getMonth())
			return true;
		else if (month<date.getMonth())
			return false;
		else if (day>date.getDay())
			return true;
		else
			return false;
	}
	

}

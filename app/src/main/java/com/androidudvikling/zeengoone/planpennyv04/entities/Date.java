package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.util.Calendar;

public class Date implements Serializable {
	private int year;
	private int month;
	private int day;

	public Date(int year, int month, int day) {
		this.year=year;
		this.month=month;
		this.day=day;
	}

	// Samnmenlign to datoer
	public boolean equals(Date testDate){
		return this.year == testDate.getYear() && this.month == testDate.getMonth() && this.day == testDate.getDay();
	}
	// Giv de to sidste tal i året
	public int getTwoDigitYear() {
		String temp = Integer.toString(year);
		return Integer.parseInt(temp.substring(temp.length()-2));
	}

	public int getYear() {
		return this.year;
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

	public Date setDateMonth(int month){
		int aar = Calendar.getInstance().get(Calendar.YEAR);
		int maaned = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int dag = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		for(int i = 0;i < month;i++){
			if(month > 0){
				maaned++;
			}
			if(maaned > 12) {
				aar += 1;
				maaned = maaned - 12;
			}
		}
		return new Date(aar,maaned,dag);
	}
	// Undersøg om datoen er før angivne dato
	public boolean before(Date date){
		if (year<date.getYear())
			return true;
		else if (year>date.getYear())
			return false;
		else if (month<date.getMonth())
			return true;
		else if (month>date.getMonth())
			return false;
		else return day < date.getDay();
	}

	// Undersøg om datoen er efter angivne dato
	public boolean after(Date date) {
		if (year>date.getYear())
			return true;
		else if (year<date.getYear())
			return false;
		else if (month>date.getMonth())
			return true;
		else if (month<date.getMonth())
			return false;
		else return day > date.getDay();
	}

	public String toString() {
		return Integer.toString(year)+"/"+Integer.toString(month) + "/" + Integer.toString(day);
	}

	public String toGoogleDateTime() {
		return "" + getYear() + "-" +
				getMonth() + "-" + getDay() +
				"T01:00:00-00:00";
	}
}

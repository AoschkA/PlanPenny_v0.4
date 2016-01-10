package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
	private String categoryTitle;
    private ArrayList<Plan> planList;

    public Category(String categoryTitle) {
    	planList = new ArrayList<Plan>(); 
        this.categoryTitle = categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle){
    	this.categoryTitle=categoryTitle;
    }

    public String getCategoryTitle() {
    	return categoryTitle;
    }

    public ArrayList<Plan> getPlanList(){
    	return planList;
    }

	public ArrayList<String> getPlanListDates() {
		ArrayList<String> tempPlanListDates = new ArrayList<String>();
		for(Plan p:getPlanList()){
			tempPlanListDates.add("StartDate: " + p.getStartDate().toString() + " EndDate: " + p.getEndDate().toString());
		}
		return tempPlanListDates;
	}

    public Plan getPlan(Date startDate, Date endDate) {
    	for (Plan temp : planList) {
    		if (temp.getStartDate().equals(startDate) && temp.getEndDate().equals(endDate))
    			return temp;
    	}
    	return null;
    }

    public void addPlan(Plan p) {
    	planList.add(p);
    }

	public Date getEndDate() {
		Date temp = planList.get(0).getEndDate();
		for (Plan p : planList) {
			if (p.getEndDate().after(temp)) temp=p.getEndDate();
		}
		return temp;
	}

	public Date getStartDate() {
		Date temp = planList.get(0).getStartDate();
		for (Plan p : planList) {
			if (p.getStartDate().before(temp)) temp=p.getStartDate();
		}
		return temp;
	}

    
    public ArrayList<Date> getContainingDays() {
    	ArrayList<Date> dateList = new ArrayList<Date>();
    	for (Plan p : planList) {
    		for (Date d : p.getContainingDates()){
    			if (!dateList.contains(d))
    				dateList.add(d);
    		}
    	}
    	return dateList;
    }

	public String toString(Category c){
		return c.getCategoryTitle();
	}
}
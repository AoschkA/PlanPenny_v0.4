package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.util.ArrayList;

public class Category {
	private String categoryTitle;
    private ArrayList<Plan> planList;

    public Category(String categoryTitle) {
    	planList = new ArrayList<Plan>();
        this.categoryTitle = categoryTitle;
    }

    public Plan getPlan(String date) {
    	for (Plan temp : planList) {
    		if (temp.getDate().equals(date))
    			return temp;
    	}
    	return null;
    }

    public void addPlan(Plan p) {
    	planList.add(p);
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



}
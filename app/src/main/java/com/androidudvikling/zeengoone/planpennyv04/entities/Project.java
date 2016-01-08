package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.util.ArrayList;

public class Project{
	private String title;
    private ArrayList<Category> categoryList;

    public Project(String title) {
    	this.title=title;
    	categoryList = new ArrayList<Category>();
    }

    public Category getCategory(String categoryTitle) {
    	for (Category temp : categoryList) {
    		if (temp.getCategoryTitle().equals(categoryTitle))
    			return temp;
    	}
    	return null;
    }

    public void addCategory(Category c) {
    	categoryList.add(c);
    }

    public void setTitle(String title) {
    	this.title=title;
    }

    public String getTitle() {
    	return title;
    }

    public ArrayList<Category> getCategoryList() {
    	return categoryList;
    }


	public ArrayList<String> getCategoryTitlesList(){
		ArrayList<String> tempList = new ArrayList<String>();
		for(Category c:categoryList){
			tempList.add(c.getCategoryTitle());
		}
		return tempList;
	}
    
    public ArrayList<Date> getContainingDates() {
    	ArrayList<Date> dateList = new ArrayList<Date>();
    	for (Category c : categoryList) {
    		for (Date d : c.getContainingDays()) {
    			if (!dateList.contains(d))
    				dateList.add(d);
    		}
    	}
    	return dateList;
    }
	public int getNumberOfMonths(Date currentMonth){

		return 0;
	}
}
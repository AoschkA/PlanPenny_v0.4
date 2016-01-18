package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class Category implements Serializable {
	private String categoryTitle;
	/*Comparator for sorting the list by Category Title*/
	public static Comparator<Category> CategoryNameComparator = new Comparator<Category>() {

		public int compare(Category c1, Category c2) {
			String CategoryTitle1 = c1.getCategoryTitle().toUpperCase();
			String CategoryTitle2 = c2.getCategoryTitle().toUpperCase();

			//ascending order
			return CategoryTitle1.compareTo(CategoryTitle2);
		}};
	private ArrayList<Plan> planList;
	/*Comparator for sorting the list by Category Length*/
	public static Comparator<Category> CategoryLengthComparator = new Comparator<Category>() {

		public int compare(Category c1, Category c2) {
			int CategoryDates1 = c1.getPlanListDates().size();
			int CategoryDates2 = c2.getPlanListDates().size();

			//ascending order
			return CategoryDates1 < CategoryDates2?1:-1;
		}};
	/*Comparator for sorting the list by Category Start*/
	public static Comparator<Category> CategoryStartComparator = new Comparator<Category>() {

		public int compare(Category c1, Category c2) {
			Date CategoryDates1 = c1.getStartDate();
			Date CategoryDates2 = c2.getStartDate();

			//ascending order
			return CategoryDates1.after(CategoryDates2)?1:-1;

		}
	};

    public Category(String categoryTitle) {
    	planList = new ArrayList<Plan>();
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryTitle() {
    	return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle){
    	this.categoryTitle=categoryTitle;
    }

    public ArrayList<Plan> getPlanList(){
    	return planList;
    }

	public ArrayList<String> getPlanListDates() {
		ArrayList<String> tempPlanListDates = new ArrayList<String>();
		for(Date d:getContainingDays()){
			tempPlanListDates.add("Date: " + d.toString());
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

	public int numberOfMonths(Date toDate){
		int aar = Calendar.getInstance().get(Calendar.YEAR);
		int maaned = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int countMonths = 0;
		boolean notSame = true;
		while(notSame){
			if(toDate.getYear() == aar && toDate.getMonth() == maaned){
				notSame = false;
			}
			else{
				countMonths++;
				if(maaned == 12){
					aar++;
					maaned = 1;
				}
				else{
					maaned++;
				}
			}
		}
		return countMonths;
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
    	ArrayList<Date> dateList = new ArrayList<>();
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
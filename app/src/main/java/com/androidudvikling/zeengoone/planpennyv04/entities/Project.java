package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Project implements Serializable{
	private String title;
	/*Comparator for sorting the list by Project Title*/
	public static Comparator<Project> ProjectNameComparator = new Comparator<Project>() {

		public int compare(Project p1, Project p2) {
			String ProjectTitle1 = p1.getTitle().toUpperCase();
			String ProjectTitle2 = p2.getTitle().toUpperCase();

			//ascending order
			return ProjectTitle1.compareTo(ProjectTitle2);
			}};
    private ArrayList<Category> categoryList;
	/*Comparator for sorting the list by Project Length*/
	public static Comparator<Project> ProjectLengthComparator = new Comparator<Project>() {

		public int compare(Project p1, Project p2) {
			int ProjectDates1 = p1.getContainingDates().size();
			int ProjectDates2 = p2.getContainingDates().size();

			//ascending order
			return ProjectDates1 < ProjectDates2?1:-1;

			//descending order
			//return StudentName2.compareTo(StudentName1);
		}};
	/*Comparator for sorting the list by Project Start*/
	public static Comparator<Project> ProjectStartComparator = new Comparator<Project>() {

		public int compare(Project p1, Project p2) {
			Date ProjectDates1 = p1.getStartDate();
			Date ProjectDates2 = p2.getStartDate();

			//ascending order
			return ProjectDates1.after(ProjectDates2)?1:-1;

			}
		};

    public Project(String title) {
    	this.title=title;
    	categoryList = new ArrayList<>();
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

    public String getTitle() {
    	return title;
    }

    public void setTitle(String title) {
    	this.title=title;
    }
    
    public ArrayList<Category> getCategoryList() {
    	return categoryList;
    }

	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public ArrayList<String> getCategoryTitlesList(){
		ArrayList<String> tempList = new ArrayList<>();
		for(Category c:categoryList){
			tempList.add(c.getCategoryTitle());
		}
		return tempList;
	}

    public ArrayList<Date> getContainingDates() {
    	ArrayList<Date> dateList = new ArrayList<>();
    	for (Category c : categoryList) {
    		for (Date d : c.getContainingDays()) {
    			if (!dateList.contains(d))
    				dateList.add(d);
    		}
    	}
    	return dateList;
    }

	public Date getEndDate() {
		Date temp = categoryList.get(0).getEndDate();
		for (Category c : categoryList) {
			if (c.getEndDate().after(temp)) temp = c.getEndDate();
		}
		return temp;
	}

	public Date getStartDate() {
		Date temp = categoryList.get(0).getStartDate();
		for (Category c : categoryList) {
			if (c.getStartDate().before(temp)) temp = c.getStartDate();
		}
		return temp;
	}

	public void sortCategories(int type) {
		ArrayList<Category> newCategories = sortCategories(type, getCategoryList());
		setCategoryList(newCategories);
	}

	/* type:
	1 for alphabetic
	2 for biggest first
	3 for first planned first
	4 for first created first
	 */
	private ArrayList<Category> sortCategories(int sortType_category, ArrayList<Category> categories) {
		if (sortType_category==1) {
			Collections.sort(categories, Category.CategoryNameComparator);
			return categories;
		}
		else if (sortType_category==2) {
			Collections.sort(categories, Category.CategoryLengthComparator);
			return categories;
		}
		else if (sortType_category==3) {
			Collections.sort(categories, Category.CategoryStartComparator);
			return categories;
		}
		else {
			// not implemented
			return categories;
		}
	}
}
package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Project implements Serializable{
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

	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
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
			ArrayList<Category> sortedList = new ArrayList<Category>();
			ArrayList<String> titles = new ArrayList<String>();
			for (int i=0; i<categories.size(); i++)
				titles.add(categories.get(i).getCategoryTitle());

			Collections.sort(titles, String.CASE_INSENSITIVE_ORDER);
			for (Category c : categories) {
				for (String s : titles)
					if (c.getCategoryTitle().equals(s)) sortedList.add(c);
			}
			return sortedList;
		}
		else if (sortType_category==2) {
			ArrayList<Category> sortedList = new ArrayList<Category>();
			ArrayList<Integer> sizeList = new ArrayList<Integer>();
			for (Category c : categories)
				sizeList.add(c.getContainingDays().size());

			Collections.sort(sizeList);
			Collections.reverse(sizeList);

			for (int i : sizeList) {
				for (Category c : categories)
					if (c.getContainingDays().size()==i) sortedList.add(c);
			}
			return sortedList;
		}
		else if (sortType_category==3) {
			ArrayList<Category> sortedList = new ArrayList<Category>();
			ArrayList<String> stringList = new ArrayList<String>();

			for (Category c : categories)
				stringList.add(c.getStartDate().toString());

			Collections.sort(stringList, String.CASE_INSENSITIVE_ORDER);
			for (Category c : categories) {
				for (String s : stringList)
					if (c.getStartDate().toString().equals(s)) sortedList.add(c);
			}
			return sortedList;
		}
		else {
			// not implemented
			return categories;
		}
	}

}
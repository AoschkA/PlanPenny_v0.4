package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jonasandreassen on 16/11/15.
 */
public class Project {
    public String title;
    public ArrayList<Category> categories;

    public Project(String title){categories = new ArrayList<Category>(); this.title=title;}
    public void createCategory(String title) {categories.add(new Category(title));}
    public Category getCategory(String title) {
        Category target = new Category("00000");
        for (Category temp: categories) {
            if (temp.categoryTitle.equals(title)){ target=temp; break;}
        }
        if (target.categoryTitle.equals(title)) return target;
        else return null;
    }
    public Date getFirstDate(){
        Date startDate = categories.get(0).getFirstDate();
        if (categories.size()>0) {
            for (int i = 1; i < categories.size(); i++) {
                Category temp = categories.get(i);
                if (temp.getFirstDate().before(startDate)) startDate = temp.getFirstDate();
            }
        }
        return startDate;
    }
    public Date getLastDate() {
        Date endDate = categories.get(0).getLastDate();
        if (categories.size()>0) {
            for (int i = 1; i < categories.size(); i++) {
                Category temp = categories.get(i);
                if (temp.getLastDate().after(endDate)) endDate = temp.getLastDate();
            }
        }
        return endDate;
    }
}

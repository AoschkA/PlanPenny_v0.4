package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectDB implements Serializable{
    private ArrayList<Project> projectList;

    public ProjectDB() {
    	projectList = new ArrayList<Project>();
    }

    public Project getProject(String title) {
    	for (Project temp : projectList) {
    		if (temp.getTitle().equals(title))
    			return temp;
    	}
    	return null;
    }

    public void addProject(Project p) {
    	projectList.add(p);
    }

    public ArrayList<Project> getProjectList(int sortType_category) {
    	for (Project temp : projectList) {
            temp.sortCategories(sortType_category);
        }
        return projectList;
    }

    public void setProjectList(ArrayList<Project> projectList) {
        this.projectList=projectList;
    }

    public void clearList(){
        projectList.clear();
    }
}
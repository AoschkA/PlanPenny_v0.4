package com.androidudvikling.zeengoone.planpennyv04.logic;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.ProjectDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by Jonas Praem on 13-Dec-15.
 */

public class DataLogic implements Serializable {
	private static int sortType_project = 1;
	private static int sortType_category = 1;
	private static boolean sync_google = false;
	private static ProjectDB projectDB = new ProjectDB();
	
	public DataLogic() {
		if(getProjects().size() > 0) {
	    }
		else{
			addDefaultProjects();
		}
	}

	public int getSortType_project() {
		return sortType_project;
	}

	public void setSortType_project(int sortType) {
		sortType_project=sortType;
	}

	public int getSortType_category() {
		return sortType_category;
	}

	public void setSortType_category(int sortType) {
		sortType_category=sortType;
	}

	public boolean getSync_google() {
		return sync_google;
	}

	public void setSync_google(boolean sync) {
		sync_google=sync;
	}

	public void clearProjects(){
		projectDB.clearList();
	}

    public void deleteProject(int projectNumber) {
        projectDB.deleteProject(projectNumber);
    }
	
	public void addProject(String projectTitle) {
		projectDB.addProject(new Project(projectTitle));
	}
	
	public void addCategory(String projectTitle, String categoryTitle) {
		projectDB.getProject(projectTitle).addCategory(new Category(categoryTitle));
	}
	
	public void addPlan(String projectTitle, String categoryTitle, Date startDate, Date endDate, String color, String title) {
		projectDB.getProject(projectTitle).getCategory(categoryTitle).addPlan(new Plan(startDate, endDate, color, title));
	}
	
	public ArrayList<Project> getProjects() {
		return projectDB.getProjectList(sortType_category);
	}
    public ProjectDB getProjectDB() { return projectDB; }
	public void setProjectList(ArrayList<Project> projectlist){ projectDB.setProjectList(projectlist); }
	public ArrayList<String> getProjectsTitles() {
		ArrayList<String> projectlistTitles = new ArrayList<>();
		for(Project p:projectDB.getProjectList(sortType_project)){
			projectlistTitles.add(p.getTitle());
		}
		return projectlistTitles;
	}
	
	public ArrayList<Category> getCategories(String projectTitle) {
		return projectDB.getProject(projectTitle).getCategoryList();
	}
	
	public ArrayList<Plan> getPlans(String projectTitle, String categoryTitle) {
		return projectDB.getProject(projectTitle).getCategory(categoryTitle).getPlanList();
	}

	// Returns projects for a given month
	public ArrayList<Project> getProjectsForMonth(int year, int month) {
		ArrayList<Project> projectList = new ArrayList<>();
		for (Project p : projectDB.getProjectList(sortType_category)){
			for (Date d : p.getContainingDates()) {
				if(d.getYear()==year && d.getMonth()==month) {
					projectList.add(p);
					break;
				}
			}
		}
		return projectList;
	}

	// Returns categories in a specific project for a given month
	public ArrayList<Category> getCategoriesForMonth(String projectTitle, int year, int month) {
		ArrayList<Category> categoryList = new ArrayList<>();
		for (Category c : projectDB.getProject(projectTitle).getCategoryList()){
			for (Date d : c.getContainingDates()) {
				if (d.getYear()==year && d.getMonth()==month) {
					categoryList.add(c);
					break;
				}
			}
		}
		return categoryList;
	}
	// Returns categories in a specific project for a given month
	public ArrayList<Plan> getCategoryForMonth(String projectTitle,int categoryIndex, int year, int month) {
		ArrayList<Plan> planList = new ArrayList<>();
		for (Plan p : projectDB.getProject(projectTitle).getCategoryList().get(categoryIndex).getPlanList()){
				for (Date d : p.getContainingDates()) {
					if (d.getYear()==year && d.getMonth()==month) {
						planList.add(p);
					}
				}
		}
		return planList;
	}

	// returns plans in a specific category in a given month
	public ArrayList<Plan> getPlansForMonth(String projectTitle, String categoryTitle, int year, int month) {
		ArrayList<Plan> planList = new ArrayList<>();
		for (Plan p : projectDB.getProject(projectTitle).getCategory(categoryTitle).getPlanList()) {
			for (Date d : p.getContainingDates()) {
				if (d.getYear()==year && d.getMonth()==month) {
					planList.add(p);
					break;
				}
			}
		}
		return planList;
	}

	// Returns the relevant months for a given project
	public ArrayList<Integer> getRelevantMonthsForProject(String projectTitle){
		ArrayList<Integer> integerList = new ArrayList<>();
		for (Date d : projectDB.getProject(projectTitle).getContainingDates()) {
			if (!integerList.contains(d.getMonth()))
				integerList.add(d.getMonth());
		}
		return integerList;
	}

	// Returns the relevant months for a given category in a specific project
	public ArrayList<Integer> getRelevantMonthsForCategory(String projectTitle, String categoryTitle) {
		ArrayList<Integer> integerList = new ArrayList<>();
		for (Date d : projectDB.getProject(projectTitle).getCategory(categoryTitle).getContainingDates()) {
			if (!integerList.contains(d.getMonth()))
				integerList.add(d.getMonth());
		}
		return integerList;
	}

	public int getRemainingMonthsForProject(Date currentDate, String currentProjectTitle) {
		Project currentProject = projectDB.getProject(currentProjectTitle);
		int remainingMonths = 0;

		if (currentDate.after(currentProject.getEndDate())) return -1;
		if (currentDate.equals(currentProject.getEndDate())) return 0;
		while (true) {
			if (currentDate.getYear()==currentProject.getEndDate().getYear()) break;
			else {
				remainingMonths+=12;
				currentDate.setYear(currentDate.getYear()+1);
			}
		}
		remainingMonths += currentProject.getEndDate().getMonth()-currentDate.getMonth();
		return remainingMonths;
	}

	public int getRemainingMonthsForCategory(Date currentDate, String currentProjectTitle, String currentCategoryTitle) {
		Category currentCategory = projectDB.getProject(currentProjectTitle).getCategory(currentCategoryTitle);
		int remainingMonths = 0;

		if (currentDate.after(currentCategory.getEndDate())) return -1;
		if (currentDate.equals(currentCategory.getEndDate())) return 0;
		while (true) {
			if (currentDate.getYear()==currentCategory.getEndDate().getYear()) break;
			else {
				remainingMonths+=12;
				currentDate.setYear(currentDate.getYear()+1);
			}
		}
		remainingMonths += currentCategory.getEndDate().getMonth()-currentDate.getMonth();
		return remainingMonths;
	}

	public void sortProjects() {
		projectDB.setProjectList(sortProjects(projectDB.getProjectList(sortType_category)));
	}

	private ArrayList<Project> sortProjects(ArrayList<Project> projects) {
		if (sortType_project==1) {
			Collections.sort(projects, Project.ProjectNameComparator);
			return projects;
		}
		else if (sortType_project==2) {
			Collections.sort(projects, Project.ProjectLengthComparator);
			return projects;
		}
		else if (sortType_project==3) {
			Collections.sort(projects, Project.ProjectStartComparator);
			return projects;
		}
		else {
			// not implemented
			return projects;
		}
	}
	
	// Temporary function
	// Creates some data for startup
	public void addDefaultProjects() {
		String color = "#ff6600";
		// Project 1
		addProject("Redesign of company");
		addCategory("Redesign of company", "Design");
		addCategory("Redesign of company", "Planing");
		addCategory("Redesign of company", "Contruction");
		addPlan("Redesign of company", "Design", new Date(2016, 01, 01), new Date(2016, 01, 29), color, "design fase");
		addPlan("Redesign of company", "Planing", new Date(2016, 01, 01), new Date(2016, 01, 20), color, "planning fase 1");
		addPlan("Redesign of company", "Planing", new Date(2016, 02, 01), new Date(2016, 02, 20), color, "planning fase 2");
		addPlan("Redesign of company", "Contruction", new Date(2016, 03, 01), new Date(2016, 05, 20), color, "konstruktion");
		// Project 2
		addProject("Create software expansion");
		addCategory("Create software expansion", "Analyse");
		addCategory("Create software expansion", "Design");
		addCategory("Create software expansion", "Implementation");
		addCategory("Create software expansion", "Test");
		addCategory("Create software expansion", "Release");
		addPlan("Create software expansion", "Analyse", new Date(2016, 2, 2), new Date(2016, 2, 28), color, "analyse fase");
		addPlan("Create software expansion", "Design", new Date(2016, 3, 2), new Date(2016, 4, 1), color, "design fase");
		addPlan("Create software expansion", "Implementation", new Date(2016, 4, 1), new Date(2016, 6, 25), color, "implementations fase");
		addPlan("Create software expansion", "Test", new Date(2016, 6, 5), new Date(2016, 9, 28), color, "test fase");
		addPlan("Create software expansion", "Release", new Date(2016, 8, 10), new Date(2016, 8, 15), color, "udgivelse");
		// Project 3
		addProject("Preparing for marathon");
		addCategory("Preparing for marathon", "Run training sessions");
		addPlan("Preparing for marathon", "Run training sessions", new Date(2016, 2, 2), new Date(2016, 5, 28), color, "Løb");
	}
}
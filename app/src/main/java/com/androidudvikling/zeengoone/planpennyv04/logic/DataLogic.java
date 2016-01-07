package com.androidudvikling.zeengoone.planpennyv04.logic;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.ProjectDB;

import java.util.ArrayList;


public class DataLogic {
	private ProjectDB projectDB = new ProjectDB();
	
	public DataLogic() {}

	public void clearProjects(){
		projectDB.clearList();
	}
	
	public void addProject(String projectTitle) {
		projectDB.addProject(new Project(projectTitle));
	}
	
	public void addCategory(String projectTitle, String categoryTitle) {
		projectDB.getProject(projectTitle).addCategory(new Category(categoryTitle));
	}
	
	public void addPlan(String projectTitle, String categoryTitle, Date startDate, Date endDate, String color) {
		projectDB.getProject(projectTitle).getCategory(categoryTitle).addPlan(new Plan(startDate, endDate, color));
	}
	
	public ArrayList<Project> getProjects() {
		return projectDB.getProjectList();
	}
	
	public ArrayList<Category> getCategories(String projectTitle) {
		return projectDB.getProject(projectTitle).getCategoryList();
	}
	
	public ArrayList<Plan> getPlans(String projectTitle, String categoryTitle) {
		return projectDB.getProject(projectTitle).getCategory(categoryTitle).getPlanList();
	}

	// Returns projects for a given month
	public ArrayList<Project> getProjectsForMonth(int year, int month) {
		ArrayList<Project> projectList = new ArrayList<Project>();
		for (Project p : projectDB.getProjectList()){
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
		ArrayList<Category> categoryList = new ArrayList<Category>();
		for (Category c : projectDB.getProject(projectTitle).getCategoryList()){
			for (Date d : c.getContainingDays()) {
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
		ArrayList<Plan> planList = new ArrayList<Plan>();
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
		ArrayList<Plan> planList = new ArrayList<Plan>();
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
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		for (Date d : projectDB.getProject(projectTitle).getContainingDates()) {
			if (!integerList.contains(d.getMonth()))
				integerList.add(d.getMonth());
		}
		return integerList;
	}

	// Returns the relevant months for a given category in a specific project
	public ArrayList<Integer> getRelevantMonthsForCategory(String projectTitle, String categoryTitle) {
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		for (Date d : projectDB.getProject(projectTitle).getCategory(categoryTitle).getContainingDays()) {
			if (!integerList.contains(d.getMonth()))
				integerList.add(d.getMonth());
		}
		return integerList;
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
		addPlan("Redesign of company", "Design", new Date(2016, 01, 01), new Date(2016, 01, 29), color);
		addPlan("Redesign of company", "Planing", new Date(2016, 01, 01), new Date(2016, 01, 20), color);
		addPlan("Redesign of company", "Planing", new Date(2016, 02, 01), new Date(2016, 02, 20), color);
		addPlan("Redesign of company", "Contruction", new Date(2016, 03, 01), new Date(2016, 05, 20), color);
		// Project 2
		addProject("Create software expansion");
		addCategory("Create software expansion", "Analyse");
		addCategory("Create software expansion", "Design");
		addCategory("Create software expansion", "Implementation");
		addCategory("Create software expansion", "Test");
		addCategory("Create software expansion", "Release");
		addPlan("Create software expansion", "Analyse", new Date(2016, 2, 2), new Date(2016, 2, 28), color);
		addPlan("Create software expansion", "Design", new Date(2016, 3, 2), new Date(2016, 4, 1), color);
		addPlan("Create software expansion", "Implementation", new Date(2016, 4, 1), new Date(2016, 6, 25), color);
		addPlan("Create software expansion", "Test", new Date(2016, 6, 5), new Date(2016, 9, 28), color);
		addPlan("Create software expansion", "Release", new Date(2016, 8, 10), new Date(2016, 8, 15), color);
		
	}
	
	
}

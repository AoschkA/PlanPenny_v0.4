package com.androidudvikling.zeengoone.planpennyv04.logic;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.ProjectList;

import java.util.Date;

/**
 * Created by jonasandreassen on 16/11/15.
 */
public class Datalogic {
    ProjectList list;
    Project activeProject;

    public Datalogic() {list = new ProjectList();}
    public void addProject(String title){ list.projects.add(new Project(title));}
    public void setActiveProject(String title) {
        activeProject = list.getProject(title);}
    public void addPlan(String categoryTitle, Date startDate, Date endDate){
        activeProject.getCategory(categoryTitle).plans.add(new Plan(startDate, endDate));}
    public void addCategory(String categoryTitle) {
        activeProject.categories.add(new Category(categoryTitle));}
    public String getProjectTitle() {return activeProject.title;}
    public Date getProjectStartDate() {return activeProject.getFirstDate();}
    public Date getProjectEndDate() {return activeProject.getLastDate();}
    public Date getCategoryStartDate(String categoryTitle){return activeProject.getCategory(categoryTitle).getFirstDate();}
    public Date getCategoryEndDate(String categoryTitle){return activeProject.getCategory(categoryTitle).getLastDate();}
    public void changePlanDates(String categoryTitle, Date startDate, Date endDate, Date newStartDate, Date newEndDate){
        activeProject.getCategory(categoryTitle).getPlan(startDate, endDate).setStart_date(newStartDate);
        activeProject.getCategory(categoryTitle).getPlan(startDate, endDate).setEnd_date(newEndDate);
    }
    public String[] getProjectList() {
        String[] projectList = new String[list.projects.size()];
        for (int i=0; i<projectList.length; i++){
            projectList[i] = list.projects.get(i).title;
        }
        return projectList;
    }

}

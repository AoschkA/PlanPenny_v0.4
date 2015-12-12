package com.androidudvikling.zeengoone.planpennyv04.logic;
import android.content.Context;
import android.content.SharedPreferences;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by alexandervpedersen on 24/11/15.
 * Modified by Jonas Praem on 10/24/15
 */
public class FileHandler{
    Context context;

    public FileHandler(Context context){
    }

    // titles of categories and projects can't contain '#' for load method to work
    public void saveAllData(ArrayList<Project> projectList) {
        SharedPreferences projectPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
        SharedPreferences categoryPref = context.getSharedPreferences("CategoryList", Context.MODE_PRIVATE);
        SharedPreferences planPref = context.getSharedPreferences("PlanList", Context.MODE_PRIVATE);
        SharedPreferences.Editor projectEditor = projectPref.edit();
        SharedPreferences.Editor categoryEditor = categoryPref.edit();
        SharedPreferences.Editor planEditor = planPref.edit();

        int projectCounter = 0;
        int categoryCounter = 0;
        int planCounter = 0;
        for (Project p : projectList) {
            projectEditor.putString("Project"+projectCounter, p.getTitle());
            projectCounter++;
            for (Category c : p.getCategoryList()) {
                categoryEditor.putString("Category"+categoryCounter, p.getTitle()+"#"+c.getCategoryTitle());
                categoryCounter++;
                for (Plan plan : c.getPlanList()) {
                    Date startDate = plan.getStartDate();
                    Date endDate = plan.getEndDate();
                    String color = plan.getColor();
                    planEditor.putString("Plan"+planCounter, p.getTitle()+"#"+c.getCategoryTitle()+"#"+startDate+"#"+endDate+"#"+color);
                    planCounter++;
                }
            }
        }
        projectEditor.apply();
        categoryEditor.apply();
        planEditor.apply();
        int globalCount = projectCounter+categoryCounter+planCounter;
        System.out.println("***DATA SAVED SUCCESFULLY***");
        System.out.println("Amount of data saved: "+globalCount);
        System.out.println("Which exist of: ");
        System.out.println("Projects: "+projectCounter);
        System.out.println("Categories: "+categoryCounter);
        System.out.println("Plans: "+planCounter);
    }

    public ArrayList<Project> loadAllDate() {
        ArrayList<Project> projectList = new ArrayList<Project>();
        SharedPreferences projectPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
        SharedPreferences categoryPref = context.getSharedPreferences("CategoryList", Context.MODE_PRIVATE);
        SharedPreferences planPref = context.getSharedPreferences("PlanList", Context.MODE_PRIVATE);

        for (int p=0; p<projectPref.getAll().size(); p++){
            String projectName = projectPref.getString("Project"+p, "");
            projectList.add(new Project(projectName));
        }

        for (int p=0; p<categoryPref.getAll().size(); p++){
            String line = categoryPref.getString("Category"+p, "");
            String[] lineData = line.split("#");
            for (Project pro : projectList) {
                if (pro.getTitle().equals(lineData[0]))
                    pro.addCategory(new Category(lineData[1]));
            }
        }

        for (int p=0; p<planPref.getAll().size(); p++) {
            String line = planPref.getString("Plan"+p, "");
            String[] lineData = line.split("#");
            for (Project pro : projectList) {
                for (Category cat : pro.getCategoryList()){
                    if (pro.getTitle().equals(lineData[0]) && cat.getCategoryTitle().equals(lineData[1])) {
                        String[] date1 = lineData[2].split("/");
                        String[] date2 = lineData[3].split("/");
                        cat.addPlan(new Plan(new Date(Integer.getInteger(date1[0]), Integer.getInteger(date1[1]), Integer.getInteger(date1[2])),
                                new Date(Integer.getInteger(date2[0]), Integer.getInteger(date2[1]), Integer.getInteger(date2[2])), lineData[4]));
                    }
                }
            }
        }
        return projectList;
    }

//    public Project[] getProjectlist(){
//        SharedPreferences sharedPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
//        int size = sharedPref.getAll().size();
//        Project[] tempProjects = new Project[size];
//
//        for(int i = 0; i<size; i++){
//            String projectname = sharedPref.getString("Project " + i , "");
//            Project projectObj = new Project(projectname);
//            tempProjects[i] = projectObj;
//        }
//
//        return tempProjects;
//    }
//
//    public Category[] getCategoryList(String project){
//        SharedPreferences sharedPref = context.getSharedPreferences(project + "_CategoryList", Context.MODE_PRIVATE);
//        int size = sharedPref.getAll().size();
//        Category[] tempCategories = new Category[size];
//
//        for(int i = 0; i<size; i++){
//            String categoryname = sharedPref.getString("Category " + i , "");
//            Category categoryObj = new Category(categoryname);
//            tempCategories[i] = categoryObj;
//        }
//
//        return tempCategories;
//    }
//
//    public Plan[] getPlanList(String project, String category){
//        SharedPreferences sharedPref = context.getSharedPreferences(project + "_" + category + "_PlanList", Context.MODE_PRIVATE);
//        int size = sharedPref.getAll().size();
//        Plan[] tempPlans = new Plan[size];
///*
//        for(int i = 0; i<size; i++){
//            String planname = sharedPref.getString("Plan " + i , "");
//            String[] plandetails = planname.split(",");
//            Plan planObj = new Plan(new Date(Integer.parseInt(plandetails[0]),
//                                             Integer.parseInt(plandetails[1]),
//                                             Integer.parseInt(plandetails[2])),
//                                    new Date(Integer.parseInt(plandetails[3]),
//                                             Integer.parseInt(plandetails[4]),
//                                             Integer.parseInt(plandetails[5])));
//            tempPlans[i] = planObj;
//        }
//*/
//        return tempPlans;
//
//    }
//
//
//    public void makeProject(String projectname){
//        SharedPreferences sharedPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        int size = sharedPref.getAll().size();
//        int projectPointer = size;
//        editor.putString("Project " + projectPointer, projectname);
//        editor.apply();
//    }
//
//    public void makeCategory(String curprojectname, String categoryname){
//        SharedPreferences sharedPref = context.getSharedPreferences(curprojectname + "_CategoryList", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        int size = sharedPref.getAll().size();
//        int categoryPointer = size;
//        System.out.println("Næste kategori tal er for " + curprojectname + ": " + categoryPointer);
//        editor.putString("Category " + categoryPointer, categoryname);
//        editor.apply();
//
//    }
//
//    public void makePlan(String curprojectname, String curcategoryname, Date start, Date end){
//        SharedPreferences sharedPref = context.getSharedPreferences(curprojectname + "_" + curcategoryname
//                + "_PlanList", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        int size = sharedPref.getAll().size();
//        int planPointer = size;
//        editor.putString("Plan " + planPointer, ""
//                + start.getYear() + ","
//                + start.getMonth() + ","
//                + start.getDate() + ","
//                + end.getYear() + ","
//                + end.getMonth() + ","
//                + end.getDate());
//        editor.apply();
//
//    }



}

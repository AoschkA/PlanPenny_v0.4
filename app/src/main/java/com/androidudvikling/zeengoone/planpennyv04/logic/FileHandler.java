package com.androidudvikling.zeengoone.planpennyv04.logic;
import android.content.Context;
import android.content.SharedPreferences;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.util.ArrayList;


/**
 * Created by alexandervpedersen on 24/11/15.
 */
public class FileHandler{
    Context context;

    public FileHandler(Context context){
    }

    public void saveAllData(ArrayList<Project> projectList) {
        SharedPreferences sharedPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int projectCounter = 0;
        int categoryCounter = 0;
        int planCounter = 0;
        for (Project p : projectList) {
            editor.putString("Project"+projectCounter, p.getTitle());
            projectCounter++;
            for (Category c : p.getCategoryList()) {
                editor.putString("Category"+categoryCounter, c.getCategoryTitle());
                categoryCounter++;
                for (Plan plan : c.getPlanList()) {
                    Date startDate = plan.getStartDate();
                    Date endDate = plan.getEndDate();
                    String color = plan.getColor();
                    editor.putString("Plan"+planCounter+"-1", startDate.toString());
                    editor.putString("Plan"+planCounter+"-2", endDate.toString());
                    editor.putString("Plan"+planCounter+"-3", color);
                    planCounter++;
                }
            }
        }
        editor.apply();
        int globalCount = projectCounter+categoryCounter+planCounter;
        System.out.println("***DATA SAVED SUCCESFULLY***");
        System.out.println("Amount of data saved: "+globalCount);
        System.out.println("Which exist of: ");
        System.out.println("Projects: "+projectCounter);
        System.out.println("Categories: "+categoryCounter);
        System.out.println("Plans: "+planCounter);
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

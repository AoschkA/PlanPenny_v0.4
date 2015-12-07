package com.androidudvikling.zeengoone.planpennyv04.test;

import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.exceptions.PlanException;

import java.util.Date;

/**
 * Created by jonasandreassen on 16/11/15.
 */
public class testDate {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Project project1 = new Project("Project1");
        project1.createCategory("Category1");
        project1.createCategory("Category2");
        project1.createCategory("Category3");
        project1.createCategory("Category4");
        try {
            project1.getCategory("Category1").createPlan(new Date(2016, 3, 1), new Date(2016, 3, 20));
            project1.getCategory("Category2").createPlan(new Date(2016, 1, 5), new Date(2016, 5, 1));
            project1.getCategory("Category3").createPlan(new Date(2016, 4, 20), new Date(2016, 6, 1));
            project1.getCategory("Category4").createPlan(new Date(2016, 0, 1), new Date(2016, 2, 5));
            project1.getCategory("Category4").createPlan(new Date(2016, 5, 5), new Date(2016, 5, 5));
        } catch (PlanException e){System.out.println("exception");}
        System.out.println(project1.getFirstDate());
        System.out.println(project1.getLastDate());
    }
}

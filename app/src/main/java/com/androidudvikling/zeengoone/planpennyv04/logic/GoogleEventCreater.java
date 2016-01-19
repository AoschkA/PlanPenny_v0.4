package com.androidudvikling.zeengoone.planpennyv04.logic;

import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.util.ArrayList;

/**
 * Created by jonasandreassen on 19/01/16.
 */
public class GoogleEventCreater {
    DataLogic dl = new DataLogic();

    public ArrayList<Date> getBoundaryDates(String projectName){
        ArrayList<Date> boundaries = new ArrayList<Date>();
        Project project = findCurrentProject(projectName);
        boundaries.add(project.getStartDate());
        boundaries.add(project.getEndDate());
        return boundaries;
    }

    private Project findCurrentProject(String projectName) {
        for (Project p : dl.getProjects())
            if (p.getTitle().equals(projectName)) return p;
        return null;
    }
}

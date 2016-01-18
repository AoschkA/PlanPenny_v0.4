package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by alexandervpedersen on 15/01/16.
 */
public class OfflineFilehandler {

    Context ctx;
    FileOutputStream fos;
    String projectHandler = "ProjectHandler";
    String projectInformation = "ProjectInformation";
    String extension = ".pp";
    String[] allFileNames;
    Project[] projectList;

    public OfflineFilehandler(Context ctx){
        this.ctx = ctx;
    }

    public Project[] getAllProjects(){
        allFileNames = ctx.getFilesDir().list();

        for(int i = 0 ; i<allFileNames.length ; i++){
            projectList[i] = getProject(allFileNames[i]);
        }

        return projectList;
    }

    public Project getProject(String project){
        // Laver vores string om til et projekt.
        Project newProject = new Project(project);
        String ret = "";

        try {
            InputStream inputStream = ctx.openFileInput(project + extension);

            // Tjekker på om vi allerede har en inputStream.
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString = "";
                StringBuilder stringBuilder = new StringBuilder();

                // Henter string fra bufferedReader.
                receivedString = bufferedReader.readLine();
                stringBuilder.append(receivedString);

                // Lukker forbindelsen og benytter stringbuilderen til at give os stringen.
                inputStream.close();
                ret = stringBuilder.toString();

                String[] kategories = ret.split("-");
                String color = "#ff6600";

                for(int i = 0 ; i<kategories.length ; i++) {
                    String[] planlist = kategories[i].split(",");
                    String categoryName = planlist[0];

                    Category category = new Category(categoryName);
                    newProject.addCategory(category);

                    for(int k = 1; k<planlist.length ; k++) {
                        String planDetails = planlist[k];
                        String[] planDates = planDetails.split("\\.");
                        String dateTitle = planDates[0];
                        String[] dateStart = planDates[1].split("/");
                        String[] dateEnd = planDates[2].split("/");

                        Date start = new Date(Integer.parseInt(dateStart[0]),Integer.parseInt(dateStart[1]),Integer.parseInt(dateStart[2]));
                        Date end = new Date(Integer.parseInt(dateEnd[0]),Integer.parseInt(dateEnd[1]),Integer.parseInt(dateEnd[2]));

                        Plan plan = new Plan();
                        plan.setStartDate(start);
                        plan.setEndDate(end);
                        plan.setTitle(dateTitle);
                        plan.setColor(color);

                        newProject.getCategory(categoryName).addPlan(plan);

                    }
                }



            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
            return null;
        }
        return newProject;
    }

    public boolean saveAllProjects(Project[] projects){
        for(int i = 0 ; i<projects.length ; i++){
            saveProject(projects[i]);
        }
        return false;
    }

    public boolean saveProject(Project project){
        String projectTitle = project.getTitle();


        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(projectTitle + extension, Context.MODE_PRIVATE));
            // Indskriv alle kategorier under projekt.
            // Foregår efter syntax: kategorinavn,plannavn.start.slut,plannavn.start.slut-kategorinavn,plannavn.start.slut...
            for(int i = 0; i<project.getCategoryList().size();i++){
                outputStreamWriter.write(project.getCategoryList().get(i).getCategoryTitle());
                for(int k=0; k<project.getCategoryList().get(i).getPlanList().size();k++){
                    outputStreamWriter.write("," + project.getCategoryList().get(i).getPlanList().get(k).getTitle());
                    outputStreamWriter.write("." + project.getCategoryList().get(i).getPlanList().get(k).getStartDate().toString());
                    outputStreamWriter.write("." + project.getCategoryList().get(i).getPlanList().get(k).getEndDate().toString());

                }
                if(i<project.getCategoryList().size()-1)
                outputStreamWriter.write("-");

            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }

        return true;

    }

    public boolean testProjectSave(String text){
        String extension = ".pp";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(projectHandler + extension, Context.MODE_PRIVATE));
            outputStreamWriter.write(text);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        return true;
    }

}

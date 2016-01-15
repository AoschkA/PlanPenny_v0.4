package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;
import android.util.Log;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
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

    public OfflineFilehandler(Context ctx){
        this.ctx = ctx;
    }

    public Project[] getProjects(){
        try {
            fos = ctx.openFileOutput(projectHandler, Context.MODE_PRIVATE);
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Filen findes ikke problem.");
        }

        return null;
    }

    public Project getProject(String project){

        return null;
    }

    public boolean saveProjects(Project[] projects){

        return false;
    }

    public boolean saveProject(Project project){
        String extension = ".pp";

        String projectTitle = project.getTitle();


        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(projectTitle + extension, Context.MODE_PRIVATE));
            // Indskriv alle kategorier under projekt.

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

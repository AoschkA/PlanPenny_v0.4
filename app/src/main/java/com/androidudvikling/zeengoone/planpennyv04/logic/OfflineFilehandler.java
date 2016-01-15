package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;

import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by alexandervpedersen on 15/01/16.
 */
public class OfflineFilehandler {

    Context ctx;
    FileOutputStream fos;
    String projectHandler = "ProjectHandler";
    String projectInformation = "ProjectInformation";

    OfflineFilehandler(){
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

    public boolean saveProjects(Project[] projects){

        return false;
    }

    public boolean saveProject(Project project){
        File f = new File(projectInformation);

        try {
            fos = new FileOutputStream(f);
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return false;
    }

}

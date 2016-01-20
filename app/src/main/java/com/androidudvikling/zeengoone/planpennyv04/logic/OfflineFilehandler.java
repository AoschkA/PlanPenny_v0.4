package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;
import android.util.Log;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;

import java.io.BufferedReader;
import java.io.File;
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
    ArrayList<Project> projectList;
    OnlineFilehandler onf;


    public OfflineFilehandler(Context ctx){
        this.ctx = ctx;
        onf =  new OnlineFilehandler(ctx);
    }

    /* Til evt. brug af timestamp (Virker ikke)
    public void checkUsedProject(String timestamp){
        String onfTimeStamp = timestamp;
        System.out.println(onfTimeStamp);


        try {
            InputStream inputStream = ctx.openFileInput("VersionTimeStampOffline" + extension);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString = "";
                StringBuilder stringBuilder = new StringBuilder();

                // Henter string fra bufferedReader.
                try {
                    receivedString = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stringBuilder.append(receivedString);

                // Lukker forbindelsen og benytter stringbuilderen til at give os stringen.
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                offlineTimeStamp = stringBuilder.toString();
                System.out.println("Offline timestamp: " + offlineTimeStamp);

            }
        } catch (FileNotFoundException e) {
            usedProject = "OnlineTimestamp";
            onf.getAllProjects();
        }

        if(offlineTimeStamp == null) {
            usedProject = "OnlineTimestamp";
            onf.getAllProjects();
        } else if(onfTimeStamp == null)
        {
            usedProject = "OfflineTimestamp";
        }
        else {


            System.out.println(onfTimeStamp);
            System.out.println(offlineTimeStamp);
            // For online
            String[] onlineDate = onfTimeStamp.split("_");
            String[] onlineDateymd = onlineDate[0].split("/");
            String[] onlineDatetime = onlineDate[1].split("\\.");

            // For offline
            String[] offlineDate = offlineTimeStamp.split("_");
            String[] offlineDateymd = offlineDate[0].split("/");
            String[] offlineDatetime = offlineDate[1].split("\\.");
            System.out.println(offlineDatetime[0]);
            System.out.println(onlineDatetime[0]);

            Date dateOnlineTimeStamp = new Date(Integer.parseInt(onlineDateymd[0]), Integer.parseInt(onlineDateymd[1]), Integer.parseInt(onlineDateymd[2]));
            Date dateOfflineTimeStamp = new Date(Integer.parseInt(offlineDateymd[0]), Integer.parseInt(offlineDateymd[1]), Integer.parseInt(offlineDateymd[2]));

            // Tjek på den nyeste og vælg hvilket projekt som skal bruges
            if (dateOnlineTimeStamp.after(dateOfflineTimeStamp)) {
                usedProject = "OnlineTimestamp";
            } else if (dateOfflineTimeStamp.after(dateOnlineTimeStamp)) {
                usedProject = "OfflineTimestamp";
            } else if (dateOfflineTimeStamp.equals(dateOnlineTimeStamp)) {
                if (Integer.parseInt(offlineDatetime[0]) > Integer.parseInt(onlineDatetime[0])) {
                    usedProject = "OfflineTimestamp";
                } else if (Integer.parseInt(offlineDatetime[0]) < Integer.parseInt(onlineDatetime[0])) {
                    usedProject = "OnlineTimestamp";
                } else if (Integer.parseInt(offlineDatetime[0]) == Integer.parseInt(onlineDatetime[0])) {
                    if (Integer.parseInt(offlineDatetime[1]) > Integer.parseInt(onlineDatetime[1])) {
                        usedProject = "OfflineTimestamp";
                    } else if(Integer.parseInt(offlineDatetime[1]) < Integer.parseInt(onlineDatetime[1])){
                        usedProject = "OnlineTimestamp";
                    }
                    else if(Integer.parseInt(offlineDatetime[1]) == Integer.parseInt(onlineDatetime[1])){
                        if(Integer.parseInt(offlineDatetime[2]) > Integer.parseInt(onlineDatetime[2])){
                            usedProject = "OfflineTimestamp";
                        }
                        else{
                            usedProject = "OnlineTimestamp";
                        }
                    }
                }
            }
            if (usedProject == "OnlineTimestamp") {
                System.out.println("Det blev online timestamp!");
                onf.getAllProjects();
            }
        }
    }
    */

    public ArrayList<Project> getAllProjects(){
        // Først undersøg om Firebase versionen er nyere end den lokale
            projectList = new ArrayList<Project>();
            allFileNames = ctx.getFilesDir().list();

            for(int i = 0 ; i<allFileNames.length ; i++){
                String[] filename = allFileNames[i].split("\\.");
                projectList.add(getProject(filename[0]));
            }
        return projectList;
    }

    public boolean isEmpty(){
        String[] projects = ctx.getFilesDir().list();
        return projects.length == 0;
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

    public boolean saveAllProjects(ArrayList<Project> projects){
        // Først fjern de forrige projekter for så at lave dem igen.
        deleteFilesInFolder();

        for(int i = 0 ; i<projects.size() ; i++){
            saveProject(projects.get(i));
        }
        return false;
    }

    public void deleteFilesInFolder(){
        File dir = ctx.getFilesDir();
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
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


}

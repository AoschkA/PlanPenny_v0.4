package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zengoone on 11/12/15.
 */
public class FilHaandtering extends AppCompatActivity{
    protected void writeProjects(ArrayList<String> projectWriteList) throws IOException {
        File path = getDir("projekter", 0);
        File file = new File(path, "projektliste.pp");
        FileOutputStream stream = new FileOutputStream(file);
        for(String s:projectWriteList) {
            try {
                stream.write(s.getBytes());
                stream.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("FileWriteERROR: ", "Der opstod en fejl ved skrivning til fil");
            }
        }
        stream.close();
    }

    protected ArrayList<String> readProjects() throws IOException {
        File path = getDir("projekter", 0);
        File file = new File(path, "projektliste.pp");
        BufferedReader br = null;
        ArrayList<String> projectList = new ArrayList<String>();
        try
        {
            StringBuffer project_input = new StringBuffer();
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null)
            {
                projectList.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.d("FILHÅNDTERING: ", "Filen findes ikke endnu");
            projectList.add("Projekt Test");
        }
        for(String s:projectList){
            System.out.println(s);
        }
        return projectList;
    }
    private void testSetupProjekter(){

    }
}

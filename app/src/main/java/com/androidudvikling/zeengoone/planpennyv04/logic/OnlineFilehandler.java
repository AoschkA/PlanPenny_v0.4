package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by alexandervpedersen on 18/01/16.
 */


// Denne klasse er oprettet med henblik på brug af Firebase.
public class OnlineFilehandler {
    Context ctx;
    Firebase myFirebaseRef;
    Project project;
    ArrayList<Project> allProjects = new ArrayList<Project>();
    int counter = 0;
    int count = 0;
    String timestamp = null;
    boolean timestampchecked = false;


    public boolean isTimestampchecked() {
        return timestampchecked;
    }

    public OnlineFilehandler(Context ctx){
        myFirebaseRef = new Firebase("https://planpenny.firebaseio.com/");
        this.ctx = ctx;
        Firebase.setAndroidContext(ctx);
    }

    public boolean saveProject(Project project){
        myFirebaseRef.child(project.getTitle()).setValue(project);
        return true;
    }

    /*
    public Project getProjectTest(String projectName){

        Firebase projectRef =  myFirebaseRef.child(projectName);
        projectRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                newProject = snapshot.getValue(Project.class);
                System.out.println(newProject);
            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    return newProject;
    }
    */

    public void saveAllProjects(ArrayList<Project> projects){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH.mm.ss").format(Calendar.getInstance().getTime());
        myFirebaseRef.removeValue();
        System.out.println(timeStamp);

        for(int i = 0 ; i<projects.size() ; i++) {
            myFirebaseRef.child("Projects").child(Integer.toString(i)).setValue(projects.get(i));
        }

        myFirebaseRef.child("VersionTimestamp").setValue(timeStamp);
        Toast.makeText(ctx, "Projekterne er lagt op på Firebase.", Toast.LENGTH_LONG).show();
    }

    public void checkTimeStamp(){
        myFirebaseRef.child("VersionTimestamp").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                timestamp = (String)snapshot.getValue();

            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    }

    public String getTimeStamp(){
        return timestamp;
    }

    public void getAllProjects(){
        //Får alle projekter på ny

            myFirebaseRef.child("Projects").addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Først tag projektet og titlen.
                    counter = 0;
                    count = (int)snapshot.getChildrenCount()-1;
                    allProjects.clear();

                    for (int i = 0; i < snapshot.getChildrenCount(); i++) {
                        DataSnapshot projectSnapshot = snapshot.child(Integer.toString(i));
                        String projectTitle = projectSnapshot.child("title").getValue().toString();

                        System.out.println("Cool" + projectTitle);

                        Project newProject = new Project(projectTitle);


                        //Herefter kategorierne under projektet.
                        long categoryCount = projectSnapshot.child("categoryList").getChildrenCount();

                        for (int k = 0; k < categoryCount; k++) {
                            // Tager titlen på kategorien.
                            String categoryTitle = projectSnapshot
                                    .child("categoryList")
                                    .child(Integer.toString(k))
                                    .child("categoryTitle").getValue().toString();

                            Category category = new Category(categoryTitle);


                            // Finder ud af hvor mange planer der er for den givne titel.
                            long planCount = projectSnapshot
                                    .child("categoryList")
                                    .child(Integer.toString(k))
                                    .child("planList")
                                    .getChildrenCount();

                            System.out.println(categoryTitle);

                            for (int j = 0; j < planCount; j++) {
                                // Finder titlen på planen
                                String planTitle = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("title").getValue().toString();

                                // Finder start plan
                                String startDateDay = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("startDate")
                                        .child("day").getValue().toString();

                                String startDateMonth = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("startDate")
                                        .child("month").getValue().toString();

                                String startDateYear = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("startDate")
                                        .child("year").getValue().toString();

                                // Og tilsvarende for slut date.
                                String endDateDay = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("endDate")
                                        .child("day").getValue().toString();

                                String endDateMonth = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("endDate")
                                        .child("month").getValue().toString();

                                String endDateYear = projectSnapshot
                                        .child("categoryList")
                                        .child(Integer.toString(k))
                                        .child("planList")
                                        .child(Integer.toString(j))
                                        .child("endDate")
                                        .child("year").getValue().toString();

                                Date startDate = new Date(Integer.parseInt(startDateYear), Integer.parseInt(startDateMonth), Integer.parseInt(startDateDay));
                                Date endDate = new Date(Integer.parseInt(endDateYear), Integer.parseInt(endDateMonth), Integer.parseInt(endDateDay));

                                Plan plan = new Plan(startDate, endDate, "#ff6600", planTitle);

                                category.addPlan(plan);
                            }
                            newProject.addCategory(category);

                        }
                        allProjects.add(newProject);
                        counter++;
                        if (counter == count)
                            Toast.makeText(ctx, "Projekterne er synkroniseret med Firebase", Toast.LENGTH_LONG).show();
                    }
                }



                @Override
                public void onCancelled(FirebaseError error) {
                    System.out.println("Fejl");
                }

            });
    }


    public void getProject(String projectName){
        myFirebaseRef.child(projectName).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Først tag projektet og titlen.

                String projectTitle = snapshot.child("title").getValue().toString();

                System.out.println("Cool" + projectTitle);

                Project newProject = new Project(projectTitle);


                //Herefter kategorierne under projektet.
                long categoryCount = snapshot.child("categoryList").getChildrenCount();

                for (int i = 0; i < categoryCount; i++) {
                    // Tager titlen på kategorien.
                    String categoryTitle = snapshot
                            .child("categoryList")
                            .child(Integer.toString(i))
                            .child("categoryTitle").getValue().toString();

                    Category category = new Category(categoryTitle);


                    // Finder ud af hvor mange planer der er for den givne titel.
                    long planCount = snapshot
                            .child("categoryList")
                            .child(Integer.toString(i))
                            .child("planList")
                            .getChildrenCount();

                    for (int k = 0; k < planCount; k++) {
                        // Finder titlen på planen
                        String planTitle = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("title").getValue().toString();

                        // Finder start plan
                        String startDateDay = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("startDate")
                                .child("day").getValue().toString();

                        String startDateMonth = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("startDate")
                                .child("month").getValue().toString();

                        String startDateYear = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("startDate")
                                .child("year").getValue().toString();

                        // Og tilsvarende for slut date.
                        String endDateDay = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("endDate")
                                .child("day").getValue().toString();

                        String endDateMonth = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("endDate")
                                .child("month").getValue().toString();

                        String endDateYear = snapshot
                                .child("categoryList")
                                .child(Integer.toString(i))
                                .child("planList")
                                .child(Integer.toString(k))
                                .child("endDate")
                                .child("year").getValue().toString();

                        Date startDate = new Date(Integer.parseInt(startDateYear), Integer.parseInt(startDateMonth), Integer.parseInt(startDateDay));
                        Date endDate = new Date(Integer.parseInt(endDateYear), Integer.parseInt(endDateMonth), Integer.parseInt(endDateDay));

                        Plan plan = new Plan(startDate, endDate, "#ff6600", planTitle);

                        category.addPlan(plan);
                    }
                    newProject.addCategory(category);

                }
                project = newProject;
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("Fejl");
            }

        });


    }

    public Project getSpecificProject() {
        return project;
    }

    public ArrayList<Project> getAllProjectsArrayList() {
        return allProjects;
    }
}

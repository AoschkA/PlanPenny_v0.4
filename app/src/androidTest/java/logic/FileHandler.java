package logic;
import android.content.Context;
import android.content.SharedPreferences;

import com.androidudvikling.zeengoone.planpenny.entities.Category;
import com.androidudvikling.zeengoone.planpenny.entities.Plan;
import com.androidudvikling.zeengoone.planpenny.entities.Project;

import java.util.Date;


/**
 * Created by alexandervpedersen on 24/11/15.
 */
public class FileHandler{
    Context context;
    /*  1. Skal læse hvilke projekter der eksisterer.
        2. Skal herunder læse hvilke kategorier som eksisterer.
        3. Herunder planerne for hver kategori.
     */
    public FileHandler(Context context){
        this.context = context;
    }

    public Project[] getProjectlist(){
        SharedPreferences sharedPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
        int size = sharedPref.getAll().size();
        Project[] tempProjects = new Project[size];

        for(int i = 0; i<size; i++){
            String projectname = sharedPref.getString("Project " + i , "");
            Project projectObj = new Project(projectname);
            tempProjects[i] = projectObj;
        }

        return tempProjects;
    }

    public Category[] getCategoryList(String project){
        SharedPreferences sharedPref = context.getSharedPreferences(project + "_CategoryList", Context.MODE_PRIVATE);
        int size = sharedPref.getAll().size();
        Category[] tempCategories = new Category[size];

        for(int i = 0; i<size; i++){
            String categoryname = sharedPref.getString("Category " + i , "");
            Category categoryObj = new Category(categoryname);
            tempCategories[i] = categoryObj;
        }

        return tempCategories;
    }

    public Plan[] getPlanList(String project, String category){
        SharedPreferences sharedPref = context.getSharedPreferences(project + "_" + category + "_PlanList", Context.MODE_PRIVATE);
        int size = sharedPref.getAll().size();
        Plan[] tempPlans = new Plan[size];

        for(int i = 0; i<size; i++){
            String planname = sharedPref.getString("Plan " + i , "");
            String[] plandetails = planname.split(",");
            Plan planObj = new Plan(new Date(Integer.parseInt(plandetails[0]),
                                             Integer.parseInt(plandetails[1]),
                                             Integer.parseInt(plandetails[2])),
                                    new Date(Integer.parseInt(plandetails[3]),
                                             Integer.parseInt(plandetails[4]),
                                             Integer.parseInt(plandetails[5])));
            tempPlans[i] = planObj;
        }

        return tempPlans;

    }


    public void makeProject(String projectname){
        SharedPreferences sharedPref = context.getSharedPreferences("ProjectList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = sharedPref.getAll().size();
        int projectPointer = size;
        editor.putString("Project " + projectPointer, projectname);
        editor.apply();
    }

    public void makeCategory(String curprojectname, String categoryname){
        SharedPreferences sharedPref = context.getSharedPreferences(curprojectname + "_CategoryList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = sharedPref.getAll().size();
        int categoryPointer = size;
        System.out.println("Næste kategori tal er for " + curprojectname + ": " + categoryPointer);
        editor.putString("Category " + categoryPointer, categoryname);
        editor.apply();

    }

    public void makePlan(String curprojectname, String curcategoryname, Date start, Date end){
        SharedPreferences sharedPref = context.getSharedPreferences(curprojectname + "_" + curcategoryname
                + "_PlanList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = sharedPref.getAll().size();
        int planPointer = size;
        editor.putString("Plan " + planPointer, ""
                + start.getYear() + ","
                + start.getMonth() + ","
                + start.getDate() + ","
                + end.getYear() + ","
                + end.getMonth() + ","
                + end.getDate());
        editor.apply();

    }



}

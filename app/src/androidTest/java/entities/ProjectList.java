package entities;

import java.util.ArrayList;

/**
 * Created by jonasandreassen on 16/11/15.
 */
public class ProjectList {
    public ArrayList<Project> projects;

    public ProjectList() {projects=new ArrayList<Project>();}
    public Project getProject(String title) {
            Project target = new Project("0000");
            for (Project temp: projects) {
                if (temp.title.equals(title)){ target=temp; break;}
            }
            if (target.title.equals(title)) return target;
            else return null;
    }

}

package com.androidudvikling.zeengoone.planpennyv04.logic;


import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.ProjectDB;

public class DataLogic {
	private ProjectDB projectList = new ProjectDB();
	
	public DataLogic() {}
	
	public boolean sortCategories(String projectTitle, String categoryTitle, int month) {
		// Does not work for several years
		for (Date d : projectList.getProject(projectTitle).getCategory(categoryTitle).getContainingDays()){
			if (d.getMonth()==month)
				return true;
		}
		return false;
	}
	
}

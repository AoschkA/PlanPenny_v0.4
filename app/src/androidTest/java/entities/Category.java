package entities;


import java.util.ArrayList;
import java.util.Date;

import exceptions.PlanException;

/**
 * Created by jonasandreassen on 16/11/15.
 */
public class Category {
    public String categoryTitle;
    public ArrayList<Plan> plans;

    public Category(String categoryTitle) {plans = new ArrayList<Plan>(); this.categoryTitle = categoryTitle;}
    public void createPlan(Date startDate, Date endDate) throws PlanException {
        for (Plan temp:plans){
            // if date is occupied
            if(temp.getStart_date().after(startDate)&&temp.getStart_date().before(endDate)) throw new PlanException();
            if(temp.getEnd_date().after(startDate)&&temp.getEnd_date().before(endDate)) throw new PlanException();
        }
        plans.add(new Plan(startDate, endDate));
    }
    public Plan getPlan(Date startDate, Date endDate){
        Plan target = new Plan(new Date(0,0,0), new Date(0,0,0));
        for (Plan temp: plans) {
            if (temp.getStart_date().equals(startDate)&&temp.getEnd_date().equals(endDate)){ target=temp; break;}
        }
        if (target.getStart_date().equals(startDate)&&target.getEnd_date().equals(endDate)) return target;
        else return null;
    }
    public Date getFirstDate() {
        Date startDate = plans.get(0).getStart_date();
        if (plans.size()>0) {
            for (int i = 1; i < plans.size(); i++) {
                Plan temp = plans.get(i);
                if (temp.getStart_date().before(startDate)) startDate = temp.getStart_date();
            }
        }
        return startDate;
    }
    public Date getLastDate() {
        Date endDate = plans.get(0).getEnd_date();
        if (plans.size()>0) {
            for (int i = 1; i < plans.size(); i++) {
                Plan temp = plans.get(i);
                if (temp.getEnd_date().after(endDate)) endDate = temp.getEnd_date();
            }
        }
        return endDate;
    }
}

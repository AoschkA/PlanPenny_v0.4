package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.util.Date;

/**
 * Created by jonasandreassen on 16/11/15.
 */
public class Plan {
    private Date start_date;
    private Date end_date;
    private String note;
    private String color;
    private String plan_desc;

    public Plan(Date start_date, Date end_date) {
        this.start_date = start_date;
        this.end_date = end_date;

    }

    public void SetColor(String color){
        this.color = color;
    }

    public void SetPlan_Desc(String plan_desc){
        this.plan_desc = plan_desc;
    }

    public void SetNote(String note){
        this.note = note;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getNote() {
        return note;
    }

    public String getColor() {
        return color;
    }

    public String getPlan_desc() {
        return plan_desc;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPlan_desc(String plan_desc) {
        this.plan_desc = plan_desc;
    }
}

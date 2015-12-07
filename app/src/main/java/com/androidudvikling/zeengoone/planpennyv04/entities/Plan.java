package com.androidudvikling.zeengoone.planpennyv04.entities;

public class Plan {
    private String date;
    private String color;

    public Plan(String date) {
    	this.date=date;
    	color="#EB5D3E";
    }

    public Plan(String date, String color) {
    	this.date=date;
    	this.color=color;
    }

    public void setDate(String newDate) {
    	this.date=newDate;
    }

    public void setColor(String newColor){
        this.color=newColor;
    }

    public String getDate(){
    	return this.date;
    }

    public String getColor(){
    	return this.color;
    }    
}
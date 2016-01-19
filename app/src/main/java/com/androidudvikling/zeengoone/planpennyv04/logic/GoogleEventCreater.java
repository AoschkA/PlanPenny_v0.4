package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.util.Log;

import com.androidudvikling.zeengoone.planpennyv04.Fragment_Controller;
import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jonasandreassen on 19/01/16.
 */
public class GoogleEventCreater {
    HttpTransport transport    = AndroidHttp.newCompatibleTransport();
    JacksonFactory jsonFactory = new JacksonFactory();
    private DataLogic dl = new DataLogic();
    private Calendar service;

    public GoogleEventCreater() {
        service = new Calendar.Builder(transport, jsonFactory, Fragment_Controller.mCredential)
                .setApplicationName("PlanPenny").build();
    }

    public ArrayList<Date> getBoundaryDates(String projectName){
        ArrayList<Date> boundaries = new ArrayList<Date>();
        Project project = findCurrentProject(projectName);
        boundaries.add(project.getStartDate());
        boundaries.add(project.getEndDate());
        return boundaries;
    }

    public void createEvent(String projectName) {
        Project project = findCurrentProject(projectName);
        System.out.println("testing createEvent i Google Event Creater");
        for (Category c: project.getCategoryList()) {
            // Opretter Event
            Event event = new Event()
                    .setDescription(c.getCategoryTitle())
                    .setSummary(project.getTitle());

            // Sætter starttidspunkt
            DateTime startDateTime = new DateTime(c.getStartDate().toGoogleDateTime());
            EventDateTime start = new EventDateTime()
                    .setDate(startDateTime)
                    .setTimeZone("Denmark/Copenhagen");
            event.setStart(start);

            // Sætter sluttidspunkt
            DateTime endDateTime = new DateTime(c.getEndDate().toGoogleDateTime());
            EventDateTime end = new EventDateTime()
                    .setDate(endDateTime)
                    .setTimeZone("Denmark/Copenhagen");
            event.setEnd(end);

            /*
                Hjemmesider:
                https://developers.google.com/google-apps/calendar/create-events
                https://developers.google.com/gdata/javadoc/com/google/gdata/client/Service
             */
            String calendarId = "primary";
            try {
                event = service.events().insert(calendarId, event).execute();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("GoogleEventCreator Exception!");
            }
            Log.d("GoogleEventCreater","Event created: %s\n" + " " + event.getHtmlLink());
        }
    }

    private Project findCurrentProject(String projectName) {
        for (Project p : dl.getProjects())
            if (p.getTitle().equals(projectName)) return p;
        return null;
    }

}

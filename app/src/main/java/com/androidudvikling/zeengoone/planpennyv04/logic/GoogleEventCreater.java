package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.os.AsyncTask;
import android.util.Log;

import com.androidudvikling.zeengoone.planpennyv04.Fragment_Controller;
import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jonasandreassen on 19/01/16.
 */
public class GoogleEventCreater extends AsyncTask<Void, Void, Void>{
    HttpTransport transport    = AndroidHttp.newCompatibleTransport();
    JacksonFactory jsonFactory = new JacksonFactory();
    private DataLogic dc = Fragment_Controller.dc;
    private com.google.api.services.calendar.Calendar service = Fragment_Controller.mService;
    private String currentProjectTitle;
    private String calendarID;
    private boolean deleteCalendarProject = false;

    public GoogleEventCreater(String currentProject) {

        // Initialize Calendar service with valid OAuth credentials
        service = new com.google.api.services.calendar.Calendar.Builder(transport, jsonFactory, Fragment_Controller.mCredential)
                .setApplicationName("applicationName").build();

        //service = new com.google.api.services.calendar.Calendar.Builder(transport, jsonFactory, Fragment_Controller.mCredential)
        //        .setApplicationName("PlanPenny").build();
        createEvent(currentProject);
    }

    public static GoogleEventCreater newInstance(String currentProject) {
        GoogleEventCreater event = new GoogleEventCreater(currentProject);

        return event;
    }
    public void setDelete(boolean delete){
        this.deleteCalendarProject = delete;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Project project = dc.getProjectDB().getProject(currentProjectTitle);
        Log.d("Fragment_Controller", "testing createEvent i Google Event Creater");
        if(deleteCalendarProject){
                    // Delete an event
            try {
                service.calendarList().delete(calendarID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            // Create a new calendar
            com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
            calendar.setSummary(currentProjectTitle);
            calendar.setTimeZone("Europe/Copenhagen");

            // Insert the new calendar
            Calendar createdCalendar = null;
            try {
                createdCalendar = service.calendars().insert(calendar).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(createdCalendar.getSummary());
            System.out.println(createdCalendar.getId());
            for (Category c: project.getCategoryList()) {

                // Opretter Event
                Event event = new Event()
                        .setDescription(project.getTitle())
                        .setSummary(c.getCategoryTitle())
                        .setColorId("4");

                // Sætter starttidspunkt
                DateTime startDateTime = new DateTime(c.getStartDate().toGoogleDateTime());
                EventDateTime start = new EventDateTime()
                        .setDateTime(startDateTime)
                        .setTimeZone("Europe/Copenhagen");
                event.setStart(start);

                // Sætter sluttidspunkt
                DateTime endDateTime = new DateTime(c.getEndDate().toGoogleDateTime());
                EventDateTime end = new EventDateTime()
                        .setDateTime(endDateTime)
                        .setTimeZone("Europe/Copenhagen");
                event.setEnd(end);

            /*
                Hjemmesider:
                https://developers.google.com/google-apps/calendar/create-events
                https://developers.google.com/gdata/javadoc/com/google/gdata/client/Service
             */
                String calendarId = currentProjectTitle;
                calendarID = createdCalendar.getId();
                try {
                    service.events().insert(createdCalendar.getId(), event).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("GoogleEventCreator Exception!");
                }
            }
        }
        this.deleteCalendarProject = false;
        return null;
    }

    public ArrayList<Date> getBoundaryDates(String projectName){
        ArrayList<Date> boundaries = new ArrayList<Date>();
        Project project = findCurrentProject(projectName);
        boundaries.add(project.getStartDate());
        boundaries.add(project.getEndDate());
        return boundaries;
    }

    public void createEvent(String currentProjectTitle) {
        this.currentProjectTitle = currentProjectTitle;
    }
    @Override
    protected void onPostExecute(Void ie) {
        Log.d("GoogleEventCreater", "Event created: %s\n");
    }

    private Project findCurrentProject(String projectName) {
        for (Project p : dc.getProjects())
            if (p.getTitle().equals(projectName)) return p;
        return null;
    }
    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onCancelled() {

    }
}

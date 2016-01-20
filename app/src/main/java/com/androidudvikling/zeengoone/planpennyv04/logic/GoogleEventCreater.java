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
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jonasandreassen on 19/01/16.
 */
public class GoogleEventCreater extends AsyncTask<Void, Void, Void>{
    private static String currentProjectTitle;
    HttpTransport transport    = AndroidHttp.newCompatibleTransport();
    JacksonFactory jsonFactory = new JacksonFactory();
    private DataLogic dc = Fragment_Controller.dc;
    private com.google.api.services.calendar.Calendar service = Fragment_Controller.mService;
    //private boolean deleteCalendarProject = false;
    //private Context ctx;

    public GoogleEventCreater(String currentProject) {
        //this.ctx = Fragment_Controller.ctx;
        currentProjectTitle = currentProject;
        // Initialize Calendar service with valid OAuth credentials
        service = new com.google.api.services.calendar.Calendar.Builder(transport, jsonFactory, Fragment_Controller.mCredential)
                .setApplicationName("applicationName").build();
    }
    public static GoogleEventCreater newInstance(String currentProject){
        currentProjectTitle = currentProject;
        return new GoogleEventCreater(currentProjectTitle);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Project project = dc.getProjectDB().getProject(currentProjectTitle);
        Log.d("Fragment_Controller", "testing createEvent i Google Event Creater");
        /*if(deleteCalendarProject){
            /*
                Hjemmesider:
                https://developers.google.com/google-apps/calendar/create-events
                https://developers.google.com/gdata/javadoc/com/google/gdata/client/Service

                try {
                    service.events().insert("primary", event).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("GoogleEventCreator Exception!");
                }
        }
        else{*/
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
                try {
                    service.events().insert("primary", event).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("GoogleEventCreator Exception!");
                }
                // int eventId = FindEventId(currentProjectTitle);
                // System.out.println("Test af eventID metode: " + eventId);
            }
       /* }
        this.deleteCalendarProject = false;*/
        return null;
    }

    public ArrayList<Date> getBoundaryDates(String projectName){
        ArrayList<Date> boundaries = new ArrayList<Date>();
        Project project = findCurrentProject(projectName);
        boundaries.add(project.getStartDate());
        boundaries.add(project.getEndDate());
        return boundaries;
    }

    @Override
    protected void onPostExecute(Void ie) {
        Log.d("GoogleEventCreater", "Event created: %s\n");
    }

    private Project findCurrentProject(String projectName) {
        for (Project p : dc.getProjects()) if (p.getTitle().equals(projectName)) return p;
        return null;
    }
    /*
    Benyttes ikke lige nu, ikke implementeret

    public void setDelete(boolean delete){
        this.deleteCalendarProject = delete;
    }
    */
    /*
    Benyttes ikke endnu, tiltænkt for at fjerne Events der er oprettet

    private int FindEventId(String eventtitle) {

        Uri eventUri;
        eventUri = Uri.parse("content://com.android.calendar/events");
        int result = 0;
        String projection[] = { "_id", "title" };
        Cursor cursor = ctx.getContentResolver().query(eventUri, null, null, null,
                null);

        if (cursor.moveToFirst()) {

            String calName;
            String calID;

            int nameCol = cursor.getColumnIndex(projection[1]);
            int idCol = cursor.getColumnIndex(projection[0]);
            do {
                calName = cursor.getString(nameCol);
                calID = cursor.getString(idCol);

                if (calName != null && calName.contains(eventtitle)) {
                    result = Integer.parseInt(calID);
                }

            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }
    */
}

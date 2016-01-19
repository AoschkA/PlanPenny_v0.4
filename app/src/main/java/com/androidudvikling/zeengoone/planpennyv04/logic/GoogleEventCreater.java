package com.androidudvikling.zeengoone.planpennyv04.logic;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Service;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by jonasandreassen on 19/01/16.
 */
public class GoogleEventCreater {
    private DataLogic dl = new DataLogic();
    private Service service;

    public GoogleEventCreater() {
        initializeService();
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
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }

    }

    private Project findCurrentProject(String projectName) {
        for (Project p : dl.getProjects())
            if (p.getTitle().equals(projectName)) return p;
        return null;
    }

    private void initializeService() {
        service = new Service() {
            @Override
            public Service startAsync() {
                return null;
            }

            @Override
            public boolean isRunning() {
                return false;
            }

            @Override
            public State state() {
                return null;
            }

            @Override
            public Service stopAsync() {
                return null;
            }

            @Override
            public void awaitRunning() {

            }

            @Override
            public void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {

            }

            @Override
            public void awaitTerminated() {

            }

            @Override
            public void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {

            }

            @Override
            public Throwable failureCause() {
                return null;
            }

            @Override
            public void addListener(Listener listener, Executor executor) {

            }
        };
    }
}

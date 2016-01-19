package com.androidudvikling.zeengoone.planpennyv04;


import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;
import com.androidudvikling.zeengoone.planpennyv04.logic.OfflineFilehandler;
import com.androidudvikling.zeengoone.planpennyv04.logic.OnlineFilehandler;
import com.androidudvikling.zeengoone.planpennyv04.logic.PreferenceManager;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Fragment_Controller extends AppCompatActivity {
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR_READONLY};
    public static DataLogic dc = new DataLogic();
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;
    private PreferenceManager pManager = new PreferenceManager(this);
    private ListView projekt_liste_view;
    private DrawerLayout pennydrawerLayout;
    private ActionBarDrawerToggle penny_Projekt_Drawer_Toggle;
    private FloatingActionButton drawerFab;
    private String curProjectName;
    private ArrayList categoryList;
    private ArrayList<List<String>> categoryListOfPlans;
    private Firebase uRef;
    private OfflineFilehandler off;
    private OnlineFilehandler onf;
    private Context ctx;
    private Boolean landscape;
    private ArrayAdapter<String> adapter;
    private Handler filehand = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getApplicationContext();
        landscape = ctx.getResources().getBoolean(R.bool.is_landscape);

        if (!landscape) {
            setContentView(R.layout.main_activity_controller_portrait);
            // Sæt drawer elementer til ProjektVisning
            projekt_liste_view = (ListView) findViewById(R.id.penny_projekt_drawer_list);
            adapter = new ArrayAdapter<>(this, R.layout.skuffe_projekt_liste_element, dc.getProjectsTitles());
            projekt_liste_view.setAdapter(adapter);

            // Sæt Drawer op
            pennydrawerLayout = (DrawerLayout) findViewById(R.id.penny_projekt_drawer_layout);
            penny_Projekt_Drawer_Toggle = new ActionBarDrawerToggle(this, pennydrawerLayout,
                    R.string.drawer_close, R.string.drawer_open) {
                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            pennydrawerLayout.setDrawerListener(penny_Projekt_Drawer_Toggle);
            // Onclick listener til projektlistemenuen
            projekt_liste_view.setOnItemClickListener(new DrawerItemClickListener());

            // Instantiere Firebase
            Firebase.setAndroidContext(this);

            // Instantiere ProgressDialog
            mProgress = new ProgressDialog(this);
            mProgress.setMessage("Calling Google Calendar API ...");

            //Opsæt offline filehandler
            off = new OfflineFilehandler(ctx);

            // Opsæt online filehandler
            onf = new OnlineFilehandler(ctx);

            //Lav et timestamp check
            filehand.post(run);

            //Opsæt actionbar burgermenu og titel
            this.setTitle(getString(R.string.app_title));
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setLogo(R.drawable.penny_logo);
            getSupportActionBar().setDisplayUseLogoEnabled(true);


            // Instantiere credentials og service objekt
            SharedPreferences googleSettings = getPreferences(Context.MODE_PRIVATE);
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff())
                    .setSelectedAccountName(googleSettings.getString(PREF_ACCOUNT_NAME, null));
        } else {
            setContentView(R.layout.main_activity_controller_landscape);
            // Instantiere credentials og service objekt
            SharedPreferences googleSettings = getPreferences(Context.MODE_PRIVATE);
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff())
                    .setSelectedAccountName(googleSettings.getString(PREF_ACCOUNT_NAME, null));
        }
    }

    // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    public void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Sæt drawer elementer til ProjektVisning
        adapter = new ArrayAdapter<>(this,
                R.layout.skuffe_projekt_liste_element, dc.getProjectsTitles());
        projekt_liste_view.setAdapter(adapter);
        return true;
    }

    public void opdaterDrawer() {
        // Sæt drawer elementer til ProjektVisning
        adapter.notifyDataSetChanged();
        projekt_liste_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!landscape) {
            // show menu when menu button is pressed
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.projekt_oversigt, menu);
            return true;
        } else {
            return false;
        }
    }

    public void menuClick(MenuItem menuitem) {
        if (menuitem.getTitle().equals("Indstillinger")) {
            FragmentSettings fragment = new FragmentSettings();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "indstillinger").commit();
        } else if (menuitem.getTitle().equals("Hjælp")) {
            startActivity(new Intent(this, ActivityHelp.class));
        }
    }

    public void drawerFabClick(View v) {
        dc.setProjectList(off.getAllProjects());

        Intent CreateProject = new Intent(Fragment_Controller.this, PopCreateProject.class);
        startActivityForResult(CreateProject, 2);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.penny_projekt_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //fh.saveAllData(dc.getProjects());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (penny_Projekt_Drawer_Toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!landscape) {
            penny_Projekt_Drawer_Toggle.syncState();
        }
    }

    // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */

    private void refreshResults() {
        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                new MakeRequestTask(mCredential).execute();
                Toast.makeText(ctx, "Forbindelse til Google Oprettet: Login: " + mCredential.getSelectedAccountName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx, "Forbindelse til Google IKKE oprettet, tjek internetforbindelse", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        startActivityForResult(
                mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                connectionStatusCode,
                Fragment_Controller.this,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    Runnable run = new Runnable() {
        @Override
        public void run()
        {
            if(onf.getTimeStamp() == null) {
                onf.checkTimeStamp();
                filehand.postDelayed(run,100);
            }else{
                System.out.println(onf.getTimeStamp());
                off.checkUsedProject(onf.getTimeStamp());
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kører onActivityResult for google play service
        // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android
        /**
         * Called when an activity launched here (specifically, AccountPicker
         * and authorization) exits, giving you the requestCode you started it with,
         * the resultCode it returned, and any additional data from it.
         * @param requestCode code indicating which activity result is incoming.
         * @param resultCode code indicating the result of the incoming
         *     activity result.
         * @param data Intent (containing result data) returned by incoming
         *     activity result.
         */
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        mCredential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != RESULT_OK) {
                    chooseAccount();
                }
                break;
        }
        // køre onActivityResult igen efter google request til google services
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            // Projekt
            Bundle bundle = data.getExtras();
            curProjectName = bundle.getString("NyDl");
            dc.addProject(curProjectName);
            System.out.println(dc.getProjects().get(dc.getProjects().size() - 1).getTitle().toString());

            //Kategorier
            Intent CreateCategory = new Intent(Fragment_Controller.this, PopCreateCategory.class)
                    .putExtra("ProjectName", curProjectName);
            startActivityForResult(CreateCategory, 3);
        }

        if (resultCode == 3) {
            Bundle bundle = data.getExtras();

            categoryList = (ArrayList) bundle.get("CategoryNames");
            for (int i = 0; i < categoryList.size(); i++) {
                dc.addCategory(curProjectName, categoryList.get(i).toString());

            }

            Intent CreateCategory = new Intent(Fragment_Controller.this, PopCreatePlan.class)
                    .putExtra("ProjectName", curProjectName).putExtra("Categories", categoryList);
            startActivityForResult(CreateCategory, 4);
        }

        if (resultCode == 4) {
            Bundle bundle = data.getExtras();
            categoryListOfPlans = (ArrayList<List<String>>) bundle.get("Plans");

            // For kategorier
            for (int i = 0; i < categoryListOfPlans.size(); i++) {
                // For planer
                for (int k = 0; k < categoryListOfPlans.get(i).size(); k++) {
                    System.out.println(categoryListOfPlans.get(i).size());
                    //Henter datoer for givne kategori
                    String dates[] = categoryListOfPlans.get(i).get(k).split("-");
                    String start = dates[0];
                    String end = dates[1];

                    System.out.println(start + " " + end);
                    //Splitter igen ved komma
                    String date_start[] = start.split(",");
                    String date_end[] = end.split(",");

                    // Laver om til dato klassen:
                    Date start_date = new Date(Integer.parseInt(date_start[2]), Integer.parseInt(date_start[1]), Integer.parseInt(date_start[0]));
                    Date end_date = new Date(Integer.parseInt(date_end[2]), Integer.parseInt(date_end[1]), Integer.parseInt(date_end[0]));


                    dc.addPlan(curProjectName, categoryList.get(i).toString(), start_date, end_date, "#ff6600", date_end[3]);
                }
            }

            onf.saveAllProjects(dc.getProjects());
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //Fold draweren ind
            pennydrawerLayout.closeDrawer(Gravity.LEFT);
            ViewPagerFragment vpFragment = new ViewPagerFragment().newInstance(position);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, vpFragment, "viewpager").commit();
        }
    }

    // Hel klasse taget fra https://developers.google.com/google-apps/calendar/quickstart/android
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

        /**
         * Background task to call Google Calendar API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        // Forklaring fra google: https://developers.google.com/google-apps/calendar/quickstart/android

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }
            return eventStrings;
        }


        @Override
        protected void onPreExecute() {
            if (!landscape) mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            if (!landscape) mProgress.hide();
            if (output == null || output.size() == 0) {
            } else {
                output.add(0, "Data retrieved using the Google Calendar API:");
            }
        }

        @Override
        protected void onCancelled() {
            if (!landscape) mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            Fragment_Controller.REQUEST_AUTHORIZATION);
                }
            }
        }
    }
}





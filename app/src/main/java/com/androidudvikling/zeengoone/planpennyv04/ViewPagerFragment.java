package com.androidudvikling.zeengoone.planpennyv04;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;
import com.androidudvikling.zeengoone.planpennyv04.logic.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

/**
 * Created by zeengoone on 1/15/16.
 */
public class ViewPagerFragment extends Fragment implements SensorEventListener {
    public static final String TAG = ViewPagerFragment.class.getName();
    public static SensorManager sensorManager;
    public static Sensor sensor;
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private ArrayList<String> tabMaaneder = new ArrayList<String>();
    private int currentMonth;
    private int projectNumber;
    private Calendar cal = new GregorianCalendar();
    private DataLogic dc = Fragment_Controller.dc;
    private ViewPagerAdapter adapter;
    private long lastUpdate = 0;

    public static void vpChangeCurrentItem(int position){
        tabLayout.setScrollPosition(position, 0f, true);
        viewPager.setCurrentItem(position);
    }

    public ViewPagerAdapter getAdapter(){
        return adapter;
    }

    public ViewPagerFragment newInstance(int position){
        ViewPagerFragment temp = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("Project_Number", position);
        temp.setArguments(args);

        // Gemmer nuværende lokation
        Log.d("Location save", Integer.toString(position) + " - under newInstance");
        Fragment_Controller.pManager.saveAppLocation(position);

        return temp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectNumber = getArguments().getInt("Project_Number");
        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private Calendar addMonth() {
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, currentMonth++);
        return cal;
    }

    private void populateTabList(int years){
        int month;
        int year;
        for(int i = 0;i < years;i++){
            month = addMonth().get(Calendar.MONTH);
            month = month+1;
            year = cal.get(Calendar.YEAR);
            tabMaaneder.add(new SimpleDateFormat("MMM").format(cal.getTime()));
        }
    }
    public void flipTab(int value, String orientation) {
        Log.d("APP STATUS", "ORIENTATION: "+orientation);
        int tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
        Log.d("Tab Location", Integer.toString(tabLocation+value));
        vpChangeCurrentItem(tabLocation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // forbered view, viewpager og set adapter
        // Læg to års måneder ind i tab-listen
        int aar = Calendar.getInstance().get(Calendar.YEAR);
        int maaned = Calendar.getInstance().get(Calendar.MONTH);
        int dag = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Date dato = new Date(aar,maaned,dag);
        populateTabList(dc.getRemainingMonthsForProject(dato, dc.getProjects().get(projectNumber).getTitle()));
        View root = inflater.inflate(R.layout.view_pager, container, false);
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.setTabFields(tabMaaneder);
        adapter.setProject(projectNumber);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) root.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                // Gemmer Tab location
                Log.d("Location save", "TAB "+tab.getPosition());
                Fragment_Controller.pManager.saveTabLocation(dc.getProjects().get(projectNumber).getTitle(),tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("SENSOR", "Sensor opened");
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Åbner det rigtige projekt
        int appLocation = Fragment_Controller.pManager.loadAppLocation();
        Log.d("App Location", Integer.toString(appLocation));
        if (appLocation==-1) {
            Log.d("ERROR", "Couldn't reload project - project not found");
        }
        else {
            newInstance(appLocation);
        }

        // åbner den rigtige tab
        Log.d("APP STATUS", "RESUMED - In ViewPagerFragment");
        int tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
        Log.d("Tab Location", Integer.toString(tabLocation));
        vpChangeCurrentItem(tabLocation);
    }

    /* Sensor metoder
     * Kun onSensorChanged benyttes
     */

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        // Opdater kun hvert sekund
        if ((curTime - lastUpdate) > 1000) {

            // log hver update (ikke nødvendigt mere)
        /* Log.e("SENSOR UPDATE", "Type: "+ event.sensor.getType() + "\n"+
                "Name: "+ event.sensor.getName()+ "\n"+
                "Vendor: "+ event.sensor.getVendor() + "\n"+
                "Time: "+ event.timestamp + "\n"+
               "Precession: "+ event.accuracy);
        */
            float x = event.values[0];
            // til at teste x værdi (ikke nødvendigt mere)
            // Log.i("SENSOR value (x)", Float.toString(x));

            // Ændre if statements hvis den er for følsom, eller ikke følsom nok
            if (x > 4) {
                Log.e("APP STATUS", "ORIENTATION: " + "LEFT");
                int tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
                Log.d("Tab flip (L)", Integer.toString(tabLocation - 1));
                vpChangeCurrentItem(tabLocation - 1);
                lastUpdate = curTime;
            } else if (x < -4) {
                Log.e("APP STATUS", "ORIENTATION: " + "RIGHT");
                int tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
                Log.d("Tab flip (R)", Integer.toString(tabLocation + 1));
                vpChangeCurrentItem(tabLocation + 1);
                lastUpdate = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    // Åbning og lukning af sensor
    @Override
    public void onStop(){
        super.onStop();
        Log.d("SENSOR", "Sensor closed");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("SENSOR", "Sensor opened");
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onPause(){
        super.onPause();
        Log.d("SENSOR", "Sensor closed");
        sensorManager.unregisterListener(this);
    }
}

package com.androidudvikling.zeengoone.planpennyv04;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by zeengoone on 1/15/16.
 */
public class ViewPagerFragment extends Fragment implements SensorEventListener {
    public static final String TAG = ViewPagerFragment.class.getName();
    public static int projectLocation = -1;
    public static int tabLocation = -1;
    public static SensorManager sensorManager;
    public static Sensor sensor;
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private static int projectNumber;
    private static DataLogic dc = Fragment_Controller.dc;
    private static ViewPagerAdapter adapter;
    private ArrayList<String> tabMaaneder = new ArrayList<String>();
    private int currentMonth;
    private Calendar cal = new GregorianCalendar();
    private long lastUpdate = 0;

    public static void vpChangeCurrentItem(int position){
        tabLayout.setScrollPosition(position, 0f, true);
        viewPager.setCurrentItem(position);
        // Gemmer Tab location
        Log.d("Location save", "TAB " + tabLocation);
        Fragment_Controller.pManager.saveTabLocation(dc.getProjects().get(projectNumber).getTitle(), tabLocation);
    }

    public static void updateViewPagerList(){
        dc = Fragment_Controller.dc;
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

    private void setSensor(){
        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (Fragment_Controller.pManager.loadSettings().getSyncSetting(2)) {
            Log.d("SENSOR", "Sensor opened");
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            if (adapter!=null) updateViewPagerList();
        }
    }

    public ViewPagerFragment newInstance(int position){
        ViewPagerFragment temp = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("Project_Number", position);
        temp.setArguments(args);

        projectLocation = position;
        // Gemmer nuværende lokation
        Log.d("Location save", Integer.toString(projectLocation));
        Fragment_Controller.pManager.saveAppLocation(projectLocation);
        return temp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectNumber = getArguments().getInt("Project_Number");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // forbered view, viewpager og set adapter
        // Læg to års måneder ind i tab-listen
        Fragment_Controller.insertMainFab();
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
                tabLocation = tab.getPosition();

                // Gemmer Tab location
                Log.d("Location save", "TAB " + tabLocation);
                Fragment_Controller.pManager.saveTabLocation(dc.getProjects().get(projectNumber).getTitle(), tabLocation);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        setSensor();
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("VIEWPAGE", "onResume");
        Fragment_Controller.insertMainFab();
        if (Fragment_Controller.pManager.loadSettings().getSyncSetting(2)) {
            Log.d("SENSOR", "Sensor opened");
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            if (adapter!=null) updateViewPagerList();
        }


        // Åbner det rigtige projekt
        int appLocation = Fragment_Controller.pManager.loadAppLocation();
        Log.d("App Location", Integer.toString(appLocation));
        if (appLocation>-1) newInstance(appLocation);

        // åbner den rigtige tab
        int tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
        Log.d("Tab Location", Integer.toString(tabLocation));
        if (tabLocation>-1) vpChangeCurrentItem(tabLocation);
    }

    /* Sensor metoder
     * Kun onSensorChanged benyttes
     */

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        // Opdater kun hvert sekund
        if ((curTime - lastUpdate) > 500) {

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
                tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
                Log.d("Tab flip (L)", Integer.toString(-1));
                vpChangeCurrentItem(tabLocation - 1);
                lastUpdate = curTime;
            } else if (x < -4) {
                Log.e("APP STATUS", "ORIENTATION: " + "RIGHT");
                tabLocation = Fragment_Controller.pManager.loadTabLocation(dc.getProjects().get(projectNumber).getTitle());
                Log.d("Tab flip (R)", Integer.toString(1));
                vpChangeCurrentItem(tabLocation + 1);
                lastUpdate = curTime;
            }
        }
    }

    // Skal være der for at sensor virker - lad den være tom
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    // Åbning og lukning af sensor
    @Override
    public void onStop(){
        super.onStop();
        Log.d("VIEWPAGE", "onStop");

        Log.d("SENSOR", "Sensor closed");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("VIEWPAGE", "onStart");
        if (Fragment_Controller.pManager.loadSettings().getSyncSetting(2)) {
            Log.d("SENSOR", "Sensor opened");
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            if (adapter!=null) updateViewPagerList();
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        Log.d("VIEWPAGE", "onPause");

        Log.d("SENSOR", "Sensor closed");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("VIEWPAGE", "onDestroy");
        Fragment_Controller.removeMainFab();
        Log.d("SENSOR", "Sensor closed");
        sensorManager.unregisterListener(this);
    }
}

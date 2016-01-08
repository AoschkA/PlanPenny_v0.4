package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fragment_Controller extends AppCompatActivity {
    private ArrayList<String> projekt_liste = new ArrayList<>();
    private ArrayList<String> tabMaaneder = new ArrayList<String>();
    private ListView projekt_liste_view;
    private DrawerLayout pennydrawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private ActionBarDrawerToggle penny_Projekt_Drawer_Toggle;
    public static DataLogic dc = new DataLogic();
    private Calendar cal = new GregorianCalendar();
    private int currentMonth;
    private FloatingActionButton drawerFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_controller);

        // Lav default projekter at teste på
        dc.addDefaultProjects();
        // Gør listen klar og smid den i projekt listen
        for(Project p:dc.getProjects()){
            projekt_liste.add(p.getTitle());
        }
        projekt_liste_view = (ListView) findViewById(R.id.penny_projekt_drawer_list);
        projekt_liste_view.setAdapter(new ArrayAdapter<String>(this,
                R.layout.skuffe_projekt_liste_element, projekt_liste));

        // Opsæt actionbar burgermenu
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setLogo(R.drawable.penny_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        //Opsæt coordinatorlayout:
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.penny_projekt_drawer_coordinatorlayout);

        // Sæt Drawer op
        pennydrawerLayout = (DrawerLayout) findViewById(R.id.penny_projekt_drawer_layout);
        penny_Projekt_Drawer_Toggle = new ActionBarDrawerToggle(
                this,
                pennydrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close);
        pennydrawerLayout.setDrawerListener(penny_Projekt_Drawer_Toggle);

        // Onclick listener til projektlistemenuen
        projekt_liste_view.setOnItemClickListener(new DrawerItemClickListener());


        // Læg to års måneder ind i tab-listen
        populateTabList(24);
        // Få fat i ViewPager og set dens pageradapter så den kan vise items

    }

    private void setupDrawer() {
        penny_Projekt_Drawer_Toggle.setDrawerIndicatorEnabled(true);
    }

    private Calendar addMonth(){
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

    public void drawerFabClick(View v){
        // Virker ikke?
        System.out.println("fab blev klikket på");


        startActivity(new Intent(Fragment_Controller.this,PopCreateProject.class));
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
    public void onPause(){
        super.onPause();
        //fh.saveAllData(dc.getProjects());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        dc.clearProjects();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        penny_Projekt_Drawer_Toggle.onConfigurationChanged(newConfig);
        penny_Projekt_Drawer_Toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (penny_Projekt_Drawer_Toggle.onOptionsItemSelected(item)) { return true; }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // Toast.makeText(Fragment_Controller.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            //Fold draweren ind
            pennydrawerLayout.closeDrawer(Gravity.LEFT);
            final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setOffscreenPageLimit(0);
            final PennyFragmentPagerAdapter.NyViewPagerAdapter adapter = new PennyFragmentPagerAdapter.NyViewPagerAdapter(getSupportFragmentManager());
            adapter.setTabFields(tabMaaneder);
            adapter.setProject(position);
            viewPager.setAdapter(adapter);
            final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        penny_Projekt_Drawer_Toggle.syncState();
    }
}

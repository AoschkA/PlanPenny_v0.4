package com.androidudvikling.zeengoone.planpennyv04;

import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Fragment_Controller extends AppCompatActivity {
    private ArrayList<String> projekt_liste = new ArrayList<String>();
    private ArrayList<String> projekter_test = new ArrayList<String>();
    private ArrayList<String> tabMaaneder = new ArrayList<String>();
    private ListView projekt_liste_view;
    private DrawerLayout pennydrawerLayout;
    private ActionBarDrawerToggle penny_Projekt_Drawer_Toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_controller);

        // Læs projektlister fra disken
        try {
            projekt_liste = readProjects();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Gør listen klar og smid den i projekt listen
        projekt_liste_view = (ListView) findViewById(R.id.penny_projekt_drawer_list);
        projekt_liste_view.setAdapter(new ArrayAdapter<String>(this,
                R.layout.penny_drawer_listview_item, projekt_liste));

        // Opsæt actionbar
        getSupportActionBar().setLogo(R.drawable.penny_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        startKategorier();
        populateTabList(2);
        // Få fat i ViewPager og set dens pageradapter så den kan vise items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                Fragment_Controller.this, tabMaaneder, projekter_test));

        // Sender tabLayoutet videre til viewpageren
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void startKategorier(){
        for(int i = 0;i < 30;i++){
            projekter_test.add("kategori" + i);
        }
    }
    private void populateTabList(int years){
        for(int i = 0;i < years;i++){
            tabMaaneder.add("Januar");
            tabMaaneder.add("Februar");
            tabMaaneder.add("Marts");
            tabMaaneder.add("April");
            tabMaaneder.add("Maj");
            tabMaaneder.add("Juni");
            tabMaaneder.add("Juli");
            tabMaaneder.add("August");
            tabMaaneder.add("September");
            tabMaaneder.add("Oktober");
            tabMaaneder.add("November");
            tabMaaneder.add("December");
        }
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

    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        penny_Projekt_Drawer_Toggle.syncState();
    }*/

    @Override
    public void onPause(){
        super.onPause();
        try {
            writeProjects(projekt_liste);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
            writeProjects(projekt_liste);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        penny_Projekt_Drawer_Toggle.onConfigurationChanged(newConfig);
        penny_Projekt_Drawer_Toggle.syncState();
        //start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (penny_Projekt_Drawer_Toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(Fragment_Controller.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            pennydrawerLayout.closeDrawer(projekt_liste_view);
        }
    }
    protected void writeProjects(ArrayList<String> projectWriteList) throws IOException {
        File path = getDir("projekter", 0);
        File file = new File(path, "projektliste.pp");
        FileOutputStream stream = new FileOutputStream(file);
        for(String s:projectWriteList) {
            try {
                stream.write(s.getBytes());
                stream.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("FileWriteERROR: ", "Der opstod en fejl ved skrivning til fil");
            }
        }
        stream.close();
    }

    protected ArrayList<String> readProjects() throws IOException {
        File path = getDir("projekter", 0);
        File file = new File(path, "projektliste.pp");
        BufferedReader br = null;
        ArrayList<String> projectList = new ArrayList<String>();
        try
        {
            StringBuffer project_input = new StringBuffer();
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null)
            {
                projectList.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.d("FILHÅNDTERING: ", "Filen findes ikke endnu");
            projectList.add("Projekt Test");
        }
        for(String s:projectList){
            System.out.println(s);
        }
        return projectList;
    }
    private void testSetupProjekter(){

    }
}

package test;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;

import entities.Plan;
import logic.FileHandler;

/**
 * Created by alexandervpedersen on 23/11/15.
 */
@SuppressWarnings("deprecation")
public class testLayout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> projektListe = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FileHandler filehandler = new FileHandler(this);

        projektListe.add("Projekt 1");
        projektListe.add("Projekt 2");
        projektListe.add("Projekt 3");
        projektListe.add("Projekt 4");
        projektListe.add("Projekt 5");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Test for projekt
        /*
        filehandler.makeProject("Nulte projekt");
        Project[] projects = filehandler.getProjectlist();
        String[] projectnames = new String[projects.length];
        */

        // Kategori test
        /*
        filehandler.makeCategory("Project 1", "Projekt 1 kategori");
        filehandler.makeCategory("Project 0", "Projekt 0 kategori");
        filehandler.makeCategory("Project 0", "Et nyt projekt");
        Category[] categories = filehandler.getCategoryList("Project 0");
        String[] categorynames = new String[categories.length];
        */


        // Plan test

        Date date1 = new Date(2015,04,25);
        Date date2 = new Date(2015,07,17);
        filehandler.makePlan("Project 0", "Category 0", date1, date2);

        Plan[] plans = filehandler.getPlanList("Project 0", "Category 0");
        String[] plandates = new String[plans.length];


        //Få alle datoerne for en plan
        for(int i=0;i<plandates.length;i++){
            plandates[i] =
                    Integer.toString(plans[i].getStart_date().getDay()) + "/" +
                    Integer.toString(plans[i].getStart_date().getMonth()) + "/" +
                    Integer.toString(plans[i].getStart_date().getYear()) + " - " +
                    Integer.toString(plans[i].getEnd_date().getDay()) + "/" +
                    Integer.toString(plans[i].getEnd_date().getMonth()) + "/" +
                    Integer.toString(plans[i].getEnd_date().getYear());
        }
        createListview(plandates);

        //Få alle kategori navnene for et given projekt
        /*
        for(int i=0;i<categories.length;i++){
            categorynames[i] = categories[i].categoryTitle;
        }
        createListview(categorynames);
        /*


        //Få alle projektnavnene
        /*
        for(int i=0;i<projects.length;i++) {
            projectnames[i] = projects[i].title;
        }
        createListview(projectnames);
        */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_projectlist) {
            TextView temp = (TextView) findViewById(R.id.contextText);
            temp.setText("Det virker!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            TextView temp = (TextView) findViewById(R.id.contextText);
            temp.setText("Det virker!");

        } else if (id == R.id.nav_slideshow) {
            TextView temp = (TextView) findViewById(R.id.contextText);
            temp.setText("hejsa hvor det går!");

        } else if (id == R.id.nav_manage) {
            TextView temp = (TextView) findViewById(R.id.contextText);
            temp.setText("jowda!");

        } else if (id == R.id.nav_share) {
            TextView temp = (TextView) findViewById(R.id.contextText);
            temp.setText("jesus!");

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createListview(String[] elementer){

        // Adapteren
        // ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        ListView listview = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementer);
        listview.setAdapter(adapter);
    }
}


package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;
import com.androidudvikling.zeengoone.planpennyv04.logic.OfflineFilehandler;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Controller extends AppCompatActivity {
    public static DataLogic dc = new DataLogic();
    private static ArrayList<String> projekt_liste = new ArrayList<>();
    private ListView projekt_liste_view;
    private DrawerLayout pennydrawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private ActionBarDrawerToggle penny_Projekt_Drawer_Toggle;
    private FloatingActionButton drawerFab;
    private String curProjectName;
    private ArrayList categoryList;
    private ArrayList<List<String>> categoryListOfPlans;
    private Firebase uRef;
    private OfflineFilehandler of;
    private Context ctx;

    public static void populateDrawer(){
        projekt_liste.clear();
        for(Project p:dc.getProjects()){
            projekt_liste.add(p.getTitle());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_controller);

        ctx = getApplicationContext();

        // Lav default projekter at teste på
        //dc.addDefaultProjects();
        // Gør listen klar og smid den i projekt listen
        populateDrawer();

        // Instantier Firebase
        Firebase.setAndroidContext(this);
        uRef = new Firebase("https://g26planpenny.firebaseio.com/");

        uRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Opsæt filehandler

        of = new OfflineFilehandler(ctx);


        // Sæt drawer elementer til ProjektVisning
        projekt_liste_view = (ListView) findViewById(R.id.penny_projekt_drawer_list);
        projekt_liste_view.setAdapter(new ArrayAdapter<String>(this,
                R.layout.skuffe_projekt_liste_element, projekt_liste));

        // Opsæt actionbar burgermenu og titel
        this.setTitle(getString(R.string.app_title));
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

        // Få fat i ViewPager og set dens pageradapter så den kan vise items

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.projekt_oversigt, menu);
        return true;
    }

    public void menuClick(MenuItem menuitem){
        if (menuitem.getTitle().equals("Indstillinger")) {
            Log.d("Click","Indstillinger");
            FragmentSettings fragment = new FragmentSettings();
            SettingsAdapter s_adapter = new SettingsAdapter(this, new Settings(getApplicationContext()));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "indstillinger").commit();
        }
       else if (menuitem.getTitle().equals("Hjælp")) {
            startActivity(new Intent(this, ActivityHelp.class));
        }
    }

    private void setupDrawer() {
        penny_Projekt_Drawer_Toggle.setDrawerIndicatorEnabled(true);
    }

    public ViewPager getViewPager() {
         ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        return mViewPager;
    }

    public void drawerFabClick(View v){
        Project test = dc.getProjects().get(0);
        of.saveProject(test);
        Project project = of.getProject("Redesign of company");
        System.out.println(project.getTitle());
        System.out.println(project.getCategoryList().get(0).getCategoryTitle());
        System.out.println(project.getCategoryList().get(0).getPlanList().get(0).toString());
        System.out.println(project.getCategoryList().get(1).getCategoryTitle());
        System.out.println(project.getCategoryList().get(1).getPlanList().get(0).toString());

        Intent CreateProject = new Intent(Fragment_Controller.this,PopCreateProject.class);
        startActivityForResult(CreateProject, 2);
    }

    @Override
    public void onBackPressed() {
        populateDrawer();
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



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        penny_Projekt_Drawer_Toggle.syncState();
    }

    @Override

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

                    if (resultCode == 2) {
                        // Projekt
                        Bundle bundle = data.getExtras();
                        curProjectName= bundle.getString("NyDl");
                        dc.addProject(curProjectName);
                        System.out.println(dc.getProjects().get(dc.getProjects().size()-1).getTitle().toString());

                        //Kategorier
                        Intent CreateCategory = new Intent(Fragment_Controller.this,PopCreateCategory.class)
                                .putExtra("ProjectName", curProjectName);
                        startActivityForResult(CreateCategory, 3);
                    }

                    if (resultCode == 3){
                        Bundle bundle = data.getExtras();

                        categoryList = (ArrayList) bundle.get("CategoryNames");
                         for(int i=0;i<categoryList.size();i++) {
                             dc.addCategory(curProjectName,categoryList.get(i).toString());

                        }

                        Intent CreateCategory = new Intent(Fragment_Controller.this,PopCreatePlan.class)
                                .putExtra("ProjectName", curProjectName).putExtra("Categories", categoryList);
                        startActivityForResult(CreateCategory, 4);
                    }

                    if(resultCode == 4){
                        Bundle bundle = data.getExtras();
                        categoryListOfPlans = (ArrayList<List<String>>) bundle.get("Plans");

                        // For kategorier
                        for(int i=0;i<categoryListOfPlans.size();i++) {
                            // For planer
                            for (int k = 0; k < categoryListOfPlans.get(i).size();k++) {
                                System.out.println(categoryListOfPlans.get(i).size());
                                //Henter datoer for givne kategori
                                String dates[] = categoryListOfPlans.get(i).get(k).split("-");
                                String start = dates[0];
                                String end = dates[1];

                                System.out.println(start + " " +  end);
                                //Splitter igen ved komma
                                String date_start[] = start.split(",");
                                String date_end[] = end.split(",");

                                // Laver om til dato klassen:
                                Date start_date = new Date(Integer.parseInt(date_start[2]),Integer.parseInt(date_start[1]),Integer.parseInt(date_start[0]));
                                Date end_date = new Date(Integer.parseInt(date_end[2]),Integer.parseInt(date_end[1]), Integer.parseInt(date_end[0]));


                                dc.addPlan(curProjectName,categoryList.get(i).toString(),start_date,end_date,"#ff6600", date_end[3]);
                            }
                        }
                    }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // Toast.makeText(Fragment_Controller.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            //Fold draweren ind
            pennydrawerLayout.closeDrawer(Gravity.LEFT);
            ViewPagerFragment vpFragment = new ViewPagerFragment().newInstance(position);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, vpFragment, "viewpager").commit();
        }
    }
}

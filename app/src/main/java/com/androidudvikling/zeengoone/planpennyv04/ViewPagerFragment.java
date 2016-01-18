package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
public class ViewPagerFragment extends Fragment {
    public static final String TAG = ViewPagerFragment.class.getName();
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private ArrayList<String> tabMaaneder = new ArrayList<String>();
    private int currentMonth;
    private int projectNumber;
    private Calendar cal = new GregorianCalendar();
    private DataLogic dc = Fragment_Controller.dc;
    private ViewPagerAdapter adapter;

    public static void vpChangeCurrentItem(int position){
        tabLayout.setScrollPosition(position, 0f, true);
        viewPager.setCurrentItem(position);
    }

    public ViewPagerFragment newInstance(int position){
        ViewPagerFragment temp = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("Project_Number", position);
        temp.setArguments(args);
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
}

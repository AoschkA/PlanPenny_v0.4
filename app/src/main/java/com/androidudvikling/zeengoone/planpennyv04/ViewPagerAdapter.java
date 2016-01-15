package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by zeengoone on 1/15/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> listToTabs;
    private int currentProject;
    private int Page_Count;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // beregn antallet af tabs
        public void setTabFields(ArrayList<String> tabTitles) {
            Page_Count = tabTitles.size();
            populateTabs(tabTitles);
        }
        public void setProject(int projectNumber){
            currentProject = projectNumber;
        }

        private void populateTabs(ArrayList<String> maaneder){
            listToTabs = maaneder;
        }
        @Override
        public int getCount() {
            return Page_Count;
        }

        @Override
        public Fragment getItem(int position) {
            // find fragmentet til den givne position og gem position og projekt i bundle
            Bundle args = new Bundle();
            args.putInt(Fragment_Gantt.PROJECT_KEY, currentProject);
            args.putInt(Fragment_Gantt.POSITION_KEY, position);
            return Fragment_Gantt.newInstance(args);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listToTabs.get(position) + " " + Fragment_Gantt.yearToTab(position).getYear();

        }
    }


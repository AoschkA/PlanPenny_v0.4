package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.ProjectDB;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zengoone on 09/12/15.
 */

public class PennyFragmentPagerAdapter extends Fragment {
    private static int PAGE_COUNT;
    public static final String TAG = PennyFragmentPagerAdapter.class.getName();

    public static PennyFragmentPagerAdapter newInstance() {
        return new PennyFragmentPagerAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_fragment_controller, container, false);

        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        /** Important: Must use the child FragmentManager or you will see side effects. */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        return root;
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> listToTabs;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        public void setTabFields(ArrayList<String> tabTitles) {
            PAGE_COUNT = tabTitles.size();
            populateTabs(tabTitles);
        }
        private void populateTabs(ArrayList<String> maaneder){
            listToTabs = maaneder;
        }
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt(Fragment_Gantt.POSITION_KEY, position);
            return Fragment_Gantt.newInstance(args);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listToTabs.get(position) + " " + Fragment_Gantt.yearToTab(position).getYear();
        }
    }
}
/*
    public PennyFragmentPagerAdapter(FragmentManager fm, Context context, DataLogic dc) {
        super(fm);
        this.dc = dc;
        this.context = context;
    }
    @Override
    public int getItemPosition (Object object) { return POSITION_NONE; }



    @Override
    public int getCount() {

    }

    @Override
    public Fragment getItem(int position) {
        return Fragment_Gantt.newInstance(position, this.dc);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generer tab-title baseret på position i arrayet
        return listToTabs.get(position).toString();
    }*/
package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zengoone on 09/12/15.
 */

public class PennyFragmentPagerAdapter extends Fragment {
    public static final String TAG = PennyFragmentPagerAdapter.class.getName();
    private static int PAGE_COUNT;

    public static PennyFragmentPagerAdapter newInstance() {
        return new PennyFragmentPagerAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // forbered view, viewpager og set adapter
        View root = inflater.inflate(R.layout.main_activity_controller, container, false);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        viewPager.setAdapter(new NyViewPagerAdapter(getChildFragmentManager()));
        return root;
    }

    public static class NyViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> listToTabs;
        private int currentProject;
        public NyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        // beregn antallet af tabs
        public void setTabFields(ArrayList<String> tabTitles) {
            PAGE_COUNT = tabTitles.size();
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
            return PAGE_COUNT;
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
}
package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by zengoone on 09/12/15.
 */
public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int PAGE_COUNT;
    private String tabTitles[];
    private Context context;
    private ArrayList listToShow;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<String> maaneder, ArrayList list) {
        super(fm);
        this.context = context;
        PAGE_COUNT = maaneder.size();
        tabTitles = new String[maaneder.size()];
        populateTabs(maaneder);
        listToShow = list;
    }

    private void populateTabs(ArrayList<String> maaneder){
        for(int i = 0;i < maaneder.size();i++){
            tabTitles[i] = maaneder.get(i);
        }
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment_Gantt.newInstance(position + 1, listToShow);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generer tab-title baseret på position i arrayet
        return tabTitles[position];
    }
}
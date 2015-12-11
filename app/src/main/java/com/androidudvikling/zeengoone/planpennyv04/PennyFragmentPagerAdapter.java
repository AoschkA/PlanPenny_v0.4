package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.ProjectDB;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.util.ArrayList;

/**
 * Created by zengoone on 09/12/15.
 */
public class PennyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int PAGE_COUNT;
    private Context context;
    private ArrayList listToTabs;
    private static DataLogic dc;

    public PennyFragmentPagerAdapter(FragmentManager fm, Context context, DataLogic dc) {
        super(fm);
        this.dc = dc;
        this.context = context;
    }

    public void setTabFields(ArrayList<String> tabTitles){
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
        return Fragment_Gantt.newInstance(position, this.dc);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generer tab-title baseret på position i arrayet
        return listToTabs.get(position).toString();
    }
}
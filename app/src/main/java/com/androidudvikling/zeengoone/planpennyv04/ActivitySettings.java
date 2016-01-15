package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;
import com.androidudvikling.zeengoone.planpennyv04.entities.UserSettings;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;
import com.androidudvikling.zeengoone.planpennyv04.logic.PreferenceManager;

/**
 * Created by Jonas Praem on 11/01/16.
 */
public class ActivitySettings extends Activity {

    private Settings indstillinger;
    private DataLogic data = new DataLogic();
    private PreferenceManager preferenceManager = new PreferenceManager();
    private UserSettings userSettings;
    private SettingsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indstillinger = new Settings(this);
        readData();
        updateSettings();
        setContentView(R.layout.expandable_list_layout);
        // Læg listen ind i arrayadapteren for kategorier
        adapter = new SettingsAdapter(this, indstillinger);
        // Lav listviewet og setadapter til adapteren lavet herover
        final ExpandableListView settingsView = (ExpandableListView) findViewById(R.id.kategoriliste_udv);
        settingsView.setAdapter(adapter);
        setContentView(R.layout.activity_settings);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutSettings);
        layout.addView(settingsView);
    }

    private void readData() {
        userSettings = preferenceManager.loadSettings();
        data.setSortType_project(userSettings.getSortSetting1());
        data.setSortType_category(userSettings.getSortSetting2());
    }
    private void updateSettings() {
        RadioButton r11 = (RadioButton) adapter.getChild(0, 0);
        RadioButton r12 = (RadioButton) adapter.getChild(0, 1);
        RadioButton r13 = (RadioButton) adapter.getChild(0, 2);
        RadioButton r14 = (RadioButton) adapter.getChild(0, 3);
        RadioButton r21 = (RadioButton) adapter.getChild(1, 0);
        RadioButton r22 = (RadioButton) adapter.getChild(1, 1);
        RadioButton r23 = (RadioButton) adapter.getChild(1, 2);
        RadioButton r24 = (RadioButton) adapter.getChild(1, 3);
        Switch s1 = (Switch) adapter.getChild(2,0);

        switch (userSettings.getSortSetting1()) {
            case 1:
                r11.setPressed(true);
                r12.setPressed(false);
                r13.setPressed(false);
                r14.setPressed(false);
                break;
            case 2:
                r11.setPressed(false);
                r12.setPressed(true);
                r13.setPressed(false);
                r14.setPressed(false);
                break;
            case 3:
                r11.setPressed(false);
                r12.setPressed(false);
                r13.setPressed(true);
                r14.setPressed(false);
                break;
            case 4:
                r11.setPressed(false);
                r12.setPressed(false);
                r13.setPressed(false);
                r14.setPressed(true);
                break;
        }
        switch (userSettings.getSortSetting2()) {
            case 1:
                r21.setPressed(true);
                r22.setPressed(false);
                r23.setPressed(false);
                r24.setPressed(false);
                break;
            case 2:
                r21.setPressed(false);
                r22.setPressed(true);
                r23.setPressed(false);
                r24.setPressed(false);
                break;
            case 3:
                r21.setPressed(false);
                r22.setPressed(false);
                r23.setPressed(true);
                r24.setPressed(false);
                break;
            case 4:
                r21.setPressed(false);
                r22.setPressed(false);
                r23.setPressed(true);
                r24.setPressed(false);
                break;
        }
        if (userSettings.getSyncSetting1())
            s1.setPressed(true);
        else
            s1.setPressed(false);
    }


}

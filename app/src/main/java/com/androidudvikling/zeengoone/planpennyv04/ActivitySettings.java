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

import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;

/**
 * Created by Jonas Praem on 11/01/16.
 */
public class ActivitySettings extends Activity {

    private Settings indstillinger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indstillinger = new Settings(this);
        setContentView(R.layout.expandable_list_layout);
        // Læg listen ind i arrayadapteren for kategorier
        SettingsAdapter adapter = new SettingsAdapter(this, indstillinger);
        // Lav listviewet og setadapter til adapteren lavet herover
        final ExpandableListView settingsView = (ExpandableListView) findViewById(R.id.kategoriliste_udv);
        settingsView.setAdapter(adapter);
        setContentView(R.layout.activity_settings);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutSettings);
        layout.addView(settingsView);



    }

}

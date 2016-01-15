package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

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

    }
}

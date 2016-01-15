package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonas Praem on 11/01/16.
 */
public class ActivitySettings extends Activity {

    ListView list;
    ArrayList<View> viewList = new ArrayList<View>();
    TextView textView1;
    TextView textView2;
    Switch switchSynchronize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        list = (ListView) findViewById(R.id.settingsList);
        textView1 = (TextView) findViewById(R.id.textViewProject);
        textView2 = (TextView) findViewById(R.id.textViewCategory);
        switchSynchronize = (Switch) findViewById(R.id.synchronize);

        populateListView();
    }

    private void populateListView() {
        View[] views = {textView1, textView2, switchSynchronize};
        String[] strings = {"1", "2", "3"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.settings_list,
                strings);

        list.setAdapter(adapter);

    }


}

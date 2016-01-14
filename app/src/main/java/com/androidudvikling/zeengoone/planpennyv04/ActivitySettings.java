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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        list = (ListView) findViewById(R.id.settingsList);
        ArrayAdapter<View> adapter = new ArrayAdapter<View>(
                this,
                android.R.layout.simple_list_item_1,
                viewList);
        list.setAdapter(adapter);

        TextView textView1 = (TextView) findViewById(R.id.textViewProject);
        TextView textView2 = (TextView) findViewById(R.id.textViewCategory);
        Switch switchSynchronize = (Switch) findViewById(R.id.synchronize);

        list.addView(textView1, 0);
        list.addView(textView2, 1);
        list.addView(switchSynchronize, 2);


    }


}

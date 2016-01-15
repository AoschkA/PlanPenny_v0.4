package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        List<String> test = new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("3");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                test);

        list.setAdapter(adapter);

    }


}

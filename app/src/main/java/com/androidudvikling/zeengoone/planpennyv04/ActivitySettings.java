package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Jonas Praem on 11/01/16.
 */
public class ActivitySettings extends Activity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        list = (ListView) findViewById(R.id.settingsList);

    }
}

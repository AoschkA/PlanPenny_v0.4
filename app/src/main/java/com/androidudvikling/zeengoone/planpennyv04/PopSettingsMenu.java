package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by jonasandreassen on 11/01/16.
 */
public class PopSettingsMenu extends Activity {

    TextView sortProjects;
    TextView sortCategories;
    Switch synchronize;
    TextView help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_settings);

        //Opsætter fremvisnings matricen
        DisplayMetrics dpm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpm);

        double scaleHeight = 0.8;
        double scaleWidth = 0.8;
        int width = (int) ((dpm.widthPixels) * scaleWidth);
        int height = (int) ((dpm.heightPixels) * scaleHeight);
        getWindow().setLayout(width, height);

        //Afdæmpet baggrund til fokus
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_style));

        sortProjects = (TextView) findViewById(R.id.textViewProject);
        sortCategories = (TextView) findViewById(R.id.textViewCategory);
        synchronize = (Switch) findViewById(R.id.synchronize);
        help = (TextView) findViewById(R.id.textViewHelp);
    }
    public void OnBtnClicked(View v){
        switch (v.getId()) {
            case R.id.textViewProject: Intent i = getIntent();
                i.putExtra("NP", "project");

        }
    }
}

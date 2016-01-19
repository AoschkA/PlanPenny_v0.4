package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.logic.GoogleEventCreater;

import java.util.ArrayList;

public class PopCreateEvent extends Activity implements View.OnClickListener{
    private static GoogleEventCreater googleEventCreater;
    TextView tv_Event;
    Button button_regret;
    Button button_ok;
    String current_projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_create_event);
        Intent intent = getIntent();

        //Opsætter fremvisnings matricen
        DisplayMetrics dpm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpm);

        // Definerer dimensionerne på pop
        double scaleHeight = 0.5;
        double scaleWidth = 0.8;
        int width = (int) ((dpm.widthPixels) * scaleWidth);
        int height = (int) ((dpm.heightPixels) * scaleHeight);

        //Sætter layout dimensionerne på pop
        getWindow().setLayout(width, height);

        //Afdæmpet baggrund til fokus
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //Sætter stilen for pop
        getWindow().setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.border_style, null));

        // Finder views
        tv_Event = (TextView) findViewById(R.id.textViewEvent);
        button_regret = (Button) findViewById(R.id.buttonRegret);
        button_ok = (Button) findViewById(R.id.buttonOK);

        // sets onClickListeners
        button_regret.setOnClickListener(this);
        button_ok.setOnClickListener(this);

        // Finder information fra intent
        Bundle bundle = intent.getExtras();
        current_projectName = bundle.getString("currentProjectName");

        if(googleEventCreater==null)
            googleEventCreater = new GoogleEventCreater();

        ArrayList<Date> boundaries = googleEventCreater.getBoundaryDates(current_projectName);

        // sætter information om projectet i textviewet
        String info = "Projektet "+ current_projectName + " vil oprette et google event fra " +
                boundaries.get(0).toString() +
                " til " + boundaries.get(1).toString() +
                " \n vil du fortsætte?";
        tv_Event.setText(info);

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==button_regret.getId())
            finish();
        else if (v.getId()==button_ok.getId()) {
            // opret event
            googleEventCreater.createEvent(current_projectName);
            Log.d("PopCreateEvent", "kører onclick i popcreateevent");
        }
    }
}

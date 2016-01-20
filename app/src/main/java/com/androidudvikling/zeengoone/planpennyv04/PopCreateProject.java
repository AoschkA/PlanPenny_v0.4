package com.androidudvikling.zeengoone.planpennyv04;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alexandervpedersen on 08/01/16.
 */
public class PopCreateProject extends Activity  {

    private TextView projectText;
    private EditText projectName;
    private TextView errorText;
    private RelativeLayout contentLayout;
    private Button projectCreateBtn;
    private String projectTextFromUser;
    private DataLogic dl;
    private ArrayList<String> prevProjectnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        prevProjectnames = bundle.getStringArrayList("ProjectNames");
        System.out.println("Modtag til create project: " + prevProjectnames.get(0));

        // Sætter layout
        setContentView(R.layout.create_pop_window);

        //Opsætter fremvisnings matricen
        DisplayMetrics dpm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpm);

        // Definerer dimensionerne på pop
        double scaleHeight = 0.8;
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
        getWindow().setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.google_style, null));

        // Top Text
        projectText = (TextView) findViewById(R.id.textNytProjekt);

        //Content
        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);

        //Content opret
        projectName = (EditText) findViewById(R.id.createEdit);
        errorText = (TextView) findViewById(R.id.errorText);
        projectCreateBtn = (Button) findViewById(R.id.buttonCreateProject);

        }


    // Effekt til button
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

        //Knap i content
       public void OnBtnClicked(View v){
           buttonEffect(v);
           switch (v.getId()) {
               // Knap opret projekt
               case R.id.buttonCreateProject: {
                   if (projectName.getText().toString().isEmpty()) {
                       errorText.setText("Projektnavnet skal mindst have et bogstav.");
                   } else if(isProjectTaken()) {
                       errorText.setText("Projekt navnet er allerede brugt");
                   }else{
                       projectTextFromUser = projectName.getText().toString();
                       Intent i = getIntent();
                       i.putExtra("NyDl", projectTextFromUser);
                       setResult(2,i);
                       finish();
                   }
               }break;
           }
        }

        public boolean isProjectTaken(){
            for(int k = 0 ; k<prevProjectnames.size() ; k++) {
                if (prevProjectnames.get(k).equals(projectName.getText().toString())) {
                    return true;
                }
            }
            return false;
        }
}




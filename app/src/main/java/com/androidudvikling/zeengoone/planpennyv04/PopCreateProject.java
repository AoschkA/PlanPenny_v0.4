package com.androidudvikling.zeengoone.planpennyv04;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.io.Serializable;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.border_style));

        // Top Text
        projectText = (TextView) findViewById(R.id.textNytProjekt);

        //Content
        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);

        //Content opret
        projectName = (EditText) findViewById(R.id.createEdit);
        errorText = (TextView) findViewById(R.id.errorText);
        projectName.getLayoutParams().width = 330;
        projectCreateBtn = (Button) findViewById(R.id.buttonCreateProject);

        }

        //Knap i content
       public void OnBtnClicked(View v){
           switch (v.getId()) {
               // Knap opret projekt
               case R.id.buttonCreateProject: {
                   if (projectName.getText().toString().isEmpty()) {
                       errorText.setText("Projektnavnet skal mindst have et bogstav.");
                   } else {
                       projectTextFromUser = projectName.getText().toString();
                   }
               }

           }

           Intent i = getIntent();
           i.putExtra("NyDl", projectTextFromUser);
           setResult(2,i);
           finish();

        }
}




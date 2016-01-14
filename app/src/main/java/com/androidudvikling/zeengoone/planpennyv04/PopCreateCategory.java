package com.androidudvikling.zeengoone.planpennyv04;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.util.ArrayList;

/**
 * Created by alexandervpedersen on 08/01/16.
 */
public class PopCreateCategory extends Activity  {

    private TextView categoryText;
    private EditText categoryname;
    private TextView errorText;
    private RelativeLayout contentLayout;
    private Button categoryCreateBtn;
    private String categoryTextFromUser;
    private ListView categoryList;
    public ArrayList <String> categoryNames = new ArrayList<String>();
    private String curProject;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        curProject = bundle.getString("ProjectName");


        // Sætter layout
        setContentView(R.layout.category_pop_window);

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
        categoryText = (TextView) findViewById(R.id.textNytProjekt);
        categoryText.setText(curProject);

        //Content
        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);

        //Content opret
        categoryname = (EditText) findViewById(R.id.createEdit);
        errorText = (TextView) findViewById(R.id.errorText);
        categoryname.getLayoutParams().width = 330;
        categoryCreateBtn = (Button) findViewById(R.id.buttonCreateProject);
        categoryList = (ListView) findViewById(R.id.categoryList);


        }

        //Knap i content
       public void OnBtnClicked(View v){
        buttonEffect(v);
           switch (v.getId()) {
               // Knap opret projekt
               case R.id.buttonCreateProject: {
                   if (categoryname.getText().toString().isEmpty()) {
                       errorText.setText("Kategori navnet skal mindst have et bogstav.");
                   } else {
                       boolean previousCategory = false;
                       for(int i = 0;i<categoryNames.size();i++){
                           String catName = categoryNames.get(i).toString();
                           System.out.println(catName);
                           System.out.println(categoryname.getText().toString());

                           if(catName.equals(categoryname.getText().toString())) {
                               System.out.println("Ja!");
                               previousCategory = true;
                           }
                       }

                       if(previousCategory == true) {
                           errorText.setText("Denne kategori er allerede oprettet.");
                       } else {
                           categoryTextFromUser = categoryname.getText().toString();
                           System.out.println(categoryTextFromUser);
                           categoryNames.add(categoryTextFromUser);
                           categoryname.setText("");

                           // Tilføjer ArrayAdapter til at tage imod arraylist til fremvisning.
                           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                   this,
                                   android.R.layout.simple_list_item_1,
                                   categoryNames);
                           categoryList.setAdapter(arrayAdapter);

                       }
                   }
               }break;

               case R.id.buttonDone: {
                   if (categoryNames.isEmpty()){
                       errorText.setText("Ingen kategorier er oprettet.");
                   }else{
                       System.out.println("Farvel");
                       Intent i = getIntent();
                       i.putExtra("CategoryNames", categoryNames);
                       setResult(3, i);
                       finish();
                   }

               }break;
           }
           Fragment_Controller.populateDrawer();
        }


    // Effekt til button
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP);
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
}




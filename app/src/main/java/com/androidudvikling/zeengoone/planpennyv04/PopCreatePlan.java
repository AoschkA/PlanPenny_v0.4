package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alexandervpedersen on 08/01/16.
 */
public class PopCreatePlan extends Activity implements OnItemSelectedListener{

    private TextView planText;
    private TextView errorText;
    private RelativeLayout contentLayout;
    private Button planCreateBtn, startDateBtn, endDateBtn;
    private String categoryTextFromUser;
    private ListView planList;
    public ArrayList <String> curCategories = new ArrayList<String>();
    ArrayList<List<String>> listOfPlansInCategories = new ArrayList<List<String>>();
    private String curProject;
    private int year_x,month_x,day_x,year_y,month_y,day_y;
    static final int start = 0;
    static final int end = 1;
    private Spinner categoryChooser;
    private String chosenCategory = "not set";
    int catId = 0;
    boolean startIsSet = false,endIsSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        curProject = bundle.getString("ProjectName");
        curCategories = bundle.getStringArrayList("Categories");

        //Indsætter de pre lavede kategorier i vores Arrayliste af lister:
        for(int count = 0; count < curCategories.size(); count++)  {
            listOfPlansInCategories.add(new ArrayList<String>());
        }




        // Sætter layout
        setContentView(R.layout.plan_pop_window);

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

        // Top tekst
        planText = (TextView) findViewById(R.id.textNytProjekt);
        planText.setText(curProject);

        //Content
        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);

        //Content opret
        errorText = (TextView) findViewById(R.id.ErrorText);
        planCreateBtn = (Button) findViewById(R.id.buttonCreateProject);
        planList = (ListView) findViewById(R.id.categoryList);
        errorText.getLayoutParams().height= 60;
        startDateBtn = (Button) findViewById(R.id.btnStart);
        endDateBtn = (Button) findViewById(R.id.btnEnd);
        categoryChooser = (Spinner) findViewById(R.id.categoryChooser);

        // Lidt til dato
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        year_y = cal.get(Calendar.YEAR);
        month_y = cal.get(Calendar.MONTH);
        day_y = cal.get(Calendar.DAY_OF_MONTH);


        // Spinner click listener
        categoryChooser.setOnItemSelectedListener(this);

        // Adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, curCategories);

        // Drop down
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter sat på spinner
        categoryChooser.setAdapter(dataAdapter);
    }





        //Knap i content
       public void OnBtnClicked(View v){
           switch (v.getId()) {
               // Knap opret projekt
               case R.id.btnEnd: {
                   showDialog(end);
               }break;

               case R.id.btnStart: {
                   showDialog(start);
               }break;


               case R.id.buttonCreateProject: {
                   if (!startIsSet) {
                       errorText.setText("Du skal vælge en start dato.");
                   }else if(!endIsSet){
                       errorText.setText("Du skal vælge en slut dato.");

                   }else if (year_x <= year_y) {
                           if ((year_x == year_y && month_x <= month_y) || year_x < year_y) {
                               if ((month_x == month_y && day_x <= day_y) || month_x < month_y || year_x < year_y) {
                                   // Hvis alt er ok, opret:
                                   int catID = catId;
                                   String plan =
                                           day_x + "," + month_x + "," + year_x + "-"
                                                   + day_y + "," + month_y + "," + year_y;
                                   System.out.println(catID);
                                   listOfPlansInCategories.get(catID).add(plan);

                                   errorText.setText("Planen: " + plan + " er oprettet.");
                               }else{errorText.setText("Start dag er efter slut dag");}
                           }else{errorText.setText("Start måned er efter slut måned");}
                       }else{errorText.setText("Start år er efter slut år");}
               }break;

               case R.id.buttonDone: {
                   String errorCategories = null;
                   for(int i=0;i<listOfPlansInCategories.size();i++)
                   {
                       if(listOfPlansInCategories.get(i).isEmpty()) {
                           if (errorCategories == null) {
                               errorCategories = curCategories.get(i).toString() + ",";
                           } else {
                               errorCategories += curCategories.get(i).toString() + ",";
                           }
                       }

                   }
                   if (errorCategories != null){
                       errorText.setText("Følgende kategorier mangler planer: " + errorCategories);
                   }else{
                       System.out.println("Farvel");
                       Intent i = getIntent();
                       Bundle bundle = new Bundle();
                       bundle.putSerializable("Plans",listOfPlansInCategories);
                       i.putExtra("Plans",listOfPlansInCategories);
                       setResult(4, i);
                       finish();
                   }

               }break;

           }
           Fragment_Controller.populateDrawer();
        }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == start)
            return new DatePickerDialog(this,dpickerListnerStart,year_x,month_x,day_x);
        if(id == end)
            return new DatePickerDialog(this,dpickerListnerEnd,year_y,month_y,day_y);

        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListnerStart
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear+1;
            day_x = dayOfMonth;
            startDateBtn.setText(day_x + "-" + month_x + "-" + year_x);
            startIsSet = true;
        }
    };

        private DatePickerDialog.OnDateSetListener dpickerListnerEnd
                = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    year_y = year;
                    month_y = monthOfYear + 1;
                    day_y = dayOfMonth;
                    endDateBtn.setText(day_y + "-" + month_y + "-" + year_y);
                    endIsSet = true;

            }
        };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);

        // Navnet defineret
        String item = parent.getItemAtPosition(position).toString();

        // Valgte spinner item
        Toast.makeText(parent.getContext(), "Valgt: " + item, Toast.LENGTH_LONG).show();
        catId = position;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // Hvis ønsket implementeret.
        // TODO Auto-generated method stub
    }



    }





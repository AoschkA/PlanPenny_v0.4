package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alexandervpedersen on 08/01/16.
 */
public class PopCreatePlan extends Activity implements OnItemSelectedListener{

    static final int start = 0;
    static final int end = 1;
    public ArrayList <String> curCategories = new ArrayList<String>();
    ArrayList<List<String>> listOfPlansInCategories = new ArrayList<List<String>>();
    int catId = 0;
    boolean startIsSet = false,endIsSet = false;
    String overlap;
    private TextView planText;
    private TextView errorText;
    private RelativeLayout contentLayout;
    private Button planCreateBtn, startDateBtn, endDateBtn;
    private String categoryTextFromUser;
    private ListView planList;
    private String curProject;
    private int year_x,month_x,day_x,year_y,month_y,day_y;
    private Spinner categoryChooser;
    private String chosenCategory = "not set";
    private EditText planTitle;
    private String spinnerSelectedName;
    private Button mDateButton;
    public Calendar mCalendar;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    DialogFragment dateFragment;

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
        mCalendar = Calendar.getInstance();


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
        getWindow().setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.google_style, null));

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
        planTitle = (EditText) findViewById(R.id.planTextEdit);

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

        //Titlens tekstboks bliver sat.

    }

        //Knap i content
       public void OnBtnClicked(View v){
           buttonEffect(v);
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
                   }else if(!endIsSet) {
                       errorText.setText("Du skal vælge en slut dato.");
                   }
                   else {
                       checkOverlap(day_x,month_x,year_x,day_y,month_y,year_y,catId);
                   }if(overlap != "unset") {
                       errorText.setText("Datoen overlapper en forrige plan for denne kategori: " + overlap);
                       }else if (year_x <= year_y) {
                           if ((year_x == year_y && month_x <= month_y) || year_x < year_y) {
                               if ((month_x == month_y && day_x <= day_y) || month_x < month_y || year_x < year_y) {

                                   // Hvis alt er ok, opret:
                                   int catID = catId;
                                   String planName = planTitle.getText().toString();
                                   String plan =
                                           day_x + "," + month_x + "," + year_x + "-"
                                                   + day_y + "," + month_y + "," + year_y + "," + planName;
                                   System.out.println(catID);
                                   listOfPlansInCategories.get(catID).add(plan);
                                   planTitle.setText("");

                                   errorText.setText("Planen: " + plan + " er oprettet.");
                               }else{errorText.setText("Start dag er efter slut dag");}
                           }else{errorText.setText("Start måned er efter slut måned");}
                       }else{errorText.setText("Start år er efter slut år");}
               }break;

               case R.id.buttonDone: {
                   String errorCategories = null;
                   int errorCat = 0;
                   String currentErrorCategory = null;
                   for(int i=0;i<listOfPlansInCategories.size();i++)
                   {
                       if(listOfPlansInCategories.get(i).isEmpty()) {
                           errorCat++;
                           currentErrorCategory = curCategories.get(i);
                           if (errorCategories == null) {
                               errorCategories = curCategories.get(i).toString() + ",";
                           } else {
                               errorCategories += curCategories.get(i).toString() + ",";
                           }
                       }

                   }
                   if(errorCat == 1){
                       System.out.println(currentErrorCategory);
                       System.out.println(spinnerSelectedName);
                       if(currentErrorCategory == spinnerSelectedName){
                           if (!startIsSet) {
                               errorText.setText("Du skal vælge en start dato.");
                           }else if(!endIsSet) {
                               errorText.setText("Du skal vælge en slut dato.");
                           }
                           else {
                               checkOverlap(day_x,month_x,year_x,day_y,month_y,year_y,catId);
                           }if(overlap != "unset") {
                               errorText.setText("Datoen overlapper en forrige plan for denne kategori: " + overlap);
                           }else if (year_x <= year_y) {
                               if ((year_x == year_y && month_x <= month_y) || year_x < year_y) {
                                   if ((month_x == month_y && day_x <= day_y) || month_x < month_y || year_x < year_y) {

                                       // Hvis alt er ok, opret:
                                       int catID = catId;
                                       String planName = planTitle.getText().toString();
                                       String plan =
                                               day_x + "," + month_x + "," + year_x + "-"
                                                       + day_y + "," + month_y + "," + year_y + "," + planName;
                                       System.out.println(catID);
                                       listOfPlansInCategories.get(catID).add(plan);
                                       createProject();

                                       errorText.setText("Planen: " + plan + " er oprettet.");
                                   }else{errorText.setText("Start dag er efter slut dag");}
                               }else{errorText.setText("Start måned er efter slut måned");}
                           }else{errorText.setText("Start år er efter slut år");}
                       }

                   }
                   else if (errorCategories != null && !(errorCat == 1 && currentErrorCategory == spinnerSelectedName) ){
                       errorText.setText("Følgende kategorier mangler planer: " + errorCategories);
                   }else{

                       Intent i = getIntent();
                       Bundle bundle = new Bundle();
                       bundle.putSerializable("Plans", listOfPlansInCategories);
                       i.putExtra("Plans",listOfPlansInCategories);
                       setResult(4, i);
                       finish();
                   }

               }break;

           }
       }


    public void createProject(){
        Intent i = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Plans", listOfPlansInCategories);
        i.putExtra("Plans",listOfPlansInCategories);
        setResult(4, i);
        finish();
    }


    protected Dialog onCreateDialog(int id) {
        if (id == start) {
            DatePickerDialog sdpd = new DatePickerDialog(this, dpickerListnerStart, year_x, month_x, day_x);
            sdpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return sdpd;
        }
        if(id == end) {
            DatePickerDialog edpd = new DatePickerDialog(this, dpickerListnerEnd, year_x, month_x, day_x);
            edpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return edpd;
        }

        return null;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        // Navnet defineret
        spinnerSelectedName = parent.getItemAtPosition(position).toString();

        // Valgte spinner item
        Toast.makeText(parent.getContext(), "Valgt: " + spinnerSelectedName, Toast.LENGTH_LONG).show();
        catId = position;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // Hvis ønsket implementeret.
        // TODO Auto-generated method stub
    }

    // Bofore og after problem!
    public void checkOverlap(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, int catID) {
        Date trueDateStart = new Date(startYear, startMonth, startDay);
        Date trueDateEnd = new Date(endYear, endMonth, endDay);
        String overlapPlan = "unset";

        for (int i = 0; i < listOfPlansInCategories.get(catID).size(); i++) {

            String testDate = listOfPlansInCategories.get(catID).get(i);
            String[] testDateStartEnd = testDate.split("-");
            String[] testDateStart = testDateStartEnd[0].split(",");
            String[] testDateEnd = testDateStartEnd[1].split(",");

            Date testStart = new Date(Integer.parseInt(testDateStart[2]), Integer.parseInt(testDateStart[1]), Integer.parseInt(testDateStart[0]));
            Date testEnd = new Date(Integer.parseInt(testDateEnd[2]), Integer.parseInt(testDateEnd[1]), Integer.parseInt(testDateEnd[0]));


            if (!(trueDateStart.after(testEnd)&& trueDateEnd.after(testEnd)) && !(trueDateStart.after(testEnd) && trueDateEnd.before(testEnd))) {
                overlapPlan = listOfPlansInCategories.get(catID).get(i);
                break;
            }

        }
        overlap = overlapPlan;


    }

    public boolean checkTitleName(String name){
        for(int i=0;i<listOfPlansInCategories.get(catId).size();i++) {

            String testTitle = listOfPlansInCategories.get(catId).get(i);
            String[] testDateStartEnd = testTitle.split("-");
            String[] testDateEnd = testDateStartEnd[1].split(",");
            String title = testDateEnd[3];

            if(name.equals(title)) {
                return true;
            }

        }
        return false;
    }

   /* Skulle være implementeret istedet for showdialog da den er deprecated
   public void showstartDatePickerDialog(View v) {
        dateFragment = new StartDatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public void updateDateButtonText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(mCalendar.getTime());
        startDateBtn.setText(dateForButton);
    }

    class StartDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateButtonText();
        }
    }
    */

}






package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment{
    private static Date currentMonth;
    private static ArrayList<Category> tempCategories;
    private static Date tabMonth;
    private static int currentPosition = 0;
    private static DataLogic dl;
    private static String currentProject = "";
    private static int currentProjectNumber = 0;

    public static Fragment_Gantt newInstance(int page, DataLogic dc) {
        Fragment_Gantt gantt_fragment = new Fragment_Gantt();
        dl = dc;
        return gantt_fragment;
    }

    public static void setProject(String projectTitle){ currentProject = projectTitle; }
    public static void setProjectNumber(int projectNumber){ currentProjectNumber = projectNumber; }

    public static void beregnMaanedOgAar(int tab){
        currentPosition = tab;
        currentMonth = new Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        tabMonth = currentMonth.setDateMonth(tab);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_controller, container, false);
        // Læg listen ind i arrayadapteren for kategorier
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.penny_listview_item, R.id.listeelem_overskrift, dl.getProjects().get(currentProjectNumber).getCategoryTitlesList()) {

            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);
                if (dl.getProjects().get(currentProjectNumber).getCategoryList().get(position).getStartDatePlan(position).after(tabMonth)) {
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    billedeh.setImageResource(R.drawable.pil_hoejre);
                    billedev.setImageResource(R.drawable.pil_ingen);

                }
                else if (dl.getProjects().get(currentProjectNumber).getCategoryList().get(position).getStartDatePlan(position).before(tabMonth)){
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billedeh.setImageResource(R.drawable.pil_ingen);
                    billedev.setImageResource(R.drawable.pil_venstre);
                }
                else if(dl.getCategoriesForMonth(currentProject, tabMonth.getYear(), tabMonth.getMonth()).size() > 0) {
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billedeh.setImageResource(R.drawable.pil_ingen);
                    billedev.setImageResource(R.drawable.pil_ingen);
                }
                return view;
            }
        };

        // Lav listviewet og setadapter til adapteren lavet herover
        ListView lv = (ListView) view.findViewById(R.id.fragment_content);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Adapter click works" + " " + position, Toast.LENGTH_LONG).show();
            }
        });
        lv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment{
    private Date currentMonth;
    private static ArrayList<Category> tempCategories;
    private Date tabMonth;
    private String currentProject = "";
    private int currentProjectNumber = 0;
    private static DataLogic dl = Fragment_Controller.dc;
    ArrayAdapter<String> adapter;

    public static final String POSITION_KEY = "FragmentPositionKey";
    private int faneposition;
    public static final String PROJECT_KEY = "FragmentProjectKey";
    private int project;

    public static Fragment_Gantt newInstance(Bundle args) {
        Fragment_Gantt fragment = new Fragment_Gantt();
        fragment.setArguments(args);
        return fragment;
    }

    public void setProject(String projectTitle){ currentProject = projectTitle; }
    public void setProjectNumber(int projectNumber){ currentProjectNumber = projectNumber; }

    public void beregnMaanedOgAar(int tab){
        currentMonth = new Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        tabMonth = currentMonth.setDateMonth(tab);
    }
    public static Date yearToTab(int tab){
        Date tempDate = new Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tempDate.setDateMonth(tab);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projekt_visnings_liste, container, false);
        faneposition = getArguments().getInt(Fragment_Gantt.POSITION_KEY);
        project = getArguments().getInt(Fragment_Gantt.PROJECT_KEY);
        currentProject = dl.getProjects().get(project).getTitle();
        setProjectNumber(project);
        beregnMaanedOgAar(faneposition);
        // Læg listen ind i arrayadapteren for kategorier
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.projekt_kategori_liste_element, R.id.listeelem_overskrift, dl.getProjects().get(currentProjectNumber).getCategoryTitlesList()) {

            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);
                if(dl.getCategoryForMonth(currentProject, position, tabMonth.getYear(), tabMonth.getMonth()).size() > 0) {
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billedeh.setImageResource(R.drawable.pil_ingen);
                    billedev.setImageResource(R.drawable.pil_ingen);
                } else if (dl.getProjects().get(currentProjectNumber).getCategoryList().get(position).getStartDate().after(tabMonth) || dl.getProjects().get(currentProjectNumber).getCategoryList().get(position).getEndDate().after(tabMonth) ) {
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    billedeh.setImageResource(R.drawable.pil_hoejre);
                    billedev.setImageResource(R.drawable.pil_ingen);
                }
                else if (dl.getProjects().get(currentProjectNumber).getCategoryList().get(position).getStartDate().before(tabMonth) || dl.getProjects().get(currentProjectNumber).getCategoryList().get(position).getEndDate().before(tabMonth)){
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billedeh.setImageResource(R.drawable.pil_ingen);
                    billedev.setImageResource(R.drawable.pil_venstre);
                }
                else{
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
                Toast.makeText(getActivity(), "Kategori er trykket på #: " + " " + ++position, Toast.LENGTH_LONG).show();
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
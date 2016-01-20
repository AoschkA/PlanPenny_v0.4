package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment{
    public static final String POSITION_KEY = "FragmentPositionKey";
    public static final String PROJECT_KEY = "FragmentProjectKey";
    private static ArrayList<Category> tempCategories;
    private static DataLogic dl = Fragment_Controller.dc;
    private static KategoriAdapter adapter;
    protected TextView kategoriTitel, txthoejre, txtvenstre,kategori_element;
    protected EditText kategoriAendreTitel;
    protected Button btnkategoriGem, btnPlanGem,btnKategoriSlet;
    protected NumberPicker nbAar, nbMaaned, nbDag;
    private Date currentMonth;
    private Date tabMonth;
    private String currentProject = "";
    private int currentProjectNumber = 0;
    private int faneposition;
    private int project;
    private int lastGroup = -1;
    private FloatingActionButton fab;

    public static Fragment_Gantt newInstance(Bundle args) {
        Fragment_Gantt fragment = new Fragment_Gantt();
        fragment.setArguments(args);
        return fragment;
    }

    public static Date yearToTab(int tab){
        Date tempDate = new Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tempDate.setDateMonth(tab);
    }

    public static void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void setProject(String projectTitle){ currentProject = projectTitle; }

    public void setProjectNumber(int projectNumber){ currentProjectNumber = projectNumber; }

    public void beregnMaanedOgAar(int tab){
        currentMonth = new Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        tabMonth = currentMonth.setDateMonth(tab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_layout_project, container, false);
        System.out.println("Position KEY: " + getArguments().getInt(Fragment_Gantt.POSITION_KEY));
        System.out.println("Project KEY: " + getArguments().getInt(Fragment_Gantt.PROJECT_KEY));
        faneposition = getArguments().getInt(Fragment_Gantt.POSITION_KEY);
        project = getArguments().getInt(Fragment_Gantt.PROJECT_KEY);
        currentProject = dl.getProjects().get(project).getTitle();
        setProjectNumber(project);
        beregnMaanedOgAar(faneposition);

        // Læg listen ind i arrayadapteren for kategorier
        adapter = new KategoriAdapter(getActivity(), currentProjectNumber) {

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                View view = super.getGroupView(groupPosition, isExpanded, convertView, parent);
                dl = Fragment_Controller.dc;
                    if (dl.getCategoryForMonth(currentProject, groupPosition, tabMonth.getYear(), tabMonth.getMonth()).size() > 0) {
                        txthoejre = (TextView) view.findViewById(R.id.txtHoejrePil);
                        txtvenstre = (TextView) view.findViewById(R.id.txtVenstrePil);
                        txthoejre.setBackgroundResource(R.drawable.pil_ingen);
                        txtvenstre.setBackgroundResource(R.drawable.pil_ingen);
                        txthoejre.setText("");
                        txtvenstre.setText("");
                    } else if (dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getStartDate().after(tabMonth) || dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getEndDate().after(tabMonth)) {
                        txthoejre = (TextView) view.findViewById(R.id.txtHoejrePil);
                        txtvenstre = (TextView) view.findViewById(R.id.txtVenstrePil);
                        txthoejre.setBackgroundResource(R.mipmap.pil_hoejre_ny);
                        txtvenstre.setBackgroundResource(R.drawable.pil_ingen);
                        final Date dato = dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getStartDate();
                        final int currentMonth = dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).numberOfMonths(dato);
                        txthoejre.setText(dato.getDay() + "/" + dato.getMonth() + "-" + dato.getTwoDigitYear());
                        txtvenstre.setText("");
                        txthoejre.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ViewPagerFragment.vpChangeCurrentItem(currentMonth);
                            }
                        });
                    } else if (dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getStartDate().before(tabMonth) || dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getEndDate().before(tabMonth)) {
                        txthoejre = (TextView) view.findViewById(R.id.txtHoejrePil);
                        txtvenstre = (TextView) view.findViewById(R.id.txtVenstrePil);
                        txthoejre.setBackgroundResource(R.drawable.pil_ingen);
                        txtvenstre.setBackgroundResource(R.mipmap.pil_venstre_ny);
                        final Date dato = dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getStartDate();
                        final int currentMonth = dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).numberOfMonths(dato);
                        txthoejre.setText("");
                        txtvenstre.setText(dato.getDay() + "/" + dato.getMonth() + "-" + dato.getTwoDigitYear());
                        txtvenstre.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ViewPagerFragment.vpChangeCurrentItem(currentMonth);
                            }
                        });
                    } else {
                        txthoejre = (TextView) view.findViewById(R.id.txtHoejrePil);
                        txtvenstre = (TextView) view.findViewById(R.id.txtVenstrePil);
                        txthoejre.setBackgroundResource(R.drawable.pil_ingen);
                        txtvenstre.setBackgroundResource(R.drawable.pil_ingen);
                        txthoejre.setText("");
                        txtvenstre.setText("");
                    }
                    if (convertView == null) {
                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = inflater.inflate(R.layout.kategori_liste, parent, false);
                    }
                    kategori_element = (TextView) convertView.findViewById(R.id.txtkategori_liste_element);
                    kategori_element.setText(dl.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getCategoryTitle());
                return view;
            }
        };

        // Lav listviewet og setadapter til adapteren lavet herover
        final ExpandableListView projektListeView = (ExpandableListView) view.findViewById(R.id.expandable_list_projects_id);
        projektListeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);
                final int childPosition, groupPosition;
                boolean retVal = false;
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    childPosition = ExpandableListView.getPackedPositionChild(id);
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    //do your per-item callback here
                    final TextView planElement = (TextView) view.findViewById(R.id.plan_liste_element);
                    nbAar = (NumberPicker) view.findViewById(R.id.vaelgAar);
                    nbMaaned = (NumberPicker) view.findViewById(R.id.vaelgMaaned);
                    nbDag = (NumberPicker) view.findViewById(R.id.vaelgDag);
                    btnPlanGem = (Button) view.findViewById(R.id.btnGemPlan);
                    nbAar.setMinValue(Calendar.getInstance().get(Calendar.YEAR));
                    nbAar.setMaxValue(Calendar.getInstance().get(Calendar.YEAR) + 10);
                    nbMaaned.setMinValue(Calendar.getInstance().get(Calendar.MONTH) + 1);
                    nbMaaned.setMaxValue(12);
                    nbDag.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    nbDag.setMaxValue(31);
                    planElement.setVisibility(View.GONE);

                    btnPlanGem.setVisibility(View.VISIBLE);
                    nbAar.setVisibility(View.VISIBLE);
                    nbMaaned.setVisibility(View.VISIBLE);
                    nbDag.setVisibility(View.VISIBLE);
                    nbAar.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            Plan plandato = new Plan();
                            if(nbAar.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                                nbMaaned.setMinValue(Calendar.getInstance().get(Calendar.MONTH)+1);
                            }
                            else{
                                nbMaaned.setMinValue(1);
                            }
                            if(nbMaaned.getValue() == Calendar.getInstance().get(Calendar.MONTH)+1 && nbAar.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                                nbDag.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            }
                            else{
                                nbDag.setMinValue(1);
                            }
                            nbDag.setMaxValue(Plan.maxDaysInMonth(nbAar.getValue(), nbMaaned.getValue()));

                        }
                    });
                    nbMaaned.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            Plan plandato = new Plan();
                            if(nbAar.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                                nbMaaned.setMinValue(Calendar.getInstance().get(Calendar.MONTH)+1);
                            }
                            else{
                                nbMaaned.setMinValue(1);
                            }
                            if(nbMaaned.getValue() == Calendar.getInstance().get(Calendar.MONTH)+1 && nbAar.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                                nbDag.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            }
                            else{
                                nbDag.setMinValue(1);
                            }
                            nbDag.setMaxValue(Plan.maxDaysInMonth(nbAar.getValue(), nbMaaned.getValue()));
                        }
                    });
                    nbAar.setValue(Calendar.getInstance().get(Calendar.YEAR));
                    nbMaaned.setValue(Calendar.getInstance().get(Calendar.MONTH) + 1);
                    nbDag.setValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    btnPlanGem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println(btnPlanGem.getText());
                            if(btnPlanGem.getText().equals("Gem StartDato")){
                                btnPlanGem.setText("Gem SlutDato");
                                dl.getProjects().get(project).getCategoryList().get(groupPosition).getPlanList().get(childPosition).setStartDate(new Date(nbAar.getValue(),nbMaaned.getValue(),nbDag.getValue()));
                                System.out.println(nbAar.getValue() + ", " + nbMaaned.getValue() + ", " + nbDag.getValue());
                            }else{
                                Date testDato = new Date(nbAar.getValue(), nbMaaned.getValue(), nbDag.getValue());
                                if(dl.getProjects().get(project).getCategoryList().get(groupPosition).getPlanList().get(childPosition).getStartDate().equals(testDato)){
                                    Toast.makeText(getActivity(), "Start og Slut Dato må ikke være Ens", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    btnPlanGem.setText("Gem StartDato");
                                    System.out.println(nbAar.getValue() + ", " + nbMaaned.getValue() + ", " + nbDag.getValue());
                                    btnPlanGem.setVisibility(View.GONE);
                                    nbAar.setVisibility(View.GONE);
                                    nbMaaned.setVisibility(View.GONE);
                                    nbDag.setVisibility(View.GONE);
                                    planElement.setVisibility(View.VISIBLE);
                                    projektListeView.collapseGroup(groupPosition);
                                    projektListeView.expandGroup(groupPosition);
                                    dl.getProjects().get(project).getCategoryList().get(groupPosition).getPlanList().get(childPosition).setEndDate(new Date(nbAar.getValue(), nbMaaned.getValue(), nbDag.getValue()));
                                }
                            }
                        }
                    });
                    return false; //true if we consumed the click, false if not

                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    //do your per-group callback here
                    kategoriTitel = (TextView) view.findViewById(R.id.txtkategori_liste_element);
                    kategoriAendreTitel = (EditText) view.findViewById(R.id.editaendreKategori);
                    btnkategoriGem = (Button) view.findViewById(R.id.btnkategoriAendring);
                    btnKategoriSlet = (Button) view.findViewById(R.id.btnkategoriSlet);
                    kategoriTitel.setVisibility(View.GONE);
                    txthoejre.setVisibility(View.GONE);
                    txtvenstre.setVisibility(View.GONE);
                    kategoriAendreTitel.setVisibility(View.VISIBLE);
                    kategoriAendreTitel.setText(kategoriTitel.getText());
                    btnKategoriSlet.setVisibility(View.VISIBLE);
                    btnKategoriSlet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kategoriTitel.setText(kategoriAendreTitel.getText());
                            btnkategoriGem.setVisibility(View.GONE);
                            btnKategoriSlet.setVisibility(View.GONE);
                            kategoriAendreTitel.setVisibility(View.GONE);
                            kategoriTitel.setVisibility(View.VISIBLE);
                            txthoejre.setVisibility(View.VISIBLE);
                            txtvenstre.setVisibility(View.VISIBLE);
                            dl.getProjects().get(project).getCategoryList().remove(groupPosition);
                            ViewPagerFragment.updateViewPagerList();
                        }
                    });
                    btnkategoriGem.setVisibility(View.VISIBLE);
                    btnkategoriGem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kategoriTitel.setText(kategoriAendreTitel.getText());
                            btnkategoriGem.setVisibility(View.GONE);
                            kategoriAendreTitel.setVisibility(View.GONE);
                            kategoriTitel.setVisibility(View.VISIBLE);
                            txthoejre.setVisibility(View.VISIBLE);
                            txtvenstre.setVisibility(View.VISIBLE);
                            dl.getProjects().get(project).getCategoryList().get(groupPosition).setCategoryTitle(kategoriAendreTitel.getText().toString());
                        }
                    });
                    retVal = true;
                    return retVal; //true if we consumed the click, false if not

                } else {
                    // null item; we don't consume the click
                    return false;
                }
            }
        });

        projektListeView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
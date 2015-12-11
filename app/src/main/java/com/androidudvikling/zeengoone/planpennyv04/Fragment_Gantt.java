package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Category;
import com.androidudvikling.zeengoone.planpennyv04.entities.Date;
import com.androidudvikling.zeengoone.planpennyv04.entities.Plan;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private static ArrayList<Category> categories;
    private static Date currentMonth;
    private Date tabMonth;
    private int mPage;

    public static Fragment_Gantt newInstance(int page, ArrayList kategoriList) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment_Gantt fragment = new Fragment_Gantt();
        fragment.setArguments(args);
        categories = kategoriList;
        currentMonth = new Date(GregorianCalendar.YEAR, GregorianCalendar.MONTH + 1, GregorianCalendar.DAY_OF_MONTH);
        return fragment;
    }

    public void beregnMaanedOgAar(int tab){
        currentMonth = new Date(GregorianCalendar.YEAR, GregorianCalendar.MONTH + 1, GregorianCalendar.DAY_OF_MONTH);
        tabMonth = currentMonth.setDateMonth(tab);
    }
    public static ArrayList<Category> setList(ArrayList<Category> list){
        return list;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_controller, container, false);
        final ArrayList<String> categoryList = new ArrayList<>();
        for(Category c:categories){
            categoryList.add(c.getCategoryTitle());
        }
        // Læg listen ind i arrayadapteren
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.penny_listview_item, R.id.listeelem_overskrift, categories) {

            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);
                if (categories.get(position).get && !findesIMaaneden) {
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    billedeh.setImageResource(R.drawable.pil_hoejre);
                    billedev.setImageResource(R.drawable.pil_ingen);
                } else if(!erEfter && !findesIMaaneden){
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billedeh.setImageResource(R.drawable.pil_ingen);
                    billedev.setImageResource(R.drawable.pil_venstre);
                }
                else if(findesIMaaneden){
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billedeh.setImageResource(R.drawable.pil_ingen);
                    billedev.setImageResource(R.drawable.pil_ingen);
                    Log.d("else_array", "gået ind i else statement");
                }

                return view;
            }

            @Override
            public String getItem(int position){
                return categories.get(position).getCategoryTitle();
            }

        };

        // Lav listviewet og setadapter til adapteren lavet herover
        ListView listview = (ListView) view;
        listview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }
}
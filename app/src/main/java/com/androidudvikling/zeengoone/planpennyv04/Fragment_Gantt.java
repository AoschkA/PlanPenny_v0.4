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

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private static ArrayList<Category> categories;
    private static int currentMonth;
    private int mPage;

    public static Fragment_Gantt newInstance(int page, ArrayList kategoriList) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment_Gantt fragment = new Fragment_Gantt();
        fragment.setArguments(args);
        categories = setList(kategoriList);
        currentMonth = GregorianCalendar.MONTH;
        return fragment;
    }

    public static ArrayList<Category> setList(ArrayList<Category> list){
        return list;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_controller, container, false);
        ArrayList<Category> categoryList = new ArrayList<>();
        /*for(Category c:categories){
            categoryList.add(c.getCategoryTitle());
        }*/
        // Læg listen ind i arrayadapteren
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.penny_listview_item, R.id.listeelem_overskrift, categoryList) {
            private boolean findesIMaaneden = false;
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);
                if (position == 1) {
                    ImageView billedev = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    ImageView billedeh = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    billedeh.setImageResource(R.drawable.pil_hoejre);
                    billedev.setImageResource(R.drawable.pil_ingen);
                } else if(position %3 == 1){
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
                    Log.d("else_array", "gået ind i else statement");
                }
                return view;
            }
            @Override
            public Object getItem(Category c){
                // Skal teste om containing days findes i nuværende måned
                return c;
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
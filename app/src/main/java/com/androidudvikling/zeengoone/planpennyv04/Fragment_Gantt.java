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

import java.util.ArrayList;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment {
    private static ArrayList testList;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static Fragment_Gantt newInstance(int page, ArrayList kategoriList) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment_Gantt fragment = new Fragment_Gantt();
        fragment.setArguments(args);
        testList = kategoriList;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_controller, container, false);

        // Læg listen ind i arrayadapteren
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.penny_listview_item, R.id.listeelem_overskrift, testList) {
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);
                if (position % 3 == 2) {
                    ImageView billede = (ImageView) view.findViewById(R.id.listeelem_venstre);
                    billede.setImageResource(android.R.drawable.sym_action_call);
                } else if(position %3 == 1){
                    ImageView billede = (ImageView) view.findViewById(R.id.listeelem_hoejre);
                    billede.setImageResource(android.R.drawable.sym_action_email);
                }
                else{
                    Log.d("else_array", "gået ind i else statement");
                }
                return view;
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
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
        if(mPage %3 == 0){
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, testList);

            // Lav listviewet og setadapter til adapteren lavet herover
            ListView listview = (ListView) view;
            listview.setAdapter(adapter);
        }
        else if(mPage %3 == 1){
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.pil_liste_venstre, R.id.listeelem_overskrift, testList) {
                @Override
                public View getView(int position, View cachedView, ViewGroup parent) {
                    View view = super.getView(position, cachedView, parent);
                    ImageView billede = (ImageView) view.findViewById(R.id.listeelem_billede);
                    if (position % 3 == 2) {
                        billede.setImageResource(android.R.drawable.sym_action_call);
                    } else {
                        billede.setImageResource(android.R.drawable.sym_action_email);
                    }

                    return view;
                }
            };

            // Lav listviewet og setadapter til adapteren lavet herover
            ListView listview = (ListView) view;
            listview.setAdapter(adapter);
        }
        else{
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.pil_liste_hoejre, R.id.listeelem_overskrift, testList) {
                @Override
                public View getView(int position, View cachedView, ViewGroup parent) {
                    View view = super.getView(position, cachedView, parent);
                    ImageView billede = (ImageView) view.findViewById(R.id.listeelem_billede);
                    if (position % 3 == 2) {
                        billede.setImageResource(android.R.drawable.sym_action_call);
                    } else {
                        billede.setImageResource(android.R.drawable.sym_action_email);
                    }

                    return view;
                }
            };

            // Lav listviewet og setadapter til adapteren lavet herover
            ListView listview = (ListView) view;
            listview.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }
}
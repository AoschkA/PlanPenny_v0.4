package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment {
    private static ArrayList testList;

    public static Fragment_Gantt newInstance(int page, ArrayList kategoriList) {
        Bundle args = new Bundle();
        Fragment_Gantt fragment = new Fragment_Gantt();
        testList = kategoriList;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_controller, container, false);
        // Læg listen ind i arrayadapteren
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, testList);

        // Lav listviewet og setadapter til adapteren lavet herover
        ListView listview = (ListView) view;
        listview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

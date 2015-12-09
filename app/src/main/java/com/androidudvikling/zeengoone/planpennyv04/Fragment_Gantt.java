package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static String[] listen;
    private int mPage;

    public static Fragment_Gantt newInstance(int page, ArrayList kategoriList) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment_Gantt fragment = new Fragment_Gantt();
        fragment.setArguments(args);
        listen = new String[kategoriList.size()];
        populateList(kategoriList);
        return fragment;
    }

    private static void populateList(ArrayList<String> tempList){
        for(int i = 0;i < tempList.size();i++){
            listen[i] = tempList.get(i);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_controller, container, false);
        //TextView textView = (TextView) view;
        //textView.setText("Fragment #" + mPage);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listen);
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

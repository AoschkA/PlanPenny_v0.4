package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;

/**
 * Created by Jonas Praem on 11/01/16.
 */
public class FragmentSettings extends Fragment {

    private Settings indstillinger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indstillinger = new Settings(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_layout, container, false);

        // Læg listen ind i arrayadapteren for kategorier
        SettingsAdapter adapter = new SettingsAdapter(getActivity(), indstillinger);

        // Lav listviewet og setadapter til adapteren lavet herover
        final ExpandableListView settingsView = (ExpandableListView) view.findViewById(R.id.expandable_list_id);
        settingsView.setAdapter(adapter);
        return view;
    }

}

package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;
import com.androidudvikling.zeengoone.planpennyv04.entities.UserSettings;
import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;
import com.androidudvikling.zeengoone.planpennyv04.logic.PreferenceManager;

/**
 * Created by Jonas Praem on 11/01/16.
 */
public class FragmentSettings extends Fragment {

    private Settings indstillinger;
    private DataLogic data = new DataLogic();
    private PreferenceManager preferenceManager;
    private UserSettings userSettings;
    private SettingsAdapter adapter;
    private boolean savedExists = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indstillinger = new Settings(getActivity());
        preferenceManager = new PreferenceManager(getActivity());
        //savedExists = readData();
        //((Fragment_Controller)getActivity()).parentPublicMethod();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_layout_project, container, false);
        // Læg listen ind i arrayadapteren for kategorier
        adapter = new SettingsAdapter(getActivity(), indstillinger, preferenceManager);

        // Lav listviewet og setadapter til adapteren lavet herover
        final ExpandableListView settingsView = (ExpandableListView) view.findViewById(R.id.expandable_list_settings_id);
        settingsView.setAdapter(adapter);
        return view;
    }
    private boolean readData() {
        if(preferenceManager.loadSettings() != null){
            userSettings = preferenceManager.loadSettings();
            data.setSortType_project(userSettings.getSortSetting1());
            data.setSortType_category(userSettings.getSortSetting2());
            Toast.makeText(getActivity(), "Data hentet", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}

package com.androidudvikling.zeengoone.planpennyv04.entities;

import com.androidudvikling.zeengoone.planpennyv04.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonasandreassen on 15/01/16.
 */
public class Settings {
    private ArrayList<HashMap<String, ArrayList<String>>> settingsList = new ArrayList<HashMap<String, ArrayList<String>>>();
    private HashMap map = new HashMap();

    public Settings() {
        populateHashMap();
    }

    private void populateHashMap() {
        ArrayList<String> settingTypes = new ArrayList<String>();
        ArrayList<String> synchronizeTypes = new ArrayList<String>();
        ArrayList<String> sortTypes = new ArrayList<String>();

        settingTypes.add(String.valueOf(R.string.setting1));
        settingTypes.add(String.valueOf(R.string.setting2));
        settingTypes.add(String.valueOf(R.string.setting3));
        sortTypes.add(String.valueOf(R.string.sortsetting1));
        sortTypes.add(String.valueOf(R.string.sortsetting2));
        sortTypes.add(String.valueOf(R.string.sortsetting3));
        sortTypes.add(String.valueOf(R.string.sortsetting4));
        synchronizeTypes.add(String.valueOf(R.string.syncsetting1));

        HashMap setting1 = new HashMap();
        setting1.put(settingTypes.get(0), sortTypes);
        HashMap setting2 = new HashMap();
        setting2.put(settingTypes.get(1), sortTypes);
        HashMap setting3 = new HashMap();
        setting3.put(settingTypes.get(2), synchronizeTypes);

        settingsList.add(setting1);
        settingsList.add(setting2);
        settingsList.add(setting3);

    }

    public ArrayList<HashMap<String, ArrayList<String>>> getHashMap() {
        return settingsList;
    }





}

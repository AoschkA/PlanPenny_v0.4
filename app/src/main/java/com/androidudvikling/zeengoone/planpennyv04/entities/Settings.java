package com.androidudvikling.zeengoone.planpennyv04.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonasandreassen on 15/01/16.
 */
public class Settings {
    private HashMap map = new HashMap();

    public Settings() {
        populateHashMap();
    }

    private void populateHashMap() {
        ArrayList<String> sortTypes = new ArrayList<String>();
        ArrayList<String> synchronizeTypes = new ArrayList<String>();
        sortTypes.add("Alfabetisk");
        sortTypes.add("Største først");
        sortTypes.add("Først planlagte først");
        sortTypes.add("Først oprettet først");

        synchronizeTypes.add("Google Calendar");


        map.put("1", sortTypes);
        map.put("2", sortTypes);
        map.put("3", synchronizeTypes);
    }

    public HashMap getHashMap() {
        return map;
    }





}

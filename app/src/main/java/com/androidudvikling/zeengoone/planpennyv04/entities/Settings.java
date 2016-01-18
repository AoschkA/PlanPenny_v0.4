package com.androidudvikling.zeengoone.planpennyv04.entities;

import android.content.Context;

import com.androidudvikling.zeengoone.planpennyv04.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonasandreassen on 15/01/16.
 */
public class Settings {
    Context ctx;
    private HashMap<String, ArrayList<String>> settingsList = new HashMap<String, ArrayList<String>>();

    public Settings(Context ctx) {
        this.ctx = ctx;
        populateHashMap();
    }

    private void populateHashMap() {
        ArrayList<String> sortTypes = new ArrayList<>();
        sortTypes.add(ctx.getString(R.string.sortsetting1));
        sortTypes.add(ctx.getString(R.string.sortsetting2));
        sortTypes.add(ctx.getString(R.string.sortsetting3));
        sortTypes.add(ctx.getString(R.string.sortsetting4));

        ArrayList<String> synchronizeTypes = new ArrayList<>();
        synchronizeTypes.add(ctx.getString(R.string.syncsetting1));

        this.settingsList.put(ctx.getString(R.string.setting1), sortTypes);
        this.settingsList.put(ctx.getString(R.string.setting2), sortTypes);
        this.settingsList.put(ctx.getString(R.string.setting3), synchronizeTypes);
    }

    public HashMap<String, ArrayList<String>> getHashMap() {
        return settingsList;
    }

}

package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.androidudvikling.zeengoone.planpennyv04.entities.UserSettings;

/**
 * Created by jonasandreassen on 15/01/16.
 */
public class PreferenceManager {

    private Context context;

    public PreferenceManager(Context ctx) {
        context=ctx;
    }

    public void saveSettings(UserSettings userSettings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("setting1", userSettings.getSortSetting1());
        editor.putInt("setting2", userSettings.getSortSetting2());
        editor.putBoolean("setting3", userSettings.getSyncSetting1());
        editor.apply();
    }

    public UserSettings loadSettings() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        UserSettings userSettings;

        int setting1 = sharedPreferences.getInt("setting1", 0);
        int setting2 = sharedPreferences.getInt("setting2", 0);
        boolean setting3 = sharedPreferences.getBoolean("setting3", false);

        userSettings = new UserSettings(setting1, setting2, setting3);
        return userSettings;
    }

    public void saveAppLocation(int appLocation) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appLocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("mainLocation", appLocation);
        editor.apply();
    }

    public int loadAppLocation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appLocation", Context.MODE_PRIVATE);
        int appLocation = sharedPreferences.getInt("mainLocation", -1);

        return appLocation;
    }

    public void saveTabLocation(int tabLocation) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appLocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("tabLocation", tabLocation);
        editor.apply();
    }

    public int loadTabLocation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appLocation", Context.MODE_PRIVATE);
        int tabLocation = sharedPreferences.getInt("tabLocation", -1);

        return tabLocation;
    }
}

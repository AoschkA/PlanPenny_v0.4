package com.androidudvikling.zeengoone.planpennyv04.logic;

import android.content.Context;
import android.content.SharedPreferences;

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
        editor.putBoolean("setting3", userSettings.getSyncSetting(0));
        editor.putBoolean("setting4", userSettings.getSyncSetting(1));
        editor.putBoolean("setting5", userSettings.getSyncSetting(2));
        editor.putBoolean("setting_sensor", userSettings.getSensorSettings1());
        editor.apply();
    }

    public UserSettings loadSettings() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        UserSettings userSettings;

        int setting1 = sharedPreferences.getInt("setting1", 0);
        int setting2 = sharedPreferences.getInt("setting2", 0);
        boolean setting3 = sharedPreferences.getBoolean("setting3", false);
        boolean setting4 = sharedPreferences.getBoolean("setting4", false);
        boolean setting5 = sharedPreferences.getBoolean("setting5", false);
        boolean setting_sensor = sharedPreferences.getBoolean("setting_sensor", false);

        userSettings = new UserSettings(setting1, setting2, setting3, setting4, setting5, setting_sensor);
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

    public void saveActivityLocation(String location) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appLocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("activityLocation", location);
        editor.apply();
    }

    public String loadActivityLocation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appLocation", Context.MODE_PRIVATE);

        String location = sharedPreferences.getString("activityLocation", "");
        return location;
    }

    public void saveTabLocation(String projectName, int tabLocation) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(projectName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("tabLocation", tabLocation);
        editor.apply();
    }

    public int loadTabLocation(String projectName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(projectName, Context.MODE_PRIVATE);
        int tabLocation = sharedPreferences.getInt("tabLocation", -1);

        return tabLocation;
    }
}

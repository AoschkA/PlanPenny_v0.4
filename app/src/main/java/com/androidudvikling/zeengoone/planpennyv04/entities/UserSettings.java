package com.androidudvikling.zeengoone.planpennyv04.entities;

/**
 * Created by jonasandreassen on 15/01/16.
 */
public class UserSettings {

    int sortSetting1;
    int sortSetting2;
    boolean syncSetting1;

    public UserSettings(int sortSetting1, int sortSetting2, boolean syncSetting1){
        this.sortSetting1 = sortSetting1;
        this.sortSetting2 = sortSetting2;
        this.syncSetting1 = syncSetting1;
    }

    public void setSortSetting1(int setting) {
        sortSetting1=setting;
    }
    public void setSortSetting2(int setting) {
        sortSetting2=setting;
    }
    public void setSyncSetting1(boolean setting) {
        syncSetting1=setting;
    }

    public int getSortSetting1() {
        return sortSetting1;
    }
    public int getSortSetting2() {
        return sortSetting2;
    }
    public boolean getSyncSetting1(){
        return syncSetting1;
    }

    public void setSortSetting1(String setting) {
        sortSetting1=Integer.parseInt(setting);
    }
    public void setSortSetting2(String setting) {
        sortSetting2=Integer.parseInt(setting);
    }
    public void setSyncSetting1(String setting) {
        syncSetting1 = Boolean.parseBoolean(setting);
    }

    public String getSortSetting1String() {
        return Integer.toString(sortSetting1);
    }
    public String getSortSetting2String() {
        return Integer.toString(sortSetting2);
    }
    public String getSyncSetting1String() {
        return Boolean.toString(syncSetting1);
    }

}

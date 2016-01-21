package com.androidudvikling.zeengoone.planpennyv04.entities;

/**
 * Created by jonasandreassen on 15/01/16.
 */
public class UserSettings {

    int sortSetting1;
    int sortSetting2;
    boolean syncSetting1;
    boolean syncSetting2;
    boolean syncSetting3;
    boolean sensorSetting1;

    public UserSettings(int sortSetting1, int sortSetting2,
                        boolean syncSetting1,boolean syncSetting2, boolean syncSetting3,
                        boolean sensorSetting1){
        this.sortSetting1 = sortSetting1;
        this.sortSetting2 = sortSetting2;
        this.syncSetting1 = syncSetting1;
        this.syncSetting2 = syncSetting2;
        this.syncSetting3 = syncSetting3;
        this.sensorSetting1 = sensorSetting1;
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

    public void setSortSetting1(String setting) {
        sortSetting1=Integer.parseInt(setting);
    }

    public int getSortSetting2() {
        return sortSetting2;
    }

    public void setSortSetting2(String setting) {
        sortSetting2=Integer.parseInt(setting);
    }

    public boolean getSyncSetting(int type){
        if (type==1) return syncSetting1;
        if (type==2) return syncSetting2;
        else return syncSetting3;
    }

    public void setSyncSetting(int type, boolean setting) {
        if (type==1) syncSetting1=setting;
        else if (type==2) syncSetting2=setting;
        else if (type==3) syncSetting3=setting;
    }

    public boolean getSensorSettings1(){
        return sensorSetting1;
    }

    public void setSensorSetting1(boolean setting) {
        sensorSetting1=setting;
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
    public String getSensorSetting1String() {
        return Boolean.toString(sensorSetting1);
    }
}

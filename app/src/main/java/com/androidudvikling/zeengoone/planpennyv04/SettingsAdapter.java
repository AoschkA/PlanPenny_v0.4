package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;

/**
 * Created by zeengoone on 1/15/16.
 */
public class SettingsAdapter extends BaseExpandableListAdapter {

    protected Context ctx;
    protected Settings indstillinger;

    public SettingsAdapter(Context ctx, Settings indstillinger) {
        this.ctx = ctx;
        this.indstillinger = indstillinger;
    }

    @Override
    public int getGroupCount() {
        return indstillinger.getHashMap().size();
    }

    private String getSettingsTitle(int groupPosition, int childPosition) {
        return indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(childPosition);
    }

    private String getSettingsKey(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return ctx.getString(R.string.setting1);
            case 1:
                return ctx.getString(R.string.setting2);
            case 2:
                return ctx.getString(R.string.setting3);
            default:
                return ctx.getString(R.string.setting1);
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition < 2){
            return 1;
        }
        return indstillinger.getHashMap().get(getSettingsKey(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return indstillinger.getHashMap().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // check om viewet findes allerede, hvis ikke lav det
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.settings_list, parent, false);
        }
        // hent listen af kategorier og læg sæt tekst i listen til deres titler
        TextView settings_element = (TextView) convertView.findViewById(R.id.settings_list_element);
        settings_element.setText(getSettingsKey(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(groupPosition < 2){
            // check om subviewet findes allerede, hvis ikke lav det
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.settings_sublist_sort, parent, false);
            }
            // hent planer start og slut dato og titel og læg dem ind i submenu eller "childview"
            final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);
            if(groupPosition == 0){
                RadioButton button1 = (RadioButton) convertView.findViewById(R.id.radioBtn1);
                RadioButton button2 = (RadioButton) convertView.findViewById(R.id.radioBtn2);
                RadioButton button3 = (RadioButton) convertView.findViewById(R.id.radioBtn3);
                button1.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(0));
                button2.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(1));
                button3.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(2));
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("RadioButton 1 skal enables i sorteringstype");
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("RadioButton 2 skal enables i sorteringstype");
                    }
                });
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("RadioButton 3 skal enables i sorteringstype");
                    }
                });
            }
            else{
                RadioButton button4 = (RadioButton) convertView.findViewById(R.id.radioBtn1);
                RadioButton button5 = (RadioButton) convertView.findViewById(R.id.radioBtn2);
                RadioButton button6 = (RadioButton) convertView.findViewById(R.id.radioBtn3);
                button4.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(0));
                button5.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(1));
                button6.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(2));
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("RadioButton 4 skal enables i sorteringstype");
                    }
                });
                button5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("RadioButton 5 skal enables i sorteringstype");
                    }
                });
                button6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("RadioButton 6 skal enables i sorteringstype");
                    }
                });
            }
        }
        else{
            // check om subviewet findes allerede, hvis ikke lav det
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.settings_sublist_sync, parent, false);
            }
            Switch syncswitch = (Switch) convertView.findViewById(R.id.sync_switch);
            syncswitch.setText(getSettingsTitle(groupPosition,childPosition));
            syncswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    System.out.println("Google Calendar aktiveret: " + isChecked);
                }
            });

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

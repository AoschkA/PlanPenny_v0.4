package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.entities.Settings;

/**
 * Created by zeengoone on 1/15/16.
 */
public class SettingsAdapter extends BaseExpandableListAdapter {

        protected Context ctx;
        protected Settings indstillinger;

        public SettingsAdapter(Context ctx, Settings indstillinger){
            this.ctx = ctx;
            this.indstillinger = indstillinger;
        }
        @Override
        public int getGroupCount() {
            return indstillinger.getHashMap().size();
        }

        private String getSettingsTitle(int groupPosition, int childPosition){
            return indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(childPosition);
        }
        private String getSettingsKey(int groupPosition){
            switch(groupPosition){
                case 0: return ctx.getString(R.string.setting1);
                case 1: return ctx.getString(R.string.setting2);
                case 2: return ctx.getString(R.string.setting3);
                default: return ctx.getString(R.string.setting1);
            }
        }
        @Override
        public int getChildrenCount(int groupPosition) {
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
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.settings_list, parent, false);
            }
            // hent listen af kategorier og læg sæt tekst i listen til deres titler
            TextView settings_element = (TextView) convertView.findViewById(R.id.settings_list_element);
            settings_element.setText(getSettingsKey(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            // check om subviewet findes allerede, hvis ikke lav det
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.settings_sublist, parent, false);
            }
            // hent planer start og slut dato og titel og læg dem ind i submenu eller "childview"
            if (groupPosition == 2) {
                Switch sublist_element = (Switch) convertView.findViewById(R.id.settings_sublist_switch_element);
                sublist_element.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(childPosition));
            }
            else {
                RadioButton sublist_element = (RadioButton) convertView.findViewById(R.id.settings_sublist_element);
                sublist_element.setText(indstillinger.getHashMap().get(getSettingsKey(groupPosition)).get(childPosition));
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

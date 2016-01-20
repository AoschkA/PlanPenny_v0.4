package com.androidudvikling.zeengoone.planpennyv04;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.androidudvikling.zeengoone.planpennyv04.logic.DataLogic;

/**
 * Created by zeengoone on 1/8/16.
 */
public class KategoriAdapter extends BaseExpandableListAdapter {
    protected Context ctx;
    private int currentProjectNumber;
    private DataLogic dc = Fragment_Controller.dc;

    public KategoriAdapter(Context ctx, int currentProject){
        this.ctx = ctx;
        this.currentProjectNumber = currentProject;
    }
    @Override
    public int getGroupCount() {
        return dc.getProjects().get(currentProjectNumber).getCategoryList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getPlanList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getPlanList().get(childPosition);
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
        if (dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getPlanList() == null ||
                dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getPlanList().isEmpty()) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.kategori_liste_empty, parent, false);

            TextView kategori_element = (TextView) convertView.findViewById(R.id.kategori_liste_element2);
            kategori_element.setText(dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getCategoryTitle());
        }
        else {
            // check om viewet findes allerede, hvis ikke lav det
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.kategori_liste, parent, false);
            }
            // hent listen af kategorier og læg sæt tekst i listen til deres titler
            TextView kategori_element = (TextView) convertView.findViewById(R.id.kategori_liste_element);
            kategori_element.setText(dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getCategoryTitle());
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // check om subviewet findes allerede, hvis ikke lav det
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plan_liste, parent, false);
        }
        // hent planer start og slut dato og titel og læg dem ind i submenu eller "childview"
        TextView plan_element = (TextView) convertView.findViewById(R.id.plan_liste_element);
        plan_element.setText(dc.getProjects().get(currentProjectNumber).getCategoryList().get(groupPosition).getPlanList().get(childPosition).toString());
        switch(groupPosition){
            case 0:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveEt));
                plan_element.setTextColor(ContextCompat.getColor(ctx, R.color.colorSecondaryText));
                break;
            case 1:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveTo));
                break;
            case 2:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveTre));
                break;
            case 3:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveFire));
                break;
            case 4:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveFem));
                break;
            case 5:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveSeks));
                break;
            default:
                plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveEt));
                plan_element.setTextColor(ContextCompat.getColor(ctx, R.color.colorSecondaryText));
                break;
        }
        if(childPosition > 0){
            plan_element.setBackgroundColor(ContextCompat.getColor(ctx, R.color.planFarveSyv));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

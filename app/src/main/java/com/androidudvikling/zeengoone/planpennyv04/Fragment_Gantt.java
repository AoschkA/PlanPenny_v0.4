package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zeengoone on 11/26/15.
 */
public class Fragment_Gantt extends Fragment {

    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gantt_View = inflater.inflate(R.layout.fragment_gantt, container, false);
        return gantt_View;
    }
}

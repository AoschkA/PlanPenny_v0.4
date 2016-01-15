package com.androidudvikling.zeengoone.planpennyv04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by Jonas Praem on 13-Jan-16.
 */
public class ActivityHelp extends Fragment implements View.OnClickListener {

    ScrollView scrollView;
    ImageView imageView1;
    TextView textView1;
    TextView finishText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == finishText.getId()) {
            getActivity().finish();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_layout, container, false);

        scrollView = (ScrollView) view.findViewById(R.id.scrollViewHelp);
        imageView1 = (ImageView) view.findViewById(R.id.imageViewHelp);
        textView1 = (TextView) view.findViewById(R.id.textViewHelp1);
        textView1.setText(R.string.Describtion);
        finishText = (TextView) view.findViewById(R.id.textViewHelp2);
        finishText.setOnClickListener(this);
        return view;
    }
}

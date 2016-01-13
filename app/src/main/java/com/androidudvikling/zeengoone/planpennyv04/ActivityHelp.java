package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by Jonas Praem on 13-Jan-16.
 */
public class ActivityHelp extends Activity {

    ScrollView scrollView;
    ImageView imageView1;
    TextView textView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        scrollView = (ScrollView) findViewById(R.id.scrollViewHelp);
        imageView1 = (ImageView) findViewById(R.id.imageViewHelp);
        textView1 = (TextView) findViewById(R.id.textViewHelp1);
        textView1.setText(R.string.Describtion);
    }
}

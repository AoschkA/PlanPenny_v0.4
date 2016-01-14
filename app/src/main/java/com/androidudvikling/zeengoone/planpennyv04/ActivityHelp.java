package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by Jonas Praem on 13-Jan-16.
 */
public class ActivityHelp extends Activity implements View.OnClickListener {

    ScrollView scrollView;
    ImageView imageView1;
    TextView textView1;
    TextView finishText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        scrollView = (ScrollView) findViewById(R.id.scrollViewHelp);
        imageView1 = (ImageView) findViewById(R.id.imageViewHelp);
        textView1 = (TextView) findViewById(R.id.textViewHelp1);
        textView1.setText(R.string.Describtion);
        finishText = (TextView) findViewById(R.id.textViewHelp2);
        finishText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == finishText.getId()) {
            finish();
        }
    }
}

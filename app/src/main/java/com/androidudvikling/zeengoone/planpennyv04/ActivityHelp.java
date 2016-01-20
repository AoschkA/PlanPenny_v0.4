package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button buttonFinish;
    Button buttonWeb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        scrollView = (ScrollView) findViewById(R.id.scrollViewHelp);
        imageView1 = (ImageView) findViewById(R.id.imageViewHelp);
        textView1 = (TextView) findViewById(R.id.textViewHelp1);
        textView1.setText(R.string.Describtion);
        buttonFinish = (Button) findViewById(R.id.buttonHelp2);
        buttonWeb = (Button) findViewById(R.id.buttonHelp1);
        buttonFinish.setOnClickListener(this);
        buttonWeb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonFinish.getId()) {
            finish();
        }
        else if (v.getId() == buttonWeb.getId()) {
            startActivity(new Intent(this, PopHomepage.class));
            // Gemmer nuværende lokation
            Log.d("Location save", Integer.toString(-11) + " - (help - webview)");
            Fragment_Controller.pManager.saveAppLocation(-11);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Fragment_Controller.pManager.loadAppLocation()==-11) {
            startActivity(new Intent(this, PopHomepage.class));
        }
        else {
            Log.d("Location save", Integer.toString(-10) + " - (help)");
            Fragment_Controller.pManager.saveAppLocation(-10);
        }
    }
}

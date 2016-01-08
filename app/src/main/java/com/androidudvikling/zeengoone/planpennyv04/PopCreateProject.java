package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by alexandervpedersen on 08/01/16.
 */
public class PopCreateProject extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_pop_window);
        DisplayMetrics dpm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpm);

        // Definerer dimensionerne på pop
        double scaleHeight = 0.9;
        double scaleWidth = 0.8;

        int width = (int)((dpm.widthPixels)*scaleWidth);
        int height = (int)((dpm.heightPixels)*scaleHeight);



        getWindow().setLayout(width,height);



    }
}

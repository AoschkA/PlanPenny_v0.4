package com.androidudvikling.zeengoone.planpennyv04;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
        double scaleHeight = 0.8;
        double scaleWidth = 0.8;

        int width = (int)((dpm.widthPixels)*scaleWidth);
        int height = (int)((dpm.heightPixels)*scaleHeight);

        getWindow().setLayout(width, height);


        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.border_style));



    }
}

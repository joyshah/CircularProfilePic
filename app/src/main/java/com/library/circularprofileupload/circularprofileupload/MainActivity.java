package com.library.circularprofileupload.circularprofileupload;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.library.circularprofileupload.circularprofilepiclibrary.CircularProfile;
import com.library.circularprofileupload.circularprofilepiclibrary.CircularProfileClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CircularProfile ap = (CircularProfile) findViewById(R.id.img1);
        ap.setCircularProfileClickListener(new CircularProfileClickListener() {
            @Override
            public void onConcentricCircleClick() {
                Log.i("MAinActivity", "onclick");
            }

            @Override
            public void onClick() {
                Log.i("MAinActivity", "noty");
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ap.setConcentricCircleRadius(60);
                ap.setCircularBorderWidth(60);
                ap.setCircularBorderRadius(150);
                ap.setCircularBorderColor(Color.parseColor("#FF2CD90E"));
                ap.setConcentricCircleColor(Color.parseColor("#FF2CD90E"));
                ap.setConcentricCircleDegree(135);
                ap.setConcentricCircleImage(R.drawable.ic_person_add_black_24dp);
                //ap.setHideConcentricCircle(true);
            }
        });
    }
}

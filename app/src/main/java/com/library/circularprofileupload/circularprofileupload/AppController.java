package com.library.circularprofileupload.circularprofileupload;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

/**
 * Created by Joy Shah on 15-07-2018.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Test", "in appcontroller");
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}

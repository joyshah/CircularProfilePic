package com.library.circularprofileupload.circularprofilepiclibrary;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Joy Shah on 04-11-2017.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}

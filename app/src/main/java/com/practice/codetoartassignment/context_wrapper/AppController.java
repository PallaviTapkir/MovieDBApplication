package com.practice.codetoartassignment.context_wrapper;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pallavi on 19/9/17.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private static AppController mInstance;
    private static AppCompatActivity activity;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static void setActivity(AppCompatActivity activity) {
        AppController.activity = activity;
    }

    /**
     * Get the context from AppController throughout the App
     *
     * @return Context
     */
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
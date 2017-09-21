package com.practice.codetoartassignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.practice.codetoartassignment.context_wrapper.AppController;

/**
 * Created by pallavi on 19/9/17.
 */
public class Utility {

    //Regular expression for alphabets.
    public static final String TAG = Utility.class.getSimpleName();

    // for avoid creation of object
    private Utility() {
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }

        str = str.trim();
        return TextUtils.isEmpty(str);
    }

    /**
     * Check Internet connection availability.
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        if (AppController.getAppContext() == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) AppController.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // if no network is available networkInfo will be null, otherwise check if we are connected

        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) return false;
            if (activeNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

package com.sridevi.sample1.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import com.sridevi.sample1.R;

/**
 * Created by sridevi on 3/28/15.
 */
public class AboutDialog{

    public static void show(Activity activity) {
        // Inflate the about message contents
        View messageView = activity.getLayoutInflater().inflate(R.layout.dialog_about, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }
}

package com.neointernet.neo360;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.neointernet.neo360.activity.BackGroundActivity;

import static org.rajawali3d.util.RajLog.TAG;

/**
 * Created by yasar on 2/2/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        Log.e(TAG, "handleUncaughtException: " + e.getMessage());
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        Intent intent = new Intent(getApplicationContext(), BackGroundActivity.class);
//        intent.setAction("com.mydomain.SEND_LOG"); // see step 5.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);

        System.exit(1); // kill off the crashed app
    }
}
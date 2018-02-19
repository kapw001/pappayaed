package com.pappayaed.splash;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.App.Con;
import com.pappayaed.Main.MainActivity;
import com.pappayaed.R;
import com.pappayaed.demoadmin.AdminActivity;
import com.pappayaed.login.LoginActivity;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.pricepaldemo.PrincipalActivity;

import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private ThreeDotsLoader avi;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static SessionManagenent sessionManagenent;

    private static final String TAG = "FullscreenActivity";
    //    private String mainUrl = "http://192.168.2.119:8069/";
//    private String mainUrl = "https://srml.pappaya.education/";
    private String mainUrl = "https://demoschool.pappaya.co.uk/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        Uri uri = getIntent().getData();
        if (uri != null) {

//            String na = uri.getScheme();
            String n = uri.getHost();

            Con.url = "https://" + n;

//            Toast.makeText(this, "" + Con.url , Toast.LENGTH_SHORT).show();

        } else {

            Con.url = mainUrl;

        }
//        Toast.makeText(this, "" + Con.url, Toast.LENGTH_SHORT).show();

        App.getApp().setUrlForRetrofit(Con.url);


//            Log.e(TAG, "onCreate:uri  " + uri.toString());
//            String url;
//            String email;
//            String password;
//            url = uri.getQueryParameter("url"); // x = "1.2"
////            email = uri.getQueryParameter("email"); // y = "3.4"
////            password = uri.getQueryParameter("password"); // y = "3.4"
////            if (url != null && email != null && password != null) {
////                // do something interesting with x and y
////
////                Con.url = url;
////
////                App.getApp().setUrlForRetrofit(Con.url);
////
////                Log.e(TAG, "onCreate: " + url + "  " + email + "   " + password);
////            }
//
//            Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
//        }


        sessionManagenent = SessionManagenent.getSessionManagenent();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sessionManagenent.isLoggedIn()) {

                    Map map = sessionManagenent.getSession();

                    String name = map.get("username").toString();
                    String password = map.get("password").toString();


                    if (name.toLowerCase().equalsIgnoreCase("admin") && password.toLowerCase().equalsIgnoreCase("admin")) {

                        startActivity(new Intent(getApplicationContext(), AdminActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    } else if (name.toLowerCase().equalsIgnoreCase("principal") && password.toLowerCase().equalsIgnoreCase("principal")) {

                        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    } else if (name.toLowerCase().equalsIgnoreCase("secretary") && password.toLowerCase().equalsIgnoreCase("secretary")) {

                        startActivity(new Intent(getApplicationContext(), AdminActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }


                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

            }
        }, AUTO_HIDE_DELAY_MILLIS);

    }
}

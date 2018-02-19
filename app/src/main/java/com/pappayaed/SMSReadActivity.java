package com.pappayaed;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.data;

public class SMSReadActivity extends AppCompatActivity {

    private static final String TAG = "SMSReadActivity";
    private SmsVerifyCatcher smsVerifyCatcher;
    private TextView codeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsread);


        Uri uri = getIntent().getData();
        if (uri != null) {
            Log.e(TAG, "onCreate:uri  " + uri.toString());
            String url;
            String email;
            String password;
            url = uri.getQueryParameter("url"); // x = "1.2"
            email = uri.getQueryParameter("email"); // y = "3.4"
            password = uri.getQueryParameter("password"); // y = "3.4"
            if (url != null && email != null && password != null) {
                // do something interesting with x and y

                Log.e(TAG, "onCreate: " + url + "  " + email + "   " + password);
            }
        }


        codeTxt = (TextView) findViewById(R.id.code);

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                Log.e(TAG, "onSmsCatch: " + message);
                String code = parseCode(message);//Parse verification code

                codeTxt.setText(code + "");//set code in edit text
                codeTxt.setTextColor(Color.BLACK);
                //then you can send verification code to server


            }
        });

        smsVerifyCatcher.setPhoneNumberFilter("777");

    }


    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    private String parseCode(String message) {

        Pattern p = Pattern.compile("\\b\\d{8}\\b");
        Log.e(TAG, "parseCode: " + p.toString());
        Matcher m = p.matcher(message);
        Log.e(TAG, "parseCode: " + m.toString());
        String code = "";
        while (m.find()) {
            code = m.group(0);
            Log.e(TAG, "parseCode:c  " + code);
        }
        return code;

    }


    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

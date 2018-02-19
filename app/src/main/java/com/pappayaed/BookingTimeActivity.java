package com.pappayaed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingTimeActivity extends AppCompatActivity {
    private static final String TAG = "BookingTimeActivity";

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_time);

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] list = getResources().getStringArray(R.array.list);

                String ct = list[position];
                compare(ct, "10:00 AM", "12:30 PM", "4:00 PM", "10:00 PM");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void compare(String cT, String ms, String me, String es, String ee) {

        Date currentTime = U.getDate(cT);
        Date MSTime = U.getDate(ms);
        Date METime = U.getDate(me);
        Date ESTime = U.getDate(es);
        Date EETime = U.getDate(ee);


        List<String> listOfTime = new ArrayList<>();


        String MinsOrPlus = null;

        if (currentTime.after(MSTime) && currentTime.before(METime) || currentTime.equals(MSTime) || currentTime.equals(METime)) {

            currentTime = MSTime;
            MinsOrPlus = "Plus";
        } else if (currentTime.before(MSTime)) {
            MinsOrPlus = "Plus";


        } else if (currentTime.after(METime)) {
            MinsOrPlus = "Minus";
        }


        for (int i = 0; i < 8; i++) {

            if (currentTime.after(MSTime) && currentTime.before(METime) || currentTime.equals(MSTime) || currentTime.equals(METime)) {

//                Log.e(TAG, "compare: true");

                listOfTime.add(U.getTime(currentTime));
                if (MinsOrPlus.equalsIgnoreCase("Plus")) {
                    currentTime = U.getDatePlus30Min(U.getTime(currentTime));
                } else {
                    currentTime = U.getDateMins30Min(U.getTime(currentTime));
//                    Log.e(TAG, "compare: " + currentTime.getTime());
                }


            } else {
//                Log.e(TAG, "compare: false");
                if (MinsOrPlus.equalsIgnoreCase("Plus")) {
                    currentTime = U.getDatePlus30Min(U.getTime(currentTime));
                } else {
                    currentTime = U.getDateMins30Min(U.getTime(currentTime));
//                    Log.e(TAG, "compare: " + currentTime.getTime());
                }
//                Log.e(TAG, "compare: " + U.getTime(currentTime));
            }
        }

        for (int i = 0; i < listOfTime.size(); i++) {
            Log.e(TAG, "compare: " + listOfTime.get(i));
        }


    }
}

package com.pappayaed.test;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.pappayaed.R;

public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";
    private NestedScrollView nestedScrollView;
    private AppBarLayout appBarLayout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        appBarLayout = (AppBarLayout) findViewById(R.id.appp);
        nestedScrollView = (NestedScrollView) findViewById(R.id.scroll);

        linearLayout = (LinearLayout) findViewById(R.id.currentpostion);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float y = appBarLayout.getY() + appBarLayout.getHeight() / 2;
                float ly = linearLayout.getY();
                Log.e(TAG, "onScrollChange: Applayout " + y + "  Scrolly  " + scrollY + " ly    " + ly);

                if (scrollY > 300) {
                    appBarLayout.setAlpha(1f);
                } else {
                    appBarLayout.setAlpha(0.1f);
                }

            }
        });
    }
}

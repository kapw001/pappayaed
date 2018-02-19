package com.pappayaed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        U test = (U) getIntent().getSerializableExtra("t") != null ? (U) getIntent().getSerializableExtra("t") : new U();

        Log.e(TAG, "onCreate: " + test);
    }

    public void onClic(View view) {

        Intent intent = new Intent();
//        intent.putExtra("name", "Testing");

        setResult(1, intent);

        finish();


    }
}

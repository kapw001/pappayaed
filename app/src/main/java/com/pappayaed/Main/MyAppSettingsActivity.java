package com.pappayaed.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.R;

public class MyAppSettingsActivity extends AppCompatActivity {


    private EditText saveUrl;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");

        saveUrl = (EditText) findViewById(R.id.saveUrl);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = saveUrl.getText().toString();

                App.getApp().getPreferencesUrl().edit().putString("url", url).apply();

                Toast.makeText(MyAppSettingsActivity.this, "Successfully url is saved.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

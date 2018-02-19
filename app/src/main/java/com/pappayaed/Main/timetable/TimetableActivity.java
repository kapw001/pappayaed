package com.pappayaed.Main.timetable;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.pappayaed.Main.fragments.StudentExamFragment;
import com.pappayaed.Main.fragments.StudentTableFragment;
import com.pappayaed.R;

public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Timetable");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        StudentTableFragment moreFragment = new StudentTableFragment();
        fragmentTransaction.replace(R.id.container, moreFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.pappayaed.Main.examp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pappayaed.Main.fragments.StudentExamFragment;
import com.pappayaed.Main.fragments.StudentMarkSheetFragment;
import com.pappayaed.R;

public class ExamMarkSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Exam marksheet");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        StudentMarkSheetFragment moreFragment = new StudentMarkSheetFragment();
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

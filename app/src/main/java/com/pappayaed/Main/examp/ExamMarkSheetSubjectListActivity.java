package com.pappayaed.Main.examp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pappayaed.Main.fragments.StudentMarkSheetFragment;
import com.pappayaed.Main.fragments.StudentMarkSheetSubjectListFragment;
import com.pappayaed.R;

import java.util.List;

public class ExamMarkSheetSubjectListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Exam marksheet");


        List<Integer> result_line = (List<Integer>) getIntent().getSerializableExtra("result_line");


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        StudentMarkSheetSubjectListFragment moreFragment = StudentMarkSheetSubjectListFragment.getInstance(result_line);
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

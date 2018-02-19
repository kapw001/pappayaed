package com.pappayaed.demoadmin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.pappayaed.R;
import com.pappayaed.preference.SessionManagenent;

public class AdminActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    addFragment(1);
                    return true;
                case R.id.navigation_dashboard:
                    addFragment(2);
                    return true;
                case R.id.more:
                    addFragment(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addFragment(1);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void addFragment(int pos) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = null;
        if (pos == 1) {

            fragment = new StateFragment();
            getSupportActionBar().setTitle(getString(R.string.title_state));

        } else if (pos == 2) {

            fragment = new DistrictFragment();
            getSupportActionBar().setTitle(getString(R.string.title_district));

        } else if (pos == 3) {

            fragment = new MoreAddFragment();
            getSupportActionBar().setTitle(getString(R.string.title_more));
        }

        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();


    }

}

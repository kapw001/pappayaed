package com.pappayaed.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pappayaed.Main.fragments.AssignmentFragment;
import com.pappayaed.Main.fragments.LeaveBaseFragment;
import com.pappayaed.Main.fragments.LeaveFragment;
import com.pappayaed.Main.fragments.LeaveTapFragment;
import com.pappayaed.R;
import com.pappayaed.fragmentnavigation.FragmentNavigationManager;
import com.pappayaed.fragmentnavigation.NavigationManager;
import com.pappayaed.preference.SessionManagenent;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements LeaveBaseFragment.CalloNRefersh {

    private Fragment fragment = null;
    private static final String TAG = "MainActivity";

    private NavigationManager navigationManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.leave:
                    addFragment(1);
                    return true;
//                case R.id.timetable:
//
//                    addFragment(4);
//                    return true;
                case R.id.assignment:

                    addFragment(3);
                    return true;
                case R.id.more:

                    addFragment(2);
//                    SessionManagenent.getSessionManagenent().clear();
                    return true;

//                case R.id.exam:
//
//                    addFragment(5);
////                    SessionManagenent.getSessionManagenent().clear();
//                    return true;
//                case R.id.studentexam:
//
//                    addFragment(6);
////                    SessionManagenent.getSessionManagenent().clear();
//                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);

        navigationManager = FragmentNavigationManager.obtain(this);

        addFragment(1);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().clear();
        SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
        String userType = (String) sessionManagenent.getSession().get(SessionManagenent.KEY_USERTYPE);
        if (userType.toLowerCase().equalsIgnoreCase("parent")) {
            navigation.inflateMenu(R.menu.buttomtab);
        } else if (userType.toLowerCase().equalsIgnoreCase("faculty")) {
            navigation.inflateMenu(R.menu.buttomtabfaculty);
        } else {
            navigation.inflateMenu(R.menu.buttomtab);
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        getSupportActionBar().hide();

    }

    private void addFragment(int pos) {
        if (pos == 1) {
            navigationManager.LeaveTapFragment(getString(R.string.title_leave));
//            getSupportActionBar().setTitle(getString(R.string.title_leave));
        } else if (pos == 2) {
            navigationManager.MoreFragment(getString(R.string.title_more));
//            getSupportActionBar().setTitle(getString(R.string.title_more));
        } else if (pos == 3) {
            if (SessionManagenent.getSessionManagenent().getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
                navigationManager.AssignmentFragmentBackup(getString(R.string.title_Assignment));
            } else {
                navigationManager.StudentSubmissionFragment(getString(R.string.title_Assignment));
            }
//            getSupportActionBar().setTitle(getString(R.string.title_Assignment));
        } else if (pos == 4) {
            navigationManager.StudentTableFragment(getString(R.string.title_timetable));
//            getSupportActionBar().setTitle(getString(R.string.title_timetable));
        } else if (pos == 5) {
            navigationManager.StudentExamFragment(getString(R.string.title_Exam));
//            getSupportActionBar().setTitle(getString(R.string.title_Exam));//
        } else if (pos == 6) {
            navigationManager.StudentExamParentFragment(getString(R.string.title_Exam1));
//            getSupportActionBar().setTitle(getString(R.string.title_Exam1));//
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment = navigationManager.getFragment();
        if (resultCode == 1) {
            if (fragment instanceof AssignmentFragment) {
//                ((AssignmentFragment) fragment).onRefresh();
            }
        } else if (resultCode == 2) {
            if (fragment instanceof LeaveTapFragment) {
//                try {
                ((LeaveTapFragment) fragment).onRefresh();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }

//        Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        fragment = navigationManager.getFragment();
        if (fragment instanceof LeaveTapFragment) {
            ((LeaveTapFragment) fragment).onRefresh();
        }
    }
}

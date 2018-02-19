package com.pappayaed.fragmentnavigation;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pappayaed.BuildConfig;
import com.pappayaed.Main.fragments.AssignmentFragmentBackup;
import com.pappayaed.Main.fragments.LeaveTapFragment;
import com.pappayaed.Main.fragments.MoreFragment;
import com.pappayaed.Main.fragments.StudentExamFragment;
import com.pappayaed.Main.fragments.StudentExamParentFragment;
import com.pappayaed.Main.fragments.StudentSubmissionFragment;
import com.pappayaed.Main.fragments.StudentTableFragment;
import com.pappayaed.R;

/**
 * Created by yasar on 20/7/17.
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private AppCompatActivity mActivity;
    private Fragment fragment;

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public static FragmentNavigationManager obtain(AppCompatActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(AppCompatActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void LeaveTapFragment(String title) {
        showFragment(new LeaveTapFragment(), false, title);
    }

    @Override
    public void MoreFragment(String title) {
        showFragment(new MoreFragment(), false, title);
    }

    @Override
    public void AssignmentFragmentBackup(String title) {
        showFragment(new AssignmentFragmentBackup(), false, title);
    }

    @Override
    public void StudentSubmissionFragment(String title) {
        showFragment(new StudentSubmissionFragment(), false, title);
    }

    @Override
    public void StudentTableFragment(String title) {
        showFragment(new StudentTableFragment(), false, title);
    }

    @Override
    public void StudentExamFragment(String title) {
        showFragment(new StudentExamFragment(), false, title);
    }

    @Override
    public void StudentExamParentFragment(String title) {
        showFragment(new StudentExamParentFragment(), false, title);
    }


    private void showFragment(Fragment fragment, boolean allowStateLoss, String tag) {
        FragmentManager fm = mFragmentManager;

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction();
//        ft.addToBackStack(null);
        setFragment(fragment);
        mActivity.getSupportActionBar().setTitle(tag);
        Fragment faFragment1 = fm.findFragmentByTag(tag);
        if (faFragment1 != null && faFragment1.isAdded()) {
            ft.show(fragment);
        } else {
            ft.replace(R.id.content, fragment, tag);
        }

        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }
}


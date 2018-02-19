package com.pappayaed.fragmentnavigation;

import android.support.v4.app.Fragment;

/**
 * Created by yasar on 20/7/17.
 */

public interface NavigationManager {

    void LeaveTapFragment(String title);

    void MoreFragment(String title);

    void AssignmentFragmentBackup(String title);

    void StudentTableFragment(String title);

    void StudentExamFragment(String title);

    void StudentExamParentFragment(String title);

    void StudentSubmissionFragment(String title);

    Fragment getFragment();
}

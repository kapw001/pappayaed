package com.pappayaed.Main.fragments;

import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.AssignmentContract;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.presenter.AssignmentPresenterImpl;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasar on 2/5/17.
 */

public class AssignmentFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String TAG = "AssignmentFragment";
    private ViewPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assignment_fragment, container, false);

        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

//                fragment = (BaseFragment) adapter.getItem(position);
//                publishFinish();


                if (position == 1) {
                    SubmissionFragment submissionFragment = (SubmissionFragment) adapter.getItem(position);
                    submissionFragment.onRefresh();
                }

                Log.e(TAG, "onTabSelected: " + position + "     " + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
//        adapter.addFragment(new BaseFragment(), "New");
////        adapter.addFragment(new PendingFragment(), "Pending");
//        adapter.addFragment(new BaseFragment(), "Completed");

        adapter.addFragment(new BaseFragment(), "Assignments");
//        adapter.addFragment(new PendingFragment(), "Pending");
        adapter.addFragment(new SubmissionFragment(), "Submission");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

package com.pappayaed.Main.fragments;

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

public class AssignmentFragmentBackup extends Fragment implements AssignmentContract.AssignmentView, SwipeRefreshLayout.OnRefreshListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String TAG = "AssignmentFragment";
    private ViewPagerAdapter adapter;
    private List<AssignmentList> list;
    private AssignmentPresenterImpl schollingPresenter;
    private BaseFragment fragment;
    private List<AssignmentList> publishList;
    private List<AssignmentList> finishList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assignment_fragment, container, false);
        schollingPresenter = new AssignmentPresenterImpl(this);
        list = new ArrayList<>();
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        publishList = new ArrayList<>();
        finishList = new ArrayList<>();

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                fragment = (BaseFragment) adapter.getItem(position);
                publishFinish();

                Log.e(TAG, "onTabSelected: " + position + "     " + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragment = (BaseFragment) adapter.getItem(0);

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         fetchDataFromServer();
                                     }
                                 }
        );
        return view;
    }

    private void fetchDataFromServer() {
        mSwipeRefreshLayout.setRefreshing(true);
        try {
            schollingPresenter.getDataformServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new BaseFragment(), "New");
//        adapter.addFragment(new PendingFragment(), "Pending");
        adapter.addFragment(new BaseFragment(), "Completed");

//        adapter.addFragment(new BaseFragment(), "Assignments");
////        adapter.addFragment(new PendingFragment(), "Pending");
//        adapter.addFragment(new SubmissionFragment(), "Submission");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void showProgress(String msg) {


        Utils.showProgress(getActivity(), msg);

    }

    @Override
    public void hideProgress() {

        Utils.hideProgress();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private String errorMsg = "";

    @Override
    public void showToast(String msg) {
        if (fragment != null) {
            fragment.setErrorMsg(msg);
            errorMsg = msg;
        }
//        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList(List<AssignmentList> listd) {



        mSwipeRefreshLayout.setRefreshing(false);
        if (listd != null && listd.size() > 0) {
            this.list.clear();
            this.list = listd;
        }

        publishList = filterList(list, "publish");
        finishList = filterList(list, "finish");
        publishFinish();

//        fragment.updateList(this.list);
//        if (errorMsg.equalsIgnoreCase("PappayaED server error,please try again later")) {
//            fragment.setErrorMsg(errorMsg);
//        }

    }

    public void publishFinish() {
        int pos = tabLayout.getSelectedTabPosition();
        if (pos == 0) {
            publish();
        } else {
            finish();
        }
    }

    private void publish() {
        fragment.updateList(publishList);
        if (errorMsg.equalsIgnoreCase("PappayaED server error,please try again later")) {
            fragment.setErrorMsg(errorMsg);
        }
    }

    private void finish() {
        fragment.updateList(finishList);
        if (errorMsg.equalsIgnoreCase("PappayaED server error,please try again later")) {
            fragment.setErrorMsg(errorMsg);
        }
    }

    private ArrayList<AssignmentList> filterList(List<AssignmentList> list, String state) {

        ArrayList<AssignmentList> newList = new ArrayList<>();
        for (AssignmentList assignmentList : list) {
            if (assignmentList.getState().toLowerCase().equalsIgnoreCase(state.toLowerCase())) {
                newList.add(assignmentList);
            }
        }
        return newList;
    }

    @Override
    public void onRefresh() {
        fetchDataFromServer();
        Log.e(TAG, "onRefresh: working.... ");
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

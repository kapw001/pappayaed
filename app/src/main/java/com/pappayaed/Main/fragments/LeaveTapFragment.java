package com.pappayaed.Main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pappayaed.App.App;
import com.pappayaed.Main.leave.LeaveActivity;
import com.pappayaed.Main.leave.ParentLeaveActivity;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.AssignmentContract;
import com.pappayaed.errormsg.Error;
import com.pappayaed.interf.WebResponse;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.StudentHolidaysDatum;
import com.pappayaed.network.WebRequest;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.AssignmentPresenterImpl;
import com.pappayaed.showprofile.ProfileApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.pappayaed.preference.SessionManagenent.KEY_USERTYPE;

/**
 * Created by yasar on 2/5/17.
 */

public class LeaveTapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String TAG = "AssignmentFragment";
    private ViewPagerAdapter adapter;
    private List<StudentHolidaysDatum> list;
    private LeaveBaseFragment fragment;
    private List<StudentHolidaysDatum> publishList;
    private List<StudentHolidaysDatum> finishList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private Retrofit retrofit = App.getApp().getRetrofit();
    private FloatingActionButton floatingActionButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.leave_fragmentnew, container, false);
//        schollingPresenter = new AssignmentPresenterImpl(this);
        list = new ArrayList<>();

        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManagenent.getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Parent")) {
                    startActivityForResult(new Intent(getActivity(), ParentLeaveActivity.class), 2);
                } else {
                    startActivityForResult(new Intent(getActivity(), LeaveActivity.class), 2);
                }


            }
        });

        if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
            floatingActionButton.setVisibility(View.GONE);
        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
        }


        mSwipeRefreshLayout.setOnRefreshListener(this);
        publishList = new ArrayList<>();
        finishList = new ArrayList<>();

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                fragment = (LeaveBaseFragment) adapter.getItem(position);
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

        fragment = (LeaveBaseFragment) adapter.getItem(0);


        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         try {
                                             getStudentHolidaysDatum();
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new LeaveBaseFragment(), "New");
        adapter.addFragment(new LeaveBaseFragment(), "Others");
        viewPager.setAdapter(adapter);
    }

    //    @Override
    public void showProgress(String msg) {
        Utils.showProgress(getActivity(), msg);
    }

    //    @Override
    public void hideProgress() {

        Utils.hideProgress();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private static String errorMsg = "";

    //    @Override
    public void showToast(String msg) {
        if (fragment != null) {
            fragment.setErrorMsg(msg);
            errorMsg = msg;
        }
//        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    public void updateList(List<StudentHolidaysDatum> listd) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (listd != null && listd.size() > 0) {
//            this.list.clear();
            this.list = listd;
        }

        publishList = filterList(listd);
        finishList = filterListCancel(listd);
        publishFinish();
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

    private ArrayList<StudentHolidaysDatum> filterList(List<StudentHolidaysDatum> list) {

        ArrayList<StudentHolidaysDatum> newList = new ArrayList<>();
        for (StudentHolidaysDatum assignmentList : list) {
            if (assignmentList.getState().toLowerCase().equalsIgnoreCase("confirm") || assignmentList.getState().toLowerCase().equalsIgnoreCase("draft")) {
                newList.add(assignmentList);
            }
        }
        return newList;
    }

    private ArrayList<StudentHolidaysDatum> filterListCancel(List<StudentHolidaysDatum> list) {

        ArrayList<StudentHolidaysDatum> newList = new ArrayList<>();
        for (StudentHolidaysDatum assignmentList : list) {
            if (assignmentList.getState().toLowerCase().equalsIgnoreCase("accepted") || assignmentList.getState().toLowerCase().equalsIgnoreCase("rejected") || assignmentList.getState().toLowerCase().equalsIgnoreCase("cancel") || assignmentList.getState().toLowerCase().equalsIgnoreCase("validate")) {
                newList.add(assignmentList);
            }
        }
        return newList;
    }

    @Override
    public void onRefresh() {
        try {
            getStudentHolidaysDatum();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


    public void getStudentHolidaysDatum() throws JSONException {

        Utils.showProgress(getActivity(), "Loading");

        ProfileApi profileApi = retrofit.create(ProfileApi.class);

        Map map = sessionManagenent.getSession();

        String username = map.get(SessionManagenent.KEY_EMAIL).toString();
        String password = map.get(SessionManagenent.KEY_PASSWORD).toString();
        String usertypes = map.get(SessionManagenent.KEY_USERTYPE).toString();

        JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("login", username);
        params.put("password", password);
        params.put("user_types", usertypes);
        jsonObject.put("params", params);

        Log.e(TAG, "getStudentHolidaysDatum: " + jsonObject.toString());


        profileApi.getLeave_list(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(false);
                }
                Utils.hideProgress();

                try {

                    if (response.body() != null) {

                        if (response.body().getResult().getStudentHolidaysData() != null) {
                            List<StudentHolidaysDatum> listv = response.body().getResult().getStudentHolidaysData();


                            list.clear();
                            list.addAll(listv);
                            updateList(listv);

                        } else {
//                                error.setVisibility(View.VISIBLE);
//                                error.setText(Error.nodata);
                            showToast(Error.nodata);
                        }


                    } else {
                        Log.e(TAG, "onResponse: There is no date ");
                        showToast(Error.nodata);
//                            error.setVisibility(View.VISIBLE);
//                            error.setText(Error.error);
                    }
                } catch (NullPointerException e) {
                    Log.e(TAG, "onResponse: There is no date ");
//                        error.setText(Error.error);
//                        error.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(false);
                    showToast(Error.error);
                }

            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
//                    error.setVisibility(View.VISIBLE);
                Utils.hideProgress();
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
//                    error.setText(Error.servererror);
                showToast(Error.servererror);
                Log.e(TAG, "onResponse: There is no date " + t.getMessage());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

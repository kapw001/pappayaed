package com.pappayaed.Main.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.Main.leave.LeaveActivity;
import com.pappayaed.R;
import com.pappayaed.adapter.LeaveListAdapter;
import com.pappayaed.adapter.SubmissionListAdapter;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentFormDatum;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.AssignmentSubLine;
import com.pappayaed.model.BystatusAssignmentListDatum;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.StudentHolidaysDatum;
import com.pappayaed.preference.SessionManagenent;
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

public class SubmissionFragment extends Fragment implements SubmissionListAdapter.RecyclerAdapterPositionClicked, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SubmissionListAdapter leaveListAdapter;
    private FloatingActionButton floatingActionButton;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

    private ArrayList<AssignmentSubLine> list;
    private Retrofit retrofit = App.getApp().getRetrofit();
    private static final String TAG = "LeaveFragment";
    private TextView error;

    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    private int edit_position;
    private AssignmentList assignmentList;
    //    private AlertDialog.Builder alertDialog;
//    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_fragment1, container, false);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);

        assignmentList = (AssignmentList) getArguments().getSerializable("assignmentlist");
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        list = new ArrayList<>();

        error = (TextView) view.findViewById(R.id.error);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);


        leaveListAdapter = new SubmissionListAdapter(this, list, recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(leaveListAdapter);
        error.setVisibility(View.GONE);
//        onRefresh();
        return view;
    }

    public static SubmissionFragment getInstance(AssignmentList assignmentList) {
        SubmissionFragment submissionFragment = new SubmissionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("assignmentlist", assignmentList);
        submissionFragment.setArguments(bundle);
        return submissionFragment;
    }

    public void getStudentHolidaysDatum(AssignmentList assignmentList) throws JSONException {
//        mSwipeRefreshLayout.setRefreshing(false);
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
        params.put("assignment_id", assignmentList.getAssignmentId());
        jsonObject.put("params", params);

        Log.e(TAG, "getStudentHolidaysDatum: " + jsonObject.toString());


        profileApi.getAssignmentForm(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {

//                Log.e(TAG, "onResponse: true");

                Utils.hideProgress();
//                mSwipeRefreshLayout.setRefreshing(false);
//                mSwipeRefreshLayout.setEnabled(false);
                try {

                    if (response.body() != null) {
                        error.setVisibility(View.GONE);

                        if (response.body().getResult().getAssignmentFormData() != null) {
                            Log.e(TAG, "onResponse: called ");
                            List<AssignmentSubLine> subLinesList = response.body().getResult().getAssignmentFormData().get(0).getAssignmentSubLine();

                            if (subLinesList.size() > 0) {

                                list.clear();
                                list.addAll(subLinesList);
                                leaveListAdapter.updateList(subLinesList);
                            } else {
                                error.setVisibility(View.VISIBLE);
                                error.setText(Error.nodata);
                            }
                        } else {
                            error.setVisibility(View.VISIBLE);
                            error.setText(Error.nodata);
                        }


                    } else {
                        Log.e(TAG, "onResponse: There is no date ");
                        error.setVisibility(View.VISIBLE);
                        error.setText(Error.error);
                    }
                } catch (NullPointerException e) {
                    Log.e(TAG, "onResponse: There is no date ");
                    error.setText(Error.error);
                    error.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: false");
//                mSwipeRefreshLayout.setRefreshing(false);
//                mSwipeRefreshLayout.setEnabled(false);
                error.setVisibility(View.VISIBLE);
                Utils.hideProgress();
                error.setText(Error.servererror);
                Log.e(TAG, "onResponse: There is no date " + t.getMessage());
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (leaveListAdapter != null) {
            leaveListAdapter.saveStates(outState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (leaveListAdapter != null) {
            leaveListAdapter.restoreStates(savedInstanceState);
        }

    }

    @Override
    public void position(int pos, View view) {

    }

    @Override
    public void onRefresh() {
//        mSwipeRefreshLayout.setRefreshing(true);
        try {
            getStudentHolidaysDatum(assignmentList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

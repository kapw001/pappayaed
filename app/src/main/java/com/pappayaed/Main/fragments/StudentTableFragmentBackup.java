package com.pappayaed.Main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pappayaed.App.App;
import com.pappayaed.Main.showassignmentprofile.TimetableDetailActivity;
import com.pappayaed.R;
import com.pappayaed.adapter.TimeTableAdapter;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.TimetableDatum;
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

/**
 * Created by yasar on 2/5/17.
 */

public class StudentTableFragmentBackup extends Fragment implements TimeTableAdapter.RecyclerAdapterPositionClicked {

    private static final String TAG = "StudentTableFragment";
    private RecyclerView recyclerView;
    private TimeTableAdapter recyclerViewAdapter;
    private ArrayList<TimetableDatum> list;
    private TextView error;

    private Retrofit retrofit = App.getApp().getRetrofit();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.timttable, container, false);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        error = (TextView) view.findViewById(R.id.error);
        recyclerViewAdapter = new TimeTableAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        Utils.showProgress(getActivity(), "Loading");
        try {
            getTimeTable();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }


    private void getTimeTable() throws JSONException {
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

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
        profileApi.getTimetable_list(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
//                Log.e(TAG, "onResponse: " + response.body().getResult().getFacultyTimetableFormData());
//                error.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.GONE);
                Utils.hideProgress();

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().getTimetableData() != null) {
                            list.clear();
                            list.addAll(response.body().getResult().getTimetableData());
                            List<TimetableDatum> list = response.body().getResult().getTimetableData();
                            recyclerViewAdapter.updateList(list);
                            error.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            if (list.size() <= 0) {
                                error.setText(Error.nodata);
                                error.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            Log.e(TAG, "onResponse: error Time Table ");
                            error.setVisibility(View.VISIBLE);
                            error.setText(Error.nodata);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        error.setVisibility(View.VISIBLE);
                        error.setText(Error.error);
                        recyclerView.setVisibility(View.GONE);
                    }

                } catch (NullPointerException e) {
                    error.setVisibility(View.VISIBLE);
                    error.setText(Error.error);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                error.setText(Error.servererror);
                error.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Utils.hideProgress();
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    @Override
    public void position(int pos, View view) {

        TimetableDatum as = list.get(pos);


        Intent intent = new Intent(getActivity(), TimetableDetailActivity.class);
        intent.putExtra("assignmentlist", as);
        // Get the transition name from the string
        String transitionName = "test";

        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        view,   // Starting view
                        transitionName    // The String
                );

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

    }
}

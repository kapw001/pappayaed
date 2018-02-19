package com.pappayaed.Main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pappayaed.App.App;
import com.pappayaed.R;
import com.pappayaed.adapter.ExamMarkSheetAdapter;
import com.pappayaed.adapter.ExamMarkSheetSubjectListAdapter;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.MarkListData;
import com.pappayaed.model.MarksheetDatum;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.ProfileApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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

public class StudentMarkSheetSubjectListFragment extends Fragment implements ExamMarkSheetSubjectListAdapter.RecyclerAdapterPositionClicked {

    private static final String TAG = "StudentTableFragment";
    private RecyclerView recyclerView;
    private ExamMarkSheetSubjectListAdapter recyclerViewAdapter;
    private ArrayList<MarkListData> list;
    private TextView error;

    private Retrofit retrofit = App.getApp().getRetrofit();

    private LinearLayout linearLayout;

    public static StudentMarkSheetSubjectListFragment getInstance(List<Integer> result_line) {
        StudentMarkSheetSubjectListFragment frag = new StudentMarkSheetSubjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("result_line", (Serializable) result_line);

        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.timttable, container, false);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        linearLayout = (LinearLayout) view.findViewById(R.id.dateslayout);
        linearLayout.setVisibility(View.GONE);

        error = (TextView) view.findViewById(R.id.error);
        recyclerViewAdapter = new ExamMarkSheetSubjectListAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
//        Utils.showProgress(getActivity(), "Loading");

        List<Integer> result_line = (List<Integer>) getArguments().getSerializable("result_line");


        try {
            getTimeTable(result_line);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }


    private void getTimeTable(List<Integer> result_line) throws JSONException {
        Utils.showProgress(getActivity(), "Loading");
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
        params.put("result_line", new JSONArray(result_line));
        jsonObject.put("params", params);

        Log.e(TAG, "getTimeTable:post data  " + jsonObject.toString());

        profileApi.getMarksheetResultList(jsonObject.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());
                Utils.hideProgress();
                try {

                    if (response.body() != null) {
                        if (response.body() != null) {
                            list.clear();


                            JSONObject jsonObject1 = new JSONObject(response.body());

                            JSONArray jsonArray = jsonObject1.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject11 = jsonArray.optJSONObject(i);

                                MarkListData markListData = new MarkListData();
                                markListData.setExam_id(jsonObject11.optString("exam_id"));
                                markListData.setMarks(jsonObject11.optString("marks"));
                                markListData.setPer(jsonObject11.optString("per"));
                                markListData.setTotal_marks(jsonObject11.optString("total_marks"));
                                markListData.setStatus(jsonObject11.optString("status"));
                                markListData.setStudent_id(jsonObject11.optString("student_id"));
                                markListData.setSubject(jsonObject11.optString("subject"));
                                list.add(markListData);


                            }


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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.setText(Error.servererror);
                error.setVisibility(View.VISIBLE);
                Utils.hideProgress();
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    @Override
    public void position(int pos, View view) {

//        MarkListData marksheetDatum = list.get(pos);

//        Log.e(TAG, "position: Line Id " + marksheetDatum.getResultLine());
//
//
//        Intent intent = new Intent(getActivity(), TimetableDetailActivity.class);
//        intent.putExtra("lineid", marksheetDatum.getResultLine().get(0));
//        // Get the transition name from the string
//        String transitionName = "test";
//
//        ActivityOptionsCompat options =
//
//                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                        view,   // Starting view
//                        transitionName    // The String
//                );
//
//        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

    }
}

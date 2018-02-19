package com.pappayaed.Main.fragments;

import android.app.Activity;
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
import android.widget.Toast;

import com.pappayaed.Main.showassignmentprofile.AssignmentProfileActivity;
import com.pappayaed.R;
import com.pappayaed.adapter.AssignmentAdapter;
import com.pappayaed.adapter.LeaveListAdapter;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.StudentHolidaysDatum;
import com.pappayaed.preference.SessionManagenent;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.pappayaed.preference.SessionManagenent.KEY_USERTYPE;

/**
 * Created by yasar on 2/5/17.
 */

public class LeaveBaseFragment extends Fragment implements LeaveListAdapter.RecyclerAdapterPositionClicked {

    private RecyclerView recyclerView;
    private LeaveListAdapter recyclerViewAdapter;
    private ArrayList<StudentHolidaysDatum> list;
    private TextView error;

    private static int requestid = 1;

    private CalloNRefersh calloNRefersh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        calloNRefersh = (CalloNRefersh) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment, container, false);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        error = (TextView) view.findViewById(R.id.error);
        recyclerViewAdapter = new LeaveListAdapter(this, list, recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }


    public void updateList(List<StudentHolidaysDatum> list) {
        if (list != null && list.size() > 0) {
            this.list.clear();
            this.list.addAll(list);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.updateList(list);
            error.setVisibility(View.GONE);
        } else {
            error.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public void setErrorMsg(String msg) {

        error.setText(msg);
        error.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        Log.e(TAG, "setErrorMsg: " + msg);

    }

    @Override
    public void position(int pos, View view) {


    }

    @Override
    public void onRefresh() {
        calloNRefersh.onRefresh();

    }

    public interface CalloNRefersh {
        void onRefresh();
    }


}

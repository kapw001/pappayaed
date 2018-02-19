package com.pappayaed.Main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import com.pappayaed.common.Utils;
import com.pappayaed.contract.AssignmentContract;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.AssignmentPresenterImpl;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.pappayaed.preference.SessionManagenent.KEY_USERTYPE;

/**
 * Created by yasar on 2/5/17.
 */

public class BaseFragment extends Fragment implements AssignmentAdapter.RecyclerAdapterPositionClicked {
    private static final String TAG = "BaseFragment";
    private RecyclerView recyclerView;
    private AssignmentAdapter recyclerViewAdapter;
    private ArrayList<AssignmentList> list;
    private TextView error;
    private static int requestid = 1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
            Log.d("MyFragment", "Fragment is visible.");
        else
            Log.d("MyFragment", "Fragment is not visible.");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);

        list = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        error = (TextView) view.findViewById(R.id.error);
        recyclerViewAdapter = new AssignmentAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }


    public void updateList(List<AssignmentList> listl) {

        Log.e(TAG, "updateList: size " + listl.size());

        if (listl != null && listl.size() > 0) {
            this.list = new ArrayList<>();
            ;
            this.list.addAll(listl);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.updateList(listl);
            recyclerViewAdapter.notifyDataSetChanged();
            Log.e(TAG, "updateList: size Visible " + listl.size());
            error.setVisibility(View.GONE);

            for (int i = 0; i < listl.size(); i++) {
                Log.e(TAG, "updateList: getCompanyId  " + listl.get(i).getCompanyId());
            }

        } else {
            error.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public void setErrorMsg(String msg) {
        if (msg != null) {

            try {
                error.setText(msg);
                error.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);

            } catch (NullPointerException e) {

            }


        }

    }

    @Override
    public void position(int pos, View view) {

        AssignmentList as = list.get(pos);


        Intent intent = new Intent(getActivity(), AssignmentProfileActivity.class);
        intent.putExtra("assignmentlist", as);
        // Get the transition name from the string
        String transitionName = "test";

        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        view,   // Starting view
                        transitionName    // The String
                );

        if (SessionManagenent.getSessionManagenent().getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
            ActivityCompat.startActivityForResult(getActivity(), intent, requestid, options.toBundle());

        } else {

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }

//        startActivity(new Intent(getActivity(), AssignmentProfileActivity.class).putExtra("assignmentlist", as));

//        Log.e(TAG, "position: " + as.toString());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getContext(), "" + requestCode + "    " + resultCode, Toast.LENGTH_SHORT).show();
        if (resultCode == requestid) {

//            ((AssignmentFragment) getParentFragment()).onRefresh();
        }
    }


}

package com.pappayaed.Main.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
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

public class LeaveFragmentBackUp extends Fragment implements LeaveListAdapter.RecyclerAdapterPositionClicked {

    private RecyclerView recyclerView;
    private LeaveListAdapter leaveListAdapter;
    private FloatingActionButton floatingActionButton;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

    private ArrayList<StudentHolidaysDatum> list;
    private Retrofit retrofit = App.getApp().getRetrofit();
    private static final String TAG = "LeaveFragment";
    private TextView error;

    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    private int edit_position;
//    private AlertDialog.Builder alertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.leave_fragment, container, false);

        list = new ArrayList<>();

        error = (TextView) view.findViewById(R.id.error);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        leaveListAdapter = new LeaveListAdapter(this, list, recyclerView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), LeaveActivity.class), 2);
            }
        });
        if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
            floatingActionButton.setVisibility(View.GONE);
        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                linearLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(leaveListAdapter);
        error.setVisibility(View.GONE);
        initSwipe();
//        initDialog();

        try {
            getStudentHolidaysDatum();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
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


        if (sessionManagenent.getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Student")) {


            Log.e(TAG, "Assignment : post profle data  " + jsonObject.toString());

            profileApi.getStudent_leave_request_tree_view(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    Utils.hideProgress();
                    try {

                        if (response.body() != null) {
                            error.setVisibility(View.GONE);
                            if (response.body().getResult().getStudentHolidaysData() != null) {
                                List<StudentHolidaysDatum> listv = response.body().getResult().getStudentHolidaysData();
                                list.clear();
                                list.addAll(listv);
                                leaveListAdapter.updateList(listv);
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
                    error.setVisibility(View.VISIBLE);
                    Utils.hideProgress();
                    error.setText(Error.servererror);
                    Log.e(TAG, "onResponse: There is no date " + t.getMessage());
                }
            });
        } else {

            Log.e(TAG, "Assignment : post profle data  " + jsonObject.toString());

            profileApi.getfaculty_leave_request_tree_view(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    Utils.hideProgress();
                    try {

                        if (response.body() != null) {
                            error.setVisibility(View.GONE);
                            if (response.body().getResult().getStudentHolidaysData() != null) {
                                List<StudentHolidaysDatum> listv = response.body().getResult().getStudentHolidaysData();
                                list.clear();
                                list.addAll(listv);
                                leaveListAdapter.updateList(listv);
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
                    error.setVisibility(View.VISIBLE);
                    Utils.hideProgress();
                    error.setText(Error.servererror);
                    Log.e(TAG, "onResponse: There is no date " + t.getMessage());
                }
            });

        }

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


        if (sessionManagenent.getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {


        }


    }

    @Override
    public void onRefresh() {
        try {
            getStudentHolidaysDatum();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {

                    initDialog();

                }

//                else {
////                    removeView();
////                    edit_position = position;
//////                    alertDialog.setTitle("Cancel request");
//////                    alertDialog.show();
//                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(view);


        if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {

            alertDialog.setTitle("Accept or Reject");
            ;

            alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Accept", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });
            alertDialog.setNegativeButton("Reject", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Reject", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        } else {

            alertDialog.setTitle("Cancel");
            alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });

        }

        view.findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

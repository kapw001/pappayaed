package com.pappayaed.Main.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.Main.showassignmentprofile.TimetableDetailActivity;
import com.pappayaed.R;
import com.pappayaed.adapter.GridViewRecyclerAdapter;
import com.pappayaed.adapter.TimeTableAdapter;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.TimetableDatum;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.ProfileApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yasar on 2/5/17.
 */

public class StudentTableFragment extends Fragment implements TimeTableAdapter.RecyclerAdapterPositionClicked, GridViewRecyclerAdapter.OnItemClickListener {

    private static final String TAG = "StudentTableFragment";
    private RecyclerView recyclerView;
    private TimeTableAdapter recyclerViewAdapter;
    private ArrayList<TimetableDatum> list;
    private TextView error;
    private RecyclerView gridRecyclerView;
    private List<String> listgrid;
    private GridViewRecyclerAdapter gridViewRecyclerAdapter;
    private ArrayList<TimetableDatum> listFilterd;

    private Retrofit retrofit = App.getApp().getRetrofit();


    private LinearLayout layoutstartdate, layoutendate;
    private TextView startdate, enddate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.timttable, container, false);

        layoutstartdate = (LinearLayout) view.findViewById(R.id.layoutstartdate);
        layoutendate = (LinearLayout) view.findViewById(R.id.layoutenddate);
        startdate = (TextView) view.findViewById(R.id.startdate);
        enddate = (TextView) view.findViewById(R.id.enddate);
        startdate.setText(Utils.getCurrentDate(0));
        enddate.setText(Utils.getCurrentDate(7));
        layoutstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(startdate);
            }
        });

        layoutendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerEndDate(enddate);
            }
        });


        listFilterd = new ArrayList<>();
        listgrid = new ArrayList<>();

        listgrid.addAll(getDateList(startdate.getText().toString(), enddate.getText().toString()));


        gridViewRecyclerAdapter = new GridViewRecyclerAdapter(this, getActivity(), listgrid);

        gridRecyclerView = (RecyclerView) view.findViewById(R.id.gridView);
// set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 7);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        gridRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        gridRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)); // set LayoutManager to RecyclerView
//        gridRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        gridRecyclerView.addItemDecoration(new EqualSpaceItemDecoration(1));
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.setAdapter(gridViewRecyclerAdapter);


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

    private void datePicker(final TextView textView) {
        if (textView.getText().toString().length() > 0) {
            int mYear, mMonth, mDay, mHour, mMinute;
            // Get Current Date
            final Calendar c = Calendar.getInstance();

            Date date = getDate(textView.getText().toString());
            c.setTime(date);

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar cu = Utils.getCalendar(getDate(enddate.getText().toString()));

                            Calendar sl = Utils.getCalendar(getDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));

                            if (sl.compareTo(cu) <= 0) {


                                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                List<String> list = getDateList(startdate.getText().toString(), enddate.getText().toString());

                                gridViewRecyclerAdapter.update(list);
                                gridViewRecyclerAdapter.notifyDataSetChanged();

                                try {
                                    getTimeTable();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(getActivity(), "Start date cannot be greater than end date ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else {
            Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
        }

    }

    private void datePickerEndDate(final TextView textView) {
        if (textView.getText().toString().length() > 0) {
            int mYear, mMonth, mDay, mHour, mMinute;
            // Get Current Date
            final Calendar c = Calendar.getInstance();

            Date date = getDate(textView.getText().toString());
            c.setTime(date);

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar cu = Utils.getCalendar(getDate(startdate.getText().toString()));

                            Calendar sl = Utils.getCalendar(getDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));

                            if (cu.compareTo(sl) <= 0) {

                                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                List<String> list = getDateList(startdate.getText().toString(), enddate.getText().toString());

                                gridViewRecyclerAdapter.update(list);
                                gridViewRecyclerAdapter.notifyDataSetChanged();
                                try {
                                    getTimeTable();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(getActivity(), "End date cannot be lesthen than start date ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else {
            Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
        }

    }

    private Date getDate(String s) {

        DateFormat formatter;
        Date date = null;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }


    private List<String> getDateList(String startdate, String endDate) {

        List<String> list = new ArrayList<>();

        int numberDays = Utils.diffBwTwodates(startdate, endDate);

        for (int i = 0; i <= numberDays; i++) {

            list.add(Utils.getCurrentDate(startdate, i));
        }

        return list;

    }


    @Override
    public void position(String itemName) {

        itemName = Utils.convertDateToString(Utils.convertStringToDate(itemName));


        List<TimetableDatum> filterItem = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            TimetableDatum timetableDatum = list.get(i);

            String st = timetableDatum.getStartDatetime();

            Log.e(TAG, "position: " + st + "       " + itemName);

            if (st.contains(itemName.toLowerCase().toString())) {
                filterItem.add(timetableDatum);
            }

        }

        listFilterd.clear();
        listFilterd.addAll(filterItem);

        recyclerViewAdapter.updateList(filterItem);
        error.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        if (filterItem.size() <= 0) {
            error.setText(Error.nodata);
            error.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


    }

    public class EqualSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mSpaceHeight;

        public EqualSpaceItemDecoration(int mSpaceHeight) {
            this.mSpaceHeight = mSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mSpaceHeight;
            outRect.top = mSpaceHeight;
            outRect.left = mSpaceHeight;
            outRect.right = mSpaceHeight;
        }
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
        params.put("start_datetime", Utils.convertDateToString(Utils.convertStringToDate(startdate.getText().toString())) + " 00:00:00");
        params.put("end_datetime", Utils.convertDateToString(Utils.convertStringToDate(enddate.getText().toString())) + " 00:00:00");

        jsonObject.put("params", params);

        //{"params": {"login":"thomas@mail.com", "password":"a","user_types":"Faculty","start_datetime":"2017-09-01 12:00:00","end_datetime":"2017-09-20 12:00:00"}}

        Log.e(TAG, "getTimeTable: post data    " + jsonObject.toString());

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
                            position(startdate.getText().toString());

                            gridViewRecyclerAdapter.selectedPosition = 0;
                            gridRecyclerView.scrollToPosition(gridViewRecyclerAdapter.selectedPosition);
                            gridViewRecyclerAdapter.notifyDataSetChanged();

//                            recyclerViewAdapter.updateList(list);
//                            error.setVisibility(View.GONE);
//                            recyclerView.setVisibility(View.VISIBLE);
//                            if (list.size() <= 0) {
//                                error.setText(Error.nodata);
//                                error.setVisibility(View.VISIBLE);
//                                recyclerView.setVisibility(View.GONE);
//                            }
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

        TimetableDatum as = listFilterd.get(pos);


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

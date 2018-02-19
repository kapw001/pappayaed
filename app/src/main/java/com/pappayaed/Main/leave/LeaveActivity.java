package com.pappayaed.Main.leave;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.R;
import com.pappayaed.adapter.CustomAdapter;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.StudentHolidaysStatusDatum;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.Profile;
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
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.pappayaed.preference.SessionManagenent.KEY_USERID;
import static com.pappayaed.preference.SessionManagenent.KEY_USERNAME;


public class LeaveActivity extends AppCompatActivity {

    private EditText description;
    private TextView startdate, enddate, days, studentname;
    private Spinner spinner;

    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private Retrofit retrofit = App.getApp().getRetrofit();

    private CustomAdapter customAdapter;

    private ArrayList<StudentHolidaysStatusDatum> list;
    private static final String TAG = "LeaveActivity";

    private LinearLayout errormsglayout, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();

        errormsglayout = (LinearLayout) findViewById(R.id.errormsglayout);
        content = (LinearLayout) findViewById(R.id.content);

        String name = sessionManagenent.getSession().get(KEY_USERNAME).toString();

        studentname = (TextView) findViewById(R.id.studentname);
        description = (EditText) findViewById(R.id.description);
        startdate = (TextView) findViewById(R.id.startdate);
        days = (TextView) findViewById(R.id.days);
        enddate = (TextView) findViewById(R.id.enddate);
        spinner = (Spinner) findViewById(R.id.leave);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(startdate, enddate);
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(enddate);
            }
        });
//        customAdapter = new CustomAdapter(this, R.layout.spinner_row, R.id.name, list);
//        spinner.setAdapter(customAdapter);

        studentname.setText(name);
        try {
            getStudentLeaveType();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getStudentLeaveType() throws JSONException {

        if (App.getApp().hasNetwork()) {

            Utils.showProgress(this, "Loading");
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

            Log.e(TAG, "getStudentLeaveType: " + jsonObject.toString());

            profileApi.getStudent_get_leave_type(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    Utils.hideProgress();
                    try {

                        if (response.body() != null && response.body().getResult().getStudentHolidaysStatusData() != null) {

                            List<StudentHolidaysStatusDatum> listv = response.body().getResult().getStudentHolidaysStatusData();
                            list.clear();
                            list.addAll(listv);
                            customAdapter = new CustomAdapter(LeaveActivity.this, R.layout.spinner_row, R.id.name, listv);
                            spinner.setAdapter(customAdapter);
                        } else {
                            errormsglayout.setVisibility(View.VISIBLE);
                            content.setVisibility(View.GONE);
                            Toast.makeText(LeaveActivity.this, "There is no leave type available.", Toast.LENGTH_SHORT).show();

                        }
                    } catch (NullPointerException e) {
                        errormsglayout.setVisibility(View.VISIBLE);
                        content.setVisibility(View.GONE);
                        Toast.makeText(LeaveActivity.this, "There is no leave type available.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResultResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Utils.hideProgress();
                }
            });
        } else {
            Toast.makeText(this, Error.servererror, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.leavemenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        } else if (item.getItemId() == R.id.approve) {
            try {
                applyLeaveRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            StudentHolidaysStatusDatum datum = list.get(spinner.getSelectedItemPosition());
//
//            Log.e(TAG, "onOptionsItemSelected: " + datum.getName() + "   id  " + datum.getId());

//            Toast.makeText(this, "Approve Clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void datePicker(final TextView textView, final TextView textView1) {
        int mYear, mMonth, mDay, mHour, mMinute;
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        Calendar cu = Utils.getCalendar(new Date());

                        Calendar sl = Utils.getCalendar(getDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));

                        Log.e(TAG, "onDateSet: " + cu.compareTo(sl));

//                        Log.e(TAG, "onDateSet: " + currentDate.after(selectedDate) + "    " + currentDate.equals(selectedDate));

                        if (cu.compareTo(sl) <= 0) {

                            textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            textView1.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            try {
                                putdays();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LeaveActivity.this, "Please select future date ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

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


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar cu = Utils.getCalendar(getDate(startdate.getText().toString()));

                            Calendar sl = Utils.getCalendar(getDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));

                            if (cu.compareTo(sl) <= 0) {
                                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                try {
                                    putdays();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(LeaveActivity.this, "Please select future date ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else {
            Toast.makeText(this, "Please select first start date ", Toast.LENGTH_SHORT).show();
        }

    }

    private void putdays() throws ParseException {


        String str_date = startdate.getText().toString();
        String end_date = enddate.getText().toString();
        DateFormat formatter;
        Date s;
        Date e;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        s = formatter.parse(str_date);
        e = formatter.parse(end_date);

        long diff = e.getTime() - s.getTime();
        long leavedays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        Log.e(TAG, "putdays: " + leavedays);
        days.setText(leavedays + "");


    }

    private String getTimeDate(Date date) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return formatter.format(date);
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

    private Date getDateTime(String s) {

        DateFormat formatter;
        Date date = null;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }

    private void applyLeaveRequest() throws JSONException {


//        Log.e(TAG, "applyLeaveRequest:  id  " + SessionManagenent.getSessionManagenent().getSession().get(KEY_USERID).toString());

        if (App.getApp().hasNetwork()) {
            if (list.size() > 0) {
                int spinnerItem = list.get(spinner.getSelectedItemPosition()).getId();

                description.setError(null);
                if (description.getText().length() <= 0) {
                    description.setError("Description should not be empty");
                }
//            else if (!spinnerItem.isEmpty() && spinnerItem.length() <= 0) {
//                Toast.makeText(this, "Please select leave type", Toast.LENGTH_SHORT).show();
//            }
                else if (startdate.getText().length() <= 0 && enddate.getText().length() <= 0) {
                    Toast.makeText(this, "Please select start and end date", Toast.LENGTH_SHORT).show();
                } else {


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
                    params.put("description", description.getText().toString());
                    params.put("leave_type", spinnerItem + "");
                    params.put("date_from", startdate.getText().toString() + " 12:00:00");
                    params.put("date_to", enddate.getText().toString() + " 12:00:00");
                    jsonObject.put("params", params);

                    Log.e(TAG, "applyLeaveRequest: post data " + jsonObject.toString());

                    profileApi.getStudentLeaveApplyForm(jsonObject.toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            Log.e(TAG, "onResponse: " + response.body().toString());

                            try {
                                JSONObject jsonObject1 = new JSONObject(response.body());

                                JSONObject result = jsonObject1.getJSONObject("result");

                                String error = result.getString("error_msg");

                                if (error != null && error.length() > 0) {
                                    Toast.makeText(LeaveActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            setResult(2);
                            finish();

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });

                    Toast.makeText(this, "Leave request applied", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "There is no leave type ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, Error.servererror, Toast.LENGTH_SHORT).show();
        }

    }


}

package com.pappayaed.fetchdataformserver;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pappayaed.App.App;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentFormDatum;
import com.pappayaed.model.AttachmentFileId;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.SchollingPresenterImpl;
import com.pappayaed.showprofile.Profile;
import com.pappayaed.showprofile.ProfileApi;
import com.pappayaed.showprofile.StudentList;
import com.pappayaed.showprofile.UserDetails;

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
 * Created by yasar on 8/5/17.
 */

public class ScrollingPresenterFetchData {
    private static final String TAG = "ScrollingPresenterFetch";
    private SchollingPresenterImpl schollingPresenter;
    private Retrofit retrofit = App.getApp().getRetrofit();
    private Profile profile;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

    public ScrollingPresenterFetchData(SchollingPresenterImpl schollingPresenter) {

        this.schollingPresenter = schollingPresenter;
    }

    public void getDataFromServer() {

    }

    public void getDataFromLocal() throws JSONException {

        schollingPresenter.showProgress("Fetching data");


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


        Log.e(TAG, "getDataFromLocal: post profle data  " + jsonObject.toString());

        profileApi.getstudentprofile(jsonObject.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());

                Gson gson = new GsonBuilder().create();
                try {

                    if (response.body() != null) {
                        String result = new JSONObject(response.body()).getString("result");

                        profile = gson.fromJson(result, Profile.class);
//
                        updateList(profile);
                    } else {
                        schollingPresenter.hideProgress();
                        schollingPresenter.showTost(Error.error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    schollingPresenter.hideProgress();
                    schollingPresenter.showTost(Error.odooservererror);

                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
                schollingPresenter.hideProgress();
                schollingPresenter.showTost(Error.servererror);
            }
        });


    }

    public void getDataFromLocalAssignment(int assingmentId) throws JSONException {

        schollingPresenter.showProgress("Fetching data");


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
        params.put("assignment_id", assingmentId);
        jsonObject.put("params", params);


        Log.e(TAG, "getDataFromLocal: post Assignment ID data  " + jsonObject.toString());

        profileApi.getAssignmentForm(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
//                Log.e(TAG, "onResponse: Test " + response.body().getResult().getAssignmentFormData().toString());


                try {
                    if (response.body().getResult().getAssignmentFormData() != null) {
                        List<AssignmentFormDatum> assignmentFormDatumList = response.body().getResult().getAssignmentFormData();

                        AssignmentFormDatum assignmentFormDatum = assignmentFormDatumList.get(0);
                        updateListAssignment(assignmentFormDatum);
                        schollingPresenter.hideProgress();
                    } else {
                        schollingPresenter.hideProgress();
                        schollingPresenter.showTost(Error.error);
                    }
                } catch (NullPointerException e) {
                    schollingPresenter.hideProgress();
                    schollingPresenter.showTost(Error.error);

                }
//                    schollingPresenter.showTost(Error.odooservererror);


            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
                schollingPresenter.hideProgress();
                schollingPresenter.showTost(Error.servererror);
            }
        });


    }

    public void updateLocalStudent(StudentList studentList) {

        updateList(studentList);

    }


    private void updateList(Profile profile) {
        List<UserDetails> list = new ArrayList<>();
        if (!sessionManagenent.getKeyUsertype().equalsIgnoreCase("Parent")) {
            list.add(new UserDetails("Personal Detail", null, null, UserDetails.HEADER));
            list.add(new UserDetails("Email", profile.getEmail(), null, UserDetails.ROW));
            list.add(new UserDetails("Mobile", profile.getMobile(), null, UserDetails.ROW));
//            list.add(new UserDetails("Phone", profile.getPhone(), null, UserDetails.ROW));
            list.add(new UserDetails("Gender", profile.getGender(), null, UserDetails.ROW));
            list.add(new UserDetails("Date of birth", profile.getDob(), null, UserDetails.ROW));
            list.add(new UserDetails("Blood Group", profile.getBloodGroup(), null, UserDetails.ROW));
            list.add(new UserDetails("Nationality", profile.getNationality(), null, UserDetails.ROW));

            list.add(new UserDetails("Contact Detail", null, null, UserDetails.HEADER));
            list.add(new UserDetails("Street 1", profile.getStreet1(), null, UserDetails.ROW));
            list.add(new UserDetails("Street 2", profile.getStreet2(), null, UserDetails.ROW));
            list.add(new UserDetails("City", profile.getCity(), null, UserDetails.ROW));
            list.add(new UserDetails("State", profile.getState(), null, UserDetails.ROW));
            list.add(new UserDetails("Zip", profile.getZip(), null, UserDetails.ROW));
            list.add(new UserDetails("Country", profile.getCountry(), null, UserDetails.ROW));

            if (!sessionManagenent.getKeyUsertype().equalsIgnoreCase("Faculty")) {
                list.add(new UserDetails("Educational Detail", null, null, UserDetails.HEADER));
                list.add(new UserDetails("Department", profile.getDepartment(), null, UserDetails.ROW));
                list.add(new UserDetails("Course", profile.getCourseName(), null, UserDetails.ROW));
                list.add(new UserDetails("Batch", profile.getBatchName(), null, UserDetails.ROW));
                list.add(new UserDetails("GR Number", profile.getGrNumber(), null, UserDetails.ROW));
                list.add(new UserDetails("Current Roll Number", profile.getCurrentRollNumber(), null, UserDetails.ROW));
            }
        } else {

            list.add(new UserDetails("Personal Detail", null, null, UserDetails.HEADER));
            list.add(new UserDetails("Email", profile.getEmail(), null, UserDetails.ROW));
            list.add(new UserDetails("Mobile", profile.getMobile(), null, UserDetails.ROW));
//            list.add(new UserDetails("Phone", profile.getPhone(), null, UserDetails.ROW));

            list.add(new UserDetails("Address Detail", null, null, UserDetails.HEADER));
            list.add(new UserDetails("Street 1", profile.getStreet1(), null, UserDetails.ROW));
            list.add(new UserDetails("Street 2", profile.getStreet2(), null, UserDetails.ROW));
            list.add(new UserDetails("City", profile.getCity(), null, UserDetails.ROW));
            list.add(new UserDetails("State", profile.getState(), null, UserDetails.ROW));
            list.add(new UserDetails("Zip", profile.getZip(), null, UserDetails.ROW));
            list.add(new UserDetails("Country", profile.getCountry(), null, UserDetails.ROW));

            List<StudentList> studentList = profile.getStudentList();
            list.add(new UserDetails("Children Detail", null, null, UserDetails.HEADER));
            try {

                for (int i = 0; i < studentList.size(); i++) {
                    StudentList studentList1 = studentList.get(i);
                    list.add(new UserDetails("Child " + (i + 1), studentList1.getName(), studentList1.getPhoto(), studentList1, UserDetails.ROW));
                }

            } catch (NullPointerException e) {
                Log.e(TAG, "stuent not available " + e.getMessage());
            }


        }

        try {
            schollingPresenter.hideProgress();
            schollingPresenter.updateList(list);
            schollingPresenter.updateListProfile(profile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateList(StudentList profile) {
        List<UserDetails> list = new ArrayList<>();

        list.add(new UserDetails("Personal Detail", null, null, UserDetails.HEADER));
        list.add(new UserDetails("Email", profile.getEmail(), null, UserDetails.ROW));
        list.add(new UserDetails("Mobile", profile.getMobile(), null, UserDetails.ROW));
//        list.add(new UserDetails("Phone", profile.getPhone(), null, UserDetails.ROW));
        list.add(new UserDetails("Gender", profile.getGender(), null, UserDetails.ROW));
        list.add(new UserDetails("Date of birth", profile.getDob(), null, UserDetails.ROW));
        list.add(new UserDetails("Blood Group", profile.getBloodGroup(), null, UserDetails.ROW));
        list.add(new UserDetails("Nationality", profile.getNationality(), null, UserDetails.ROW));

        list.add(new UserDetails("Contact Detail", null, null, UserDetails.HEADER));
        list.add(new UserDetails("Street 1", profile.getStreet1(), null, UserDetails.ROW));
        list.add(new UserDetails("Street 2", profile.getStreet2(), null, UserDetails.ROW));
        list.add(new UserDetails("City", profile.getCity(), null, UserDetails.ROW));
        list.add(new UserDetails("State", profile.getState(), null, UserDetails.ROW));
        list.add(new UserDetails("Zip", profile.getZip(), null, UserDetails.ROW));
        list.add(new UserDetails("Country", profile.getCountry(), null, UserDetails.ROW));


        list.add(new UserDetails("Educational Detail", null, null, UserDetails.HEADER));
        list.add(new UserDetails("Department", profile.getDepartment(), null, UserDetails.ROW));
        list.add(new UserDetails("Course", profile.getCourseName(), null, UserDetails.ROW));
        list.add(new UserDetails("Batch", profile.getBatchName(), null, UserDetails.ROW));
        list.add(new UserDetails("GR Number", profile.getGrNumber(), null, UserDetails.ROW));
        list.add(new UserDetails("Current Roll Number", profile.getCurrentRollNumber(), null, UserDetails.ROW));


        try {
            schollingPresenter.hideProgress();
            schollingPresenter.updateList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateListAssignment(AssignmentFormDatum profile) {
        List<UserDetails> list = new ArrayList<>();

        list.add(new UserDetails("Assignment Detail", null, null, UserDetails.HEADER));
        list.add(new UserDetails("Name", profile.getName().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Subject", profile.getSubjectId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Faculty", profile.getFacultyId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Course", profile.getCourseId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Department", profile.getDepartmentId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Batch", profile.getBatchId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("University", profile.getUniversityId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Marks", profile.getMarks().toString().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Issued Date", Utils.getConvertedDate(profile.getIssuedDate().toString()), null, UserDetails.ROW));
        list.add(new UserDetails("Submission Date", Utils.getConvertedDate(profile.getSubmissionDate().toString()), null, UserDetails.ROW));
        list.add(new UserDetails("State", profile.getState().toString(), null, UserDetails.ROW));

        if (profile.getAttachmentFileIds() != null) {
            List<AttachmentFileId> atl = profile.getAttachmentFileIds();

            if (atl.size() > 0)
                list.add(new UserDetails("Attachment List", "Attachment List", UserDetails.ROW, atl));
        }


        try {
            schollingPresenter.hideProgress();
            schollingPresenter.updateList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

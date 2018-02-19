package com.pappayaed.network;

import android.util.Log;

import com.pappayaed.App.App;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.interf.WebResponse;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.StudentHolidaysDatum;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.ProfileApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yasar on 31/7/17.
 */

public class WebRequest {

    private SessionManagenent sessionManagenent;
    private Retrofit retrofit;
    private ProfileApi profileApi;

    private static WebRequest webRequest;

    private WebRequest() {
        sessionManagenent = SessionManagenent.getSessionManagenent();
        retrofit = App.getApp().getRetrofit();
        profileApi = retrofit.create(ProfileApi.class);

    }

    public static WebRequest getWebRequest() {
        if (webRequest == null) {
            webRequest = new WebRequest();

        }

        return webRequest;
    }


    public void getLeaveRequest(final WebResponse webResponse) {

        profileApi.getLeave_list(getUserDetails().toString()).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                webResponse.onResponse(response);
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                webResponse.onFailure(t);
            }
        });


    }


    private JSONObject getUserDetails() {
        JSONObject jsonObject = new JSONObject();
        try {
            Map map = sessionManagenent.getSession();
            String username = map.get(SessionManagenent.KEY_EMAIL).toString();
            String password = map.get(SessionManagenent.KEY_PASSWORD).toString();
            String usertypes = map.get(SessionManagenent.KEY_USERTYPE).toString();
            JSONObject params = new JSONObject();
            params.put("login", username);
            params.put("password", password);
            params.put("user_types", usertypes);
            jsonObject.put("params", params);
        } catch (JSONException e) {

        }

        return jsonObject;
    }
}

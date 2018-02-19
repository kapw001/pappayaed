package com.pappayaed.fetchdataformserver;

import android.util.Log;
import com.pappayaed.App.App;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.ResultResponse;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.AssignmentPresenterImpl;
import com.pappayaed.showprofile.Profile;
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
 * Created by yasar on 8/5/17.
 */

public class AssignmentPresenterFetchData {
    private static final String TAG = "ScrollingPresenterFetch";
    private AssignmentPresenterImpl schollingPresenter;
    private Retrofit retrofit = App.getApp().getRetrofit();
    private Profile profile;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

    public AssignmentPresenterFetchData(AssignmentPresenterImpl schollingPresenter) {

        this.schollingPresenter = schollingPresenter;
    }


    public void getDataFromServer() throws JSONException {

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


        Log.e(TAG, "Assignment : post profle data  " + jsonObject.toString());

        profileApi.getAssignmentList(jsonObject.toString()).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                Log.e(TAG, "onResponse: Assignment  " + response.body());
                try {

                    if (response.body() != null) {

                        List<AssignmentList> list = response.body().getResult().getAssignmentList();

                        if (list != null && list.size() > 0) {
                            schollingPresenter.updateList(list);
                            schollingPresenter.hideProgress();
                        } else {
                            List<AssignmentList> listEmpty = new ArrayList<AssignmentList>();
                            schollingPresenter.updateList(listEmpty);
                            schollingPresenter.hideProgress();
                        }
                    } else {
//                        List<AssignmentList> listEmpty = new ArrayList<AssignmentList>();
//                        schollingPresenter.updateList(listEmpty);
                        schollingPresenter.hideProgress();
                        schollingPresenter.showTost(Error.error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    schollingPresenter.hideProgress();
                    schollingPresenter.showTost(Error.error);

                } catch (NullPointerException e) {
                    schollingPresenter.hideProgress();
                    schollingPresenter.showTost(Error.error);
                }


            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
                schollingPresenter.hideProgress();
                schollingPresenter.showTost(Error.servererror);
            }
        });


    }


}

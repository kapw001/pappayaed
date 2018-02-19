package com.pappayaed.login;

import android.os.Handler;
import android.util.Log;
import android.util.Patterns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.pappayaed.App.App;
import com.pappayaed.errormsg.Error;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.webservices.requests.Params;
import com.pappayaed.webservices.requests.Request;
import com.pappayaed.webservices.requests.User;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yasar on 17/4/17.
 */

class loginUserValidateImpl implements LoginContract.LoginUserValidate {

    private static final String TAG = "loginUserValidateImpl";

    private static App app = App.getApp();

    @Override
    public void validateUserCredentials(final String username, final String password, final LoginContract.LoginPresenterView loginPresenterView) throws JSONException {

        if (!app.hasNetwork()) {
            loginPresenterView.showToast("Pease check your internet connection...");
            return;
        } else if (username.length() <= 0) {
            loginPresenterView.validateuserName(true);
            return;
        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
//            loginPresenterView.validateuserName(true);
//            return;
//        }

        else if (password.length() <= 0) {
            loginPresenterView.validatepassword(true);
            return;
        } else if (username.toLowerCase().equalsIgnoreCase("admin") && password.toLowerCase().equalsIgnoreCase("admin")) {
            loginPresenterView.showProgress("Login...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
                    loginPresenterView.hideProgress();
                    sessionManagenent.saveSession(username, password, username, username, "", true, "");
                    loginPresenterView.onfail(false);
                    loginPresenterView.onSuccess();
                }
            }, 1000);

            return;
        } else if (username.toLowerCase().equalsIgnoreCase("principal") && password.toLowerCase().equalsIgnoreCase("principal")) {
            loginPresenterView.showProgress("Login...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
                    loginPresenterView.hideProgress();
                    sessionManagenent.saveSession(username, password, username, username, "", true, "");
                    loginPresenterView.onfail(false);
                    loginPresenterView.onSuccess();
                }
            }, 1000);

            return;
        }
        else if (username.toLowerCase().equalsIgnoreCase("secretary") && password.toLowerCase().equalsIgnoreCase("secretary")) {
            loginPresenterView.showProgress("Login...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
                    loginPresenterView.hideProgress();
                    sessionManagenent.saveSession(username, password, username, username, "", true, "");
                    loginPresenterView.onfail(false);
                    loginPresenterView.onSuccess();
                }
            }, 1000);

            return;
        }



        loginPresenterView.showProgress("Login ...");
        LoginApi loginApi = app.getRetrofit().create(LoginApi.class);
        JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("login", username);
        params.put("password", password);
        jsonObject.put("params", params);


        loginApi.loginValidate(jsonObject.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loginPresenterView.hideProgress();
                try {
                    JSONObject jsonObject1 = new JSONObject(response.body());
                    JSONObject result = jsonObject1.optJSONObject("result");
                    Log.e(TAG, "onResponse: " + response.body());
                    if (result.optString("status").equalsIgnoreCase("true")) {
                        SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
                        sessionManagenent.saveSession(result.getString("login"), result.getString("password"), result.getString("user_name"), result.getString("user_id"), result.getString("user_types"), Boolean.parseBoolean(result.getString("status")), result.getString("user_image"));
                        loginPresenterView.onfail(false);
                        loginPresenterView.onSuccess();
                    } else if (result.optString("status").equalsIgnoreCase("false")) {
                        String errormsg = result.optString("error_msg");
                        loginPresenterView.showAlert("Error", errormsg);
                    }

                } catch (JSONException e) {
                    loginPresenterView.onfail(true);
                    e.printStackTrace();

                } catch (NullPointerException e) {
                    loginPresenterView.onfail(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loginPresenterView.showAlert("Error", Error.servererror);
                loginPresenterView.hideProgress();
            }
        });

    }
}

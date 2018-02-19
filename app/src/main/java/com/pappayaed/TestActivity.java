package com.pappayaed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pappayaed.App.App;
import com.pappayaed.App.Con;
import com.pappayaed.login.LoginApi;
import com.pappayaed.webservices.requests.Request;
import com.pappayaed.webservices.requests.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class TestActivity extends AppCompatActivity implements Responses.Res {

    private static final String TAG = "TestActivity";
    private AStart aStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        String mainUrl = "https://demoschool.pappaya.co.uk/";
        App.getApp().setUrlForRetrofit(mainUrl);
        aStart = new AStart();

    }

    public void onTest(View view) {

        ApiCalls apiCalls = new ApiCalls(App.getApp().getRetrofit());

        ApiRequest apiRequest = new ApiRequest(apiCalls);

        Map<Object, Object> params = new HashMap<>();
        Map<Object, Object> js = new HashMap<>();
        params.put("login", "zahid1994@gmail.com");
        params.put("password", "pappaya");
        js.put("params", params);

        Log.e(TAG, "onTest: " + new JSONObject(js).toString());

        apiRequest.loginValidation(new JSONObject(js).toString(), this);

//        apiRequest.getComments(this);

    }

    @Override
    public void validate(Object object) {


        if (object instanceof User) {
            User user = (User) object;
            Log.e(TAG, "Ok : " + user.getEmail() + user.getPassword());

            ApiCalls apiCalls = new ApiCalls(App.getApp().getRetrofit());

            ApiRequest apiRequest = new ApiRequest(apiCalls);

            apiRequest.getComments(TestActivity.this);

        } else if (object instanceof List) {

            Log.e(TAG, "validate: " + ((List) object).size());

            for (int i = 0; i < ((List) object).size(); i++) {

                Comment comment = (Comment) ((List) object).get(i);

                Log.e(TAG, "validate: " + comment.toString());

            }

        }


    }

    @Override
    public void fail(String s) {

        Log.e(TAG, "fail: " + s);

    }

    public void onTest2(View view) {

        aStart.startActivity(TestActivity.this, ResultActivity.class);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        aStart.onActivityResult(requestCode, resultCode, data);
    }
}


class AStart {

    private static final String TAG = "AStart";

    public void startActivity(Activity context, Class<?> bClass) {
        context.startActivityForResult(new Intent(context, bClass), 1);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1) {

            String name = data.getStringExtra("name");
            setName(name);

        }
    }

    private void setName(String name) {

        Log.e(TAG, "setName: " + name);
    }


}


class Responses {


    public static void validateUser(Response<String> response, Res res) throws JSONException {

        if (response != null && response.body() != null) {

            JSONObject jsonObject = new JSONObject(response.body().toString());
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            User user = new User();
            user.setEmail(jsonObject1.getString("user_image"));
            res.validate(user);


        }

    }

    interface Res {

        void validate(Object object);

        void fail(String s);

    }


}

class ApiRequest {

    private ApiCalls apiCalls;


    public ApiRequest(ApiCalls apiCalls) {
        this.apiCalls = apiCalls;
    }

    public void loginValidation(String json, final Responses.Res res) {
        apiCalls.loginValidate(json).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                Log.e(TAG, "onResponse: " + response.body().toString());

                try {
                    Responses.validateUser(response, res);
                } catch (JSONException e) {
                    e.printStackTrace();
                    res.fail("something went wrong");
//                    Log.e(TAG, "onResponse: " + e);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                res.fail(t.getMessage());
            }
        });
    }

    public void loginValidateUser(@Body Request json, Callback callback) {
        apiCalls.loginValidateUser(json).enqueue(callback);
    }

    public void getComments(final Responses.Res res) {
        apiCalls.getComments().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                res.validate(response.body());

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                res.fail(t.getMessage());

            }
        });

    }

}


class ApiCalls implements LoginApi {

    private Retrofit retrofit;

    public ApiCalls(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    @Override
    public Call<String> loginValidate(@Body String json) {
        return retrofit.create(LoginApi.class).loginValidate(json);
    }

    @Override
    public Call<String> loginValidateUser(@Body Request json) {
        return retrofit.create(LoginApi.class).loginValidateUser(json);
    }

    @Override
    public Call<List<Comment>> getComments() {
        return retrofit.create(LoginApi.class).getComments();
    }
}


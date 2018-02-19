package com.pappayaed.login;


import com.pappayaed.Comment;
import com.pappayaed.webservices.requests.Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yasar on 18/4/17.
 */

public interface LoginApi {

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/login")
    Call<String> loginValidate(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/login")
    Call<String> loginValidateUser(@Body Request json);


    //    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("https://jsonplaceholder.typicode.com/posts/1/comments")
    Call<List<Comment>> getComments();

}

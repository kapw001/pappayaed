package com.pappayaed.interf;

import com.pappayaed.model.ResultResponse;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yasar on 31/7/17.
 */

public interface WebResponse {

    void onResponse(Response<ResultResponse> response);

    void onFailure(Throwable t);

}

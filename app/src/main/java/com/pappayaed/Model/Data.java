package com.pappayaed.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yasar on 18/4/17.
 */

public class Data {
    @SerializedName("data")
    private List<SampleModel> list;

    public List<SampleModel> getList() {
        return list;
    }

    public void setList(List<SampleModel> list) {
        this.list = list;
    }
}

package com.pappayaed.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by yasar on 21/4/17.
 */

public class M {


    @SerializedName("result")
    @Expose
    public Map<String, String> list;

    //    @SerializedName("Obj")
//    @Expose
//    public Map<String, Obj> list;
//
    @Override
    public String toString() {
        return "M{" +
                "list=" + list +
                '}';
    }
}

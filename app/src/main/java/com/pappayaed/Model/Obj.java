package com.pappayaed.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yasar on 21/4/17.
 */

public class Obj {
    @SerializedName("a")
    String a;
    @SerializedName("b")
    String b;
    @SerializedName("c")
    String c;

    @Override
    public String toString() {
        return "Obj{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}

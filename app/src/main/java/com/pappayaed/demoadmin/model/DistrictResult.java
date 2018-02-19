package com.pappayaed.demoadmin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yasar on 7/2/18.
 */

public class DistrictResult implements Serializable, Parcelable
{

    @SerializedName("state_detail")
    @Expose
    private StateDetail stateDetail;
    @SerializedName("district_list")
    @Expose
    private List<DistrictList> districtList = null;
    public final static Parcelable.Creator<DistrictResult> CREATOR = new Creator<DistrictResult>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DistrictResult createFromParcel(Parcel in) {
            return new DistrictResult(in);
        }

        public DistrictResult[] newArray(int size) {
            return (new DistrictResult[size]);
        }

    }
            ;
    private final static long serialVersionUID = -6285702571296279750L;

    protected DistrictResult(Parcel in) {
        this.stateDetail = ((StateDetail) in.readValue((StateDetail.class.getClassLoader())));
        in.readList(this.districtList, (DistrictList.class.getClassLoader()));
    }

    public DistrictResult() {
    }

    public StateDetail getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(StateDetail stateDetail) {
        this.stateDetail = stateDetail;
    }

    public List<DistrictList> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictList> districtList) {
        this.districtList = districtList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(stateDetail);
        dest.writeList(districtList);
    }

    public int describeContents() {
        return 0;
    }

}
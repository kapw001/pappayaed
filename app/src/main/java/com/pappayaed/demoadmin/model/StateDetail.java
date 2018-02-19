package com.pappayaed.demoadmin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yasar on 7/2/18.
 */

public class StateDetail implements Serializable, Parcelable
{

    @SerializedName("totalPopulation")
    @Expose
    private Long totalPopulation;
    @SerializedName("malePopulation")
    @Expose
    private Long malePopulation;
    @SerializedName("femalePopulation")
    @Expose
    private Long femalePopulation;
    @SerializedName("malePopulationPercent")
    @Expose
    private Float malePopulationPercent;
    @SerializedName("femalePopulationPercent")
    @Expose
    private Float femalePopulationPercent;
    @SerializedName("maleLiteracy")
    @Expose
    private Float maleLiteracy;
    @SerializedName("femaleLiteracy")
    @Expose
    private Float femaleLiteracy;
    @SerializedName("totalLiteracy")
    @Expose
    private Float totalLiteracy;
    public final static Parcelable.Creator<StateDetail> CREATOR = new Creator<StateDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StateDetail createFromParcel(Parcel in) {
            return new StateDetail(in);
        }

        public StateDetail[] newArray(int size) {
            return (new StateDetail[size]);
        }

    }
            ;
    private final static long serialVersionUID = -9055019326340385673L;

    protected StateDetail(Parcel in) {
        this.totalPopulation = ((Long) in.readValue((Long.class.getClassLoader())));
        this.malePopulation = ((Long) in.readValue((Long.class.getClassLoader())));
        this.femalePopulation = ((Long) in.readValue((Long.class.getClassLoader())));
        this.malePopulationPercent = ((Float) in.readValue((Double.class.getClassLoader())));
        this.femalePopulationPercent = ((Float) in.readValue((Double.class.getClassLoader())));
        this.maleLiteracy = ((Float) in.readValue((Double.class.getClassLoader())));
        this.femaleLiteracy = ((Float) in.readValue((Double.class.getClassLoader())));
        this.totalLiteracy = ((Float) in.readValue((Double.class.getClassLoader())));
    }

    public StateDetail() {
    }

    public Long getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(Long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public Long getMalePopulation() {
        return malePopulation;
    }

    public void setMalePopulation(Long malePopulation) {
        this.malePopulation = malePopulation;
    }

    public Long getFemalePopulation() {
        return femalePopulation;
    }

    public void setFemalePopulation(Long femalePopulation) {
        this.femalePopulation = femalePopulation;
    }

    public Float getMalePopulationPercent() {
        return malePopulationPercent;
    }

    public void setMalePopulationPercent(Float malePopulationPercent) {
        this.malePopulationPercent = malePopulationPercent;
    }

    public Float getFemalePopulationPercent() {
        return femalePopulationPercent;
    }

    public void setFemalePopulationPercent(Float femalePopulationPercent) {
        this.femalePopulationPercent = femalePopulationPercent;
    }

    public Float getMaleLiteracy() {
        return maleLiteracy;
    }

    public void setMaleLiteracy(Float maleLiteracy) {
        this.maleLiteracy = maleLiteracy;
    }

    public Float getFemaleLiteracy() {
        return femaleLiteracy;
    }

    public void setFemaleLiteracy(Float femaleLiteracy) {
        this.femaleLiteracy = femaleLiteracy;
    }

    public Float getTotalLiteracy() {
        return totalLiteracy;
    }

    public void setTotalLiteracy(Float totalLiteracy) {
        this.totalLiteracy = totalLiteracy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalPopulation);
        dest.writeValue(malePopulation);
        dest.writeValue(femalePopulation);
        dest.writeValue(malePopulationPercent);
        dest.writeValue(femalePopulationPercent);
        dest.writeValue(maleLiteracy);
        dest.writeValue(femaleLiteracy);
        dest.writeValue(totalLiteracy);
    }

    public int describeContents() {
        return 0;
    }

}
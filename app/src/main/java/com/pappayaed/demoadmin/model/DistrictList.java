package com.pappayaed.demoadmin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yasar on 7/2/18.
 */

public class DistrictList implements Serializable, Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("male")
    @Expose
    private Float male;
    @SerializedName("female")
    @Expose
    private Float female;
    @SerializedName("overall")
    @Expose
    private Float overall;
    public final static Parcelable.Creator<DistrictList> CREATOR = new Creator<DistrictList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DistrictList createFromParcel(Parcel in) {
            return new DistrictList(in);
        }

        public DistrictList[] newArray(int size) {
            return (new DistrictList[size]);
        }

    };
    private final static long serialVersionUID = -3853006060470730591L;

    protected DistrictList(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.male = ((Float) in.readValue((Float.class.getClassLoader())));
        this.female = ((Float) in.readValue((Float.class.getClassLoader())));
        this.overall = ((Float) in.readValue((Float.class.getClassLoader())));
    }

    public DistrictList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMale() {
        return male;
    }

    public void setMale(Float male) {
        this.male = male;
    }

    public Float getFemale() {
        return female;
    }

    public void setFemale(Float female) {
        this.female = female;
    }

    public Float getOverall() {
        return overall;
    }

    public void setOverall(Float overall) {
        this.overall = overall;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(male);
        dest.writeValue(female);
        dest.writeValue(overall);
    }

    public int describeContents() {
        return 0;
    }
}

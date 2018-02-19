package com.pappayaed.showprofile;

/**
 * Created by yasar on 23/5/17.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentList implements Parcelable,Serializable{
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gr_number")
    @Expose
    private String grNumber;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("street1")
    @Expose
    private String street1;
    @SerializedName("street2")
    @Expose
    private String street2;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("current_roll_number")
    @Expose
    private String currentRollNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("country")
    @Expose
    private String country;

    protected StudentList(Parcel in) {
        bloodGroup = in.readString();
        photo = in.readString();
        nationality = in.readString();
        batchName = in.readString();
        grNumber = in.readString();
        city = in.readString();
        courseName = in.readString();
        zip = in.readString();
        state = in.readString();
        department = in.readString();
        email = in.readString();
        street1 = in.readString();
        street2 = in.readString();
        phone = in.readString();
        currentRollNumber = in.readString();
        name = in.readString();
        dob = in.readString();
        gender = in.readString();
        mobile = in.readString();
        country = in.readString();
    }

    public static final Creator<StudentList> CREATOR = new Creator<StudentList>() {
        @Override
        public StudentList createFromParcel(Parcel in) {
            return new StudentList(in);
        }

        @Override
        public StudentList[] newArray(int size) {
            return new StudentList[size];
        }
    };

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrNumber() {
        return grNumber;
    }

    public void setGrNumber(String grNumber) {
        this.grNumber = grNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCurrentRollNumber() {
        return currentRollNumber;
    }

    public void setCurrentRollNumber(String currentRollNumber) {
        this.currentRollNumber = currentRollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bloodGroup);
        dest.writeString(photo);
        dest.writeString(nationality);
        dest.writeString(batchName);
        dest.writeString(grNumber);
        dest.writeString(city);
        dest.writeString(courseName);
        dest.writeString(zip);
        dest.writeString(state);
        dest.writeString(department);
        dest.writeString(email);
        dest.writeString(street1);
        dest.writeString(street2);
        dest.writeString(phone);
        dest.writeString(currentRollNumber);
        dest.writeString(name);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(mobile);
        dest.writeString(country);
    }
}

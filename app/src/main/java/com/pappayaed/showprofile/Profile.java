package com.pappayaed.showprofile;

/**
 * Created by yasar on 11/5/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {

    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
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
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("password")
    @Expose
    private String password;
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
    @SerializedName("user_types")
    @Expose
    private String userTypes;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("student_list")
    @Expose
    private List<StudentList> studentList = null;

    public List<StudentList> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentList> studentList) {
        this.studentList = studentList;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

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

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String userTypes) {
        this.userTypes = userTypes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}

package com.pappayaed.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable {
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("error_msg")
    @Expose
    private String error_msg;

    public List<SubmissionList> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<SubmissionList> submissionList) {
        this.submissionList = submissionList;
    }

    @SerializedName("submission_list")
    @Expose
    private List<SubmissionList> submissionList = null;

    @SerializedName("user_types")
    @Expose
    private String userTypes;
    @SerializedName("assignment_list")
    @Expose
    private List<AssignmentList> assignmentList = null;
    private final static long serialVersionUID = -2664528074184562113L;

    @SerializedName("student_holidays_status_data")
    @Expose
    private List<StudentHolidaysStatusDatum> studentHolidaysStatusData = null;

    @SerializedName("stu_marksheet_line_data")
    @Expose
    private List<StuMarksheetLineDatum> stuMarksheetLineData = null;

    @SerializedName("bystatus_assignment_list_data")
    @Expose
    private List<BystatusAssignmentListDatum> bystatusAssignmentListData = null;

    @SerializedName("attachment_detail")
    @Expose
    private AttachmentDetail attachmentDetail;

    public AttachmentDetail getAttachmentDetail() {
        return attachmentDetail;
    }

    public void setAttachmentDetail(AttachmentDetail attachmentDetail) {
        this.attachmentDetail = attachmentDetail;
    }

    @SerializedName("marksheet_data")
    @Expose
    private List<MarksheetDatum> marksheetData = null;

    public List<MarksheetDatum> getMarksheetData() {
        return marksheetData;
    }

    public void setMarksheetData(List<MarksheetDatum> marksheetData) {
        this.marksheetData = marksheetData;
    }

    @SerializedName("student_data")
    @Expose
    private List<StudentDatum> studentData = null;

    public List<StudentDatum> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentDatum> studentData) {
        this.studentData = studentData;
    }

    public List<BystatusAssignmentListDatum> getBystatusAssignmentListData() {
        return bystatusAssignmentListData;
    }

    public void setBystatusAssignmentListData(List<BystatusAssignmentListDatum> bystatusAssignmentListData) {
        this.bystatusAssignmentListData = bystatusAssignmentListData;
    }

    public List<StudentMarksheet> getStudentMarksheet() {
        return studentMarksheet;
    }

    public void setStudentMarksheet(List<StudentMarksheet> studentMarksheet) {
        this.studentMarksheet = studentMarksheet;
    }

    @SerializedName("student_marksheet")

    @Expose
    private List<StudentMarksheet> studentMarksheet = null;

    public List<StuMarksheetLineDatum> getStuMarksheetLineData() {
        return stuMarksheetLineData;
    }

    public List<StudentHolidaysStatusDatum> getStudentHolidaysStatusData() {
        return studentHolidaysStatusData;
    }

    public void setStudentHolidaysStatusData(List<StudentHolidaysStatusDatum> studentHolidaysStatusData) {
        this.studentHolidaysStatusData = studentHolidaysStatusData;
    }

    public void setStuMarksheetLineData(List<StuMarksheetLineDatum> stuMarksheetLineData) {
        this.stuMarksheetLineData = stuMarksheetLineData;
    }

    @SerializedName("assignment_form_data")
    @Expose
    private List<AssignmentFormDatum> assignmentFormData = null;

    public List<FacultyTimetableFormDatum> getFacultyTimetableFormData() {
        return facultyTimetableFormData;
    }

    public void setFacultyTimetableFormData(List<FacultyTimetableFormDatum> facultyTimetableFormData) {
        this.facultyTimetableFormData = facultyTimetableFormData;
    }

    @SerializedName("faculty_timetable_form_data")
    @Expose
    private List<FacultyTimetableFormDatum> facultyTimetableFormData = null;

    @SerializedName("student_holidays_data")
    @Expose
    private List<StudentHolidaysDatum> studentHolidaysData = null;


    @SerializedName("timetable_data")
    @Expose
    private List<TimetableDatum> timetableData = null;

    public List<TimetableDatum> getTimetableData() {
        return timetableData;
    }

    public void setTimetableData(List<TimetableDatum> timetableData) {
        this.timetableData = timetableData;
    }

    public List<StudentHolidaysDatum> getStudentHolidaysData() {
        return studentHolidaysData;
    }

    public void setStudentHolidaysData(List<StudentHolidaysDatum> studentHolidaysData) {
        this.studentHolidaysData = studentHolidaysData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String userTypes) {
        this.userTypes = userTypes;
    }

    public List<AssignmentList> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<AssignmentList> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public List<AssignmentFormDatum> getAssignmentFormData() {
        return assignmentFormData;
    }

    public void setAssignmentFormData(List<AssignmentFormDatum> assignmentFormData) {
        this.assignmentFormData = assignmentFormData;
    }

    @Override
    public String toString() {
        return "Result{" +
                "assignmentId=" + assignmentId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userTypes='" + userTypes + '\'' +
                ", assignmentList=" + assignmentList +
                ", assignmentFormData=" + assignmentFormData +
                '}';
    }
}
package com.pappayaed.showprofile;

import com.pappayaed.model.ResultResponse;
import com.pappayaed.model.VrVideoModel;
import com.pappayaed.webservices.requests.Request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by yasar on 18/4/17.
 */

public interface ProfileApi {


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/profile")
    Call<String> getstudentprofile(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/profile")
    Call<String> getstudentprofileAnother(@Body Request json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/assignment_list")
    Call<ResultResponse> getAssignmentList(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/assignment_form")
    Call<ResultResponse> getAssignmentForm(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/assignment_form_publish")
    Call<String> publish(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/assignment_form_finish")
    Call<String> finish(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_assignment_submission")
    Call<String> submission(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_assignment_accept")
    Call<String> accept(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_assignment_reject")
    Call<String> reject(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_assignment_change_req")
    Call<String> changerequest(@Body String json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_assignment_change_and_resubmit")
    Call<String> change_and_resubmit(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_timetable_form")
    Call<ResultResponse> getTimeTable(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_exam_form")
    Call<ResultResponse> getExampTable(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_get_leave_type")
    Call<ResultResponse> getStudentLeaveType(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/leave_apply_form")
    Call<String> getStudentLeaveApplyForm(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_leave_request_tree_view")
    Call<ResultResponse> getStudent_leave_request_tree_view(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_leave_request_tree_view")
    Call<ResultResponse> getfaculty_leave_request_tree_view(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_cancel_leave_request")
    Call<String> cancelStudent_leave_request(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_approve_leave_request")
    Call<String> approveFaculty_leave_request(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_change_state_leave_request")
    Call<String> faculty_change_state_leave_request(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/faculty_refuse_leave_request")
    Call<String> refuseFaculty_leave_request(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/parent_student_marksheet_form")
    Call<ResultResponse> getParent_student_marksheet_form(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/get_leave_list")
    Call<ResultResponse> getLeave_list(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/bystatus_assignment_list")
    Call<ResultResponse> getBystatus_assignment_list(@Body String json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/change_state")
    Call<String> getChange_state(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/parant_get_student_list")
    Call<ResultResponse> getParant_get_student_list(@Body String json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/change_state")
    Call<ResultResponse> getchange_state(@Body String json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/timetable_list")
    Call<ResultResponse> getTimetable_list(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/marksheet_list")
    Call<ResultResponse> getMarksheet_list(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/student_get_leave_type")
    Call<ResultResponse> getStudent_get_leave_type(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/get_student_list")
    Call<ResultResponse> getStudent_list(@Body String json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/update_leave_state")
    Call<String> getUpdate_leave_state(@Body String json);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/attachment_id_data")
    Call<ResultResponse> getAttachment_id_data(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/get_assignment_submission_list")
    Call<ResultResponse> get_Assignment_submission_list(@Body String json);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("mobile/rest/marksheet_result_list")
    Call<String> getMarksheetResultList(@Body String json);

    @GET("https://devjk.pappaya.education/mobile/rest/video")
    Call<VrVideoModel> getVrVideosList();

}

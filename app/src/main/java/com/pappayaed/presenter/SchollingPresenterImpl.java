package com.pappayaed.presenter;

import com.pappayaed.showprofile.Profile;
import com.pappayaed.contract.ScrollingContract;
import com.pappayaed.fetchdataformserver.ScrollingPresenterFetchData;
import com.pappayaed.showprofile.StudentList;
import com.pappayaed.showprofile.UserDetails;

import org.json.JSONException;

import java.util.List;

/**
 * Created by yasar on 8/5/17.
 */

public class SchollingPresenterImpl implements ScrollingContract.ScrollingGetListData {

    private Profile profile;
    private ScrollingContract.ScrollingView scrollingView;
    private ScrollingPresenterFetchData scrollingPresenterFetchData;

    public SchollingPresenterImpl(ScrollingContract.ScrollingView scrollingView) {
        this.scrollingView = scrollingView;
        this.scrollingPresenterFetchData = new ScrollingPresenterFetchData(this);
    }

    public void getDataformServer() throws JSONException {
        scrollingPresenterFetchData.getDataFromLocal();

    }

    public void getDataformServerAssignment(int assignmentId) throws JSONException {
        scrollingPresenterFetchData.getDataFromLocalAssignment(assignmentId);

    }

    public void updateLocalData(StudentList studentList) throws JSONException {
        scrollingPresenterFetchData.updateLocalStudent(studentList);

    }

    @Override
    public void updateList(List<UserDetails> list) throws JSONException {
        scrollingView.updateList(list);
    }

    @Override
    public void updateListProfile(Profile profile) throws JSONException {
        this.profile = profile;
        scrollingView.updateListProfile(profile);
    }


    public void showProgress(String msg) {
        scrollingView.showProgress(msg);

    }

    public void showTost(String msg) {
        scrollingView.showToast(msg);
    }


    public void hideProgress() {
        scrollingView.hideProgress();
    }
}

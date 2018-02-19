package com.pappayaed.presenter;

import com.pappayaed.fetchdataformserver.AssignmentPresenterFetchData;
import com.pappayaed.contract.AssignmentContract;
import com.pappayaed.model.AssignmentList;


import org.json.JSONException;

import java.util.List;

/**
 * Created by yasar on 8/5/17.
 */

public class AssignmentPresenterImpl implements AssignmentContract.AssignmentGetListData {

    private AssignmentContract.AssignmentView scrollingView;
    private AssignmentPresenterFetchData scrollingPresenterFetchData;

    public AssignmentPresenterImpl(AssignmentContract.AssignmentView scrollingView) {
        this.scrollingView = scrollingView;
        this.scrollingPresenterFetchData = new AssignmentPresenterFetchData(this);
    }

    public AssignmentPresenterImpl() {
    }

    public void getDataformServer() throws JSONException {
        scrollingPresenterFetchData.getDataFromServer();

    }

    @Override
    public void updateList(List<AssignmentList> list) throws JSONException {
        scrollingView.updateList(list);
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

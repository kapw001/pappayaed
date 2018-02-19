package com.pappayaed.contract;

import com.pappayaed.BaseView;
import com.pappayaed.model.AssignmentFormDatum;
import com.pappayaed.model.AssignmentList;

import org.json.JSONException;

import java.util.List;

/**
 * Created by yasar on 17/4/17.
 */

public interface AssignmentContract {

    interface AssignmentView extends BaseView {

        void updateList(List<AssignmentList> list);


    }

    interface AssignmentPresenter {
        void validateCredentials(String username, String password);

    }

    interface AssignmentPresenterView extends BaseView {

    }

    interface AssignmentGetListData {
        void updateList(List<AssignmentList> list) throws JSONException;

    }

}

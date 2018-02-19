package com.pappayaed.contract;

import com.pappayaed.BaseView;
import com.pappayaed.showprofile.Profile;
import com.pappayaed.showprofile.UserDetails;

import org.json.JSONException;

import java.util.List;

/**
 * Created by yasar on 17/4/17.
 */

public interface ScrollingContract {

    interface ScrollingView extends BaseView {

        void updateList(List<UserDetails> list);

        void updateListProfile(Profile profile);
    }

    interface ScrollingPresenter {
        void validateCredentials(String username, String password);

    }

    interface ScrollingPresenterView extends BaseView {

    }

    interface ScrollingGetListData {
        void updateList(List<UserDetails> list) throws JSONException;

        void updateListProfile(Profile profile) throws JSONException;
    }

}

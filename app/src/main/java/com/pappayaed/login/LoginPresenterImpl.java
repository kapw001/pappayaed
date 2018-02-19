package com.pappayaed.login;

import com.pappayaed.App.App;

import org.json.JSONException;

/**
 * Created by yasar on 17/4/17.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter, LoginContract.LoginPresenterView {

    private LoginContract.LoginView loginView;
    private LoginContract.LoginUserValidate loginUserValidate;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {
        this.loginView = loginView;
        this.loginUserValidate = new loginUserValidateImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        onfail(false);
        try {
            loginUserValidate.validateUserCredentials(username, password, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {
        this.loginView.onSuccess();
    }

    @Override
    public void onfail(boolean isTrue) {
        this.loginView.onfail(isTrue);
    }

    @Override
    public void validateuserName(boolean isTrue) {
        this.loginView.validateuserName(isTrue);
    }

    @Override
    public void validatepassword(boolean isTrue) {

        this.loginView.validatepassword(isTrue);
    }

    @Override
    public void showAlert(String title, String msg) {
        this.loginView.showAlert(title, msg);
    }

    @Override
    public void showProgress(String msg) {
        this.loginView.showProgress(msg);
    }

    @Override
    public void hideProgress() {

        this.loginView.hideProgress();

    }

    @Override
    public void showToast(String msg) {
        this.loginView.showToast(msg);
    }
}

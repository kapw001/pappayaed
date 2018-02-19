package com.pappayaed.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.GetScollId.SchoolIdActivity;
import com.pappayaed.Main.MainActivity;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.demoadmin.AdminActivity;
import com.pappayaed.errormsg.Error;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.pricepaldemo.PrincipalActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;

    private LoginContract.LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;
    private SessionManagenent sessionManagenent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        loginPresenter = new LoginPresenterImpl(this);
        sessionManagenent = SessionManagenent.getSessionManagenent();
//        setupWindowAnimations();
    }

//    private void setupWindowAnimations() {
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);
//
//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setReenterTransition(slide);
//    }

    @OnClick(R.id.login)
    public void loginClick(View view) {

        String userName = username.getText().toString();
        String passWord = password.getText().toString();

        loginPresenter.validateCredentials(userName, passWord);

    }

    @OnClick(R.id.setsid)
    public void setSchoolId(View v) {

        startActivity(new Intent(this, SchoolIdActivity.class));

    }

    @Override
    public void showProgress(String msg) {
        Utils.showProgress(this, msg);
    }

    @Override
    public void hideProgress() {

        Utils.hideProgress();
    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess() {

        Map map = sessionManagenent.getSession();

        String name = map.get("username").toString();
        String password = map.get("password").toString();

        if (name.toLowerCase().equalsIgnoreCase("admin") && password.toLowerCase().equalsIgnoreCase("admin")) {

            startActivity(new Intent(this, AdminActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


        } else if (name.toLowerCase().equalsIgnoreCase("principal") && password.toLowerCase().equalsIgnoreCase("principal")) {

            startActivity(new Intent(this, PrincipalActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        } else if (name.toLowerCase().equalsIgnoreCase("secretary") && password.toLowerCase().equalsIgnoreCase("secretary")) {

            startActivity(new Intent(this, AdminActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        } else {

            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }

    }

    @Override
    public void onfail(boolean isTrue) {

        if (isTrue) {
            username.setError(Error.emailerror);
            password.setError(Error.passwoderror);
        } else {
            username.setError(null);
            password.setError(null);
        }

    }

    @Override
    public void validateuserName(boolean isTrue) {
        if (isTrue)
            username.setError(Error.emailerror);
        else
            username.setError(null);
    }

    @Override
    public void validatepassword(boolean isTrue) {

        if (isTrue)
            password.setError(Error.passwoderror);
        else
            password.setError(null);
    }

    @Override
    public void showAlert(String title, String msg) {


        Utils.show(LoginActivity.this, title, msg);
    }
}

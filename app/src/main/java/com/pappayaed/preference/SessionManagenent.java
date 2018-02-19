package com.pappayaed.preference;

import android.content.SharedPreferences;

import com.pappayaed.App.App;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yasar on 27/4/17.
 */

public class SessionManagenent {

    private static SharedPreferences sharedPreferences = App.getApp().getPreferences();
    private static SessionManagenent sessionManagenent;

    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    public static final String KEY_IMAGE = "userimage";
    public static final String KEY_URL = "serverurl";
    public static final String KEY_USERID = "userid";
    public static final String KEY_USERTYPE = "usertype";

    public static String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public static String getKeyUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public static String getKeyPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public static String getKeyStatus() {
        return sharedPreferences.getString(KEY_STATUS, null);
    }

    public static String getKeyImage() {
        return sharedPreferences.getString(KEY_IMAGE, null);
    }

    public static String getKeyUrl() {
        return sharedPreferences.getString(KEY_URL, null);
    }

    public static String getKeyUserid() {
        return sharedPreferences.getString(KEY_USERID, null);
    }

    public static String getKeyUsertype() {
        return sharedPreferences.getString(KEY_USERTYPE, null);
    }

    private SessionManagenent() {
    }

    public static SessionManagenent getSessionManagenent() {
        if (sessionManagenent == null) {
            sessionManagenent = new SessionManagenent();
        }

        return sessionManagenent;
    }

    public void saveSession(String email, String password, String username, String userid, String usertype, boolean status, String userimage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USERID, userid);
        editor.putString(KEY_USERTYPE, usertype);
        editor.putBoolean(KEY_STATUS, status);
        editor.putString(KEY_IMAGE, userimage);
        editor.commit();
    }

    public void saveServerURL(String serverurl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_URL, serverurl);
        editor.commit();
    }

    public String getServerURL() {
        return sharedPreferences.getString(KEY_URL, "");
    }

    public Map getSession() {
        Map hashMap = new HashMap();
        hashMap.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
        hashMap.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));
        hashMap.put(KEY_STATUS, sharedPreferences.getBoolean(KEY_STATUS, false));
        hashMap.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, ""));
        hashMap.put(KEY_IMAGE, sharedPreferences.getString(KEY_IMAGE, ""));
        hashMap.put(KEY_USERID, sharedPreferences.getString(KEY_USERID, ""));
        hashMap.put(KEY_USERTYPE, sharedPreferences.getString(KEY_USERTYPE, ""));
        return hashMap;
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_STATUS, false) == true ? true : false;
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }


}

package com.pappayaed.Main.showassignmentprofile;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.ScrollingContract;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.SchollingPresenterImpl;
import com.pappayaed.showprofile.Profile;
import com.pappayaed.showprofile.ProfileApi;
import com.pappayaed.showprofile.RecyclerViewAdapter;
import com.pappayaed.showprofile.StudentList;
import com.pappayaed.showprofile.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AssignmentProfileActivityBackup extends AppCompatActivity implements ScrollingContract.ScrollingView, RecyclerViewAdapter.RecyclerAdapterPositionClicked {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SchollingPresenterImpl schollingPresenter;
    private ArrayList<UserDetails> list;
    private AssignmentList assignmentList;
    private CircleImageView profileimage;
    private TextView profilename;
    private int downloadPosition = 0;
    private View coordinatorLayout;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private View relativeLayout;
    private LinearLayout linearLayout;
    private Retrofit retrofit = App.getApp().getRetrofit();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.assignmentprofile);
//        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        linearLayout = (LinearLayout) findViewById(R.id.lay);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        relativeLayout = (RelativeLayout) findViewById(R.id.anitest);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assignmentList = (AssignmentList) bundle.getSerializable("assignmentlist");

        toolbar.setTitle(assignmentList.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        schollingPresenter = new SchollingPresenterImpl(this);

        profileimage = (CircleImageView) findViewById(R.id.profile_image);
        profilename = (TextView) findViewById(R.id.profilename);

        recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
//
        try {
            schollingPresenter.getDataformServerAssignment(assignmentList.getAssignmentId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        setupWindowAnimations();

        coordinatorLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.animateRevealShow(coordinatorLayout);
            }
        });

        linearLayout.setVisibility(View.GONE);
//        if (SessionManagenent.getSessionManagenent().getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
//
//            linearLayout.setVisibility(View.VISIBLE);
//        } else {
//            linearLayout.setVisibility(View.GONE);
//        }

        findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.submission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.changerequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    @Override
    public void updateList(List<UserDetails> list) {
        if (list != null && list.size() > 0) {
            this.list.clear();
            this.list.addAll(list);
            findViewById(R.id.error).setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.updateList(list);
        } else {
            TextView textView = (TextView) findViewById(R.id.error);
            textView.setVisibility(View.VISIBLE);
//            textView.setText(Error.error);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void updateListProfile(Profile profile) {

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

        if (msg.equalsIgnoreCase(Error.error)) {
            TextView textView = (TextView) findViewById(R.id.error);
            textView.setVisibility(View.VISIBLE);
            textView.setText(Error.error);
        }

        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (SessionManagenent.getSessionManagenent().getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {

            getMenuInflater().inflate(R.menu.approvereject, menu);//Menu Resource, Menu
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.approve:


                try {
                    Accept();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                setResult(1, intent);
                super.onBackPressed();

                return true;

            case R.id.reject:

                try {
                    Reject();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent1 = new Intent();
                setResult(1, intent1);
                super.onBackPressed();
                return true;

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final String TAG = "AssignmentProfileActivi";


    @Override
    public void position(int pos) {
        downloadPosition = pos;
        UserDetails userDetails = list.get(pos);

//        Log.e(TAG, "position: " + userDetails.toString());

        if (userDetails.getmName().toLowerCase().equalsIgnoreCase("Download ".toLowerCase())) {
            requrestPermission(userDetails);
        } else {
            Log.e(TAG, "position: Attachment download has problems  so looking into Assignment profile Activity");
        }

    }

    @Override
    public void position(int pos, StudentList studentList) {

    }


    private void downloadFile(final UserDetails userDetails) {


        if (userDetails.getmName().toLowerCase().equalsIgnoreCase("Download ".toLowerCase())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                    if (isSDPresent) {
                        final File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + userDetails.getFileName());

                        try {
                            Log.e(TAG, "run: data " + userDetails.getProfileImage());
                            byte[] pdfAsBytes = Base64.decode(userDetails.getProfileImage(), Base64.DEFAULT);
                            FileOutputStream os;
                            os = new FileOutputStream(myFile, false);
                            os.write(pdfAsBytes);
                            os.flush();
                            os.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Download finished open file", Snackbar.LENGTH_LONG);

                        snackbar.setAction("Open", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {

                                    Intent myIntent = new Intent(Intent.ACTION_VIEW);
                                    String mime = null;
                                    try {
                                        mime = URLConnection.guessContentTypeFromStream(new FileInputStream(myFile.getAbsoluteFile()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (mime == null)
                                        mime = URLConnection.guessContentTypeFromName(myFile.getName());
                                    myIntent.setDataAndType(Uri.fromFile(myFile), mime);
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(AssignmentProfileActivityBackup.this, "The file format not supported", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        snackbar.show();

                        Toast.makeText(AssignmentProfileActivityBackup.this, "Download finished", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AssignmentProfileActivityBackup.this, "SdCard not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    private void Accept() throws JSONException {

        ProfileApi profileApi = retrofit.create(ProfileApi.class);

        Map map = sessionManagenent.getSession();

        String assignmentID = assignmentList.getAssignmentId().toString();

        String username = map.get(SessionManagenent.KEY_EMAIL).toString();
        String password = map.get(SessionManagenent.KEY_PASSWORD).toString();
        String usertypes = map.get(SessionManagenent.KEY_USERTYPE).toString();

        JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("login", username);
        params.put("password", password);
        params.put("user_types", usertypes);
        params.put("assignment_id", assignmentID);
        jsonObject.put("params", params);

        profileApi.accept(jsonObject.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());
                try {
                    JSONObject jsonObject1 = new JSONObject(response.body());

                    JSONObject result = jsonObject1.getJSONObject("result");

                    String s = result.getString("assignment_accept");

                    if (s.equalsIgnoreCase("Not completed")) {
                        Toast.makeText(AssignmentProfileActivityBackup.this, "" + s, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AssignmentProfileActivityBackup.this, "" + s, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    private void Reject() throws JSONException {

        ProfileApi profileApi = retrofit.create(ProfileApi.class);

        Map map = sessionManagenent.getSession();

        String assignmentID = assignmentList.getAssignmentId().toString();

        String username = map.get(SessionManagenent.KEY_EMAIL).toString();
        String password = map.get(SessionManagenent.KEY_PASSWORD).toString();
        String usertypes = map.get(SessionManagenent.KEY_USERTYPE).toString();

        JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("login", username);
        params.put("password", password);
        params.put("user_types", usertypes);
        params.put("assignment_id", assignmentID);
        jsonObject.put("params", params);

        profileApi.reject(jsonObject.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());

                try {
                    JSONObject jsonObject1 = new JSONObject(response.body());

                    JSONObject result = jsonObject1.getJSONObject("result");

                    String s = result.getString("assignment_reject");

                    if (s.equalsIgnoreCase("Not completed")) {
                        Toast.makeText(AssignmentProfileActivityBackup.this, "" + s, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AssignmentProfileActivityBackup.this, "" + s, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    private void requrestPermission(UserDetails userDetails) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {
            downloadFile(userDetails);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    position(downloadPosition);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    Toast.makeText(this, "Please give write and read permissions ", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }
}

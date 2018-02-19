package com.pappayaed.Main.showassignmentprofile;

import android.Manifest;
import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
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
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.Main.fragments.AssignmentTapFragment;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.ScrollingContract;
import com.pappayaed.errormsg.Error;
import com.pappayaed.load.LoadActivity;
import com.pappayaed.model.AssignmentFormDatum;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AssignmentProfileActivity extends AppCompatActivity {

    private AssignmentList assignmentList;

    private static final String TAG = "AssignmentProfileActivi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.assignmenttab);
//        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assignmentList = (AssignmentList) bundle.getSerializable("assignmentlist");

        toolbar.setTitle(assignmentList.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.e(TAG, "onCreate: " + assignmentList.toString());


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        AssignmentTapFragment assignmentTapFragment = AssignmentTapFragment.getInstance(assignmentList);

        fragmentTransaction.replace(R.id.container, assignmentTapFragment);

        fragmentTransaction.commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

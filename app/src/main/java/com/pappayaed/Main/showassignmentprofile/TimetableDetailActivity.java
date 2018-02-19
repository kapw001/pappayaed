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

import com.pappayaed.R;
import com.pappayaed.adapter.TimeTableAdapter;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.ScrollingContract;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentFormDatum;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.AttachmentFileId;
import com.pappayaed.model.FacultyTimetableFormDatum;
import com.pappayaed.model.TimetableDatum;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.SchollingPresenterImpl;
import com.pappayaed.showprofile.Profile;
import com.pappayaed.showprofile.RecyclerViewAdapter;
import com.pappayaed.showprofile.StudentList;
import com.pappayaed.showprofile.UserDetails;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimetableDetailActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerAdapterPositionClicked {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SchollingPresenterImpl schollingPresenter;
    private ArrayList<UserDetails> list;
    private TimetableDatum assignmentList;
    private CircleImageView profileimage;
    private TextView profilename;
    private int downloadPosition = 0;
    private View coordinatorLayout;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private View relativeLayout;

    private LinearLayout linearLayout;

    private TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.assignmentprofile);
        error = (TextView) findViewById(R.id.error);
        error.setVisibility(View.GONE);

        linearLayout = (LinearLayout) findViewById(R.id.lay);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        relativeLayout = (RelativeLayout) findViewById(R.id.anitest);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assignmentList = (TimetableDatum) bundle.getSerializable("assignmentlist");

        toolbar.setTitle(assignmentList.getPeriodId());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();


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


        linearLayout.setVisibility(View.GONE);

        updateListAssignment(assignmentList);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);

//                NavUtils.getParentActivityIntent(this);
//                NavUtils.navigateUpTo(this, intent);
//                finish();
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateListAssignment(TimetableDatum profile) {
        List<UserDetails> list = new ArrayList<>();

        list.add(new UserDetails("Timetable Detail", null, null, UserDetails.HEADER));
        list.add(new UserDetails("Name", profile.getStudentName().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Subject", profile.getSubjectId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Period ", profile.getPeriodId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Faculty", profile.getFacultyId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Course", profile.getCourseId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Batch", profile.getBatchId().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Days", profile.getType().toString().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("Class Room", profile.getClassroomId().toString().toString(), null, UserDetails.ROW));
//        list.add(new UserDetails("Start Datetime", Utils.getConvertedDate(profile.getStartDatetime().toString()), null, UserDetails.ROW));
//        list.add(new UserDetails("End Datetime", Utils.getConvertedDate(profile.getEndDatetime().toString()), null, UserDetails.ROW));
        list.add(new UserDetails("Start Datetime", profile.getStartDatetime().toString(), null, UserDetails.ROW));
        list.add(new UserDetails("End Datetime", profile.getEndDatetime().toString(), null, UserDetails.ROW));

        recyclerViewAdapter.updateList(list);

    }


    @Override
    public void position(int pos) {

    }

    @Override
    public void position(int pos, StudentList studentList) {

    }
}

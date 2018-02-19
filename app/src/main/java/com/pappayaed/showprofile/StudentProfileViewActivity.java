package com.pappayaed.showprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.ScrollingContract;
import com.pappayaed.errormsg.Error;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.presenter.SchollingPresenterImpl;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileViewActivity extends AppCompatActivity implements ScrollingContract.ScrollingView, RecyclerViewAdapter.RecyclerAdapterPositionClicked {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SchollingPresenterImpl schollingPresenter;
    private ArrayList<UserDetails> list;
    private StudentList studentList;
    private CircleImageView profileimage;
    private TextView profilename;
    private ProgressBar progressBar;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        studentList = (StudentList) bundle.getSerializable("studentprofile");

        list = new ArrayList<>();
        schollingPresenter = new SchollingPresenterImpl(this);

        profileimage = (CircleImageView) findViewById(R.id.profile_image);
        profilename = (TextView) findViewById(R.id.profilename);
        Map hashMap = sessionManagenent.getSession();

        String image = hashMap.get(SessionManagenent.KEY_IMAGE).toString();
        final String name = studentList.getName();

        profilename.setText(name);
        Bitmap imagebmp = Utils.decodeBitmap(this, studentList.getPhoto());
        profileimage.setImageBitmap(imagebmp);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.VISIBLE);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(name);
                    toolbar.setTitle(name);
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle("");
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

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
            schollingPresenter.updateLocalData(studentList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        coordinatorLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.animateRevealShow(coordinatorLayout);
            }
        });

    }


    @Override
    public void updateList(List<UserDetails> list) {
        if (list != null && list.size() > 0) {
            findViewById(R.id.error).setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.updateList(list);
        } else {
            findViewById(R.id.error).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void updateListProfile(Profile profile) {

    }

    @Override
    public void showProgress(String msg) {

//        Utils.showProgress(this, msg);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

//        Utils.hideProgress();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg) {
        ((TextView) findViewById(R.id.error)).setText(msg);
//        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void position(int pos) {

    }

    @Override
    public void position(int pos, StudentList studentList) {

    }
}

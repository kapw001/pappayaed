package com.pappayaed.showprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class ProfileActivity extends AppCompatActivity implements ScrollingContract.ScrollingView, RecyclerViewAdapter.RecyclerAdapterPositionClicked {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SchollingPresenterImpl schollingPresenter;
    private ArrayList<UserDetails> list;
    private CircleImageView profileimage;
    private TextView profilename;
    private Profile profile;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private ProgressBar progressBar;
    private RelativeLayout viewRoot;
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
        viewRoot = (RelativeLayout) findViewById(R.id.anitest);

        profile = new Profile();
        list = new ArrayList<>();
        schollingPresenter = new SchollingPresenterImpl(this);

        profileimage = (CircleImageView) findViewById(R.id.profile_image);
        profilename = (TextView) findViewById(R.id.profilename);
        Map hashMap = sessionManagenent.getSession();

        String image = hashMap.get(SessionManagenent.KEY_IMAGE).toString();
        final String name = hashMap.get(SessionManagenent.KEY_USERNAME).toString();

        profilename.setText(name);
        Bitmap imagebmp = Utils.decodeBitmap(this, image);
        profileimage.setImageBitmap(imagebmp);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
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

        try {
            schollingPresenter.getDataformServer();
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
            this.list.clear();
            this.list.addAll(list);
            findViewById(R.id.error).setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.updateList(list);
        } else {
            TextView textView = (TextView) findViewById(R.id.error);
            textView.setVisibility(View.VISIBLE);
            textView.setText(Error.nodata);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void updateListProfile(Profile profile) {
        this.profile = profile;

        Log.e(TAG, "updateListProfile: " + profile.toString());
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

        if (list.size() <= 0) {
            findViewById(R.id.error).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

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

    private static final String TAG = "ProfileActivity";

    @Override
    public void position(int pos) {
//        if (pos > 10 && SessionManagenent.getKeyUsertype().equalsIgnoreCase("Parent")) {
//
//            Log.e(TAG, "position: " + pos);
//
//            StudentList studentList = profile.getStudentList().get(pos - 11);
//
//            Intent in = new Intent(this, StudentProfileViewActivity.class);
//
//            View pimage = findViewById(R.id.profile_image);
//            View pname = findViewById(R.id.profilename);
//            Pair<View, String> pair1 = Pair.create(pimage, "profile_image");
//            Pair<View, String> pair2 = Pair.create(pname, "profilename");
//            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2);
//
//
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("studentprofile", studentList);
//            in.putExtras(bundle);
//            startActivity(in, optionsCompat.toBundle());
//
//
//        }

    }

    @Override
    public void position(int pos, StudentList studentList) {
        Intent in = new Intent(this, StudentProfileViewActivity.class);

        View pimage = findViewById(R.id.profile_image);
        View pname = findViewById(R.id.profilename);
        Pair<View, String> pair1 = Pair.create(pimage, "profile_image");
        Pair<View, String> pair2 = Pair.create(pname, "profilename");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2);


        Bundle bundle = new Bundle();
        bundle.putSerializable("studentprofile", studentList);
        in.putExtras(bundle);
        startActivity(in, optionsCompat.toBundle());
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}

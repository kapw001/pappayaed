package com.pappayaed.Main.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.Main.showassignmentprofile.AssignmentProfileActivity;
import com.pappayaed.Main.showassignmentprofile.AttachmentListActivity;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.contract.ScrollingContract;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.AttachmentFileId;
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
import java.io.Serializable;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;

/**
 * Created by yasar on 2/5/17.
 */

public class AssignmentProfileFragment extends Fragment implements ScrollingContract.ScrollingView, RecyclerViewAdapter.RecyclerAdapterPositionClicked {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SchollingPresenterImpl schollingPresenter;
    private ArrayList<UserDetails> list;
    private CircleImageView profileimage;
    private TextView profilename;
    private int downloadPosition = 0;
    private View coordinatorLayout;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private View relativeLayout;
    private LinearLayout linearLayout;
    private Retrofit retrofit = App.getApp().getRetrofit();
    private TextView error;
    private static final String TAG = "AssignmentProfileFragme";
    private AssignmentList assignmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assignmentprofileview, container, false);
        assignmentList = (AssignmentList) getArguments().getSerializable("assignmentlist");
        error = (TextView) view.findViewById(R.id.error);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        list = new ArrayList<>();
        schollingPresenter = new SchollingPresenterImpl(this);

        profileimage = (CircleImageView) view.findViewById(R.id.profile_image);
        profilename = (TextView) view.findViewById(R.id.profilename);

        recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);


        Log.e(TAG, "onCreateView: checking   " + assignmentList.toString());

        callService(assignmentList);

        return view;
    }

    public static AssignmentProfileFragment getInstance(AssignmentList assignmentList) {
        AssignmentProfileFragment assignmentProfileFragment = new AssignmentProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("assignmentlist", assignmentList);
        assignmentProfileFragment.setArguments(bundle);

        return assignmentProfileFragment;

    }

    public void callService(AssignmentList assignmentList) {
        try {
            schollingPresenter.getDataformServerAssignment(assignmentList.getAssignmentId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showProgress(String msg) {
        Utils.showProgress(getActivity(), msg);
    }

    @Override
    public void hideProgress() {
        Utils.hideProgress();
    }

    @Override
    public void showToast(String msg) {
        if (msg.equalsIgnoreCase(Error.error)) {

            error.setVisibility(View.VISIBLE);
            error.setText(Error.error);
        }

        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList(List<UserDetails> list) {

        if (list != null && list.size() > 0) {
            this.list.clear();
            this.list.addAll(list);
            error.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.updateList(list);
        } else {

            error.setVisibility(View.VISIBLE);
//            textView.setText(Error.error);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void updateListProfile(Profile profile) {

    }

    @Override
    public void position(int pos) {

//        downloadPosition = pos;
        UserDetails userDetails = list.get(pos);

//        Log.e(TAG, "position: " + userDetails.toString());

        if (userDetails.getmName().toLowerCase().equalsIgnoreCase("Attachment List".toLowerCase())) {

            List<AttachmentFileId> list = userDetails.getAttachmentFileIdList();

            Intent intent = new Intent(getActivity(), AttachmentListActivity.class);
            intent.putExtra("assignmentlist", (Serializable) list);
            startActivity(intent);

//            requrestPermission(userDetails);
        } else {
            Log.e(TAG, "position: Attachment download has problems  so looking into Assignment profile Activity");
        }

    }

    @Override
    public void position(int pos, StudentList studentList) {

    }

    private void downloadFile(final UserDetails userDetails) {


        if (userDetails.getmName().toLowerCase().equalsIgnoreCase("Download ".toLowerCase())) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
            Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
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
                            Toast.makeText(getActivity(), "The file format not supported", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                snackbar.show();

                Toast.makeText(getActivity(), "Download finished", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "SdCard not found", Toast.LENGTH_SHORT).show();
            }
//                }
//            });

        }
    }


    private void requrestPermission(UserDetails userDetails) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
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

                    Toast.makeText(getActivity(), "Please give write and read permissions ", Toast.LENGTH_SHORT).show();

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

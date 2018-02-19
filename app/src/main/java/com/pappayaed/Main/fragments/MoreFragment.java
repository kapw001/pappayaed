package com.pappayaed.Main.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pappayaed.Main.MyAppSettingsActivity;
import com.pappayaed.Main.examp.ExamMarkSheetActivity;
import com.pappayaed.Main.timetable.TimetableActivity;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.login.LoginActivity;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.ProfileActivity;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yasar on 2/5/17.
 */

public class MoreFragment extends Fragment {


    private CircleImageView profileimage;
    private TextView profilename, usertype;
    private RelativeLayout logout, settings;
    private RelativeLayout profileGo;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private static final String TAG = "MoreFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.more_fragment, container, false);

        profileimage = (CircleImageView) view.findViewById(R.id.profile_image);
        profilename = (TextView) view.findViewById(R.id.profilename);
        usertype = (TextView) view.findViewById(R.id.usertype);
        logout = (RelativeLayout) view.findViewById(R.id.logout);
        settings = (RelativeLayout) view.findViewById(R.id.settings);
        Map hashMap = sessionManagenent.getSession();

        String image = hashMap.get(SessionManagenent.KEY_IMAGE).toString();

        Log.e(TAG, "onCreateView: " + image);

        String name = hashMap.get(SessionManagenent.KEY_USERNAME).toString();
        String user_type = hashMap.get(SessionManagenent.KEY_USERTYPE).toString();

        profilename.setText(name);
        Bitmap imagebmp = Utils.decodeBitmap(getActivity(), image);
        profileimage.setImageBitmap(imagebmp);
        usertype.setText(user_type);
        RelativeLayout vrvideos = (RelativeLayout) view.findViewById(R.id.vrvideos);

//        RelativeLayout vr1 = (RelativeLayout) view.findViewById(R.id.vr1);
//        RelativeLayout vr2 = (RelativeLayout) view.findViewById(R.id.vr2);
//        RelativeLayout vr3 = (RelativeLayout) view.findViewById(R.id.vr3);
//        RelativeLayout vr5 = (RelativeLayout) view.findViewById(R.id.vr5);
//        RelativeLayout vr6 = (RelativeLayout) view.findViewById(R.id.vr6);
//        RelativeLayout vr7 = (RelativeLayout) view.findViewById(R.id.vr7);
//        RelativeLayout vr8 = (RelativeLayout) view.findViewById(R.id.vr8);

        RelativeLayout examp = (RelativeLayout) view.findViewById(R.id.exam);
        RelativeLayout timetable = (RelativeLayout) view.findViewById(R.id.timetable);
        View exampid = (View) view.findViewById(R.id.examid);
        View timeid = (View) view.findViewById(R.id.timeid);


        examp.setVisibility(View.VISIBLE);
        timetable.setVisibility(View.VISIBLE);

//        SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
//        String userType = (String) sessionManagenent.getSession().get(SessionManagenent.KEY_USERTYPE);
//        if (userType.toLowerCase().equalsIgnoreCase("parent")) {
//            timetable.setVisibility(View.VISIBLE);
//            timeid.setVisibility(View.VISIBLE);
//            examp.setVisibility(View.VISIBLE);
//            exampid.setVisibility(View.VISIBLE);
//        } else if (userType.toLowerCase().equalsIgnoreCase("faculty")) {
//            examp.setVisibility(View.GONE);
//            exampid.setVisibility(View.GONE);
//
//        } else {
//            timetable.setVisibility(View.VISIBLE);
//            timeid.setVisibility(View.VISIBLE);
////            examp.setVisibility(View.GONE);
////            exampid.setVisibility(View.GONE);
//        }

        if (SessionManagenent.getSessionManagenent().getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
            examp.setVisibility(View.GONE);
            exampid.setVisibility(View.GONE);
        }

        examp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ExamMarkSheetActivity.class));
            }
        });

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TimetableActivity.class));
            }
        });

        profileGo = (RelativeLayout) view.findViewById(R.id.goprofile);




        profileGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), ProfileActivity.class);
                View pimage = view.findViewById(R.id.profile_image);
                View pname = view.findViewById(R.id.profilename);
                Pair<View, String> pair1 = Pair.create(pimage, "profile_image");
                Pair<View, String> pair2 = Pair.create(pname, "profilename");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair1, pair2);
                startActivity(in, optionsCompat.toBundle());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagenent.getSessionManagenent().clear();
                startActivity(new Intent(getActivity(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), MyAppSettingsActivity.class));

            }
        });


        return view;
    }
}

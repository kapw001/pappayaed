package com.pappayaed.demoadmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pappayaed.R;
import com.pappayaed.login.LoginActivity;
import com.pappayaed.preference.SessionManagenent;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreAddFragment extends Fragment {


    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.profilename)
    TextView profilename;
    @BindView(R.id.logout)
    Button logout;
    Unbinder unbinder;

    public MoreAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_add, container, false);
        unbinder = ButterKnife.bind(this, view);

        Map map = SessionManagenent.getSessionManagenent().getSession();

        String name = map.get("username").toString();
        String password = map.get("password").toString();
        String output = name.substring(0, 1).toUpperCase() + name.substring(1);

        profilename.setText(output);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagenent.getSessionManagenent().clear();
                startActivity(new Intent(getActivity(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.logout)
    public void onLogout() {


    }
}

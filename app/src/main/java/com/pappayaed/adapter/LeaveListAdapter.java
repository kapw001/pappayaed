package com.pappayaed.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.pappayaed.App.App;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.StudentHolidaysDatum;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.ProfileApi;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.pappayaed.preference.SessionManagenent.KEY_USERTYPE;


public class LeaveListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "LeaveListAdapter";
    private List<StudentHolidaysDatum> list;
    private static RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private static Context context;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private static final int UNSELECTED = -1;
    private static RecyclerView recyclerView;
    private static int selectedItem = UNSELECTED;
    private Retrofit retrofit = App.getApp().getRetrofit();

    public LeaveListAdapter() {
        // uncomment the line below if you want to open only one row at a time
        // viewBinderHelper.setOpenOnlyOne(true);
    }

    public LeaveListAdapter(Fragment context, List<StudentHolidaysDatum> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;
        this.recyclerView = recyclerView;
        viewBinderHelper.setOpenOnlyOne(true);

    }

    public LeaveListAdapter(Context context, List<StudentHolidaysDatum> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;
        this.recyclerView = recyclerView;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    public void updateList(List<StudentHolidaysDatum> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_row, parent, false);
        return new RowViewHolder(view);

    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final StudentHolidaysDatum object = list.get(position);

        ((RowViewHolder) holder).requestlayout.setVisibility(View.GONE);


        if (((RowViewHolder) holder).requestlayout.isShown()) {
            ((RowViewHolder) holder).requestlayout.setVisibility(View.GONE);
            ((RowViewHolder) holder).cancellayout.setVisibility(View.GONE);
            ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.GONE);
        }

        ((RowViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e(TAG, "onClick: " + object.getId());

                if (position > 0) {
                    int p = position - 1;
                    notifyItemChanged(p);
                }

                SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();


                if (((RowViewHolder) holder).requestlayout.isShown()) {
                    ((RowViewHolder) holder).requestlayout.setVisibility(View.GONE);
//                    if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
                    ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.GONE);
//                    } else {
                    ((RowViewHolder) holder).cancellayout.setVisibility(View.GONE);
//                    }
                } else if (object.getState().toString().toLowerCase().equalsIgnoreCase("cancel") || object.getState().toString().toLowerCase().equalsIgnoreCase("validate")) {
                    ((RowViewHolder) holder).requestlayout.setVisibility(View.GONE);
//                    if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
                    ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.GONE);
//                    } else {
                    ((RowViewHolder) holder).cancellayout.setVisibility(View.GONE);

                } else {

                    ((RowViewHolder) holder).requestlayout.setVisibility(View.VISIBLE);
                    if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
                        ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.VISIBLE);
                        ((RowViewHolder) holder).cancellayout.setVisibility(View.GONE);
                    } else {
                        ((RowViewHolder) holder).cancellayout.setVisibility(View.VISIBLE);
                        ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.GONE);
                    }

                    recyclerView.smoothScrollToPosition(position);


                }


                ((RowViewHolder) holder).accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        changeStatsOfLeave(object.getId(), "validate");

                    }
                });

                ((RowViewHolder) holder).reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        changeStatsOfLeave(object.getId(), "refuse");


                    }
                });
                ((RowViewHolder) holder).cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        changeStatsOfLeave(object.getId(), "cancel");
                    }
                });


//                notifyDataSetChanged();

                recyclerAdapterPositionClicked.position(position, v);

            }
        });
//
        ((RowViewHolder) holder).name.setText(object.getStudentName().toString());
        ((RowViewHolder) holder).leavetype.setText(object.getLeaveType().toString());
        ((RowViewHolder) holder).startdate.setText((Utils.getConvertedDate(object.getDateFrom().toString())));
        ((RowViewHolder) holder).state.setText(object.getState().toString().substring(0, 1).toUpperCase() + object.getState().toString().substring(1));
        ((RowViewHolder) holder).enddate.setText(Utils.getConvertedDate(object.getDateTo().toString()));
        ((RowViewHolder) holder).description.setText(object.getDescription().toString());

//        ((RowViewHolder) holder).Bind(object);
//        ((RowViewHolder) holder).bind(position);

    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    private void changeStatsOfLeave(int id, String status) {

        ProfileApi profileApi = retrofit.create(ProfileApi.class);

        SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

        Map map = sessionManagenent.getSession();

        String username = map.get(SessionManagenent.KEY_EMAIL).toString();
        String password = map.get(SessionManagenent.KEY_PASSWORD).toString();
        String usertypes = map.get(SessionManagenent.KEY_USERTYPE).toString();

        JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("login", username);
            params.put("password", password);
            params.put("user_types", usertypes);
            params.put("model", "student.holidays");
            params.put("record_id", id);
            params.put("state", status);
            jsonObject.put("params", params);

            profileApi.getUpdate_leave_state(jsonObject.toString()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    recyclerAdapterPositionClicked.onRefresh();
                    try {
                        JSONObject jsonObject1 = new JSONObject(response.body());

                        Log.e(TAG, "onResponse: " + jsonObject1.toString());

                        JSONObject result = jsonObject1.getJSONObject("result");
                        Log.e(TAG, "onResponse: " + result.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.getMessage());

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView leavetype, name, startdate, enddate, description, state;
        private FrameLayout linearLayout;

        private int position;
        private RelativeLayout requestlayout;
        private LinearLayout acceptrejectlayout;
        private LinearLayout cancellayout;

        private Button accept, reject, cancel;

        public RowViewHolder(View itemView) {
            super(itemView);

            requestlayout = (RelativeLayout) itemView.findViewById(R.id.requestlayout);
            acceptrejectlayout = (LinearLayout) itemView.findViewById(R.id.acceptrejectlayout);
            cancellayout = (LinearLayout) itemView.findViewById(R.id.cancellayout);
            linearLayout = (FrameLayout) itemView.findViewById(R.id.layoutrow);
            name = (TextView) itemView.findViewById(R.id.studentname);
            leavetype = (TextView) itemView.findViewById(R.id.leavetype);
            startdate = (TextView) itemView.findViewById(R.id.startdate);
            enddate = (TextView) itemView.findViewById(R.id.enddate);
            state = (TextView) itemView.findViewById(R.id.state);
            description = (TextView) itemView.findViewById(R.id.description);

            accept = (Button) itemView.findViewById(R.id.accept);
            reject = (Button) itemView.findViewById(R.id.reject);
            cancel = (Button) itemView.findViewById(R.id.cancel);


        }


    }

    public interface RecyclerAdapterPositionClicked {
        void position(int pos, View view);

        void onRefresh();
    }

    private static void showToat(View view) {
        Toast.makeText(view.getContext(), "" + view.getId(), Toast.LENGTH_SHORT).show();
    }
}

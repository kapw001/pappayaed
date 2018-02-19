package com.pappayaed.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.pappayaed.App.App;
import com.pappayaed.Main.showassignmentprofile.AttachmentListActivity;
import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.errormsg.Error;
import com.pappayaed.model.AssignmentSubLine;
import com.pappayaed.model.AttachmentFileId;
import com.pappayaed.model.BystatusAssignmentListDatum;
import com.pappayaed.model.StudentHolidaysDatum;
import com.pappayaed.preference.SessionManagenent;
import com.pappayaed.showprofile.ProfileApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.pappayaed.preference.SessionManagenent.KEY_USERTYPE;


public class SubmissionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SubmissionListAdapter";
    private List<AssignmentSubLine> list;
    private static RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private static Context context;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private static final int UNSELECTED = -1;

    private static RecyclerView recyclerView;
    private static int selectedItem = UNSELECTED;

    private Retrofit retrofit = App.getApp().getRetrofit();

    public SubmissionListAdapter() {
        // uncomment the line below if you want to open only one row at a time
        // viewBinderHelper.setOpenOnlyOne(true);
    }

    public SubmissionListAdapter(Fragment context, List<AssignmentSubLine> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;
        this.recyclerView = recyclerView;
        viewBinderHelper.setOpenOnlyOne(true);

    }

    public SubmissionListAdapter(Context context, List<AssignmentSubLine> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;
        this.recyclerView = recyclerView;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    public void updateList(List<AssignmentSubLine> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submission_row1, parent, false);
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
        final AssignmentSubLine object = list.get(position);

        final List<AttachmentFileId> list = object.getAttachmentFileIds() != null ? object.getAttachmentFileIds() : null;

        if (list != null && list.size() > 0) {
            ((RowViewHolder) holder).attachment.setVisibility(View.VISIBLE);
            ((RowViewHolder) holder).attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AttachmentListActivity.class);
                    intent.putExtra("assignmentlist", (Serializable) list);
                    v.getContext().startActivity(intent);
                }
            });

            ((RowViewHolder) holder).attachmentcount.setText(list.size() + "");
            ((RowViewHolder) holder).attachmentcount.setVisibility(View.VISIBLE);

        } else {
            ((RowViewHolder) holder).attachment.setVisibility(View.GONE);
            ((RowViewHolder) holder).attachmentcount.setVisibility(View.GONE);
        }

        ((RowViewHolder) holder).requestlayout.setVisibility(View.GONE);


        if (((RowViewHolder) holder).requestlayout.isShown()) {
            ((RowViewHolder) holder).requestlayout.setVisibility(View.GONE);
            ((RowViewHolder) holder).cancellayout.setVisibility(View.GONE);
            ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.GONE);
        }

        ((RowViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                } else if (object.getState().toString().toLowerCase().equalsIgnoreCase("Submitted".toLowerCase()) || object.getState().toString().toLowerCase().equalsIgnoreCase("Rejected".toLowerCase()) || object.getState().toString().toLowerCase().equalsIgnoreCase("Accepted".toLowerCase()) || object.getState().toString().toLowerCase().equalsIgnoreCase("Draft".toLowerCase())

                        || object.getState().toString().toLowerCase().equalsIgnoreCase("Reject".toLowerCase())
                        || object.getState().toString().toLowerCase().equalsIgnoreCase("Accept".toLowerCase())
                        ) {
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
                        ((RowViewHolder) holder).cancellayout.setVisibility(View.GONE);
                        ((RowViewHolder) holder).acceptrejectlayout.setVisibility(View.GONE);
                    }

                    recyclerView.smoothScrollToPosition(position);


                }


                ((RowViewHolder) holder).accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        try {
                            update(object.getSubmissionId(), "accept");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                ((RowViewHolder) holder).reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        try {
                            update(object.getSubmissionId(), "reject");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                ((RowViewHolder) holder).change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        try {
                            update(object.getSubmissionId(), "change");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                ((RowViewHolder) holder).cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        try {
                            update(object.getSubmissionId(), "cancel");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


//                notifyDataSetChanged();

                recyclerAdapterPositionClicked.position(position, v);

            }
        });
//
        ((RowViewHolder) holder).name.setText(object.getStudentName().toString());
        ((RowViewHolder) holder).description.setText((object.getDescription().toString()));
        ((RowViewHolder) holder).state.setText(object.getState().toString().substring(0, 1).toUpperCase() + object.getState().toString().substring(1));
//        ((RowViewHolder) holder).submissiondate.setText(Utils.getConvertedDate(object.getSubmissionDate().toString()));

//        ((RowViewHolder) holder).Bind(object);
//        ((RowViewHolder) holder).bind(position);

    }


    private void update(int record_id, String state) throws JSONException {

        ProfileApi profileApi = retrofit.create(ProfileApi.class);

        SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();

        Map map = sessionManagenent.getSession();

        String username = map.get(SessionManagenent.KEY_EMAIL).toString();
        String password = map.get(SessionManagenent.KEY_PASSWORD).toString();
        String usertypes = map.get(SessionManagenent.KEY_USERTYPE).toString();

        JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("login", username);
        params.put("password", password);
        params.put("user_types", usertypes);
        params.put("model", "pappaya.assignment.sub.line");
        params.put("record_id", record_id);
        params.put("state", state);
        jsonObject.put("params", params);

        profileApi.getChange_state(jsonObject.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                recyclerAdapterPositionClicked.onRefresh();

                Log.e(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }


    private static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView name, assignmentname, submissiondate, state, description, attachmentcount;
        private FrameLayout linearLayout;
        private ImageView attachment;

        private int position;
        private RelativeLayout requestlayout;
        private LinearLayout acceptrejectlayout;
        private LinearLayout cancellayout;


        private Button accept, reject, cancel, change;

        public RowViewHolder(View itemView) {
            super(itemView);

            attachment = (ImageView) itemView.findViewById(R.id.attachment);
            requestlayout = (RelativeLayout) itemView.findViewById(R.id.requestlayout);
            acceptrejectlayout = (LinearLayout) itemView.findViewById(R.id.acceptrejectlayout);
            cancellayout = (LinearLayout) itemView.findViewById(R.id.cancellayout);
            linearLayout = (FrameLayout) itemView.findViewById(R.id.layoutrow);
            name = (TextView) itemView.findViewById(R.id.studentname);
            description = (TextView) itemView.findViewById(R.id.description);
            submissiondate = (TextView) itemView.findViewById(R.id.submissiondate);
            state = (TextView) itemView.findViewById(R.id.state);
            attachmentcount = (TextView) itemView.findViewById(R.id.attachmentcount);

            accept = (Button) itemView.findViewById(R.id.accept);
            change = (Button) itemView.findViewById(R.id.change);
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

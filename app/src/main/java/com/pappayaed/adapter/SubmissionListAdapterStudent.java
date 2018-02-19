package com.pappayaed.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pappayaed.model.BystatusAssignmentListDatum;
import com.pappayaed.preference.SessionManagenent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

import static com.pappayaed.preference.SessionManagenent.KEY_USERTYPE;


public class SubmissionListAdapterStudent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BystatusAssignmentListDatum> list;
    private static RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private static Context context;
    private SessionManagenent sessionManagenent = SessionManagenent.getSessionManagenent();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private static final int UNSELECTED = -1;

    private static RecyclerView recyclerView;
    private static int selectedItem = UNSELECTED;

    private Retrofit retrofit = App.getApp().getRetrofit();

    public SubmissionListAdapterStudent() {
        // uncomment the line below if you want to open only one row at a time
        // viewBinderHelper.setOpenOnlyOne(true);
    }

    public SubmissionListAdapterStudent(Fragment context, List<BystatusAssignmentListDatum> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;
        this.recyclerView = recyclerView;
        viewBinderHelper.setOpenOnlyOne(true);

    }

    public SubmissionListAdapterStudent(Context context, List<BystatusAssignmentListDatum> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;
        this.recyclerView = recyclerView;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    public void updateList(List<BystatusAssignmentListDatum> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submission_row, parent, false);
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
        final BystatusAssignmentListDatum object = list.get(position);

//        if (sessionManagenent.getSession().get(KEY_USERTYPE).toString().equalsIgnoreCase("Faculty")) {
//            ((RowViewHolder) holder).parentLayout.setVisibility(View.VISIBLE);
//        } else {
//            ((RowViewHolder) holder).studentLayout.setVisibility(View.VISIBLE);
//        }

//        viewBinderHelper.bind(((RowViewHolder) holder).swipeRevealLayout, object.getStudentHolidayId().toString());
//        if (position % 2 != 0) {
//            ((RowViewHolder) holder).linearLayout.setAlpha(.5f);
//        } else {
//            ((RowViewHolder) holder).linearLayout.setAlpha(1f);
//        }
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
                } else if (object.getState().toString().toLowerCase().equalsIgnoreCase("Submitted".toLowerCase()) || object.getState().toString().toLowerCase().equalsIgnoreCase("Rejected".toLowerCase())|| object.getState().toString().toLowerCase().equalsIgnoreCase("Accepted".toLowerCase())) {
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


                    }
                });

                ((RowViewHolder) holder).reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {


                    }
                });
                ((RowViewHolder) holder).cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {


                    }
                });


//                notifyDataSetChanged();

                recyclerAdapterPositionClicked.position(position, v);

            }
        });
//
        ((RowViewHolder) holder).name.setText(object.getStudentName().toString());

        ((RowViewHolder) holder).assignmentname.setText((object.getAssignmentName().toString()));
        ((RowViewHolder) holder).state.setText(object.getState().toString().substring(0, 1).toUpperCase() + object.getState().toString().substring(1));
        ((RowViewHolder) holder).submissiondate.setText(Utils.getConvertedDate(object.getSubmissionDate().toString()));

//        ((RowViewHolder) holder).Bind(object);
//        ((RowViewHolder) holder).bind(position);

    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }


    private static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView name, assignmentname, submissiondate, state;
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
            assignmentname = (TextView) itemView.findViewById(R.id.assignmentname);
            submissiondate = (TextView) itemView.findViewById(R.id.submissiondate);
            state = (TextView) itemView.findViewById(R.id.state);

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

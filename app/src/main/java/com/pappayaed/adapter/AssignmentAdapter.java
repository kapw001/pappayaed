package com.pappayaed.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.model.AssignmentList;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//import static com.google.vr.ndk.base.Version.TAG;


public class AssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "AssignmentAdapter";
    private List<AssignmentList> list;
    private RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private Context context;

    public AssignmentAdapter(Fragment context, List<AssignmentList> list) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public AssignmentAdapter(Context context, List<AssignmentList> list) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public void updateList(List<AssignmentList> list) {

        Log.e(TAG, "updateList: adapter " + list.size());

        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_row, parent, false);
        return new RowViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AssignmentList object = list.get(position);
        ((RowViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapterPositionClicked.position(position, v);
            }
        });

        ((RowViewHolder) holder).studentname.setText(object.getName().toString());
        ((RowViewHolder) holder).leavetypename.setText(object.getAssignmentTypeId().toString());
        ((RowViewHolder) holder).batch.setText(object.getBatchId().toString());
        ((RowViewHolder) holder).subject.setText(object.getSubjectId().toString());
        ((RowViewHolder) holder).state.setText(object.getState().toString().substring(0, 1).toUpperCase() + object.getState().toString().substring(1));
        ((RowViewHolder) holder).submission_date.setText(Utils.getConvertedDate(object.getSubmissionDate().toString()));
    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }


    private static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView studentname, leavetypename, daysleft, batch, subject, faculty, reviewer, marks, assignment_type, state, issued_date, submission_date;
        private FrameLayout linearLayout;
//        private View viewcolor;

        public RowViewHolder(View itemView) {
            super(itemView);
            linearLayout = (FrameLayout) itemView.findViewById(R.id.layoutrow);
            studentname = (TextView) itemView.findViewById(R.id.studentname);
            leavetypename = (TextView) itemView.findViewById(R.id.leavetypename);
            batch = (TextView) itemView.findViewById(R.id.batch);
            subject = (TextView) itemView.findViewById(R.id.subject);
            faculty = (TextView) itemView.findViewById(R.id.faculty);
            state = (TextView) itemView.findViewById(R.id.state);
//            daysleft = (TextView) itemView.findViewById(R.id.daysleft);
//            viewcolor = (View) itemView.findViewById(R.id.view);
            submission_date = (TextView) itemView.findViewById(R.id.submission_date);

        }
    }

    public interface RecyclerAdapterPositionClicked {
        void position(int pos, View view);
    }
}

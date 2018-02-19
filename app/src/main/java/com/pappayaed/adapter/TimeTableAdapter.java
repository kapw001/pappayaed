package com.pappayaed.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pappayaed.R;
import com.pappayaed.common.Utils;
import com.pappayaed.model.AssignmentList;
import com.pappayaed.model.FacultyTimetableFormDatum;
import com.pappayaed.model.TimetableDatum;
import com.pappayaed.preference.SessionManagenent;

import java.util.ArrayList;
import java.util.List;


public class TimeTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TimetableDatum> list;
    private RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private Context context;

    public TimeTableAdapter(Fragment context, List<TimetableDatum> list) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public TimeTableAdapter(Context context, List<TimetableDatum> list) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public void updateList(List<TimetableDatum> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_row, parent, false);
        return new RowViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TimetableDatum object = list.get(position);

//        if (position % 2 != 0) {
//            ((RowViewHolder) holder).linearLayout.setAlpha(.5f);
//        } else {
//            ((RowViewHolder) holder).linearLayout.setAlpha(1f);
//        }

        ((RowViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapterPositionClicked.position(position, v);
            }
        });

        if (SessionManagenent.getSessionManagenent().getSession().get(SessionManagenent.KEY_USERTYPE).toString().equalsIgnoreCase("Parent")) {
            ((RowViewHolder) holder).stlay.setVisibility(View.VISIBLE);
        } else {
            ((RowViewHolder) holder).stlay.setVisibility(View.GONE);
        }

        ((RowViewHolder) holder).name.setText(object.getPeriodId().toString());
        ((RowViewHolder) holder).studentname.setText(object.getStudentName().toString());
        ((RowViewHolder) holder).batch.setText(object.getBatchId().toString());
        ((RowViewHolder) holder).subject.setText(object.getSubjectId().toString());
        ((RowViewHolder) holder).faculty.setText(object.getFacultyId().toString());
        ((RowViewHolder) holder).classroom.setText(object.getClassroomId().toString());
        ((RowViewHolder) holder).startdate.setText(object.getStartDatetime().toString());
        ((RowViewHolder) holder).enddate.setText(object.getEndDatetime().toString());


    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }


    private static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView name, studentname, classroom, batch, subject, faculty, reviewer, startdate, enddate, marks, assignment_type, state, issued_date, submission_date;
        private FrameLayout linearLayout;
        private LinearLayout stlay;

        public RowViewHolder(View itemView) {
            super(itemView);
            stlay = (LinearLayout) itemView.findViewById(R.id.stlay);
            linearLayout = (FrameLayout) itemView.findViewById(R.id.layoutrow);
            name = (TextView) itemView.findViewById(R.id.name);
            studentname = (TextView) itemView.findViewById(R.id.studentname);
            batch = (TextView) itemView.findViewById(R.id.batch);
            subject = (TextView) itemView.findViewById(R.id.subject);
            faculty = (TextView) itemView.findViewById(R.id.faculty);
            classroom = (TextView) itemView.findViewById(R.id.classroom);
            startdate = (TextView) itemView.findViewById(R.id.startdate);
            enddate = (TextView) itemView.findViewById(R.id.enddate);

        }
    }

    public interface RecyclerAdapterPositionClicked {
        void position(int pos, View view);
    }
}

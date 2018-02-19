package com.pappayaed.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pappayaed.R;
import com.pappayaed.model.MarkListData;
import com.pappayaed.model.MarksheetDatum;

import java.util.ArrayList;
import java.util.List;


public class ExamMarkSheetSubjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MarkListData> list;
    private RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private Context context;

    public ExamMarkSheetSubjectListAdapter(Fragment context, List<MarkListData> list) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public ExamMarkSheetSubjectListAdapter(Context context, List<MarkListData> list) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public void updateList(List<MarkListData> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.examp_rowmarklist, parent, false);
        return new RowViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MarkListData object = list.get(position);

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

        ((RowViewHolder) holder).MarksheetRegister.setText(object.getExam_id().toString());
        ((RowViewHolder) holder).Studentname.setText(object.getStudent_id().toString());
        ((RowViewHolder) holder).TotalMarks.setText(object.getTotal_marks().toString());
        ((RowViewHolder) holder).TotalPercentage.setText(object.getPer().toString());
        ((RowViewHolder) holder).status.setText(object.getStatus().toString());
        ((RowViewHolder) holder).marks.setText(object.getMarks().toString());
        ((RowViewHolder) holder).subject.setText(object.getSubject().toString());


    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }


    private static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView Studentname, TotalMarks, marks, TotalPercentage, subject, status, MarksheetRegister;
        private FrameLayout linearLayout;

        public RowViewHolder(View itemView) {
            super(itemView);
            linearLayout = (FrameLayout) itemView.findViewById(R.id.layoutrow);
            Studentname = (TextView) itemView.findViewById(R.id.Studentname);
            subject = (TextView) itemView.findViewById(R.id.subject);
            marks = (TextView) itemView.findViewById(R.id.marks);
            TotalMarks = (TextView) itemView.findViewById(R.id.TotalMarks);
            TotalPercentage = (TextView) itemView.findViewById(R.id.TotalPercentage);
            status = (TextView) itemView.findViewById(R.id.stats);
            MarksheetRegister = (TextView) itemView.findViewById(R.id.MarksheetRegister);


        }
    }

    public interface RecyclerAdapterPositionClicked {
        void position(int pos, View view);
    }
}

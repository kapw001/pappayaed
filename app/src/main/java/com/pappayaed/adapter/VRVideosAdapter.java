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
import com.pappayaed.model.TimetableDatum;
import com.pappayaed.model.Video;
import com.pappayaed.model.VrVideoModel;
import com.pappayaed.preference.SessionManagenent;

import java.util.ArrayList;
import java.util.List;


public class VRVideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Video> list;
    private RecyclerAdapterPositionClicked recyclerAdapterPositionClicked;
    private Context context;

    public VRVideosAdapter(Fragment context, List<Video> list) {
        this.list = list;
        this.context = context.getContext();
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public VRVideosAdapter(Context context, List<Video> list) {
        this.list = list;
        this.context = context;
        this.recyclerAdapterPositionClicked = (RecyclerAdapterPositionClicked) context;

    }

    public void updateList(List<Video> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vrrow, parent, false);
        return new RowViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Video object = list.get(position);


        String name = object.getTitle().toString().replace(".mp4", "");

        ((RowViewHolder) holder).title.setText(name);

        ((RowViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapterPositionClicked.position(position, v, object.getLink());
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
        private TextView title;

        public RowViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);


        }
    }

    public interface RecyclerAdapterPositionClicked {
        void position(int pos, View view, String url);
    }
}

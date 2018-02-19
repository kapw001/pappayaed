package com.pappayaed.testrecyclerview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.pappayaed.R;
import com.pappayaed.adapter.AssignmentAdapter;

/**
 * Created by yasar on 2/6/17.
 */

public class CustomRecyclerView extends FrameLayout {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View v = inflate(getContext(), R.layout.rcom, this);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public <T> void setLinearLayoutManager(T layoutManager) {
        recyclerView.setLayoutManager((RecyclerView.LayoutManager) layoutManager);
    }

    public <T> void setAdapter(T adapter) {
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);
    }

}

package com.pappayaed.testrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.pappayaed.R;
import com.pappayaed.adapter.AssignmentAdapter;
import com.pappayaed.model.AssignmentList;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity implements AssignmentAdapter.RecyclerAdapterPositionClicked {

    private CustomRecyclerView customRecyclerView;
    private AssignmentAdapter assignmentAdapter;
    private List<AssignmentList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        customRecyclerView = (CustomRecyclerView) findViewById(R.id.cr);
        list = new ArrayList<>();
        list.addAll(getList());

        assignmentAdapter = new AssignmentAdapter(this, list);

        customRecyclerView.setAdapter(assignmentAdapter);

        customRecyclerView.setLinearLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
    }

    private List<AssignmentList> getList() {

        List<AssignmentList> li = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            AssignmentList assignmentList = new AssignmentList();
            assignmentList.setName("Name " + i);
            li.add(assignmentList);
        }

        return li;

    }

    @Override
    public void position(int pos, View view) {

    }
}

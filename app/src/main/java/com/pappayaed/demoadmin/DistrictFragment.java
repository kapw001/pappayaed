package com.pappayaed.demoadmin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.pappayaed.R;
import com.pappayaed.demoadmin.model.DistrictList;
import com.pappayaed.demoadmin.model.DistrictResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DistrictFragment extends Fragment {


    @BindView(R.id.LineChart)
    com.github.mikephil.charting.charts.LineChart LineChart;
    @BindView(R.id.barchart)
    BarChart barchart;
    Unbinder unbinder;

    private LineChartClass lineChartClass;

    private GroupBarChartClass groupBarChartClass;

    public DistrictFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_district, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String resultJson = Utils.loadJSONFromAsset(getContext());

        DistrictResult districtResult = new Gson().fromJson(resultJson, DistrictResult.class);

        lineChartClass = new LineChartClass(LineChart);
        groupBarChartClass = new GroupBarChartClass(barchart);

        List<DistrictList> districtLists = districtResult.getDistrictList();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList();

        for (int i = 0; i < districtLists.size(); i++) {

            DistrictList district = districtLists.get(i);
            String name = district.getName();
            float values = district.getOverall();
            float index = i;

            labels.add(name);
            entries.add(new Entry(index, values));

        }

        lineChartClass.addLineChartData(labels, entries);
        groupBarChartClass.addBarChart(labels, districtLists, districtLists.size());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

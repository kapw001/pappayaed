package com.pappayaed.demoadmin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.pappayaed.R;
import com.pappayaed.demoadmin.model.DistrictResult;
import com.pappayaed.demoadmin.model.StateDetail;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class StateFragment extends Fragment {

    private static final String TAG = "StateFragment";
    @BindView(R.id.PieChart)
    PieChart PieChart;
    @BindView(R.id.barchart)
    BarChart barchart;
    Unbinder unbinder;

    private PieChartClass pieChartClass;
    private BarChartClass barChartClass;

    public StateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_state, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String resultJson = Utils.loadJSONFromAsset(getContext());

        DistrictResult districtResult = new Gson().fromJson(resultJson, DistrictResult.class);

        Log.e(TAG, "onViewCreated: " + districtResult.getDistrictList().size() + "    " + districtResult.getStateDetail().toString());


        pieChartClass = new PieChartClass(PieChart);
        barChartClass = new BarChartClass(barchart);

        StateDetail stateDetail = districtResult.getStateDetail();


        float[] yData = {stateDetail.getMalePopulationPercent(), stateDetail.getFemalePopulationPercent()};
        String[] xData = {"Male", "Female"};
        pieChartClass.showPieChart(yData, xData,stateDetail.getTotalPopulation());

        List<String> xLabel = Arrays.asList("Overall", "Male", "Female");
        List<Float> values = Arrays.asList(stateDetail.getTotalLiteracy(), stateDetail.getMaleLiteracy(), stateDetail.getFemaleLiteracy());

        barChartClass.addBarChart2(xLabel, values);


//        Log.e(TAG, "onViewCreated: " + resultJson);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

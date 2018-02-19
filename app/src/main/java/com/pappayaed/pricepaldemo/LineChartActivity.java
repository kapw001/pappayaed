package com.pappayaed.pricepaldemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pappayaed.R;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {

    private LineChartAndroid lineChartAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        lineChartAndroid = (LineChartAndroid) findViewById(R.id.myline);

        ArrayList<String> labels = new ArrayList<>();
        labels.add("2014");
        labels.add("2015");
        labels.add("2016");
        labels.add("2017");

        Float[] f2 = {60.0f, 37.5f, 45.2f, 78.0f};

        lineChartAndroid.setEntry(f2, "10Th", Color.parseColor("#9AFF92"));

        Float[] f3 = {68.0f, 57.5f, 45.2f, 80.0f};

        lineChartAndroid.setEntry(f3, "12th", Color.parseColor("#1E3264"));

        Float[] f4 = {18.0f, 77.5f, 25.2f, 90.0f};

        lineChartAndroid.setEntry(f4, "12th", Color.parseColor("#FF5722"));

        lineChartAndroid.showLineChart(labels, true);


    }
}

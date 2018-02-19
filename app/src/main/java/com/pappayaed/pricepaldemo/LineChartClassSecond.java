package com.pappayaed.pricepaldemo;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pappayaed.demoadmin.PercentFormatter;

import java.util.ArrayList;

/**
 * Created by yasar on 8/2/18.
 */

public class LineChartClassSecond {

    private LineChart mChart;


    public LineChartClassSecond(LineChart lineChart) {
        this.mChart = lineChart;
    }


    public void addLineChartData(ArrayList<String> labels, ArrayList<ILineDataSet> dataSets) {
//        ArrayList<Entry> entries = new ArrayList();
//        entries.add(new Entry(0f, 30));
//        entries.add(new Entry(1f, 50));
//        entries.add(new Entry(2f, 40));
//        entries.add(new Entry(3f, 60));
////        entries.add(new Entry(4f, 80));
////        entries.add(new Entry(3f, 90));
////        entries.add(new Entry(4f, 20));
////        entries.add(new Entry(5f, 5));
//
//
//        ArrayList<String> labels = new ArrayList();
//        labels.add("2014");
//        labels.add("2015");
//        labels.add("2016");
//        labels.add("2017");


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
//        xAxis.setXOffset(10f);
//        xAxis.setAxisLineWidth(.5f);
//        xAxis.setAxisMaximum(3);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        mChart.setDescription(null);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);


        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);

//        Collections.sort(entries, new EntryXComparator());
//            LineDataSet dataset = new LineDataSet(entries, "Label");

//        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        dataset.setCubicIntensity(0.3f);
//        dataset.setDrawFilled(true);
//        dataset.setDrawCircles(true);
////        dataset.setLineWidth(1.8f);
//        dataset.setLineWidth(.0f);
//        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        dataset.setCircleRadius(6f);
//        dataset.setCircleHoleRadius(3f);
//        dataset.setCircleColor(Color.BLUE);
//        dataset.setHighLightColor(Color.rgb(244, 117, 117));
//        dataset.setColor(Color.BLUE);
//        dataset.setFillColor(Color.BLUE);
//        dataset.setFillAlpha(100);
//        dataset.setDrawHorizontalHighlightIndicator(false);
//        dataset.setFillFormatter(new IFillFormatter() {
//            @Override
//            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                return -10;
//            }
//        });

        //add legend to chart
        Legend legend = mChart.getLegend();
        legend.setEnabled(true);
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(5f);


        LineData data = new LineData(dataSets);
        data.setValueFormatter(new PercentFormatter());
        data.setDrawValues(true);
        mChart.getAxisLeft().setDrawZeroLine(false);
        mChart.setDrawGridBackground(false);
        mChart.getAxisRight().setDrawZeroLine(false);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.setMaxVisibleValueCount(4);
        mChart.setVisibleXRangeMaximum(4);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setDragEnabled(true);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(10f);
//        mChart.getAxisLeft().setDrawGridLines(false);
//        mChart.getAxisLeft().setEnabled(false);
//        mChart.getAxisRight().setEnabled(false);
//        mChart.getXAxis().setEnabled(false);

//        mChart.getAxisLeft().setDrawLabels(false);
        mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        mChart.setData(data);
        mChart.invalidate();
    }
}

package com.pappayaed.demoadmin;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.*;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yasar on 8/2/18.
 */

public class BarChartClass {

    private BarChart barChart;

    public BarChartClass(BarChart barChart) {
        this.barChart = barChart;
    }


    public void addBarChart2(List<String> xLabel, List<Float> values) {
        float barWidth = 0.25f;
        List<String> xAxisLabels = xLabel;
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        barChart.setDescription(null);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setValueFormatter(new PercentFormatter());
//        barChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
//            private DecimalFormat mFormat;
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                mFormat = new DecimalFormat("###,###,##0.0");
//                return mFormat.format(value) + " %";
//            }
//        });
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < values.size(); i++) {

            float v = values.get(i);

            yVals1.add(new BarEntry(i, v));

        }

        iBarDataSets.add(getBarDataSet2(yVals1, 0, ""));

//        for (int j = 0; j < 1; j++) {
//            Random random = new Random();
//            int budget = random.nextInt(500);
//            int expense = random.nextInt(100);
//            String[] s = {"Budget", "Expense"};
//            iBarDataSets.add(getBarDataSet2(getEntries(count, 500), j, s[j]));
//
//
//        }


        BarData data1 = barChart.getData();
        if (data1 != null) {
            barChart.clearValues();
            barChart.clear();
        }


//        Legend l = barChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setYOffset(20f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);

        BarData data = new BarData(iBarDataSets);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(10f);


        barChart.setData(data);
        barChart.getBarData().setBarWidth(barWidth);
//        barChart.setDrawValueAboveBar(false);
//        binding.barchart1.getXAxis().setAxisMinimum(0);
//        binding.barchart1.getXAxis().setAxisMaximum(0 + binding.barchart1.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//        binding.barchart1.groupBars(0, groupSpace, barSpace);
//        binding.barchart1.getData().setHighlightEnabled(false);
//        barChart.animateXY(1000, 1000);
        barChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        barChart.invalidate();
    }


    private List<BarEntry> getEntries(int count, int values) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < count; i++) {
            Random random = new Random();
            int budget = random.nextInt(500) + values;
            yVals1.add(new BarEntry(i, budget));
        }
        return yVals1;
    }

    private BarDataSet getBarDataSet(List<BarEntry> barEntry, int color, String s) {
        int[] co = ColorTemplate.JOYFUL_COLORS;
//        int[] co = {Color.rgb(217, 80, 138),Color.parseColor("#2962ff")};
        BarDataSet set1 = new BarDataSet(barEntry, s);
        set1.setColor(co[color]);
        return set1;
    }

    private BarDataSet getBarDataSet2(List<BarEntry> barEntry, int color, String s) {
//        int[] co = ColorTemplate.MATERIAL_COLORS;
        BarDataSet set1 = new BarDataSet(barEntry, s);
        ArrayList<Integer> colors = new ArrayList<Integer>();


        for (int c : Utils.MY_COLORSBAR)
            colors.add(c);
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.MATERIAL_COLORS)
//            colors.add(c);
        set1.setColors(colors);
        return set1;
    }

}

package com.pappayaed.demoadmin;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pappayaed.demoadmin.model.DistrictList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yasar on 8/2/18.
 */

public class GroupBarChartClass {

    private BarChart barChart;

    public GroupBarChartClass(BarChart barChart) {
        this.barChart = barChart;
    }


    public void addBarChart(List<String> xLabel, List<DistrictList> values, int count) {
        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
// (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;
        int groupCount = count;
        List<String> xAxisLabels = xLabel;
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        barChart.setDescription(null);
        barChart.getLegend().setEnabled(true);
        barChart.getAxisRight().setEnabled(true);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setValueFormatter(new PercentFormatter());
//        barChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
//            private DecimalFormat mFormat;
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                mFormat = new DecimalFormat("###,###,##0.0");
//                return "$" + mFormat.format(value) + " M";
//            }
//        });
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();

//        for (int j = 0; j < 2; j++) {
//            Random random = new Random();
//            int budget = random.nextInt(500);
//            int expense = random.nextInt(100);
//            String[] s = {"Budget", "Expense"};
//            if (j == 1) {
//                iBarDataSets.add(getBarDataSet(getEntries(count, 300), j, s[j]));
//            } else {
//                iBarDataSets.add(getBarDataSet(getEntries(count, 600), j, s[j]));
//            }
//
//        }

        String[] s = {"Male", "Female"};
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        for (int i = 0; i < values.size(); i++) {
            DistrictList district = values.get(i);

            float l = district.getMale();
            float l2 = district.getFemale();

            yVals1.add(new BarEntry(i, l));
            yVals2.add(new BarEntry(i, l2));
        }

        iBarDataSets.add(getBarDataSet(yVals1, 0, s[0]));
        iBarDataSets.add(getBarDataSet(yVals2, 1, s[1]));

        BarData data1 = barChart.getData();
        if (data1 != null) {
            barChart.clearValues();
            barChart.clear();
        }


        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);


        BarData data = new BarData(iBarDataSets);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(10f);
        data.setDrawValues(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setData(data);
        barChart.setMaxVisibleValueCount(4);
        barChart.setVisibleXRangeMaximum(4);
        barChart.setAutoScaleMinMaxEnabled(true);
//        barChart.setViewPortOffsets(20, 0, 20, 0);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.getData().setHighlightEnabled(false);
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

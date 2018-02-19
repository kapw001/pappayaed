package com.pappayaed.demoadmin;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

/**
 * Created by yasar on 8/2/18.
 */

public class PieChartClass {

    private PieChart pieChart;
    private float[] yData = {25.0f, 30.0f, 20.0f, 15.0f, 10.0f};
    private String[] xData = {"UK", "INDIA", "UAE", "USA", "FRANCE"};

    public PieChartClass(PieChart pieChart) {
        this.pieChart = pieChart;
        addPieChartData();
    }


    public void addPieChartData() {

        // pieChart.setDescription("Sales by employee (In Thousands $) ");
//        pieChart.setRotationEnabled(true);
//        //pieChart.setUsePercentValues(true);
//        //pieChart.setHoleColor(Color.BLUE);
//        //pieChart.setCenterTextColor(Color.BLACK);
//        pieChart.setHoleRadius(25f);
//        pieChart.setTransparentCircleAlpha(0);
//        pieChart.setCenterText("Revenue by region");
//        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
//pieChart.setEntryLabelTextSize(20);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

//        pieChart.setCenterTextTypeface(mTfLight);
//        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(48f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(90);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);

        //add legend to chart
        Legend legend = pieChart.getLegend();
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


    }


    public void showPieChart(float[] yData, String[] xData, Long values) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
//            Resources res = getResources();
//            Drawable drawable = res.getDrawable(R.drawable.ic_menu_camera);
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        String v = values + "";

        String n = v.substring(0, v.length() - 3) + " K";


        pieChart.setCenterText(n);
        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setIconsOffset(new MPPointF(0, 30));
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setValueTextColor(Color.BLUE);

//        pieDataSet.setSliceSpace(5);
//        pieDataSet.setValueTextSize(12);
//        pieDataSet.setValueTextColor(Color.BLACK);
//        pieDataSet.setVisible(true);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Utils.MY_COLORS[0]);
        colors.add(Utils.MY_COLORS[1]);

//        for (int c : ColorTemplate.MATERIAL_COLORS)
//            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
        pieDataSet.setColors(colors);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(8f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelTextSize(8f);
        pieChart.setData(pieData);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.invalidate();
    }

}

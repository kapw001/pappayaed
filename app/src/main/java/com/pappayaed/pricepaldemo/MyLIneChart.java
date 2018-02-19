package com.pappayaed.pricepaldemo;

import android.graphics.Color;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pappayaed.demoadmin.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasar on 13/2/18.
 */

public class MyLIneChart<T extends Chart> {

    private T mChart;


    public MyLIneChart(T mChart) {

        this.mChart = mChart;

    }

    public void init() {


    }

    // TODO: 13/2/18 setXaxis labels
    public XAxis setXAxis(ArrayList<String> labels) {

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
        return xAxis;

    }

    // TODO: 13/2/18  this used set legend
    public Legend setLegend(boolean isEnabled) {
        //add legend to chart
        Legend legend = mChart.getLegend();
        legend.setEnabled(isEnabled);
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(5f);

        return legend;
    }

    public LineData setLineData(ArrayList<ILineDataSet> dataSets) {

        LineData data = new LineData(dataSets);
        data.setValueFormatter(new PercentFormatter());
        data.setDrawValues(true);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(10f);

        return data;

    }

    public LineDataSet set(ArrayList<Entry> entries, String label) {


        LineDataSet dataset = new LineDataSet(entries, label);
        dataset.setDrawFilled(false);
        dataset.setDrawCircles(true);
        dataset.setLineWidth(1.8f);
        dataset.setLineWidth(.0f);
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setCircleRadius(6f);
        dataset.setCircleHoleRadius(3f);
        dataset.setCircleColor(Color.parseColor("#FF6540"));
        dataset.setHighLightColor(Color.rgb(244, 117, 117));
        dataset.setColor(Color.parseColor("#FF6540"));
        dataset.setFillColor(Color.parseColor("#FF6540"));
        dataset.setFillAlpha(100);
        dataset.setDrawHorizontalHighlightIndicator(false);
        dataset.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        return dataset;

    }


    public <T extends List> ArrayList<Entry> setEntry(T t) {

        ArrayList<Entry> entries = new ArrayList();

        for (int i = 0; i < t.size(); i++) {

            float index = i;
            float v = (float) t.get(i);
            entries.add(new Entry(index, v));
        }

        return entries;

    }
}

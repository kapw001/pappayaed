package com.pappayaed.pricepaldemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pappayaed.R;
import com.pappayaed.login.LoginActivity;
import com.pappayaed.preference.SessionManagenent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity {

    @BindView(R.id.staffstudent)
    RadioTabButton staffstudent;
    @BindView(R.id.PieChart)
    PieChart PieChart;
    @BindView(R.id.staffstudent1)
    RadioTabButton staffstudent1;
    @BindView(R.id.barchart)
    BarChart barchart;
    @BindView(R.id.LineChart)
    LineChart LineChart;
    @BindView(R.id.staffstudent2)
    RadioTabButton staffstudent2;
    @BindView(R.id.horizontalbarchart)
    HorizontalBarChart horizontalbarchart;
    @BindView(R.id.staffstudent3)
    RadioTabButton staffstudent3;
    @BindView(R.id.LineChart1)
    LineChart LineChart1;

    private PieChartClass pieChartClass;
    private BarChartClass barChartClass;
    private LineChartClass lineChartClass;
    private HorizontalBarChartClass horizontalBarChartClass;
    private LineChartClassSecond lineChartClassSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        pieChartClass = new PieChartClass(PieChart);
        barChartClass = new BarChartClass(barchart);
        lineChartClass = new LineChartClass(LineChart);
        horizontalBarChartClass = new HorizontalBarChartClass(horizontalbarchart);
        lineChartClassSecond = new LineChartClassSecond(LineChart1);

//        Toast.makeText(this, "" + staffstudent.getCurrentlyChecked(), Toast.LENGTH_SHORT).show();

        pieChartChange(staffstudent.getCurrentlyChecked());

        staffstudent.setBtn1("Staff");
        staffstudent.setBtn2("Student");

        staffstudent.listener(new RadioTabButton.RadioButtonCheckedChangeListener() {
            @Override
            public void onCheckedChangeListener(int id) {
                pieChartChange(id);
//                Toast.makeText(PrincipalActivity.this, "" + id, Toast.LENGTH_SHORT).show();
            }
        });


        StackedBarChartChange(staffstudent1.getCurrentlyChecked());

        staffstudent1.listener(new RadioTabButton.RadioButtonCheckedChangeListener() {
            @Override
            public void onCheckedChangeListener(int id) {
                StackedBarChartChange(id);
//                Toast.makeText(PrincipalActivity.this, "" + id, Toast.LENGTH_SHORT).show();
            }
        });

        staffstudent1.setBtn1("Staff");
        staffstudent1.setBtn2("Student");


        ArrayList<String> labels = new ArrayList<>();
        labels.add("2014");
        labels.add("2015");
        labels.add("2016");
        labels.add("2017");

        Random random = new Random();


        Float[] f2 = {60.0f, 37.5f, 45.2f, 78.0f};

        ArrayList<Entry> entries = new ArrayList();
        for (int i = 0; i < f2.length; i++) {
            float index = i;
//            float v = random.nextInt(100);
            float v = f2[i];
            entries.add(new Entry(index, v));
        }


        Float[] f3 = {68.0f, 57.5f, 45.2f, 80.0f};

        ArrayList<Entry> entries1 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            float index = i;
//            float v = random.nextInt(100);
            float v = f3[i];
            entries1.add(new Entry(index, v));
        }

        LineDataSet dataset = new LineDataSet(entries, "10th");
        dataset.setDrawFilled(true);
        dataset.setDrawCircles(true);
//        dataset.setLineWidth(1.8f);
        dataset.setLineWidth(.0f);
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setCircleRadius(6f);
        dataset.setCircleHoleRadius(3f);
        dataset.setCircleColor(Color.parseColor("#9AFF92"));
        dataset.setHighLightColor(Color.rgb(244, 117, 117));
        dataset.setColor(Color.parseColor("#9AFF92"));
        dataset.setFillColor(Color.parseColor("#9AFF92"));
//        dataset.setFillAlpha(100);
        dataset.setDrawHorizontalHighlightIndicator(false);
        dataset.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        LineDataSet dataset1 = new LineDataSet(entries1, "12th");
        dataset1.setDrawFilled(true);
        dataset1.setDrawCircles(true);
//        dataset.setLineWidth(1.8f);
        dataset1.setLineWidth(.0f);
        dataset1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset1.setCircleRadius(6f);
        dataset1.setCircleHoleRadius(3f);
        dataset1.setCircleColor(Color.parseColor("#1E3264"));
        dataset1.setHighLightColor(Color.rgb(244, 117, 117));
        dataset1.setColor(Color.parseColor("#1E3264"));
        dataset1.setFillColor(Color.parseColor("#1E3264"));
//        dataset1.setFillAlpha(100);
        dataset1.setDrawHorizontalHighlightIndicator(false);
        dataset1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataset);
        dataSets.add(dataset1);
        lineChartClass.addLineChartData(labels, dataSets);


        StackedHorizontalBarChartChange(staffstudent2.getCurrentlyChecked());

        staffstudent2.listener(new RadioTabButton.RadioButtonCheckedChangeListener() {
            @Override
            public void onCheckedChangeListener(int id) {
                StackedHorizontalBarChartChange(id);
//                  Toast.makeText(PrincipalActivity.this, "" + id, Toast.LENGTH_SHORT).show();
            }
        });

        staffstudent2.setBtn1("Staff");
        staffstudent2.setBtn2("Student");


        lineChartChange(staffstudent3.getCurrentlyChecked());

        staffstudent3.listener(new RadioTabButton.RadioButtonCheckedChangeListener() {
            @Override
            public void onCheckedChangeListener(int id) {
                lineChartChange(id);
//                  Toast.makeText(PrincipalActivity.this, "" + id, Toast.LENGTH_SHORT).show();
            }
        });

        staffstudent3.setBtn1("Budget/Expenses");
        staffstudent3.setBtn2("Revenue Growth");
    }


    private void pieChartChange(int id) {

        switch (id) {

            case R.id.btn1:

                float[] yData = {50.0f, 27f};
                String[] xData = {"Male", "Female"};
                pieChartClass.showPieChart(yData, xData, 87l);

                break;

            case R.id.btn2:

                float[] yData1 = {753f, 633f};
                String[] xData1 = {"Male", "Female"};
                pieChartClass.showPieChart(yData1, xData1, 1473l);

                break;

        }


    }


    private void StackedBarChartChange(int id) {

        switch (id) {

            case R.id.btn1:

                List<String> xLabel = Arrays.asList("10th", "11th", "12th");

                List<StackedModel> values = Arrays.asList(new StackedModel(10.0f, 13.0f), new StackedModel(15.0f, 16.0f), new StackedModel(12.0f, 18.0f));

                barChartClass.addBarChart2(xLabel, values);

                break;

            case R.id.btn2:

                List<String> xLabel1 = Arrays.asList("10th", "11th", "12th");

                List<StackedModel> values1 = Arrays.asList(new StackedModel(40.0f, 14.0f), new StackedModel(45.0f, 26.0f), new StackedModel(45.0f, 38.0f));

                barChartClass.addBarChart2(xLabel1, values1);
                break;

        }


    }

    private void StackedHorizontalBarChartChange(int id) {

        switch (id) {

            case R.id.btn1:

                List<String> xLabel = Arrays.asList("10th", "11th", "12th");

                List<StackedModel> values = Arrays.asList(new StackedModel(10.0f, 2.0f), new StackedModel(15.0f, 3.0f), new StackedModel(12.0f, 1.0f));

                horizontalBarChartClass.addBarChart2(xLabel, values);

                break;

            case R.id.btn2:

                List<String> xLabel1 = Arrays.asList("10th", "11th", "12th");

                List<StackedModel> values1 = Arrays.asList(new StackedModel(40.0f, 10.0f), new StackedModel(45.0f, 6.0f), new StackedModel(45.0f, 8.0f));

                horizontalBarChartClass.addBarChart2(xLabel1, values1);

                break;

        }
    }


    private void lineChartChange(int id) {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("2014");
        labels.add("2015");
        labels.add("2016");
        labels.add("2017");


        switch (id) {

            case R.id.btn1:

                Random random = new Random();

                Float[] f2 = {20.0f, 37.5f, 45.2f, 68.0f};

                ArrayList<Entry> entries = new ArrayList();
                for (int i = 0; i < 4; i++) {
                    float index = i;
                    float v = f2[i];
                    entries.add(new Entry(index, v));
                }

                Float[] f3 = {30.0f, 27.5f, 55.2f, 78.0f};

                ArrayList<Entry> entries1 = new ArrayList();
                for (int i = 0; i < 4; i++) {
                    float index = i;
                    float v = f3[i];
                    entries1.add(new Entry(index, v));
                }

                LineDataSet dataset = new LineDataSet(entries, "Budget");
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

                LineDataSet dataset1 = new LineDataSet(entries1, "Expenses");
                dataset1.setDrawFilled(false);
                dataset1.setDrawCircles(true);
                dataset.setLineWidth(1.8f);
                dataset1.setLineWidth(.0f);
                dataset1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                dataset1.setCircleRadius(6f);
                dataset1.setCircleHoleRadius(3f);
                dataset1.setCircleColor(Color.parseColor("#1E3264"));
                dataset1.setHighLightColor(Color.rgb(244, 117, 117));
                dataset1.setColor(Color.parseColor("#1E3264"));
                dataset1.setFillColor(Color.parseColor("#1E3264"));
                dataset1.setFillAlpha(100);
                dataset1.setDrawHorizontalHighlightIndicator(false);
                dataset1.setFillFormatter(new IFillFormatter() {
                    @Override
                    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                        return -10;

                    }


                });

                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

                dataSets.add(dataset);
                dataSets.add(dataset1);

                lineChartClassSecond.addLineChartData(labels, dataSets);

                break;

            case R.id.btn2:


                Random random1 = new Random();


                ArrayList<Entry> entries3 = new ArrayList();

                Float[] f = {40.0f, 67.5f, 55.2f, 88.0f};

                for (int i = 0; i < 4; i++) {

                    float index = i;

                    float v = f[i];

                    entries3.add(new Entry(index, v));

                }

                LineDataSet dataset2 = new LineDataSet(entries3, "Revenue Growth");
                dataset2.setDrawFilled(false);
                dataset2.setDrawCircles(true);
                dataset2.setLineWidth(1.8f);
                dataset2.setLineWidth(.0f);
                dataset2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                dataset2.setCircleRadius(6f);
                dataset2.setCircleHoleRadius(3f);
                dataset2.setCircleColor(Color.parseColor("#FF6540"));
                dataset2.setHighLightColor(Color.rgb(244, 117, 117));
                dataset2.setColor(Color.parseColor("#FF6540"));
                dataset2.setFillColor(Color.parseColor("#FF6540"));
                dataset2.setFillAlpha(100);
                dataset2.setDrawHorizontalHighlightIndicator(false);
                dataset2.setFillFormatter(new IFillFormatter() {
                    @Override
                    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                        return -10;
                    }
                });

                ArrayList<ILineDataSet> dataSets1 = new ArrayList<ILineDataSet>();

                dataSets1.add(dataset2);

                lineChartClassSecond.addLineChartData(labels, dataSets1);


                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log:


                SessionManagenent.getSessionManagenent().clear();
                startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

package com.pappayaed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pappayaed.R;
import com.pappayaed.model.StudentHolidaysStatusDatum;

import java.util.List;

/**
 * Created by yasar on 19/6/17.
 */

public class CustomAdapter extends ArrayAdapter<StudentHolidaysStatusDatum> {

    LayoutInflater flater;

    public CustomAdapter(Activity context, int resouceId, int textviewId, List<StudentHolidaysStatusDatum> list) {

        super(context, resouceId, textviewId, list);
        flater = context.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StudentHolidaysStatusDatum rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.spinner_row, null, true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.name);
        txtTitle.setText(rowItem.getName());

        return rowview;
    }
}
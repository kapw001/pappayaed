package com.pappayaed.pricepaldemo;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pappayaed.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yasar on 9/2/18.
 */

public class RadioTabButton extends FrameLayout {


    @BindView(R.id.btn1)
    RadioButton btn1;
    @BindView(R.id.btn2)
    RadioButton btn2;
    @BindView(R.id.rdogrp)
    RadioGroup rdogrp;

    public RadioTabButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RadioTabButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RadioTabButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RadioTabButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void setBtn1(String name) {

        btn1.setText(name);

    }

    public void setBtn2(String name) {

        btn2.setText(name);

    }

    private void init(Context context) {

        View view = inflate(context, R.layout.radiobuttongroup, this);
        ButterKnife.bind(this);

    }

    public void listener(final RadioButtonCheckedChangeListener onCheckedChangeListener) {
        rdogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                RadioButton rb=(RadioButton)findViewById(checkedId);
                onCheckedChangeListener.onCheckedChangeListener(checkedId);
            }
        });
    }

    public int getCurrentlyChecked() {
        return rdogrp.getCheckedRadioButtonId();
    }

    interface RadioButtonCheckedChangeListener {
        void onCheckedChangeListener(int id);
    }
}
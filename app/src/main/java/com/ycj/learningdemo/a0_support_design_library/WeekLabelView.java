package com.ycj.learningdemo.a0_support_design_library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycj.learningdemo.R;

/**
 * Created by YCJ on 2015/11/27.
 */
public class WeekLabelView extends LinearLayout {

    private String[] mWeekLabels;
    private Context mContext;

    private LayoutParams mParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);

    public WeekLabelView(Context context) {
        this(context, null);
    }

    public WeekLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);

        mWeekLabels = mContext.getResources().getStringArray(R.array.week_labels);

        setupChildren();
    }

    private void setupChildren(){
        mParams.weight = 1;
        for (String label: mWeekLabels){
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(mParams);
            textView.setText(label);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(0x32AABB23);
            textView.setPadding(0, 20, 0, 20);
            addView(textView);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        }
    }
}

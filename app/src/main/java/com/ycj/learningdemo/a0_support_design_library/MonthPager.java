package com.ycj.learningdemo.a0_support_design_library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ycj.learningdemo.MyLog;
import com.ycj.learningdemo.R;

import java.util.Stack;

/**
 * Created by YCJ on 2015/11/27.
 */
@CoordinatorLayout.DefaultBehavior(MonthPager.Behavior.class)
public class MonthPager extends ViewPager {

    private int selectedIndex;

    public MonthPager(Context context) {
        this(context, null);
    }

    public MonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdapter(new MonthPagerAdapter(context));
    }

    public int getTopDistance() {
        int rowCount = selectedIndex / 7;
        return getHeight()/6*rowCount;
    }

    public int getBottomDistance() {
        return getHeight()/6 * 5;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class MonthPagerAdapter extends PagerAdapter {

        private Context mContext;

        private Stack<GridView> mViewCached;

        private ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        MonthPagerAdapter(Context context) {
            this.mContext = context;
            mViewCached = new Stack<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MyLog.d("MonthPagerAdapter instantiateItem");

            GridView monthGrid;

            if (mViewCached.isEmpty()) {
                monthGrid = new GridView(mContext);
                monthGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));

                MonthGridAdapter gridAdapter = new MonthGridAdapter(mContext);
                monthGrid.setAdapter(gridAdapter);
                monthGrid.setNumColumns(7);

                monthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MonthGridAdapter gridAdapter = (MonthGridAdapter) parent.getAdapter();
                        selectedIndex = position;
                        gridAdapter.setIndex(position);
                    }
                });
            } else {
                monthGrid = mViewCached.pop();
                ((MonthGridAdapter) monthGrid.getAdapter()).setIndex(0);
            }

            container.addView(monthGrid);
            return monthGrid;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            MyLog.d("MonthPagerAdapter destroyItem");
            GridView monthGrid = (GridView) object;
            container.removeView(monthGrid);
            mViewCached.push(monthGrid);
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private static class MonthGridAdapter extends BaseAdapter {

        private Context mContext;

        private int index;

        MonthGridAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return 42;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DayView dayView;
            if (convertView == null) {
                dayView = new DayView(mContext);

            } else {
                dayView = (DayView) convertView;
            }


            dayView.setText(position + "");
            if (position == index) {
                dayView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_day_selected));
            } else {
                dayView.setBackgroundColor(Color.TRANSPARENT);

            }
            return dayView;
        }

        public void setIndex(int index) {
            this.index = index;
            notifyDataSetChanged();
        }
    }

    private static class DayView extends TextView {

        public DayView(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }

    public static class Behavior extends CoordinatorLayout.Behavior<MonthPager> {

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, MonthPager child, int layoutDirection) {

            MyLog.d("MonthPager.Behavior onLayoutChild");
            parent.onLayoutChild(child, layoutDirection);

            int index = parent.indexOfChild(child);
            if (index > 0) {
                View preView = parent.getChildAt(index - 1);
                int offset = preView.getBottom();
                child.offsetTopAndBottom(offset);
            }

            return true;
        }
    }
}

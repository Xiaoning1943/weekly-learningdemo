package com.ycj.learningdemo.a0_support_design_library;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ycj.learningdemo.MyLog;

/**
 * Created by YCJ on 2015/11/27.
 */
public class EventListBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private int mInitialOffset;
    private int mMinOffset;

    public EventListBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {

        MyLog.d("EventListBehavior onLayoutChild");
        parent.onLayoutChild(child, layoutDirection);

        int index = parent.indexOfChild(child);
        if (index > 0){
            View preView = parent.getChildAt(index - 1);
            int offset = preView.getBottom();
            child.offsetTopAndBottom(offset);
            mMinOffset = preView.getTop();
        }

        mInitialOffset = child.getTop();

        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        //We have to declare interest in the scroll to receive further events
        boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        //Only capture on the view currently being scrolled
        return isVertical && child == directTargetChild;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        MyLog.d("EventListBehavior: onNestedScroll");


    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        MyLog.d("EventListBehavior: onNestedPreScroll");

        //When not at the top, consume all scrolling for the card
        if (child.getTop() <= mInitialOffset && child.getTop() > mInitialOffset - getMonthPager(coordinatorLayout).getTopDistance()) {
            //Tell the parent what we've consumed
            consumed[1] = move(child, dy, mInitialOffset - getMonthPager(coordinatorLayout).getTopDistance(), mInitialOffset);

            getMonthPager(coordinatorLayout).scrollBy(0, consumed[1]);
        } else if (child.getTop() <= mInitialOffset && child.getTop() >= mInitialOffset - getMonthPager(coordinatorLayout).getBottomDistance()){
            consumed[1] = move(child, dy,  mInitialOffset - getMonthPager(coordinatorLayout).getBottomDistance(), mInitialOffset);
        }
    }

    private MonthPager getMonthPager(CoordinatorLayout coordinatorLayout){
        return (MonthPager)coordinatorLayout.getChildAt(1);
    }

    //Scroll the view and return back the actual distance scrolled
    private int move(View child, int dy, int minOffset, int maxOffset) {
        final int initialOffset = child.getTop();
        //Clamped new position - initial position = offset change
        int delta = clamp(initialOffset - dy, minOffset, maxOffset) - initialOffset;
        child.offsetTopAndBottom(delta);

        return -delta;
    }

    private int clamp(int value, int min, int max) {
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        } else {
            return value;
        }
    }


}

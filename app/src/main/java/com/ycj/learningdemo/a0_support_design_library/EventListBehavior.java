package com.ycj.learningdemo.a0_support_design_library;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.ycj.learningdemo.MoveUtil;
import com.ycj.learningdemo.MyLog;

/**
 * Created by YCJ on 2015/11/27.
 */
public class EventListBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private int mInitialOffset = -1;
    private int mMinOffset = -1;
    private int mTop;

    public EventListBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);

        MonthPager monthPager = getMonthPager(parent);

        if (monthPager.getBottom() > 0 && mInitialOffset == -1) {
            mInitialOffset = monthPager.getBottom();
            mMinOffset = mInitialOffset - getMonthPager(parent).getWholeMovableDistance();
            child.offsetTopAndBottom(mInitialOffset);
            saveTop(mInitialOffset);
        } else if (mInitialOffset != -1) {
            child.offsetTopAndBottom(mTop);
        }


        return true;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, RecyclerView child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        MyLog.d("EventListBehavior onMeasureChild heightUsed = " + heightUsed);

        MonthPager monthPager = getMonthPager(parent);
        final int measuredHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec) - heightUsed - monthPager.getHeight() / 6;
        int childMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY);
        child.measure(parentWidthMeasureSpec, childMeasureSpec);
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

    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        //When not at the top, consume all scrolling for the card
        if (child.getTop() <= mInitialOffset && child.getTop() >= mMinOffset) {
            //Tell the parent what we've consumed
            consumed[1] = MoveUtil.move(child, dy, mMinOffset, mInitialOffset);
            saveTop(child.getTop());
        }
    }

    @Override
    public void onStopNestedScroll(final CoordinatorLayout parent, final RecyclerView child, View target) {
        super.onStopNestedScroll(parent, child, target);

        MonthPager monthPager = getMonthPager(parent);

        if (isGoingUp) {
            if (mInitialOffset - mTop > 60){
                scrollToYCoordinate(parent, child, mMinOffset, 200);
            } else {
                scrollToYCoordinate(parent, child, mInitialOffset, 80);
            }
        } else {
            if (mTop - mMinOffset > 60){
                scrollToYCoordinate(parent, child, mInitialOffset, 200);
            } else {
                scrollToYCoordinate(parent, child, mMinOffset, 80);
            }
        }
    }

    private void scrollToYCoordinate(final CoordinatorLayout parent, final RecyclerView child, final int y, int duration){
        final Scroller scroller = new Scroller(parent.getContext());
        scroller.startScroll(0, mTop, 0, y - mTop, duration);

        ViewCompat.postOnAnimation(child, new Runnable() {
            @Override
            public void run() {
                if (scroller.computeScrollOffset()) {
                    int delta = scroller.getCurrY() - child.getTop();
                    child.offsetTopAndBottom(delta);

                    saveTop(child.getTop());
                    parent.dispatchDependentViewsChanged(child);

                    // Post ourselves so that we run on the next animation
                    ViewCompat.postOnAnimation(child, this);
                }
            }
        });
    }

    private MonthPager getMonthPager(CoordinatorLayout coordinatorLayout) {

        MonthPager monthPager = (MonthPager) coordinatorLayout.getChildAt(0);

        return monthPager;
    }



    private boolean isGoingUp;

    private void saveTop(int top){
        this.mTop = top;

        if (mTop == mInitialOffset){
            isGoingUp = true;
        } else if (mTop == mMinOffset){
            isGoingUp = false;
        }
    }


}

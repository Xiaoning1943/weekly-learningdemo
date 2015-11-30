package com.ycj.learningdemo;

import android.view.View;

/**
 * Created by YCJ on 2015/11/30.
 */
public class MoveUtil {

    private static int clamp(int value, int min, int max) {
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        } else {
            return value;
        }
    }

    //Scroll the view and return back the actual distance scrolled
    public static  int move(View child, int dy, int minOffset, int maxOffset) {
        final int initialOffset = child.getTop();
        //Clamped new position - initial position = offset change
        int delta = clamp(initialOffset - dy, minOffset, maxOffset) - initialOffset;
        child.offsetTopAndBottom(delta);

        return -delta;
    }
}

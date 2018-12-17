package com.example.mxy1217.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        measureChildren(0, 0);

        int totalWidth = 0;
        int totalHeight = 0;
        for (int j = 0; j < getChildCount(); j++) {
            View view = getChildAt(j);
            if (totalWidth + view.getMeasuredWidth() >= getMeasuredWidth()) {
                totalWidth = 0;
                totalHeight = view.getMeasuredHeight();
            }
            view.layout(totalWidth,
                    totalHeight,
                    totalWidth + view.getMeasuredWidth(),
                    totalHeight + view.getMeasuredHeight());
            totalWidth += view.getMeasuredWidth();
        }
    }
}

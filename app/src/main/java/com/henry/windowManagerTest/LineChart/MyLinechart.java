package com.henry.windowManagerTest.LineChart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;

public class MyLinechart extends LineChart {


    public MyLinechart(Context context) {
        super(context);
    }

    public MyLinechart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinechart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void notifyDataSetChangedNew() {
        if (mData == null) {
            if (mLogEnabled)
                Log.i(LOG_TAG, "Preparing... DATA NOT SET.");
            return;
        } else {
            if (mLogEnabled)
                Log.i(LOG_TAG, "Preparing...");
        }
        if (mRenderer != null)
            mRenderer.initBuffers();
        calcMinMax();
        mXAxis.mAxisRange = 100000000;
        mAxisRendererLeft.computeAxis(mAxisLeft.mAxisMinimum, mAxisLeft.mAxisMaximum, mAxisLeft.isInverted());
        mAxisRendererRight.computeAxis(mAxisRight.mAxisMinimum, mAxisRight.mAxisMaximum, mAxisRight.isInverted());
        mXAxisRenderer.computeAxis(mXAxis.mAxisMinimum, mXAxis.mAxisMaximum, false);

        if (mLegend != null)
            mLegendRenderer.computeLegend(mData);
        calculateOffsets();
    }


}

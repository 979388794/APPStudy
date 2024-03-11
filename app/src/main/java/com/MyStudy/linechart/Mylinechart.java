package com.MyStudy.linechart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;

public class Mylinechart extends LineChart {
    public Mylinechart(Context context) {
        super(context);
    }

    public Mylinechart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Mylinechart(Context context, AttributeSet attrs, int defStyle) {
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
        mXAxis.mAxisRange = 10000;
        mAxisRendererLeft.computeAxis(mAxisLeft.mAxisMinimum, mAxisLeft.mAxisMaximum, mAxisLeft.isInverted());
        mAxisRendererRight.computeAxis(mAxisRight.mAxisMinimum, mAxisRight.mAxisMaximum, mAxisRight.isInverted());
        mXAxisRenderer.computeAxis(mXAxis.mAxisMinimum, mXAxis.mAxisMaximum, false);
        if (mLegend != null)
            mLegendRenderer.computeLegend(mData);
        calculateOffsets();
    }

}

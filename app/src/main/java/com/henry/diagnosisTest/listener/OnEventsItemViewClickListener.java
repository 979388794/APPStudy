package com.henry.diagnosisTest.listener;

import android.view.View;

import com.henry.diagnosisTest.model.DiagnosisEventsItem;


/**
 * 事件项视图点击监听器
 *
 */
public interface OnEventsItemViewClickListener {
    void setOnViewClickListener(View view, int position, DiagnosisEventsItem item);
}

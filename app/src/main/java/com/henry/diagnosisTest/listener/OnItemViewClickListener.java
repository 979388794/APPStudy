package com.henry.diagnosisTest.listener;

import android.view.View;

import com.henry.diagnosisTest.model.DiagnosisHistoryItem;


/**
 * 项视图点击监听器
 */
public interface OnItemViewClickListener {
    void setOnViewClickListener(View view, int position, DiagnosisHistoryItem item);
}

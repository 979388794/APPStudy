package com.henry.diagnosisTest.adapter;

import android.content.Context;

import com.henry.basic.R;
import com.henry.basic.databinding.ItemDiagnosisHistoryBinding;
import com.henry.diagnosisTest.base.BaseBindingAdapter;
import com.henry.diagnosisTest.model.DiagnosisHistoryInfo;


public class DiagnosisHistoryRecyclerAdapter extends BaseBindingAdapter<DiagnosisHistoryInfo, ItemDiagnosisHistoryBinding> {

    public DiagnosisHistoryRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_diagnosis_history;
    }

    @Override
    protected void onBindItem(ItemDiagnosisHistoryBinding binding, DiagnosisHistoryInfo bean, int position) {
        binding.setBean(bean);
        binding.executePendingBindings();
    }


}

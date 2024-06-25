package com.henry.diagnosisTest.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ObservableArrayList;

import com.henry.basic.R;
import com.henry.basic.databinding.ItemDiagnosisBinding;
import com.henry.diagnosisTest.base.BaseBindingAdapter;
import com.henry.diagnosisTest.model.DiagnosisInfo;


public class DiagnosisRecyclerAdapter extends BaseBindingAdapter<DiagnosisInfo, ItemDiagnosisBinding> {

    private DiagnosisItemClickListener diagnosisItemClickListener;

    public void setDiagnosisItemClickListener(DiagnosisItemClickListener diagnosisItemClickListener) {
        this.diagnosisItemClickListener = diagnosisItemClickListener;
    }

    public DiagnosisRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public ObservableArrayList<DiagnosisInfo> getItems() {
        if(null == items){
            items = new ObservableArrayList<>();
        }
        return items;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_diagnosis;
    }

    @Override
    protected void onBindItem(ItemDiagnosisBinding binding, DiagnosisInfo bean, int position) {
        binding.setBean(bean);
        binding.actionTv.setVisibility(bean.getDiagnosisStatusCode() != 0 /**&& bean.getDiagnosisIsRepair() == 1 **/? View.VISIBLE : View.GONE);
        binding.uploadTv.setVisibility(bean.getDiagnosisStatusCode() != 0 ? View.VISIBLE : View.GONE);
        binding.ivcl.setMinHeight(120);
        binding.actionTv.setOnClickListener((v) -> {
            if (diagnosisItemClickListener != null) {
                diagnosisItemClickListener.clickFix(position, bean);
            }
        });
        binding.uploadTv.setOnClickListener((v) -> {
            if (diagnosisItemClickListener != null) {
                diagnosisItemClickListener.clickUpload(position, bean);
            }
        });


        binding.executePendingBindings();
    }

    public interface DiagnosisItemClickListener {
        public void clickFix(int position, DiagnosisInfo bean);

        public void clickUpload(int position, DiagnosisInfo bean);
    }

}

package com.henry.diagnosisTest.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.henry.basic.R;
import com.henry.basic.databinding.ItemDiagnosisModuleBinding;
import com.henry.diagnosisTest.activity.DiagnosisUploadLogActivity;
import com.henry.diagnosisTest.base.BaseBindingAdapter;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.viewMdodel.DiagnosisMainViewModel;



public class DiagnosisModuleRecyclerAdapter extends BaseBindingAdapter<DiagnosisModule, ItemDiagnosisModuleBinding> {

    public DiagnosisModuleRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public ObservableArrayList<DiagnosisModule> getItems() {
        if(null == items){
            items = new ObservableArrayList<>();
        }
        return items;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_diagnosis_module;
    }

    @Override
    protected void onBindItem(ItemDiagnosisModuleBinding binding, DiagnosisModule bean, int position) {
        binding.setBean(bean);
        binding.executePendingBindings();
        binding.actionTv.setClickable(true);
        binding.actionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != bean) {
                    Log.d("DiagnosisModuleRecyclerAdapter", "bean = " + bean.toString());
                }
                new ViewModelProvider((ViewModelStoreOwner) mContext).get(DiagnosisMainViewModel.class).getDiagnosisInfo(bean,mContext);
            }
        });

        binding.settingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, DiagnosisUploadLogActivity.class));
            }
        });
    }
}

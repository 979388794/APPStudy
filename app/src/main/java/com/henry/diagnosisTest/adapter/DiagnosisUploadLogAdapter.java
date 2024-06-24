package com.henry.diagnosisTest.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;

import androidx.databinding.ObservableArrayList;

import com.henry.basic.R;
import com.henry.basic.databinding.ItemKklogUploadBinding;
import com.henry.diagnosisTest.base.BaseBindingAdapter;
import com.henry.diagnosisTest.model.DiagnosisKkLogBean;
import com.quectel.communication.util.LogUtils;


public class DiagnosisUploadLogAdapter extends BaseBindingAdapter<DiagnosisKkLogBean, ItemKklogUploadBinding> {

    public DiagnosisUploadLogAdapter(Context context) {
        super(context);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.item_kklog_upload;
    }

    @Override
    public ObservableArrayList<DiagnosisKkLogBean> getItems() {
        if (null == items) {
            items = new ObservableArrayList<>();
        }
        return items;
    }

    @Override
    protected void onBindItem(ItemKklogUploadBinding binding, DiagnosisKkLogBean bean, int position) {
        ObservableArrayList<DiagnosisKkLogBean> items = getItems();
        int size = items.size();
        LogUtils.d("DiagnosisUploadLogAdapter","size = " + size);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(String.valueOf(size));
        spannableStringBuilder.append(bean.toString());
        binding.textView.setText(spannableStringBuilder.toString());
    }

}

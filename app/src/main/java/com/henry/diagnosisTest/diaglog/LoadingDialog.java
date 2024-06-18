package com.henry.diagnosisTest.diaglog;




import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.henry.basic.R;
import com.henry.basic.databinding.DialogLoadingBinding;
import com.henry.diagnosisTest.utils.DensityUtil;


public class LoadingDialog extends BaseDialog {
    /**
     * LoadingDialog在FragmentManager中的标志
     */
    private static final String TAG = LoadingDialog.class.getName();

    /**
     * 提示语的标志
     */
    private static final String TAG_PROMPT = "tag_prompt";

    /**
     * 页面持有类
     */
    private DialogLoadingBinding binding;

    /**
     * 静态构造方法
     *
     * @param prompt 提示语
     * @return LoadingDialog的实例
     */
    public static LoadingDialog newInstance(@NonNull String prompt) {
        LoadingDialog dialog = new LoadingDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PROMPT, prompt);
        dialog.setArguments(bundle);
        dialog.setCancelable(false);
        return dialog;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final android.app.Dialog dialog = new android.app.Dialog(getContext());
        if (dialog.getWindow() != null) {
            dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = DensityUtil.dip2px(getContext(), 180);
            lp.height = DensityUtil.dip2px(getContext(), 180);
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setDimAmount(0f);
        }

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_loading, container, false);
        binding.pbLoading.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.white),
                PorterDuff.Mode.MULTIPLY);
        return binding.getRoot();

    }


    @Override
    public void onStart() {
        super.onStart();
        initViews();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 初始化
     */
    private void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String prompt = bundle.getString(TAG_PROMPT);
            if (TextUtils.isEmpty(prompt)) {
                binding.tvLoading.setText(getString(R.string.loading));
            } else {
                binding.tvLoading.setText(prompt);
            }

        }
    }


    /**
     * 隐藏加载框
     */
    public void dismiss() {
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
        dismissDialog(TAG);
    }


    /**
     * 弹出加载框
     *
     * @param fragmentManager 管理器
     */
    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}

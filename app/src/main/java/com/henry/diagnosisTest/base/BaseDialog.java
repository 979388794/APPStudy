package com.henry.diagnosisTest.base;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.utils.DensityUtils;


public class BaseDialog extends DialogFragment {
    /**
     * BaseActivity的引用
     */
    private BaseActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
            this.mActivity.onFragmentAttached();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
            //铺满全屏
            dialog.getWindow().getDecorView().setPadding(DensityUtils.dip2px(getContext(), 16), 0,
                    DensityUtils.dip2px(getContext(), 16), 0);
        }
        return dialog;
    }

    /**
     * 显示弹出框
     *
     * @param fragmentManager Fragment的管理器
     * @param tag             每个fragment的标志
     */
    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        show(transaction, tag);
    }

    /**
     * 根据标志来隐藏dialog
     *
     * @param tag 标志
     */
    public void dismissDialog(String tag) {
        dismissAllowingStateLoss();
        if (getBaseActivity() != null) {
            getBaseActivity().onFragmentDetached(tag);
        }
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }
}

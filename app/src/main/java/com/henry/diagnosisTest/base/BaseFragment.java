package com.henry.diagnosisTest.base;

/**
 * @author: henry.xue
 * @date: 2024-06-17
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.henry.diagnosisTest.diaglog.LoadingDialog;

import java.lang.ref.WeakReference;


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment
{
    /**
     * BaseActivity的引用
     */
    private BaseActivity baseActivity;

    /**
     * 根布局
     */
    private View rootView;

    /**
     * ViewDataBinding 的子类
     */
    private T viewDataBinding;

    /**
     * ViewModel 的子类
     */
    private V viewModel;
    /**
     * 加载框
     */
    private LoadingDialog loadingDialog;
    private boolean isShowed = false;
    private LocalHandler mLocalHandler;
    /**
     * 复写获取BR ID 的方法
     *
     * @return 变量的BR ID
     */
    public abstract int getBindingVariable();

    /**
     * 获取布局文件的资源ID
     *
     * @return 布局文件的资源ID
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 获取ViewModel 的子类
     *
     * @return ViewModel 的子类
     */
    public abstract V getViewModel();

    /**
     * 返回子类的viewDataBinding
     *
     * @return 子类的viewDataBinding
     */
    public T getViewDataBinding() {
        return viewDataBinding;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.baseActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel();
        mLocalHandler = new LocalHandler(this);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        rootView = viewDataBinding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.setVariable(getBindingVariable(), viewModel);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.baseActivity = null;
        if (viewModel != null) {
            viewModel.onCleared();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideLoading();
    }

    protected BaseActivity getBaseActivity(){
        return this.baseActivity;
    }

    /**
     * 弹出加载框
     *
     * @param prompt 提示语
     */
    protected void showLoading(@NonNull String prompt) {
        Log.d("BaseFragment","showLoading  = " + isShowed);
        if (isShowed){
            return;
        }
        hideLoading();
        if (null != mLocalHandler){
            mLocalHandler.removeCallbacksAndMessages(null);
        }
        loadingDialog = LoadingDialog.newInstance("");
        loadingDialog.show(getChildFragmentManager());
        isShowed = true;
        if (null != mLocalHandler){
            mLocalHandler.sendEmptyMessageDelayed(10012,6*1000L);
        }
    }

    /**
     * 隐藏弹出框
     */
    protected void hideLoading() {
        if (loadingDialog != null) {
            Log.d("BaseFragment","hideLoading = " + isShowed);
            isShowed = false;
            loadingDialog.dismiss();
            loadingDialog = null;
            if (null != mLocalHandler){
                mLocalHandler.removeCallbacksAndMessages(null);
            }
        }
    }


    /**
     * 定义生命周期回调
     */
    public interface Callback {

        /**
         * Fragment Attached
         * fragment
         */
        void onFragmentAttached();

        /**
         * fragment Detached
         *
         * @param tag 每个fragment的标志
         */
        void onFragmentDetached(String tag);
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.onCleared();
            viewModel = null;
        }
        if (viewDataBinding != null) {
            Log.e("BaseFragment", " viewDataBinding.unbind()");
            viewDataBinding.unbind();
            viewDataBinding = null;
        }
        super.onDestroy();
    }

    private static class LocalHandler extends Handler {

        private BaseFragment baseFragment;

        private LocalHandler(BaseFragment fragment){
            baseFragment = new WeakReference<>(fragment).get();
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (null != baseFragment){
                if (msg.what == 10012){
                    baseFragment.hideLoading();
                }
            }
        }
    }

}

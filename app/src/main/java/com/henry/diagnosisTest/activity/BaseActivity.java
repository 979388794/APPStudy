package com.henry.diagnosisTest.activity;

/**
 * @author: henry.xue
 * @date: 2024-06-17
 */


import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.henry.diagnosisTest.fragment.BaseFragment;
import com.henry.diagnosisTest.viewMdodel.BaseViewModel;
import com.henry.diagnosisTest.diaglog.LoadingDialog;

import java.lang.ref.WeakReference;
import java.util.Objects;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseFragment.Callback {
    /**
     * ViewDataBinding 的子类
     */
    private T viewDataBinding;
    /**
     * ViewModel 的子类
     */
    protected V viewModel;

    /**
     * 加载框
     */
    private LoadingDialog loadingDialog;
    /**
     * 空页面提示语
     */
    private TextView tvEmpty;
    /**
     * 空页面按钮
     */
    private Button btnEmpty;

    public SharedPreferences sp;
    public SharedPreferences.Editor ed;
    public LocalHandler mLocalHandler;
    private boolean isShowed = false;

    public String TAG = getClass().getSimpleName();

    private static final String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mLocalHandler = new LocalHandler(this);
        performDataBinding();
        // PushManager.getInstance().bindPushAccount();
        initView();
        initEvent();
        sp = this.getSharedPreferences("app_setting_data", MODE_PRIVATE);
        ed = sp.edit();
    }


    protected void initView() {

    }

    protected void initEvent() {

    }

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
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }


    /**
     * 数据与界面绑定前的准备操作
     */
    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        if (viewModel == null) {
            viewModel = getViewModel();
        }
        Objects.requireNonNull(viewDataBinding);
        /**
         *
         */
        viewDataBinding.setVariable(getBindingVariable(), viewModel);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.executePendingBindings();
    }

    /**
     * 弹出加载框
     *
     * @param prompt 提示语
     */
    protected void showLoading(@NonNull String prompt) {
        if (isShowed) {
            return;
        }
        hideLoading();
        if (null != mLocalHandler) {
            //清空当前Handler队列所有消息
            mLocalHandler.removeCallbacksAndMessages(null);
        }
        loadingDialog = LoadingDialog.newInstance("");
        loadingDialog.show(getSupportFragmentManager());
        isShowed = true;
        if (null != mLocalHandler) {
            mLocalHandler.sendEmptyMessageDelayed(10011, 6 * 1000L);
        }
    }

    /**
     * 隐藏弹出框
     */
    protected void hideLoading() {
        if (loadingDialog != null) {
            Log.d("BaseActivity", "hideLoading = " + isShowed);
            loadingDialog.dismiss();
            loadingDialog = null;
            isShowed = false;
            if (null != mLocalHandler) {
                //清空当前Handler队列所有消息
                mLocalHandler.removeCallbacksAndMessages(null);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    this.finish();
                    return;
                }
            }
        }
    }

    /**
     * onStart()中检查权限
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkPermission(this);
    }

    /**
     * onStop()中隐藏弹出框
     */
    @Override
    protected void onStop() {
        super.onStop();
        hideLoading();
    }

    /**
     * 界面销毁时清空数据
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("BaseActivity", "onDestroy");
        if (viewModel != null) {
            viewModel.onCleared();
            viewModel = null;
        }
        if (viewDataBinding != null) {
            Log.e("BaseActivity", " viewDataBinding.unbind()");
            viewDataBinding.unbind();
            viewDataBinding = null;
        }

        if (btnEmpty != null) {
            btnEmpty = null;
        }
        if (tvEmpty != null) {
            tvEmpty = null;
        }
    }


    /**
     * 遍历读写权限
     */
    public static boolean isPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (permissions.length > 0) {
                for (String str : permissions) {
                    int checkPermission = ContextCompat.checkSelfPermission(activity, str);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        return true;
    }


    /**
     * 检查、授予权限
     *
     * @param activity
     * @return
     */
    public static boolean checkPermission(Activity activity) {
        if (isPermissionGranted(activity)) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, permissions, 0);
            return false;
        }
    }

    /**
     * 实现了一个继承自 Handler 类的静态内部类 LocalHandler，
     * 用于处理在消息队列中传递的消息，并根据特定条件在 BaseActivity 实例中隐藏加载中的 UI。
     * 使用弱引用可以避免内存泄漏问题，保证代码的健壮性。
     */
    private static class LocalHandler extends Handler {
        private BaseActivity activity;

        private LocalHandler(BaseActivity baseActivity) {
            activity = new WeakReference<>(baseActivity).get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (null != activity) {
                if (msg.what == 10011) {
                    activity.hideLoading();
                }
            }
        }
    }


    public void logd(String str) {
        Log.d(TAG, str);
    }

}

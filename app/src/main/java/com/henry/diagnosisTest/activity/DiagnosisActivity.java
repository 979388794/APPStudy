package com.henry.diagnosisTest.activity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosisBinding;
import com.henry.diagnosisTest.inter.DiagnosisMainNav;
import com.henry.diagnosisTest.viewMdodel.DiagnosisMainViewModel;

public class DiagnosisActivity extends BaseActivity<ActivityDiagnosisBinding, DiagnosisMainViewModel> implements DiagnosisMainNav {
    private DiagnosisMainViewModel viewModel;

    String Tag = getClass().getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosis;
    }

    @Override
    public DiagnosisMainViewModel getViewModel() {
        viewModel = new ViewModelProvider(this).get(DiagnosisMainViewModel.class);
        viewModel.setNavigator(this);
        viewModel.getDiagnosisModuleList(getApplicationContext());
        return viewModel;
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        getViewDataBinding().btMainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
            }
        });
    }


    @Override
    public void showLoading() {
        super.showLoading(Tag);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }


    @Override
    public void initRecycleView() {

    }
}
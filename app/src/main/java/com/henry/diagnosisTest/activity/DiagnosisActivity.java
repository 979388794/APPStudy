package com.henry.diagnosisTest.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosisBinding;
import com.henry.diagnosisTest.adapter.DiagnosisModuleRecyclerAdapter;
import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.inter.DiagnosisMainNav;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.viewMdodel.DiagnosisMainViewModel;

import java.util.ArrayList;

public class DiagnosisActivity extends BaseActivity<ActivityDiagnosisBinding, DiagnosisMainViewModel> implements DiagnosisMainNav {
    private DiagnosisMainViewModel viewModel;
    private DiagnosisModuleRecyclerAdapter recyclerAdapter;
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
        /**
         * 设置回调 监听状态 更新livedata
         */
        viewModel.getDiagnosisModuleList(getApplicationContext());
        /**
         * 错误信息的UI Toast展示
         */
        viewModel.mErrorMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(Tag, "onChanged s = " + s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        /**
         * diagnosisModulelist 数据变化时 初始化RecycleView
         */
        viewModel.diagnosisModulelist.observe(this, new Observer<ArrayList<DiagnosisModule>>() {
            @Override
            public void onChanged(ArrayList<DiagnosisModule> diagnosisModules) {
                initRecycleView();
            }
        });
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
        RecyclerView recyclerView = getViewDataBinding().rvList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new DiagnosisModuleRecyclerAdapter(this);
        ArrayList<DiagnosisModule> diagnosisModuleList = viewModel.diagnosisModulelist.getValue();
        recyclerAdapter.getItems().addAll(diagnosisModuleList);
        recyclerAdapter.setOnItemClickListener((diagnosisInfo, positionAdapter) -> {
            //TODO 点击查看详情
            if (diagnosisInfo.isShowInfo()) {
//                Intent intent = new Intent(this, DiagnosisDetailActivity.class);
//                intent.putExtra("data", diagnosisInfo.getInfoLists());
//                intent.putExtra("position", 0);
//                intent.putExtra("diagnosisModule", diagnosisModuleList.get(positionAdapter));
//                intent.putExtra("lv_data", liveData);
//                startActivity(intent);
            } else {
                if (diagnosisInfo.getStatusCode() == 2) {
                    Toast.makeText(this, "诊断数据获取失败,请查看连接！！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "请先诊断！！", Toast.LENGTH_LONG).show();
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != viewModel) {
            viewModel.resetListener();
        }
    }


}
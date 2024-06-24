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
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.viewMdodel.DiagnosisMainViewModel;
import com.quectel.communication.model.ResSerializableBean;
import com.quectel.communication.util.TimeUtils;

import java.util.ArrayList;

public class DiagnosisMainActivity extends BaseActivity<ActivityDiagnosisBinding, DiagnosisMainViewModel> implements DiagnosisMainNav {
    private DiagnosisMainViewModel viewModel;
    private DiagnosisModuleRecyclerAdapter recyclerAdapter;
    private ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> liveData = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
    public static String diagnosisTime;
    String TAG = getClass().getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.diagnosisMainViewModel;
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


        /**
         * listMutableLiveData 数据变化时 更新RecycleView的Adapter
         */
        viewModel.listMutableLiveData.observe(this, new Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>() {
            @Override
            public void onChanged(ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> resSerializableBeans) {
                Log.d(TAG, "resSerializableBeans = " + resSerializableBeans);
                if (null != resSerializableBeans && resSerializableBeans.size() > 0) {
                    liveData.clear();
                    liveData.add(resSerializableBeans.get(0));
                    recyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        /**
         * 一键诊断监听事件
         */
        getViewDataBinding().btDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diagnosisTime = TimeUtils.getCurrentDateTime();
                viewModel.getDiagnosisInfo(DiagnosisMainActivity.this);
            }
        });

        getViewDataBinding().ivSettings.setOnClickListener(v -> {
            DiagnosisMainActivity.this.startActivity(new Intent(DiagnosisMainActivity.this, DiagnosisUploadLogActivity.class));
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
    public void initRecycleView() {
        RecyclerView recyclerView = getViewDataBinding().rvList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new DiagnosisModuleRecyclerAdapter(this);
        ArrayList<DiagnosisModule> diagnosisModuleList = viewModel.diagnosisModulelist.getValue();
        recyclerAdapter.getItems().addAll(diagnosisModuleList);
        recyclerAdapter.setOnItemClickListener((diagnosisInfo, positionAdapter) -> {
            if (diagnosisInfo.isShowInfo()) {
                //todo 注释待删除
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
    public void showLoading() {
        super.showLoading(TAG);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != viewModel) {
            viewModel.resetListener();
        }
    }

}
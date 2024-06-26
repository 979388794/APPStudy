package com.henry.diagnosisTest.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosishistoryBinding;
import com.henry.diagnosisTest.adapter.HistoryDateDataAdapter;
import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.inter.DiagnosisHistoryNav;
import com.henry.diagnosisTest.listener.OnItemViewClickListener;
import com.henry.diagnosisTest.model.DiagnosisHistoryCatalogue;
import com.henry.diagnosisTest.model.DiagnosisHistoryItem;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.viewMdodel.DiagnosisHistoryViewModel;
import com.quectel.communication.model.ResSerializableBean;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.Objects;


public class DiagnosisHistoryActivity extends BaseActivity<ActivityDiagnosishistoryBinding, DiagnosisHistoryViewModel> implements DiagnosisHistoryNav, OnItemViewClickListener {

    private DiagnosisModule diagnosisModule;
    private DiagnosisHistoryViewModel diagnosisHistoryViewModel;
    private int page = 0;
    private HistoryDateDataAdapter recyclerAdapter;
    private ArrayList<DiagnosisHistoryCatalogue> historyRootData = new ArrayList<DiagnosisHistoryCatalogue>();
    private ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> hisItemData = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
    private static String his_catalogue = "catalogue";
    private static String his_item = "item";

    String TAG = getClass().getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.mDiagnosisHistoryViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosishistory;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        getViewDataBinding().btBack.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public DiagnosisHistoryViewModel getViewModel() {
        diagnosisModule = (DiagnosisModule) getIntent().getSerializableExtra("module");
        diagnosisHistoryViewModel = new ViewModelProvider(this).get(DiagnosisHistoryViewModel.class);
        diagnosisHistoryViewModel.setNavigator(this);
        Log.d(TAG, "getViewModel");
        diagnosisHistoryViewModel.getDiagnosisHistoryCatalogue(diagnosisModule, this, his_catalogue);

        diagnosisHistoryViewModel.listMutableLiveHisRootData.observe(this, new Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>>>() {
            @Override
            public void onChanged(ArrayList<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>> resSerializableBeans) {
                Log.d(TAG, "getViewModel onChanged resSerializableBeans = " + resSerializableBeans);
                if (null != resSerializableBeans && resSerializableBeans.size() > 0) {
                    loadData(resSerializableBeans.get(0).getData());
                    recyclerAdapter.setListData(historyRootData);
                    recyclerAdapter.notifyDataSetChanged();
                    getViewDataBinding().tvEmpty.setVisibility(View.GONE);
                } else {
                    getViewDataBinding().tvEmpty.setVisibility(View.VISIBLE);
                }
            }
        });

        diagnosisHistoryViewModel.mErrorMsg.observe(this, (mErrorMsg) -> {
            Log.w(TAG, "error:" + mErrorMsg);
            getViewDataBinding().tvEmpty.setVisibility(View.VISIBLE);
//            if(getViewDataBinding() != null) {
//                getViewDataBinding().rvList.setPullLoadMoreCompleted();
//            }
        });

        initRecycleView();
        return diagnosisHistoryViewModel;
    }

    @Override
    public void initRecycleView() {
        PullLoadMoreRecyclerView recyclerView = null;
        if (getViewDataBinding() != null) {
            recyclerView = getViewDataBinding().rvList;
        }
        Objects.requireNonNull(recyclerView);
        recyclerView.setLinearLayout();
        recyclerAdapter = new HistoryDateDataAdapter(historyRootData);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setPullRefreshEnable(false);
        recyclerAdapter.setOnItemViewClickListener(DiagnosisHistoryActivity.this);
    }

    @Override
    public void loadData(ArrayList<DiagnosisHistoryCatalogue> diagnosisHistoryRootDatas) {
        if (historyRootData.size() == 0) {
            historyRootData.clear();
            historyRootData = diagnosisHistoryRootDatas;
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showLoading() {
        super.showLoading("DiagnosisHistoryActivity");
    }

    private Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> hisItemObs = new Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>() {
        @Override
        public void onChanged(ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> diagnosisHistoryInfos) {
            if (hisItemData.size() == 0) {
                hisItemData.clear();
                hisItemData = diagnosisHistoryInfos;
            }
            if (hisItemData.size() != 0) {
                //二级目录下，条目点击跳转
                //Intent intent = new Intent();
                //intent.setClass(DiagnosisHistoryActivity.this, DiagnosisViewpagerDetailActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.putExtra("history", hisItemData);
                //startActivity(intent);

                Intent intent = new Intent(DiagnosisHistoryActivity.this, DiagnosisDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("history", hisItemData);
                intent.putExtra("data", hisItemData.get(0).getData());
                intent.putExtra("position", 0);
                intent.putExtra("diagnosisModule", diagnosisModule);
                intent.putExtra("lv_data", hisItemData);
                intent.putExtra("is_history", true);
                startActivity(intent);
            }
        }
    };

    @Override
    public void setOnViewClickListener(View view, int position, DiagnosisHistoryItem city) {
        diagnosisHistoryViewModel.getDiagnosisHistoryItem(diagnosisModule, this, his_item, city.getParentName() + city.getTime());
        diagnosisHistoryViewModel.listMutableLiveHisInfoData.observe(this, hisItemObs);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != diagnosisHistoryViewModel) {
            diagnosisHistoryViewModel.resetListener();
        }
    }
}

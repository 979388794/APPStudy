package com.henry.diagnosisTest.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosisEventBinding;
import com.henry.diagnosisTest.adapter.DiagnosisEventDateDataAdapter;
import com.henry.diagnosisTest.adapter.EventExpandenbleItemDecoration;
import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.inter.DiagnosisEventsNav;
import com.henry.diagnosisTest.listener.OnEventsItemViewClickListener;
import com.henry.diagnosisTest.model.DiagnosisEvent;
import com.henry.diagnosisTest.model.DiagnosisEventRoot;
import com.henry.diagnosisTest.model.DiagnosisEventsItem;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.viewMdodel.DiagnosisEventsViewModel;
import com.quectel.communication.model.ResSerializableBean;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class DiagnosisEventsActivity extends BaseActivity<ActivityDiagnosisEventBinding, DiagnosisEventsViewModel> implements DiagnosisEventsNav, OnEventsItemViewClickListener {

    private DiagnosisEventsViewModel mDiagnosisEventsViewModel;
    private DiagnosisModule mDiagnosisModule;
    private DiagnosisEventDateDataAdapter mEventDateDataAdapter;
    private ArrayList<DiagnosisEvent> mEventsRootList = new ArrayList<>();
    private EventExpandenbleItemDecoration mExpandenbleItemDecoration;

    String TAG = getClass().getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.mDiagnosisEventsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosis_event;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        getViewDataBinding().btBackEvent.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public DiagnosisEventsViewModel getViewModel() {
        logd("getViewModel:DiagnosisEventsViewModel");
        mDiagnosisModule = (DiagnosisModule) getIntent().getSerializableExtra("module");
        mDiagnosisEventsViewModel = new ViewModelProvider(this).get(DiagnosisEventsViewModel.class);
        mDiagnosisEventsViewModel.setNavigator(this);
        //获取数据
        mDiagnosisEventsViewModel.getDiagnosisEvents(mDiagnosisModule, this);
        //数据变化监听
        mDiagnosisEventsViewModel.eventRootData.observe(this, new Observer<ResSerializableBean<ArrayList<DiagnosisEventRoot>>>() {
            @Override
            public void onChanged(ResSerializableBean<ArrayList<DiagnosisEventRoot>> diagnosisEventRootResSerializableBean) {
                ArrayList<DiagnosisEventRoot> dataList = diagnosisEventRootResSerializableBean.getData();
                if (dataList != null && dataList.size() > 0) {
                    ArrayList<DiagnosisEvent> eventsList = new ArrayList<>();
                    for (DiagnosisEventRoot diagnosisEventRoot : dataList) {
                        eventsList.addAll(diagnosisEventRoot.getEvents());
                    }
                    loadData(eventsList);
                    mEventDateDataAdapter.setEventsRootData(eventsList);
                    mEventDateDataAdapter.notifyDataSetChanged();
                    getViewDataBinding().tvEmpty.setVisibility(View.GONE);
                } else {
                    getViewDataBinding().tvEmpty.setVisibility(View.VISIBLE);
                }
                /*
                DiagnosisEventRoot data = diagnosisEventRootResSerializableBean.getData();
                Log.d(TAG,"onChanged data= " + data);
                Log.d(TAG,"onChanged data getEvents= " + data.getEvents());
                List<DiagnosisEvent> events = data.getEvents();
                if (null != diagnosisEventRootResSerializableBean && null != events && events.size() > 0) {
                    loadData(data);
                    mEventDateDataAdapter.setEventsRootData((ArrayList<DiagnosisEvent>) data.getEvents());
                    mEventDateDataAdapter.notifyDataSetChanged();
                    hideLoading();
                }*/
            }
        });

        mDiagnosisEventsViewModel.mErrorMsg.observe(this, (mErrorMsg) -> {
            Log.w(TAG, "error:" + mErrorMsg);
            getViewDataBinding().tvEmpty.setVisibility(View.VISIBLE);
        });
        initRecycleView();
        return mDiagnosisEventsViewModel;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showLoading() {
        super.showLoading(TAG);
    }

    @Override
    public void initRecycleView() {
        PullLoadMoreRecyclerView recyclerView = null;
        if (getViewDataBinding() != null) {
            recyclerView = getViewDataBinding().rvListEvent;
        }
        Objects.requireNonNull(recyclerView);
        recyclerView.setLinearLayout();
        mEventDateDataAdapter = new DiagnosisEventDateDataAdapter(mEventsRootList);
        mEventDateDataAdapter.setOnViewClickListener(this);
        mExpandenbleItemDecoration = new EventExpandenbleItemDecoration(mEventDateDataAdapter.getObjects(), this);
        mEventDateDataAdapter.setOnUIChangeListener(mExpandenbleItemDecoration);
        recyclerView.addItemDecoration(mExpandenbleItemDecoration);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setPullRefreshEnable(false);
        recyclerView.setAdapter(mEventDateDataAdapter);
    }

    @Override
    public void loadData(ArrayList<DiagnosisEvent> eventArrayList) {
        if (null != mEventsRootList) {
            mEventsRootList.clear();
            mEventsRootList.addAll(eventArrayList);
        }
    }

    private ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> eventItemData = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
    private Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> eventItemObs = new Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>() {
        @Override
        public void onChanged(ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> diagnosisHistoryInfos) {
            if (eventItemData.size() != 0) {
                eventItemData.clear();
            }
            eventItemData = diagnosisHistoryInfos;
            if (eventItemData.size() != 0) {
                //二级目录下，条目点击跳转
                //Intent intent = new Intent();
                //intent.setClass(DiagnosisEventsActivity.this, DiagnosisViewpagerDetailActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.putExtra("history", eventItemData);
                //startActivity(intent);

                Intent intent = new Intent(DiagnosisEventsActivity.this, DiagnosisDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("data", eventItemData.get(0).getData());
                intent.putExtra("position", 0);
                intent.putExtra("diagnosisModule", mDiagnosisModule);
                intent.putExtra("lv_data", eventItemData);
                intent.putExtra("is_history", true);
                startActivity(intent);
            }
        }
    };

    @Override
    public void setOnViewClickListener(View view, int position, DiagnosisEventsItem item) {
        String reason = item.getReason();
        if ("Network".equals(reason)) {
            Toast.makeText(this, "当前类型不可诊断！！！", Toast.LENGTH_LONG).show();
            return;
        }
        String dayTime = item.getParentName();
        String time = item.getTime();
        Log.d(TAG, "setOnViewClickListener " + (dayTime + time));
        String dateTime = dayTime + time;
        mDiagnosisEventsViewModel.getDiagnosisEventsItem(mDiagnosisModule, this, dateTime);
        mDiagnosisEventsViewModel.eventItemData.observe(this, eventItemObs);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mDiagnosisEventsViewModel) {
            mDiagnosisEventsViewModel.resetListener();
        }
    }
}

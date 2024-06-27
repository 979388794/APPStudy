package com.henry.diagnosisTest.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import com.henry.basic.BR;
import com.henry.basic.MyApplication;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosisviewpagerdetailBinding;
import com.henry.diagnosisTest.adapter.DiagnosisCardPagerAdapter;
import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.inter.DiagnosisViewpagerDetailNav;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.pageTransformer.ShadowTransformer;
import com.henry.diagnosisTest.viewMdodel.DiagnosisViewpagerDetailViewModel;
import com.quectel.communication.model.ResSerializableBean;
import com.quectel.communication.util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DiagnosisViewpagerDetailActivity extends BaseActivity<ActivityDiagnosisviewpagerdetailBinding, DiagnosisViewpagerDetailViewModel> implements DiagnosisViewpagerDetailNav {

    private DiagnosisViewpagerDetailViewModel viewModel;
    private DiagnosisModule diagnosisModule;
    private ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> liveData = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
    private Timer timer = null;
    private RequestTimerTask task = null;
    DiagnosisCardPagerAdapter diagnosisCardPagerAdapter;
    private boolean isDiagRunning = false;
    private long startTime = 0;
    private long endTime = 0;
    String TAG=getClass().getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.diagnosisViewpagerDetailViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosisviewpagerdetail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
        diagnosisModule = (DiagnosisModule) getIntent().getSerializableExtra("module");
        ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> diagnosisHistoryInfo = (ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>) getIntent().getSerializableExtra("history");
        if (diagnosisModule != null) {
            initViewPager((List<DiagnosisInfoList>) getIntent().getSerializableExtra("data"));
            getViewDataBinding().btHistory.setVisibility(diagnosisModule.hasHistroy() ? View.VISIBLE : View.GONE);
            getViewDataBinding().btHistory.setOnClickListener((v) -> {
                Intent intent = new Intent(this, DiagnosisHistoryActivity.class);
                intent.putExtra("module", diagnosisModule);
                startActivity(intent);
            });

            getViewDataBinding().btEventInfo.setVisibility(diagnosisModule.hasEvent() ? View.VISIBLE : View.GONE);
            getViewDataBinding().btEventInfo.setOnClickListener((v) -> {
                Intent intent = new Intent(this, DiagnosisEventsActivity.class);
                intent.putExtra("module", diagnosisModule);
                startActivity(intent);
            });
        } else {
            initViewPager(diagnosisHistoryInfo.get(0).getData());
            getViewDataBinding().btHistory.setVisibility(View.GONE);
            getViewDataBinding().tvTime.setVisibility(View.GONE);
            getViewDataBinding().btPull.setVisibility(View.GONE);
            getViewDataBinding().btPush.setVisibility(View.GONE);
        }
        getViewDataBinding().btBack.setOnClickListener((v) -> {
            finish();
        });
        if(diagnosisModule != null) {
            liveData.add(((ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>) getIntent().getSerializableExtra("lv_data")).get(0));
            updateView();
        }else{
            getViewDataBinding().tvTime.setText(DiagnosisMainActivity.diagnosisTime);
        }
        getViewDataBinding().btPull.setOnClickListener((v)->{
            //TODO  一键生成报告
            String fileName = liveData.get(0).getDiag_id()+".json";
            boolean is = FileUtils.writeJson(v.getContext(),new Gson().toJson(liveData),fileName);
            if(is) {
                //Toast.makeText(v.getContext(), "报告已经生成", Toast.LENGTH_SHORT).show();
                //TODO 显示报告位置
                getViewDataBinding().tvLocation.setText("一键生成报告本地位置：" +getApplicationContext().getExternalFilesDir(null)+"/"+fileName);
            }else{
                //Toast.makeText(v.getContext(), "报告未生成,请检查", Toast.LENGTH_SHORT).show();
                //TODO 报告位置失败
                getViewDataBinding().tvLocation.setTextColor(Color.argb(255, 255, 0, 0)); // 红色
                getViewDataBinding().tvLocation.setText("一键生成报告失败");
            }
        });
        getViewDataBinding().btPush.setOnClickListener((v)->{
            //TODO 上传报告
        });
    }

    @Override
    public DiagnosisViewpagerDetailViewModel getViewModel() {
        viewModel = new ViewModelProvider(this).get(DiagnosisViewpagerDetailViewModel.class);
        viewModel.setNavigator(this);
        viewModel.getDiagnosisModuleList(getApplicationContext());
        viewModel.mErrorMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.diagnosisModulelist.observe(this, new Observer<ArrayList<DiagnosisModule>>() {
            @Override
            public void onChanged(ArrayList<DiagnosisModule> diagnosisModules) {
            }
        });

        viewModel.listMutableLiveData.observe(this, new Observer<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>() {
            @Override
            public void onChanged(ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> resSerializableBeans) {
                if (null != resSerializableBeans && resSerializableBeans.size() > 0){
                    liveData.clear();
                    liveData.add(resSerializableBeans.get(0));
                    if(diagnosisCardPagerAdapter != null){
                        int currentItem = getViewDataBinding().vpContent.getCurrentItem();
                        if(currentItem < 0){
                            currentItem = 0;
                        }
                        diagnosisCardPagerAdapter.setData(liveData.get(0).getData());
                        getViewDataBinding().vpContent.setAdapter(diagnosisCardPagerAdapter);
                        ShadowTransformer shadowTransformer = new ShadowTransformer(getViewDataBinding().vpContent, diagnosisCardPagerAdapter);
                        getViewDataBinding().vpContent.setPageTransformer(false, shadowTransformer);
                        getViewDataBinding().vpContent.setOffscreenPageLimit(30);
                        getViewDataBinding().vpContent.setPageMargin(32);
                        getViewDataBinding().vpContent.setCurrentItem(currentItem);
                    }
                }
                updateView();
            }
        });
        return viewModel;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showLoading() {
        super.showLoading("DiagnosisViewpagerDetailActivity");
    }

    @Override
    public void initViewPager(List<DiagnosisInfoList> infoLists) {
        diagnosisCardPagerAdapter = new DiagnosisCardPagerAdapter(infoLists);
        getViewDataBinding().vpContent.setAdapter(diagnosisCardPagerAdapter);
        ShadowTransformer shadowTransformer = new ShadowTransformer(getViewDataBinding().vpContent, diagnosisCardPagerAdapter);
        getViewDataBinding().vpContent.setPageTransformer(false, shadowTransformer);
        getViewDataBinding().vpContent.setOffscreenPageLimit(30);
        getViewDataBinding().vpContent.setPageMargin(32);
        diagnosisCardPagerAdapter.setClickListener(new DiagnosisCardPagerAdapter.DiagnosisCardPagerClickListener() {
            @Override
            public void click(int position) {
                Intent intent = new Intent(getApplicationContext(), DiagnosisDetailActivity.class);
                intent.putExtra("data", (ArrayList) infoLists);
                intent.putExtra("position", position);
                intent.putExtra("diagnosisModule", diagnosisModule);
                startActivity(intent);
            }
        });
    }

    public void updateView(){
        if(liveData != null && liveData.get(0) != null){
            getViewDataBinding().tvTime.setText(DiagnosisMainActivity.diagnosisTime);
        }else{
            getViewDataBinding().tvTime.setText("");
        }
        if(liveData != null && liveData.get(0) != null && liveData.get(0).getLocation() != null){
            getViewDataBinding().tvJingdu.setText(String.format("%.3f",liveData.get(0).getLocation().getLongitude()));
            getViewDataBinding().tvWeidu.setText(String.format("%.3f",liveData.get(0).getLocation().getLatitude()));
            getViewDataBinding().tvGaodu.setText(String.format("%.3f",liveData.get(0).getLocation().getAltitude()));
        }else{
            getViewDataBinding().tvJingdu.setText("0.0");
            getViewDataBinding().tvWeidu.setText("0.0");
            getViewDataBinding().tvGaodu.setText("0.0");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String diag = this.getResources().getStringArray(R.array.diag_options_values)[sp.getInt("diag_value",0)];
        Log.d(TAG,"diag = "  + diag);
        if(sp.getBoolean("diag_checked_statue",false)) {
            isDiagRunning = true;
            startTimerTask(Long.valueOf(diag));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        if(isDiagRunning) {
            isDiagRunning = false;
            cancelTimerTask();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop cancelTimerTask");
        if(isDiagRunning) {
            cancelTimerTask();
            isDiagRunning = false;
        }
        if(null != viewModel){
            viewModel.resetListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startTimerTask(long diag){
        if(timer == null){
            timer = new Timer();
        }
        if(timer != null && task != null){
            task.cancel();
        }
        task = new RequestTimerTask();
        endTime = System.currentTimeMillis();
        long delayTime = endTime - startTime;
        Log.d(TAG,"startTimerTask delayTime = " + delayTime);
        Log.d(TAG,"startTimerTask isFirsted = " + MyApplication.isFirsted);
        Log.d(TAG,"startTimerTask diag - delayTime = " + (diag -delayTime));
        if (MyApplication.isFirsted){
            MyApplication.isFirsted = false;
            timer.schedule(task,(diag -delayTime),diag);
        }else {
            timer.schedule(task,0,diag);
        }

    }

    public void cancelTimerTask(){
        MyApplication.isFirsted = true;
        if(timer != null){
            task.cancel();
            timer.cancel();
            timer = null;
        }
    }

    public class RequestTimerTask  extends  TimerTask{
        @Override
        public void run() {
            Log.d(TAG,"RequestTimerTask");
            viewModel.getDiagnosisInfo(diagnosisModule,DiagnosisViewpagerDetailActivity.this);
        }
    }

}

package com.henry.diagnosisTest.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosisdetailBinding;
import com.henry.diagnosisTest.adapter.DiagnosisPagerAdapter;
import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.fragment.DiagnosisFragment;
import com.henry.diagnosisTest.inter.DiagnosisDetailNav;
import com.henry.diagnosisTest.model.DiagnosisInfo;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.FormatChangeUtils;
import com.henry.diagnosisTest.viewMdodel.DiagnosisDetailViewModel;
import com.quectel.communication.model.ResSerializableBean;
import com.quectel.communication.util.FileUtils;


import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;


public class DiagnosisDetailActivity extends BaseActivity<ActivityDiagnosisdetailBinding, DiagnosisDetailViewModel> implements DiagnosisDetailNav {
    private DiagnosisDetailViewModel viewModel;
    DiagnosisPagerAdapter pagerAdapter;
    private boolean isSupport = true;

    private ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> liveData = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();

    public final static String jsonFilePath = "json_file_path";

    String TAG = getClass().getSimpleName();
    private String fileName;

    @Override
    public int getBindingVariable() {
        return BR.diagnosisMainViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosisdetail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //if(liveData == null) {
        //    liveData.add(((ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>) getIntent().getSerializableExtra("lv_data")).get(0));
        updateView();
        //}
        String newFileName = sp.getString(jsonFilePath, fileName);
        Log.d(TAG, "onCreate: lemon " + newFileName);
        if (newFileName != null && !newFileName.isEmpty()) {
            getViewDataBinding().tvFileLocation.setVisibility(View.VISIBLE);
            getViewDataBinding().tvFileLocation.setText("生成本地报告位置：" + newFileName);
        } else getViewDataBinding().tvFileLocation.setVisibility(View.GONE);
        getViewDataBinding().btnLocalReport.setOnClickListener((v) -> {
            //TODO  一键生成报告
            fileName = liveData.get(0).getDiag_id() + ".json";
            boolean is = FileUtils.writeJson(v.getContext(), new Gson().toJson(liveData), fileName);
            if (is) {
                getViewDataBinding().tvFileLocation.setVisibility(View.VISIBLE);
                //Toast.makeText(v.getContext(), "报告已经生成", Toast.LENGTH_SHORT).show();
                //TODO 显示报告位置
                String filePath = getApplicationContext().getExternalFilesDir(null) + "/" + fileName;
                getViewDataBinding().tvFileLocation.setText("生成本地报告位置：" + filePath);
                ed.putString(jsonFilePath, filePath).apply();
            } else {
                //Toast.makeText(v.getContext(), "报告未生成,请检查", Toast.LENGTH_SHORT).show();
                //TODO 报告位置失败
                getViewDataBinding().tvFileLocation.setVisibility(View.GONE);
                //getViewDataBinding().tvFileLocation.setTextColor(Color.argb(255, 255, 0, 0)); // 红色
                //getViewDataBinding().tvFileLocation.setText("一键生成报告失败");
            }
        });

        getViewDataBinding().btnCloudReport.setOnClickListener((v) -> {
            //TODO 上传报告
            if (isSupport) {
                getViewDataBinding().btnCloudReport.setAlpha(0.5f);
                getViewDataBinding().btnCloudReport.setText("暂不支持上传");
                isSupport = false;
                Toast.makeText(getApplicationContext(), "暂不支持上传.", Toast.LENGTH_LONG).show(); //增加toast
            } else {
                isSupport = true;
                getViewDataBinding().btnCloudReport.setAlpha(1.0f);
                getViewDataBinding().btnCloudReport.setText("生成云端报告");
            }
        });
    }

    @Override
    public DiagnosisDetailViewModel getViewModel() {
        liveData.add(((ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>) getIntent().getSerializableExtra("lv_data")).get(0));
        ArrayList<DiagnosisInfoList> data = (ArrayList<DiagnosisInfoList>) getIntent().getSerializableExtra("data");
        //todo 数据变更
        for (DiagnosisInfoList datum : data) {
            ArrayList<DiagnosisInfo> diagnosisContentList = datum.getDiagnosisContentList();
            for (DiagnosisInfo diagnosisInfo : diagnosisContentList) {
                String diagnosisStatusInfo = diagnosisInfo.getDiagnosisStatusInfo();
                String formatChange = FormatChangeUtils.formatChange(diagnosisStatusInfo);
                diagnosisInfo.setDiagnosisStatusInfo(formatChange);
            }
        }
        int position = (int) getIntent().getSerializableExtra("position");
        DiagnosisModule diagnosisModule = (DiagnosisModule) getIntent().getSerializableExtra("diagnosisModule");
        viewModel = new ViewModelProvider(this).get(DiagnosisDetailViewModel.class);
        viewModel.listMutableLiveData.setValue(data);
        viewModel.diagnosisModule.setValue(diagnosisModule);
        if (getViewDataBinding() != null) {
            getViewDataBinding().btBack.setOnClickListener((v) -> finish());
            getViewDataBinding().btnDiagOc.setOnClickListener(v -> {
                Intent intent = new Intent(this, DiagnosisHistoryActivity.class);
                intent.putExtra("module", diagnosisModule);
                startActivity(intent);
            });
            getViewDataBinding().btnDiagError.setOnClickListener(v -> {
                Intent intent = new Intent(this, DiagnosisEventsActivity.class);
                intent.putExtra("module", diagnosisModule);
                startActivity(intent);
            });
        }
        initMagicIndicator(position, data);
        return viewModel;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showLoading() {
        super.showLoading("DiagnosisDetailActivity");
    }

    @Override
    public void initMagicIndicator(int position, List<DiagnosisInfoList> diagnosisInfoLists) {

        ArrayList<DiagnosisFragment> diagnosisFragments = new ArrayList<>();
        int index = 0;

        for (DiagnosisInfoList diagnosisInfoList : diagnosisInfoLists) {
            DiagnosisFragment item = new DiagnosisFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("POSITION", index++);
            item.setArguments(bundle);
            diagnosisFragments.add(item);
        }

        pagerAdapter = new DiagnosisPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT, diagnosisFragments);
        getViewDataBinding().vpContent.setAdapter(pagerAdapter);

        MagicIndicator magicIndicator = getViewDataBinding().magicIndicator;
        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return diagnosisInfoLists == null ? 0 : diagnosisInfoLists.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(diagnosisInfoLists.get(index).getDiagnosisName());
                clipPagerTitleView.setTextColor(Color.BLACK);//#f4ea2a
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getViewDataBinding().vpContent.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.common_navigator_height_new);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#a4adb3"));//#f4ea2a
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, getViewDataBinding().vpContent);
        getViewDataBinding().vpContent.setCurrentItem(position);
    }

    public void updateView() {
        if (liveData != null && liveData.get(0) != null && liveData.get(0).getLocation() != null) {
            Log.i("wangyong", "liveData != null " + liveData.size() + liveData.get(0).toString());
            getViewDataBinding().tvTopJingdu.setText(String.format("%.3f", liveData.get(0).getLocation().getLongitude())); //显示后两位
            getViewDataBinding().tvTopWeidu.setText(String.format("%.3f", liveData.get(0).getLocation().getLatitude()));
            getViewDataBinding().tvTopGaodu.setText(String.format("%.3f", liveData.get(0).getLocation().getAltitude()));
        } else {
            Log.i("wangyong", "liveData size : " + liveData.size());
            getViewDataBinding().tvTopJingdu.setText("0.0");
            getViewDataBinding().tvTopWeidu.setText("0.0");
            getViewDataBinding().tvTopGaodu.setText("0.0");
        }
    }
}

package com.henry.diagnosisTest.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.FragmentDiagnososBinding;
import com.henry.diagnosisTest.adapter.DiagnosisRecyclerAdapter;
import com.henry.diagnosisTest.base.BaseFragment;
import com.henry.diagnosisTest.inter.DiagnosisFragmentNav;
import com.henry.diagnosisTest.model.DiagnosisInfo;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.viewMdodel.DiagnosisDetailViewModel;
import com.henry.diagnosisTest.viewMdodel.DiagnosisFragmentViewModel;


public class DiagnosisFragment extends BaseFragment<FragmentDiagnososBinding, DiagnosisFragmentViewModel> implements DiagnosisFragmentNav {

    private DiagnosisDetailViewModel mDiagnosisMainViewModel;
    private int position;


    @Override
    public int getBindingVariable() {
        return BR.mDiagnosisFragmentViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_diagnosos;
    }

    @Override
    public DiagnosisFragmentViewModel getViewModel() {
        position = getArguments().getInt("POSITION");
        mDiagnosisMainViewModel = new ViewModelProvider(getBaseActivity()).get(DiagnosisDetailViewModel.class);
        DiagnosisFragmentViewModel mDiagnosisFragmentViewModel = new ViewModelProvider(this).get(DiagnosisFragmentViewModel.class);
        mDiagnosisFragmentViewModel.setNavigator(this);

        mDiagnosisFragmentViewModel.mDiagnosisInfo.observe(this, new Observer<DiagnosisInfo>() {
            @Override
            public void onChanged(DiagnosisInfo diagnosisInfo) {
                Toast.makeText(getActivity(), "修复成功", Toast.LENGTH_SHORT).show();
            }
        });
        mDiagnosisFragmentViewModel.mSuccessMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        return mDiagnosisFragmentViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void initRecycleView() {
        RecyclerView recyclerView = getViewDataBinding().rvList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DiagnosisRecyclerAdapter recyclerAdapter = new DiagnosisRecyclerAdapter(getContext());
        DiagnosisInfoList arrayListResSerializableBean = mDiagnosisMainViewModel.listMutableLiveData.getValue().get(position);
        recyclerAdapter.getItems().addAll(arrayListResSerializableBean.getDiagnosisContentList());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setDiagnosisItemClickListener(new DiagnosisRecyclerAdapter.DiagnosisItemClickListener() {
            @Override
            public void clickFix(int position, DiagnosisInfo bean) {
                getViewModel().FixDiagnosis(mDiagnosisMainViewModel.diagnosisModule.getValue(), getContext());
            }

            @Override
            public void clickUpload(int position, DiagnosisInfo bean) {
                getViewModel().UploadLogDiagnosis(mDiagnosisMainViewModel.diagnosisModule.getValue(), getContext());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != getViewModel()) {
            getViewModel().resetListener();
        }
    }
}

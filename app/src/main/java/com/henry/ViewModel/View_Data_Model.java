package com.henry.ViewModel;

import android.os.CountDownTimer;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


public class View_Data_Model extends ViewModel {

    private MutableLiveData<Long> liveData = new MutableLiveData<>();
    private MutableLiveData<String> liveData1 = new MutableLiveData<>();
    private MutableLiveData<String> liveData2 = new MutableLiveData<>();
    private MediatorLiveData<String> liveDataMerger;

    public MediatorLiveData<String> getLiveDataMerger() {
        if (liveDataMerger == null) {
            liveDataMerger = new MediatorLiveData<>();
        }

        liveDataMerger.addSource(liveData1, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                liveDataMerger.setValue(s);
            }
        });
        liveDataMerger.addSource(liveData2, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                liveDataMerger.setValue(s);
            }
        });
        return liveDataMerger;
    }

    public MutableLiveData<Long> getLiveData() {
        return liveData;
    }

    public MutableLiveData<String> getLiveData1() {
        return liveData1;
    }

    public MutableLiveData<String> getLiveData2() {
        return liveData2;
    }

    public void countDown() {
        new CountDownTimer(1 * 60 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
                liveData.postValue(l);
            }

            @Override
            public void onFinish() {
            cancel();
            }
        }.start();
    }

    public void mergeTest() {
        new CountDownTimer(1 * 60 * 1000, 3 * 1000) {
            @Override
            public void onTick(long l) {
                liveData1.postValue("网络有数据更新了" + l / 1000);
            }

            @Override
            public void onFinish() {
                cancel();
            }
        }.start();

        new CountDownTimer(1 * 60 * 1000, 10 * 1000) {
            @Override
            public void onTick(long l) {
                liveData2.postValue("本地数据库更新了" + l / 1000);
            }

            @Override
            public void onFinish() {
                cancel();
            }
        }.start();
    }
    @Override
    protected void onCleared()
    {
        super.onCleared();
    }

}
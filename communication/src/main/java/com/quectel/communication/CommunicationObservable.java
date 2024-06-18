package com.quectel.communication;

import com.google.gson.Gson;
import com.quectel.communication.model.ResSerializableBean;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * 通信中被观察者
 */

public class CommunicationObservable {

    private static CommunicationObservable mCommunicationObservable;

    public static Gson gson = new Gson();

    String TAG = getClass().getSimpleName();

    private CommunicationObservable() {

    }

    public static CommunicationObservable getInstance() {
        if (mCommunicationObservable == null) {
            mCommunicationObservable = new CommunicationObservable();
        }
        return mCommunicationObservable;
    }


    /**
     * 普通通信方式
     *
     * @param communicationBuilder
     * @param observer
     */
    public void getObservable(CommunicationBuilder communicationBuilder, CommunicationObserver observer) {

        communicationBuilder.getObservable()
                //将被观察者切换到子线程
                .subscribeOn(Schedulers.io())
                //将观察者切换到主线程  需要在Android环境下运行
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResSerializableBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResSerializableBean s) {
                        observer.onNext(s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    /**
     * 用于多个模块同时获取数据
     *
     * @param communicationBuilders
     * @param observer
     */
    public void getObservable(List<CommunicationBuilder> communicationBuilders, CommunicationObserver observer) {

        Observable observableAll = null;


        /**
         * 在循环中，对每个 CommunicationBuilder 对象获取对应的 Observable 对象。
         * 如果 observableAll 为 null，将当前 Observable 赋值给 observableAll。
         * 如果当前 CommunicationBuilder 对象定义了一个 BiFunction，
         * 则使用 zipWith 方法将当前 Observable 与 observableAll 进行组合操作。
         * zipWith 方法允许您指定一个函数，这个函数将 在两个 Observable 上的每个元素之间调用，并以单个值作为输出。
         *
         */
        for (CommunicationBuilder communicationBuilder : communicationBuilders) {
            Observable observable = communicationBuilder.getObservable();
            if (observableAll == null) {
                observableAll = observable;
            } else if (communicationBuilder.getCommunicationDefinition().getBiFunction() != null) {
                observableAll = observableAll.zipWith(observable, communicationBuilder.getCommunicationDefinition().getBiFunction(), true);
            }
        }
        Objects.requireNonNull(observableAll);
        //将被观察者切换到子线程
        observableAll.subscribeOn(Schedulers.io())
                //将观察者切换到主线程  需要在Android环境下运行
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResSerializableBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResSerializableBean s) {
                        observer.onNext(s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

}

package com.sse.monitor.presenter;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.model.impl.HomeModel;
import com.sse.monitor.presenter.iview.HomeView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/10/20.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private HomeModel mHomeModel;
    private Subscription mSubscription;

    @Inject
    public HomePresenter() {
        this.mHomeModel = HomeModel.getInstance();
    }

    public void loadIndexData() {
        this.getIndexList();
    }

    public void stopLoadIndexData() {
        if(mSubscription != null){
            this.mCompositeSubscription.remove(mSubscription);
        }
    }

    private void getIndexList() {
        mSubscription = Observable.interval(200, 3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, List<IndexBean>>() {
                    @Override
                    public List<IndexBean> call(Long aLong) {
                        Log.d("HP","interval:"+aLong);
                        List<IndexBean> list = new ArrayList<>();
                        Type resultType = new TypeToken<List<IndexBean>>(){}.getType();
                        try {
                            list = Reservoir.get("IndexMain", resultType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IndexBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //IndexPresenter.this.getMvpView().onFailure();
                    }

                    @Override
                    public void onNext(List<IndexBean> indexBeanList) {
                        HomePresenter.this.getMvpView().renderIndexList(indexBeanList);
                    }
                });
        this.mCompositeSubscription.add(mSubscription);
    }
}

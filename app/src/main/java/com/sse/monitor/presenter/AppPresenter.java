package com.sse.monitor.presenter;

import com.orhanobut.logger.Logger;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.VersionBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.mms.LogisticApi;
import com.sse.monitor.model.impl.AppModel;
import com.sse.monitor.presenter.iview.AppView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/5/3.
 */
public class AppPresenter extends BasePresenter<AppView> {
    private AppModel appModel;

    public AppPresenter() {
        this.appModel = AppModel.getInstance();
    }

    /*public void checkUpdate() {
        this.mCompositeSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<ResultBean<VersionBean>>>() {
                    @Override
                    public Observable<ResultBean<VersionBean>> call(Long aLong) {
                        return appModel.getVersion();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultBean<VersionBean>>() {

                    @Override
                    public void onCompleted() {
                        AppPresenter.this.mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        AppPresenter.this.getMvpView().enterHome();
                    }

                    @Override
                    public void onNext(ResultBean<VersionBean> resultBean) {
                        if (resultBean.getStatusCode().equals(LogisticApi.SUCCESS_DATA)) {
                            Integer versionId = resultBean == null ? 0 : resultBean.getResultData().getVersionCode();
                            if (AppPresenter.this.getMvpView().getAppVersion() < versionId) {
                                AppPresenter.this.getMvpView().showUpdateDialog("发现新版本:" + resultBean.getResultData().getVersionName(), resultBean.getResultData().getVersionDesc());
                            } else {
                                AppPresenter.this.getMvpView().enterHome();
                            }
                        } else {
                            AppPresenter.this.getMvpView().enterHome();
                        }
                    }
                }));
    }*/

    public void downloadAPK() {

    }
}

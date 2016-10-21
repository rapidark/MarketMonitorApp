package com.sse.monitor.presenter;

import android.util.Log;

import com.shiki.okttp.OkHttpUtils;
import com.shiki.utils.ReservoirUtils;
import com.shiki.utils.StringUtils;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.mms.MmsApi;
import com.sse.monitor.model.impl.IndexModel;
import com.sse.monitor.model.impl.MainModel;
import com.sse.monitor.presenter.iview.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/10/20.
 */
public class MainPresenter extends BasePresenter<MainView> {
    private MainModel mMainModel;
    private Subscription mSubscription;

    @Inject
    public MainPresenter() {
        this.mMainModel = MainModel.getInstance();
    }

    public void loadIndexData(String list) {
        this.getIndexList(list);
    }

    public void stopLoadIndexData() {
        if(mSubscription != null){
            this.mCompositeSubscription.remove(mSubscription);
        }
    }

    private void getIndexList(final String list) {
        mSubscription = Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get().url(MmsApi.BASE_URL).addParams("list", list).build().execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                Log.d("MainPresenter",msg);
                                return msg;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Func1<String, List<IndexBean>>() {
                    @Override
                    public List<IndexBean> call(String s) {
                        List<IndexBean> indexBeanList = new ArrayList<>();
                        if (!StringUtils.isEmpty(s)) {
                            String[] strs = s.split(";");
                            for (String str : strs) {
                                if (str.indexOf("=\"") > 0) {
                                    IndexBean indexBean = new IndexBean();
                                    indexBean.setSecurityId(str.substring(str.lastIndexOf("_") + 1, str.indexOf("=")));
                                    str = str.substring(str.indexOf("=\"") + 2, str.lastIndexOf("\""));
                                    String[] contents = str.split(",");
                                    if (contents.length >= 6) {
                                        indexBean.setSymbol(contents[0]);
                                        indexBean.setIndex(Double.valueOf(contents[1]));
                                        indexBean.setPrice(Double.valueOf(contents[2]));
                                        indexBean.setRate(Double.valueOf(contents[3]));
                                        indexBean.setVolume(Long.valueOf(contents[4]));
                                        indexBean.setTurnover(Double.valueOf(contents[5]));
                                    }
                                    indexBeanList.add(indexBean);
                                }
                            }
                        }
                        return indexBeanList;
                    }
                })
                .map(new Func1<List<IndexBean>, Boolean>() {
                    @Override
                    public Boolean call(List<IndexBean> indexBeanList) {
                        boolean result = true;
                        if(indexBeanList!=null&&indexBeanList.size()>0){
                            ReservoirUtils.getInstance().refresh("IndexMain",indexBeanList);
                        }else{
                            result = false;
                        }
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        MainPresenter.this.getMvpView().onFailure("11");
                    }

                    @Override
                    public void onNext(Boolean b) {
                        //IndexPresenter.this.getMvpView().renderIndexList(indexBeanList);
                    }
                });
        this.mCompositeSubscription.add(mSubscription);
    }
}

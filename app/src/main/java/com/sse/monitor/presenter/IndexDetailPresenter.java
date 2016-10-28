package com.sse.monitor.presenter;

import android.util.Log;

import com.shiki.okttp.OkHttpUtils;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.bean.IndexDetailBean;
import com.sse.monitor.bean.IndexMinuteBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.mms.MmsApi;
import com.sse.monitor.model.impl.IndexModel;
import com.sse.monitor.presenter.iview.IndexDetailView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/10/27.
 */
public class IndexDetailPresenter extends BasePresenter<IndexDetailView> {
    private IndexModel mIndexModel;
    private IndexBean mIndexBean;

    @Inject
    public IndexDetailPresenter(IndexBean indexBean) {
        this.mIndexModel = IndexModel.getInstance();
        this.mIndexBean = indexBean;
    }

    public void loadStockData() {
        this.getStockData();
    }

    public void loadStockMinuteData() {
        this.getStockMinuteData();
    }

    private void getStockData() {
        this.mCompositeSubscription.add(Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get()
                                    .url(MmsApi.TENCENT_STOCK_URL + mIndexBean.getSecurityId()).build()
                                    .execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                Log.d("getStockData", msg);
                                return msg;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).map(new Func1<String, IndexDetailBean>() {
                    @Override
                    public IndexDetailBean call(String msg) {
                        String subStart = "=\"";
                        String content = msg.substring(msg.indexOf(subStart) + subStart.length(), msg.lastIndexOf("\""));
                        String[] contents = content.split("~");
                        IndexDetailBean indexDetailBean = new IndexDetailBean();
                        if (contents.length > 43) {
                            indexDetailBean.setSecurityId(contents[2]);
                            indexDetailBean.setSymbol(contents[1]);
                            indexDetailBean.setOpenPrice(Float.valueOf(contents[5]));
                            indexDetailBean.setClosePrice(Float.valueOf(contents[4]));
                            indexDetailBean.setTurnover(Double.valueOf(contents[37]));
                            indexDetailBean.setAmplitude(Float.valueOf(contents[43]));
                            indexDetailBean.setIndex(Float.valueOf(contents[3]));
                            indexDetailBean.setPrice(Float.valueOf(contents[31]));
                            indexDetailBean.setRate(Float.valueOf(contents[32]));
                            indexDetailBean.setMaxPrice(Float.valueOf(contents[33]));
                            indexDetailBean.setMinPrice(Float.valueOf(contents[34]));
                            indexDetailBean.setVolume(Double.valueOf(contents[6]));
                        }
                        return indexDetailBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IndexDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndexDetailBean indexDetailBean) {
                        IndexDetailPresenter.this.getMvpView().renderIndexDetail(indexDetailBean);
                    }
                }));
    }

    private void getStockMinuteData() {
        this.mCompositeSubscription.add(Observable.interval(0, 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get().url(MmsApi.TENCENT_STOCK_MINUTE_URL)
                                    .addParams("code", mIndexBean.getSecurityId()).build().execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                Log.d("getStockMinuteData", msg);
                                return msg;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Func1<String, List<IndexMinuteBean>>() {
                    @Override
                    public List<IndexMinuteBean> call(String msg) {
                        String subStart = "data\":[";
                        String subEnd = "],\"date";
                        String content = msg.substring(msg.indexOf(subStart) + subStart.length(), msg.indexOf(subEnd));
                        List<IndexMinuteBean> indexMinuteBeanList = new ArrayList<>();
                        String[] contents = content.split(",");
                        double preVolume = 0;
                        for (String minuteContent : contents) {
                            minuteContent = minuteContent.replace("\"", "");
                            String[] minuteContents = minuteContent.split(" ");
                            if (minuteContents.length >= 3) {
                                IndexMinuteBean indexMinuteBean = new IndexMinuteBean();
                                indexMinuteBean.setTime(minuteContents[0]);
                                indexMinuteBean.setIndex(Float.valueOf(minuteContents[1]));
                                double volume = Double.valueOf(minuteContents[2]);
                                indexMinuteBean.setVolume(volume - preVolume);
                                preVolume = volume;
                                indexMinuteBeanList.add(indexMinuteBean);
                            }
                        }
                        return indexMinuteBeanList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IndexMinuteBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<IndexMinuteBean> indexMinuteBeen) {
                        IndexDetailPresenter.this.getMvpView().renderIndexMinute(indexMinuteBeen);
                    }
                }));

    }
}

package com.sse.monitor.presenter;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.utils.ReservoirUtils;
import com.shiki.utils.StringUtils;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.mms.MmsApi;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.model.impl.IndexModel;
import com.sse.monitor.presenter.iview.IndexView;

import java.io.IOException;
import java.lang.reflect.Type;
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
public class IndexPresenter extends BasePresenter<IndexView> {
    private IndexModel mIndexModel;

    @Inject
    public IndexPresenter() {
        this.mIndexModel = IndexModel.getInstance();
    }

    public void loadMainIndexData(String list) {
        this.getMainIndexList(list);
    }

    public void loadGolbalIndexData(String list) {
        this.getGolbalIndexList(list);
    }

    public void loadFuturesIndexData() {
        this.getFuturesIndexList();
    }

    public void stopLoadData() {
        this.mCompositeSubscription.clear();
    }

    private void getMainIndexList(final String list) {
        this.mCompositeSubscription.add(Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get().url(MmsApi.BASE_URL).addParams("list", list).build().execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                //Log.d("IndexPresenter", msg);
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
                            if (indexBeanList != null && indexBeanList.size() > 0) {
                                ReservoirUtils.getInstance().refresh("IndexMain", indexBeanList);
                            }
                        }
                        return indexBeanList;
                    }
                })
                .map(new Func1<List<IndexBean>, List<IndexBean>>() {
                    @Override
                    public List<IndexBean> call(List<IndexBean> indexBeanList) {
                        if (indexBeanList == null || indexBeanList.size() <= 0) {
                            Type resultType = new TypeToken<List<IndexBean>>() {
                            }.getType();
                            try {
                                indexBeanList = Reservoir.get("IndexMain", resultType);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return indexBeanList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IndexBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //mIndexModel.this.getMvpView().onFailure("11");
                    }

                    @Override
                    public void onNext(List<IndexBean> indexBeanList) {
                        IndexPresenter.this.getMvpView().renderMainIndexList(indexBeanList);
                    }
                }));
    }

    private void getGolbalIndexList(final String list) {
        this.mCompositeSubscription.add(Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get().url(MmsApi.BASE_URL).addParams("list", list).build().execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                //Log.d("getGolbalIndexList", msg);
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
                                    if (contents.length >= 4) {
                                        indexBean.setSymbol(contents[0]);
                                        indexBean.setIndex(Double.valueOf(contents[1]));
                                        indexBean.setPrice(Double.valueOf(contents[2]));
                                        indexBean.setRate(Double.valueOf(contents[3].replace('%','0')));
                                        //indexBean.setVolume(Long.valueOf(contents[4]));
                                        //indexBean.setTurnover(Double.valueOf(contents[5]));
                                    }
                                    indexBeanList.add(indexBean);
                                }
                            }
                            if (indexBeanList != null && indexBeanList.size() > 0) {
                                ReservoirUtils.getInstance().refresh("IndexGolbal", indexBeanList);
                            }
                        }
                        return indexBeanList;
                    }
                })
                .map(new Func1<List<IndexBean>, List<IndexBean>>() {
                    @Override
                    public List<IndexBean> call(List<IndexBean> indexBeanList) {
                        if (indexBeanList == null || indexBeanList.size() <= 0) {
                            Type resultType = new TypeToken<List<IndexBean>>() {
                            }.getType();
                            try {
                                indexBeanList = Reservoir.get("IndexGolbal", resultType);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return indexBeanList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IndexBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //mIndexModel.this.getMvpView().onFailure("11");
                    }

                    @Override
                    public void onNext(List<IndexBean> indexBeanList) {
                        IndexPresenter.this.getMvpView().renderGolbalIndexList(indexBeanList);
                    }
                }));
    }

    private void getFuturesIndexList() {
        this.mCompositeSubscription.add(Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get().url(MmsApi.BASE_URL).addParams("list", MmsConstants.CFF_RE_LIST).build().execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                Log.d("getFuturesIndexList", msg);
                                return msg;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String result = "";
                        if (!StringUtils.isEmpty(s)) {
                            //var hq_str_CFF_RE_LIST="IC1610,IC1611,IC1612,IC1703,IF1610,IF1611,IF1612,IF1703,IH1610,IH1611,IH1612,IH1703,T1612,T1703,T1706,TF1612,TF1703,TF1706";
                            if(s.indexOf("=\"") > 0){
                                s = s.substring(s.indexOf("=\"") + 2, s.lastIndexOf("\""));
                                String[] contents = s.split(",");
                                String start = "CFF_RE_";
                                String param = "";
                                if(contents.length>=12){
                                    for (int i=0;i<2;i++){
                                        param += start+contents[i]+","+start+contents[i+4]+","+start+contents[i+8]+",";
                                    }
                                    param.substring(0,param.length()-1);
                                    try {
                                        Response response = OkHttpUtils.get().url(MmsApi.BASE_URL).addParams("list", param).build().execute();
                                        if (response.isSuccessful()) {
                                            result = response.body().string();
                                            response.body().close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        return result;
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
                                    String start = "hq_str_CFF_RE_";
                                    indexBean.setSecurityId(str.substring(str.lastIndexOf(start) + start.length(), str.indexOf("=")));
                                    str = str.substring(str.indexOf("=\"") + 2, str.lastIndexOf("\""));
                                    String[] contents = str.split(",");
                                    if (contents.length >= 4) {
                                        indexBean.setSymbol(indexBean.getSecurityId());
                                        indexBean.setIndex(Double.valueOf(contents[7]));
                                        indexBean.setPrice(Double.valueOf(contents[7])-Double.valueOf(contents[14]));
                                        indexBean.setRate((Double.valueOf(contents[7])-Double.valueOf(contents[14]))*100/Double.valueOf(contents[14]));
                                    }
                                    indexBeanList.add(indexBean);
                                }
                            }
                            if (indexBeanList != null && indexBeanList.size() > 0) {
                                ReservoirUtils.getInstance().refresh("IndexFutures", indexBeanList);
                            }
                        }
                        return indexBeanList;
                    }
                })
                .map(new Func1<List<IndexBean>, List<IndexBean>>() {
                    @Override
                    public List<IndexBean> call(List<IndexBean> indexBeanList) {
                        if (indexBeanList == null || indexBeanList.size() <= 0) {
                            Type resultType = new TypeToken<List<IndexBean>>() {
                            }.getType();
                            try {
                                indexBeanList = Reservoir.get("IndexFutures", resultType);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return indexBeanList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IndexBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //mIndexModel.this.getMvpView().onFailure("11");
                    }

                    @Override
                    public void onNext(List<IndexBean> indexBeanList) {
                        IndexPresenter.this.getMvpView().renderFuturesIndexList(indexBeanList);
                    }
                }));
    }
}

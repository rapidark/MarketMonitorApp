package com.sse.monitor.presenter;

import com.anupcowkur.reservoir.Reservoir;
import com.sse.monitor.bean.OrderBean;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.UserBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.model.impl.OrderModel;
import com.sse.monitor.presenter.iview.OrderReceiptView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Maik on 2016/5/16.
 */
public class OrderReceiptPresenter extends BasePresenter<OrderReceiptView> {
    private OrderModel orderModel;

    @Inject
    public OrderReceiptPresenter() {
        this.orderModel = OrderModel.getInstance();
    }

    public void loadOrderData() {
        this.getOrderList();
    }

    private void getOrderList() {
        this.mCompositeSubscription.add(Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        try {
                            if (Reservoir.contains("userInfo")) {
                                UserBean userBean = Reservoir.get("userInfo", UserBean.class);
                                subscriber.onNext(userBean.getUserId());
                            }
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .flatMap(new Func1<String, Observable<ResultBean<List<OrderBean>>>>() {
                    @Override
                    public Observable<ResultBean<List<OrderBean>>> call(String expressId) {
                        return orderModel.receiptOrderList(expressId);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultBean<List<OrderBean>>>() {
                    @Override
                    public void onCompleted() {
                        OrderReceiptPresenter.this.mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onNext(ResultBean<List<OrderBean>> resultBean) {
                        OrderReceiptPresenter.this.getMvpView().renderOrderList(resultBean.getResultData());
                    }
                }));
    }
}

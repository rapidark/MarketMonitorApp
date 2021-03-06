package com.sse.monitor.presenter;

import com.anupcowkur.reservoir.Reservoir;
import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.UserBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.model.impl.MessageModel;
import com.sse.monitor.presenter.iview.MessageDetailView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/5/12.
 */
public class MessageDetailPresenter extends BasePresenter<MessageDetailView> {
    private MessageModel messageModel;
    private MessageBean message;

    @Inject
    public MessageDetailPresenter(MessageBean message) {
        this.messageModel = MessageModel.getInstance();
        this.message = message;
    }

    public void loadDetailData() {
        this.getMessageDetail();
    }

    private void getMessageDetail() {
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
                .flatMap(new Func1<String, Observable<ResultBean<MessageBean>>>() {
                    @Override
                    public Observable<ResultBean<MessageBean>> call(String expressId) {
                        return messageModel.getMessageDetail(expressId, message.getMessageId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultBean<MessageBean>>() {
                    @Override
                    public void onCompleted() {
                        MessageDetailPresenter.this.mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onNext(ResultBean<MessageBean> resultBean) {
                        MessageDetailPresenter.this.getMvpView().renderMessageDetail(resultBean.getResultData());
                    }
                }));
    }
}

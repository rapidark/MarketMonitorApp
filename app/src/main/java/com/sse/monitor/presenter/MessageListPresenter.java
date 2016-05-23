package com.sse.monitor.presenter;

import com.anupcowkur.reservoir.Reservoir;
import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.UserBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.model.impl.MessageModel;
import com.sse.monitor.presenter.iview.MessageListView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/5/9.
 */
public class MessageListPresenter extends BasePresenter<MessageListView> {
    private MessageModel messageModel;

    @Inject
    public MessageListPresenter() {
        this.messageModel = MessageModel.getInstance();
    }

    public void loadMessageData() {
        this.getMessageList();
    }

    private void getMessageList() {
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
                .flatMap(new Func1<String, Observable<ResultBean<List<MessageBean>>>>() {
                    @Override
                    public Observable<ResultBean<List<MessageBean>>> call(String expressId) {
                        return messageModel.getMessageList(expressId);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultBean<List<MessageBean>>>() {
                    @Override
                    public void onCompleted() {
                        MessageListPresenter.this.mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onNext(ResultBean<List<MessageBean>> resultBean) {
                        MessageListPresenter.this.getMvpView().renderMessageList(resultBean.getResultData());
                    }
                }));
    }

    public void onMessageClicked(MessageBean message) {
        this.getMvpView().viewMessage(message);
    }
}

package com.sse.monitor.core.mvp;

/**
 * Created by Eric on 2016/4/29.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);
    void detachView();
}

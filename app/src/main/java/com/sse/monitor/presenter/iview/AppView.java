package com.sse.monitor.presenter.iview;

import com.sse.monitor.core.mvp.MvpView;

/**
 * Created by Eric on 2016/5/3.
 */
public interface AppView extends MvpView {
    void enterLogin();
    void enterGesture(boolean isSetGesture);
}

package com.sse.monitor.presenter.iview;

import com.sse.monitor.core.mvp.MvpView;

/**
 * Created by Eric on 2016/4/27.
 */
public interface LoginView extends MvpView {
    String getUserCode();
    void setUserCode(String userCode);
    String getPasswd();
    void setPasswd(String passwd);
    void showLoginProgress();
    void hideLoginProgress();
    void enterMain();
}

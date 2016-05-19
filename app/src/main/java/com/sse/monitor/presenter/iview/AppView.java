package com.sse.monitor.presenter.iview;

import com.sse.monitor.core.mvp.MvpView;

/**
 * Created by Maik on 2016/5/3.
 */
public interface AppView extends MvpView {

    void setAppVersion(String versionId);
    Integer getAppVersion();
    void showUpdateDialog(String title, String desc);
    void enterHome();
    void createProgressDialog();
}

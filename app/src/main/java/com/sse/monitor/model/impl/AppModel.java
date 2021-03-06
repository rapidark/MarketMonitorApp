package com.sse.monitor.model.impl;

import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.VersionBean;
import com.sse.monitor.mms.MmsMain;
import com.sse.monitor.model.IAppModel;

import com.squareup.okhttp.ResponseBody;

import rx.Observable;

/**
 * Created by Eric on 2016/5/3.
 */
public class AppModel implements IAppModel {
    private static final AppModel instance = new AppModel();

    public static AppModel getInstance() {
        return instance;
    }

    private AppModel() {
        //MonitorApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<ResultBean<VersionBean>> getVersion() {
        return MmsMain.getInstance().getMmsService().getVersion();
    }

    @Override
    public Observable<ResponseBody> downloadAPK() {
        return MmsMain.getInstance().getMmsService().downloadAPK();
    }
}

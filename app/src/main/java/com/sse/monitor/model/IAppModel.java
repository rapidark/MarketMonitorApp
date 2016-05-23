package com.sse.monitor.model;

import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.VersionBean;

import com.squareup.okhttp.ResponseBody;
import rx.Observable;

/**
 * Created by Eric on 2016/5/3.
 */
public interface IAppModel {

    Observable<ResultBean<VersionBean>> getVersion();
    Observable<ResponseBody> downloadAPK();
}

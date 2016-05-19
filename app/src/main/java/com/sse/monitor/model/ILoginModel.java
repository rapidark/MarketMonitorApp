package com.sse.monitor.model;

import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.UserBean;

import rx.Observable;

/**
 * Created by Maik on 2016/4/25.
 */
public interface ILoginModel {
    Observable<ResultBean<UserBean>> login(String usercode, String password);
}

package com.sse.monitor.model.impl;

import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.UserBean;
import com.sse.monitor.logistic.LogisticMain;
import com.sse.monitor.model.ILoginModel;

import rx.Observable;

/**
 * Created by Eric on 2016/4/28.
 */
public class LoginModel implements ILoginModel {
    private static final LoginModel instance = new LoginModel();

    public static LoginModel getInstance() {
        return instance;
    }

    private LoginModel() {
    }

    @Override
    public Observable<ResultBean<UserBean>> login(String usercode, String password) {
        return LogisticMain.getInstance().getLogisticService().login(usercode, password);
    }
}

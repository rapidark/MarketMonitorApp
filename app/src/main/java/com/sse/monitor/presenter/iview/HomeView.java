package com.sse.monitor.presenter.iview;

import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Eric on 2016/10/20.
 */
public interface HomeView extends MvpView {
    void renderIndexList(List<IndexBean> indexBeanList);
}

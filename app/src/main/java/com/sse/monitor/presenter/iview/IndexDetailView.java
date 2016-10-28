package com.sse.monitor.presenter.iview;

import com.sse.monitor.bean.IndexDetailBean;
import com.sse.monitor.bean.IndexMinuteBean;
import com.sse.monitor.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Eric on 2016/10/27.
 */
public interface IndexDetailView extends MvpView {
    void renderIndexDetail(IndexDetailBean indexDetailBean);
    void renderIndexMinute(List<IndexMinuteBean> indexMinuteBeanList);
}

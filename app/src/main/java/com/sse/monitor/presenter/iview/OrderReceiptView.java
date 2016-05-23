package com.sse.monitor.presenter.iview;

import com.sse.monitor.bean.OrderBean;
import com.sse.monitor.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Eric on 2016/5/16.
 */
public interface OrderReceiptView extends MvpView {
    void renderOrderList(List<OrderBean> orderData);
}

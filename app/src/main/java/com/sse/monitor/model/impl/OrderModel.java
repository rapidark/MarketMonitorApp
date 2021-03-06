package com.sse.monitor.model.impl;

import com.sse.monitor.bean.OrderBean;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.mms.MmsMain;
import com.sse.monitor.model.IOrderModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Eric on 2016/5/16.
 */
public class OrderModel implements IOrderModel {
    private static final OrderModel instance = new OrderModel();

    public static OrderModel getInstance() {
        return instance;
    }

    private OrderModel() {
    }

    @Override
    public Observable<ResultBean<List<OrderBean>>> receiptOrderList(String expressId) {
        return MmsMain.getInstance().getMmsService().receiptOrderList(expressId);
    }
}

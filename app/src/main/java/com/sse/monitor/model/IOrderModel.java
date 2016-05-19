package com.sse.monitor.model;

import com.sse.monitor.bean.OrderBean;
import com.sse.monitor.bean.ResultBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Maik on 2016/5/16.
 */
public interface IOrderModel {
    Observable<ResultBean<List<OrderBean>>> receiptOrderList(String expressId);
}

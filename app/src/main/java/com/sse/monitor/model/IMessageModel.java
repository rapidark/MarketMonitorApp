package com.sse.monitor.model;

import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.bean.ResultBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Eric on 2016/5/10.
 */
public interface IMessageModel {
    Observable<ResultBean<List<MessageBean>>> getMessageList(String expressId);
    Observable<ResultBean<MessageBean>> getMessageDetail(String expressId, String messageId);
}

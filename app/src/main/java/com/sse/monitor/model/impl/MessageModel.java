package com.sse.monitor.model.impl;

import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.mms.MmsMain;
import com.sse.monitor.model.IMessageModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Eric on 2016/5/10.
 */
public class MessageModel implements IMessageModel {
    private static final MessageModel instance = new MessageModel();

    public static MessageModel getInstance() {
        return instance;
    }

    private MessageModel() {
    }


    @Override
    public Observable<ResultBean<List<MessageBean>>> getMessageList(String expressId) {
        return MmsMain.getInstance().getMmsService().getMessageList(expressId);
    }

    @Override
    public Observable<ResultBean<MessageBean>> getMessageDetail(String expressId, String messageId) {
        return MmsMain.getInstance().getMmsService().getMessageDetail(expressId, messageId);
    }
}

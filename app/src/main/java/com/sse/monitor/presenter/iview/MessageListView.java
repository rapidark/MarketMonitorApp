package com.sse.monitor.presenter.iview;

import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Maik on 2016/5/9.
 */
public interface MessageListView extends MvpView {
    void renderMessageList(List<MessageBean> messageData);
    void viewMessage(MessageBean message);
}

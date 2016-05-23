package com.sse.monitor.presenter.iview;

import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.core.mvp.MvpView;

/**
 * Created by Eric on 2016/5/12.
 */
public interface MessageDetailView extends MvpView {
    void renderMessageDetail(MessageBean message);
    void setTitleWithFormat(String title);
    void setSubTitleWithFormat(String subtitle);
    void setContentWithFormat(String content);
}

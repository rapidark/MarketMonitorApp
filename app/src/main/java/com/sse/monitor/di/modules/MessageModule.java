package com.sse.monitor.di.modules;

import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2016/5/12.
 */
@Module
public class MessageModule {
    private MessageBean message;

    public MessageModule() {}

    public MessageModule(MessageBean message) {
        this.message = message;
    }

    @Provides
    @PerActivity
    MessageBean provideMessage() {
        return this.message;
    }
}

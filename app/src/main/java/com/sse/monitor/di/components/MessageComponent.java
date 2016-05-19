package com.sse.monitor.di.components;

import com.sse.monitor.di.PerActivity;
import com.sse.monitor.di.modules.ActivityModule;
import com.sse.monitor.di.modules.MessageModule;
import com.sse.monitor.ui.fragment.MessageDetailFragment;
import com.sse.monitor.ui.fragment.MessageListFragment;

import dagger.Component;

/**
 * Created by Maik on 2016/5/9.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MessageModule.class})
public interface MessageComponent extends ActivityComponent {
    void inject(MessageListFragment messageListFragment);
    void inject(MessageDetailFragment messageDetailFragment);
}

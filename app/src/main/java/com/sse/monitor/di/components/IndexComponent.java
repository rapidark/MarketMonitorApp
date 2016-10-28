package com.sse.monitor.di.components;

import com.sse.monitor.di.PerActivity;
import com.sse.monitor.di.modules.ActivityModule;
import com.sse.monitor.di.modules.IndexModule;
import com.sse.monitor.ui.fragment.IndexDetailFragment;
import com.sse.monitor.ui.fragment.MarketFragment;

import dagger.Component;

/**
 * Created by Eric on 2016/10/24.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, IndexModule.class})
public interface IndexComponent extends ActivityComponent {
    void inject(MarketFragment marketFragment);
    void inject(IndexDetailFragment indexDetailFragment);
}

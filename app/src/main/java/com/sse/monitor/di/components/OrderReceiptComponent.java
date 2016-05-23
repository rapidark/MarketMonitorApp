package com.sse.monitor.di.components;

import com.sse.monitor.di.PerActivity;
import com.sse.monitor.di.modules.ActivityModule;
import com.sse.monitor.ui.fragment.OrderReceiptFragment;

import dagger.Component;

/**
 * Created by Eric on 2016/5/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface OrderReceiptComponent extends ActivityComponent {
    void inject(OrderReceiptFragment orderReceiptFragment);
}

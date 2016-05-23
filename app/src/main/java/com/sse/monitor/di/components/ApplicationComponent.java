package com.sse.monitor.di.components;

import android.content.Context;

import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.di.modules.ApplicationModule;
import com.sse.monitor.di.modules.LogisticApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Eric on 2016/4/21.
 */
@Singleton
@Component(modules = {ApplicationModule.class, LogisticApiModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    Context getContext();
}

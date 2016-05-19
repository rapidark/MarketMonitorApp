package com.sse.monitor.di.modules;

import android.content.Context;

import com.sse.monitor.MonitorApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maik on 2016/4/21.
 */
@Module
public class ApplicationModule {
    private final MonitorApplication application;

    public ApplicationModule(MonitorApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
}

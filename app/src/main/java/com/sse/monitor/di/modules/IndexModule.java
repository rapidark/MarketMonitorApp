package com.sse.monitor.di.modules;

import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2016/10/24.
 */
@Module
public class IndexModule {
    private IndexBean index;

    public IndexModule() {}

    public IndexModule(IndexBean index) {
        this.index = index;
    }

    @Provides
    @PerActivity
    IndexBean provideIndex() {
        return this.index;
    }
}

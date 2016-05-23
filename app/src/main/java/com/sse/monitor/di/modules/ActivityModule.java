package com.sse.monitor.di.modules;

import android.app.Activity;

import com.sse.monitor.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2016/4/25.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.activity;
    }
}

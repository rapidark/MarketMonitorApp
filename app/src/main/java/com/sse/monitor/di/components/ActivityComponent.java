package com.sse.monitor.di.components;

import android.app.Activity;

import com.sse.monitor.di.PerActivity;
import com.sse.monitor.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by Maik on 2016/4/25.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}

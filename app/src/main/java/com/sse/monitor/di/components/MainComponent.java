package com.sse.monitor.di.components;

import com.sse.monitor.di.PerActivity;
import com.sse.monitor.di.modules.ActivityModule;
import com.sse.monitor.ui.fragment.HomeFragment;
import com.sse.monitor.ui.fragment.CustomerFragment;
import com.sse.monitor.ui.fragment.ProfileFragment;

import dagger.Component;

/**
 * Created by Eric on 2016/5/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface MainComponent extends ActivityComponent {
    void inject(ProfileFragment profileFragment);
    void inject(HomeFragment homeFragment);
    void inject(CustomerFragment customerFragment);
}

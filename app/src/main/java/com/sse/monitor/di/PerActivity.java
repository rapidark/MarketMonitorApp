package com.sse.monitor.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Eric on 2016/4/25.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}

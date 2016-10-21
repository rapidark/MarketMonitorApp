package com.sse.monitor.model.impl;

import com.sse.monitor.model.IMainModel;

/**
 * Created by Eric on 2016/10/20.
 */
public class MainModel implements IMainModel {
    private static final MainModel instance = new MainModel();

    public static MainModel getInstance() {
        return instance;
    }

    private MainModel() {
    }
}

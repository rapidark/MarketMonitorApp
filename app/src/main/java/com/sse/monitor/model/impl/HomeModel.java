package com.sse.monitor.model.impl;

import com.sse.monitor.model.IIndexModel;

/**
 * Created by Eric on 2016/10/20.
 */
public class HomeModel implements IIndexModel {
    private static final HomeModel instance = new HomeModel();

    public static HomeModel getInstance() {
        return instance;
    }

    private HomeModel() {
    }
}

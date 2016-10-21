package com.sse.monitor.model.impl;

import com.sse.monitor.model.IIndexModel;

/**
 * Created by Eric on 2016/10/20.
 */
public class IndexModel implements IIndexModel {
    private static final IndexModel instance = new IndexModel();

    public static IndexModel getInstance() {
        return instance;
    }

    private IndexModel() {
    }
}

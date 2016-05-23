package com.sse.monitor.ui.activity;

import android.os.Bundle;

import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.di.HasComponent;
import com.sse.monitor.di.components.DaggerOrderReceiptComponent;
import com.sse.monitor.di.components.OrderReceiptComponent;
import com.sse.monitor.ui.fragment.OrderReceiptFragment;

/**
 * Created by Eric on 2016/5/16.
 */
public class OrderReceiptActivity extends BaseActivity implements HasComponent<OrderReceiptComponent> {
    private OrderReceiptComponent orderReceiptComponent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new OrderReceiptFragment());
        }
    }

    @Override
    protected void initData() {

    }

    private void initializeInjector() {
        this.orderReceiptComponent = DaggerOrderReceiptComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public OrderReceiptComponent getComponent() {
        return this.orderReceiptComponent;
    }
}

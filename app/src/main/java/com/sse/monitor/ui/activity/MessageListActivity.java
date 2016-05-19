package com.sse.monitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.di.HasComponent;
import com.sse.monitor.di.components.DaggerMessageComponent;
import com.sse.monitor.di.components.MessageComponent;
import com.sse.monitor.ui.fragment.MessageListFragment;

/**
 * Created by Maik on 2016/5/9.
 */
public class MessageListActivity extends BaseActivity implements HasComponent<MessageComponent> {
    private MessageComponent messageComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MessageListActivity.class);
    }

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
            addFragment(R.id.fragmentContainer, new MessageListFragment());
        }
    }

    @Override
    protected void initData() {

    }

    private void initializeInjector() {
        this.messageComponent = DaggerMessageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public MessageComponent getComponent() {
        return messageComponent;
    }
}

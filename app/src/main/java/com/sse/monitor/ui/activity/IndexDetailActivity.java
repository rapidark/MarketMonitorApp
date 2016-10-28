package com.sse.monitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sse.monitor.R;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.di.HasComponent;
import com.sse.monitor.di.components.DaggerIndexComponent;
import com.sse.monitor.di.components.IndexComponent;
import com.sse.monitor.di.modules.IndexModule;
import com.sse.monitor.mms.LogisticApi;
import com.sse.monitor.ui.fragment.IndexDetailFragment;

public class IndexDetailActivity extends BaseActivity implements HasComponent<IndexComponent> {
    private static final String INSTANCE_STATE_PARAM_MESSAGE = "org.shiki.STATE_PARAM_MESSAGE_ID";
    private IndexComponent mIndexComponent;
    private IndexBean mIndexBean;

    public static Intent getCallingIntent(Context context, IndexBean indexBean) {
        Intent callingIntent = new Intent(context, IndexDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(LogisticApi.INTENT_EXTRA_PARAM_MESSAGE, indexBean);
        callingIntent.putExtras(bundle);
        return callingIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(INSTANCE_STATE_PARAM_MESSAGE, mIndexBean);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        getSupportActionBar().hide();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mIndexBean = getIntent().getParcelableExtra(LogisticApi.INTENT_EXTRA_PARAM_MESSAGE);
            /*mIndexBean = new IndexBean();
            mIndexBean.setSecurityId("sh000001");*/
            addFragment(R.id.fragmentContainer, new IndexDetailFragment());
        } else {
            mIndexBean = savedInstanceState.getParcelable(INSTANCE_STATE_PARAM_MESSAGE);
        }
        initializeInjector();
    }

    private void initializeInjector() {
        mIndexComponent = DaggerIndexComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .indexModule(new IndexModule(mIndexBean))
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    public IndexComponent getComponent() {
        return mIndexComponent;
    }
}

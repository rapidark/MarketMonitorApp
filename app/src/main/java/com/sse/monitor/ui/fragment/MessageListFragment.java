package com.sse.monitor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.shiki.recyclerview.FGORecyclerView;
import com.sse.monitor.R;
import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.di.components.MessageComponent;
import com.sse.monitor.logistic.LogisticApi;
import com.sse.monitor.presenter.MessageListPresenter;
import com.sse.monitor.presenter.iview.MessageListView;
import com.sse.monitor.ui.activity.MessageDetailActivity;
import com.sse.monitor.ui.adapter.MessageAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Maik on 2016/5/9.
 */
public class MessageListFragment extends BaseFragment implements MessageListView {
    @Bind(R.id.fgo_recycler_view)
    FGORecyclerView rvAnnouncement;
    @Inject
    MessageAdapter messageAdapter;
    @Inject
    MessageListPresenter messagePresenter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        this.rvAnnouncement.setAdapter(this.messageAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MessageComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.messagePresenter.attachView(this);
        if (savedInstanceState == null)
            this.messagePresenter.loadMessageData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        rvAnnouncement.setHasFixedSize(true);
        rvAnnouncement.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initListeners() {
        rvAnnouncement.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        rvAnnouncement.setOnLoadMoreListener(new FGORecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {

            }
        });
        this.messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onMessageItemClicked(MessageBean message) {
                if (MessageListFragment.this.messagePresenter != null && message != null) {
                    MessageListFragment.this.messagePresenter.onMessageClicked(message);
                }
            }
        });
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void renderMessageList(List<MessageBean> messageData) {
        if (messageData != null) {
            this.messageAdapter.setMessageData(messageData);
        }
    }

    @Override
    public void viewMessage(MessageBean message) {
        Intent intentToLaunch = MessageDetailActivity.getCallingIntent(getActivity(), message);
        startActivityForResult(intentToLaunch, LogisticApi.INTENT_REQUEST_MESSAGE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MessageBean message = data.getParcelableExtra(LogisticApi.INTENT_EXTRA_PARAM_MESSAGE);
        this.messageAdapter.updateMessage(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.messagePresenter.detachView();
    }
}

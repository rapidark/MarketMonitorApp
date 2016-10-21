package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sse.monitor.R;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.di.components.MainComponent;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.presenter.HomePresenter;
import com.sse.monitor.presenter.IndexPresenter;
import com.sse.monitor.presenter.iview.HomeView;
import com.sse.monitor.ui.adapter.IndexAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/5/12.
 */
public class HomeFragment extends BaseFragment implements HomeView {
    private static final String TAG = "HomeFragment";

    //@BindView(R.id.home_index_rv_content)
    //RecyclerView mHomeIndexRvContent;
    @Inject
    IndexAdapter mIndexAdapter;
    @Inject
    HomePresenter mHomePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mHomePresenter.attachView(this);
        //this.mHomePresenter.loadIndexData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    /*@Override
    protected void lazyData() {

    }*/

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        //mHomeIndexRvContent.setLayoutManager(new GridLayoutManager(getActivity(),3));
        //mHomeIndexRvContent.setAdapter(mIndexAdapter);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void renderIndexList(List<IndexBean> indexBeanList) {
        Log.d(TAG,"renderIndexList");
        mIndexAdapter.setIndexData(indexBeanList);
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mHomePresenter.detachView();
    }
}

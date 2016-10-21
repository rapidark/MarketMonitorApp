package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sse.monitor.R;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.di.components.MainComponent;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.presenter.IndexPresenter;
import com.sse.monitor.presenter.iview.IndexView;
import com.sse.monitor.ui.adapter.IndexAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Eric on 2016/10/14.
 */
public class MarketFragment extends BaseFragment implements IndexView {
    private static final String TAG = "MarketFragment";

    @BindView(R.id.market_index_rv_main)
    RecyclerView mMarketIndexRvMain;
    @BindView(R.id.market_index_rv_global)
    RecyclerView mMarketIndexRvGolbal;
    @BindView(R.id.market_index_rv_futures)
    RecyclerView mMarketIndexRvFutures;
    @Inject
    IndexAdapter mMainIndexAdapter;
    @Inject
    IndexAdapter mGolbalIndexAdapter;
    @Inject
    IndexAdapter mFuturesIndexAdapter;
    @Inject
    IndexPresenter mIndexPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mIndexPresenter.attachView(this);
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mMarketIndexRvMain.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mMarketIndexRvMain.setAdapter(mMainIndexAdapter);

        mMarketIndexRvGolbal.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mMarketIndexRvGolbal.setAdapter(mGolbalIndexAdapter);

        mMarketIndexRvFutures.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mMarketIndexRvFutures.setAdapter(mFuturesIndexAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void lazyData() {
        String list = MmsConstants.Index.SH_000001
                + "," + MmsConstants.Index.SZ_399001
                + "," + MmsConstants.Index.SZ_399006
                + "," + MmsConstants.Index.SH_000300
                + "," + MmsConstants.Index.SH_000016
                + "," + MmsConstants.Index.SH_000905;
        this.mIndexPresenter.loadMainIndexData(list);
        list = MmsConstants.Index.INT_HENGSENG
                + "," + MmsConstants.Index.INT_DJI
                + "," + MmsConstants.Index.INT_NASDAQ
                + "," + MmsConstants.Index.INT_NIKKEI
                + "," + MmsConstants.Index.B_TWSE
                + "," + MmsConstants.Index.B_KOSPI;
        this.mIndexPresenter.loadGolbalIndexData(list);
        this.mIndexPresenter.loadFuturesIndexData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }



    @Override
    protected void initListeners() {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mIndexPresenter.detachView();
    }

    @Override
    public void renderMainIndexList(List<IndexBean> indexBeanList) {
        mMainIndexAdapter.setIndexData(indexBeanList);
    }

    @Override
    public void renderGolbalIndexList(List<IndexBean> indexBeanList) {
        mGolbalIndexAdapter.setIndexData(indexBeanList);
    }

    @Override
    public void renderFuturesIndexList(List<IndexBean> indexBeanList) {
        mFuturesIndexAdapter.setIndexData(indexBeanList);
    }
}

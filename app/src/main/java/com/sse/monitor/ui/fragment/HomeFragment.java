package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sse.monitor.R;
import com.sse.monitor.core.BaseFragment;

import butterknife.Bind;

/**
 * Created by Eric on 2016/5/12.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.market_index_tv_title)
    TextView mMarketIndexTvTitle;
    @Bind(R.id.market_index_iv_more)
    ImageView mMarketIndexIvMore;
    @Bind(R.id.item_market_tv_title)
    TextView mItemMarketTvTitle;
    @Bind(R.id.item_market_tv_index)
    TextView mItemMarketTvIndex;
    @Bind(R.id.item_market_tv_update)
    TextView mItemMarketTvUpdate;
    @Bind(R.id.item_market_tv_percent)
    TextView mItemMarketTvPercent;
    @Bind(R.id.market_option_gl_content)
    GridLayout mMarketOptionGlContent;
    @Bind(R.id.market_option_gl_total)
    GridLayout mMarketOptionGlTotal;
    @Bind(R.id.market_option_gl_bottom)
    GridLayout mMarketOptionGlBottom;

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }
}

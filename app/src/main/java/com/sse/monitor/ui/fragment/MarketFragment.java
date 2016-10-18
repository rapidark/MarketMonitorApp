package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sse.monitor.R;
import com.sse.monitor.core.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/10/14.
 */
public class MarketFragment extends BaseFragment {

    @Bind(R.id.main_index_market_sh_0000001)
    GridLayout mSH000001GL;

    /*@Bind(parent = R.id.main_index_market_sh_0000001, value = R.id.item_market_tv_title)
    TextView mSH000001TvTitle;
    @Bind(R.id.item_market_tv_index)
    TextView mSH000001TvIndex;
    @Bind(R.id.item_market_tv_update)
    TextView mSH000001TvUpdate;
    @Bind(R.id.item_market_tv_percent)
    TextView mSH000001TvPercent;*/

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }
}

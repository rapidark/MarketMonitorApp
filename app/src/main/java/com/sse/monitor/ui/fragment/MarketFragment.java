package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.shiki.okttp.OkHttpUtils;
import com.shiki.utils.StringUtils;
import com.sse.monitor.R;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.di.components.MainComponent;
import com.sse.monitor.mms.MmsApi;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.ui.adapter.IndexAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/10/14.
 */
public class MarketFragment extends BaseFragment {
    private static final String TAG = "MarketFragment";

    @BindColor(R.color.red)
    int RED;
    @BindColor(R.color.green)
    int GREEN;
    @BindView(R.id.market_index_rv_content)
    RecyclerView mMarketIndexRvContent;
    @Inject
    IndexAdapter mIndexAdapter;


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void lazyData() {
        final String list = "s_" + MmsConstants.SH_000001
                + "," + "s_" + MmsConstants.SZ_399001
                + "," + "s_" + MmsConstants.SZ_399006
                + "," + "s_" + MmsConstants.SH_000300
                + "," + "s_" + MmsConstants.SH_000016
                + "," + "s_" + MmsConstants.SH_000905;
        Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        try {
                            Response response = OkHttpUtils.get().url(MmsApi.BASE_URL).addParams("list", list).build().execute();
                            if (response.isSuccessful()) {
                                String msg = response.body().string();
                                response.body().close();
                                Log.d(TAG, msg);
                                return msg;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Func1<String, List<IndexBean>>() {
                    @Override
                    public List<IndexBean> call(String s) {
                        List<IndexBean> indexBeanList = new ArrayList<>();
                        if (!StringUtils.isEmpty(s)) {
                            String[] strs = s.split(";");
                            for (String str : strs) {
                                if (str.indexOf("=\"") > 0) {
                                    IndexBean indexBean = new IndexBean();
                                    indexBean.setSecurityId(str.substring(str.lastIndexOf("_") + 1, str.indexOf("=")));
                                    str = str.substring(str.indexOf("=\"") + 2, str.lastIndexOf("\""));
                                    String[] contents = str.split(",");
                                    if (contents.length >= 6) {
                                        indexBean.setSymbol(contents[0]);
                                        indexBean.setIndex(Double.valueOf(contents[1]));
                                        indexBean.setPrice(Double.valueOf(contents[2]));
                                        indexBean.setRate(Double.valueOf(contents[3]));
                                        indexBean.setVolume(Long.valueOf(contents[4]));
                                        indexBean.setTurnover(Double.valueOf(contents[5]));
                                    }
                                    indexBeanList.add(indexBean);
                                }
                            }
                        }
                        return indexBeanList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<IndexBean>>() {
                    @Override
                    public void call(List<IndexBean> indexBeenList) {
                        mIndexAdapter.setIndexData(indexBeenList);
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

        mMarketIndexRvContent.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mMarketIndexRvContent.setAdapter(mIndexAdapter);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).inject(this);
    }
}

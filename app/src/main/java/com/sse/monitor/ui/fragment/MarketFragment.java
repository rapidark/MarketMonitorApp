package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.shiki.okttp.OkHttpUtils;
import com.shiki.utils.StringUtils;
import com.sse.monitor.R;
import com.sse.monitor.bean.IndexBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.mms.MmsApi;
import com.sse.monitor.mms.MmsConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/10/14.
 */
public class MarketFragment extends BaseFragment {
    private static final String TAG = "MarketFragment";

    @BindView(R.id.main_index_market_sh_0000001)
    GridLayout mSH000001GL;
    @BindView(R.id.main_index_market_sz_399001)
    GridLayout mSZ399001GL;
    @BindView(R.id.main_index_market_sz_399006)
    GridLayout mSZ399006GL;
    @BindView(R.id.main_index_market_sh_0000300)
    GridLayout mSH000300GL;
    @BindView(R.id.main_index_market_sh_0000016)
    GridLayout mSH000016GL;
    @BindView(R.id.main_index_market_sh_0000905)
    GridLayout mSH000905GL;

    TextView mSH000001TvTitle;
    TextView mSH000001TvIndex;
    TextView mSH000001TvUpdate;
    TextView mSH000001TvPercent;

    TextView mSZ399001TvTitle;
    TextView mSZ399001TvIndex;
    TextView mSZ399001TvUpdate;
    TextView mSZ399001TvPercent;

    TextView mSZ399006TvTitle;
    TextView mSZ399006TvIndex;
    TextView mSZ399006TvUpdate;
    TextView mSZ399006TvPercent;

    TextView mSH000300TvTitle;
    TextView mSH000300TvIndex;
    TextView mSH000300TvUpdate;
    TextView mSH000300TvPercent;

    TextView mSH000016TvTitle;
    TextView mSH000016TvIndex;
    TextView mSH000016TvUpdate;
    TextView mSH000016TvPercent;

    TextView mSH000905TvTitle;
    TextView mSH000905TvIndex;
    TextView mSH000905TvUpdate;
    TextView mSH000905TvPercent;

    @BindColor(R.color.red)
    int RED;
    @BindColor(R.color.green)
    int GREEN;


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
                        if(!StringUtils.isEmpty(s)){
                            String[] strs = s.split(";");
                            for (String str : strs){
                                if(str.indexOf("=\"")>0){
                                    IndexBean indexBean = new IndexBean();
                                    indexBean.setSecurityId(str.substring(str.lastIndexOf("_")+1,str.indexOf("=")));
                                    str = str.substring(str.indexOf("=\"")+2,str.lastIndexOf("\""));
                                    String[] contents = str.split(",");
                                    if(contents.length >= 6){
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
                        for (IndexBean indexBean : indexBeenList){
                            if(indexBean.getSecurityId().equals(MmsConstants.SH_000001)){
                                mSH000001TvTitle.setText(indexBean.getSymbol());
                                mSH000001TvIndex.setText(String.format("%.2f", indexBean.getIndex()));
                                if(indexBean.getRate()>0){
                                    mSH000001TvIndex.setTextColor(RED);
                                    mSH000001TvPercent.setTextColor(RED);
                                    mSH000001TvUpdate.setTextColor(RED);
                                    mSH000001TvPercent.setText("+"+indexBean.getRate()+"%");
                                    mSH000001TvUpdate.setText("+"+indexBean.getPrice()+"");
                                }else{
                                    mSH000001TvIndex.setTextColor(GREEN);
                                    mSH000001TvPercent.setTextColor(GREEN);
                                    mSH000001TvUpdate.setTextColor(GREEN);
                                    mSH000001TvPercent.setText(indexBean.getRate()+"%");
                                    mSH000001TvUpdate.setText(indexBean.getPrice()+"");
                                }
                            }else if(indexBean.getSecurityId().equals(MmsConstants.SH_000300)){
                                mSH000300TvTitle.setText(indexBean.getSymbol());
                                mSH000300TvIndex.setText(String.format("%.2f", indexBean.getIndex()));
                                if(indexBean.getRate()>0){
                                    mSH000300TvIndex.setTextColor(RED);
                                    mSH000300TvPercent.setTextColor(RED);
                                    mSH000300TvUpdate.setTextColor(RED);
                                    mSH000300TvPercent.setText("+"+indexBean.getRate()+"%");
                                    mSH000300TvUpdate.setText("+"+indexBean.getPrice()+"");
                                }else{
                                    mSH000300TvIndex.setTextColor(GREEN);
                                    mSH000300TvPercent.setTextColor(GREEN);
                                    mSH000300TvUpdate.setTextColor(GREEN);
                                    mSH000300TvPercent.setText(indexBean.getRate()+"%");
                                    mSH000300TvUpdate.setText(indexBean.getPrice()+"");
                                }
                            }else if(indexBean.getSecurityId().equals(MmsConstants.SH_000016)){
                                mSH000016TvTitle.setText(indexBean.getSymbol());
                                mSH000016TvIndex.setText(String.format("%.2f", indexBean.getIndex()));
                                if(indexBean.getRate()>0){
                                    mSH000016TvIndex.setTextColor(RED);
                                    mSH000016TvPercent.setTextColor(RED);
                                    mSH000016TvUpdate.setTextColor(RED);
                                    mSH000016TvPercent.setText("+"+indexBean.getRate()+"%");
                                    mSH000016TvUpdate.setText("+"+indexBean.getPrice()+"");
                                }else{
                                    mSH000016TvIndex.setTextColor(GREEN);
                                    mSH000016TvPercent.setTextColor(GREEN);
                                    mSH000016TvUpdate.setTextColor(GREEN);
                                    mSH000016TvPercent.setText(indexBean.getRate()+"%");
                                    mSH000016TvUpdate.setText(indexBean.getPrice()+"");
                                }
                            }else if(indexBean.getSecurityId().equals(MmsConstants.SH_000905)){
                                mSH000905TvTitle.setText(indexBean.getSymbol());
                                mSH000905TvIndex.setText(String.format("%.2f", indexBean.getIndex()));
                                if(indexBean.getRate()>0){
                                    mSH000905TvIndex.setTextColor(RED);
                                    mSH000905TvPercent.setTextColor(RED);
                                    mSH000905TvUpdate.setTextColor(RED);
                                    mSH000905TvPercent.setText("+"+indexBean.getRate()+"%");
                                    mSH000905TvUpdate.setText("+"+indexBean.getPrice()+"");
                                }else{
                                    mSH000905TvIndex.setTextColor(GREEN);
                                    mSH000905TvPercent.setTextColor(GREEN);
                                    mSH000905TvUpdate.setTextColor(GREEN);
                                    mSH000905TvPercent.setText(indexBean.getRate()+"%");
                                    mSH000905TvUpdate.setText(indexBean.getPrice()+"");
                                }
                            }else if(indexBean.getSecurityId().equals(MmsConstants.SZ_399001)){
                                mSZ399001TvTitle.setText(indexBean.getSymbol());
                                mSZ399001TvIndex.setText(String.format("%.2f", indexBean.getIndex()));
                                if(indexBean.getRate()>0){
                                    mSZ399001TvIndex.setTextColor(RED);
                                    mSZ399001TvPercent.setTextColor(RED);
                                    mSZ399001TvUpdate.setTextColor(RED);
                                    mSZ399001TvPercent.setText("+"+indexBean.getRate()+"%");
                                    mSZ399001TvUpdate.setText("+"+indexBean.getPrice()+"");
                                }else{
                                    mSZ399001TvIndex.setTextColor(GREEN);
                                    mSZ399001TvPercent.setTextColor(GREEN);
                                    mSZ399001TvUpdate.setTextColor(GREEN);
                                    mSZ399001TvPercent.setText(indexBean.getRate()+"%");
                                    mSZ399001TvUpdate.setText(indexBean.getPrice()+"");
                                }
                            }else if(indexBean.getSecurityId().equals(MmsConstants.SZ_399006)){
                                mSZ399006TvTitle.setText(indexBean.getSymbol());
                                mSZ399006TvIndex.setText(String.format("%.2f", indexBean.getIndex()));
                                if(indexBean.getRate()>0){
                                    mSZ399006TvIndex.setTextColor(RED);
                                    mSZ399006TvPercent.setTextColor(RED);
                                    mSZ399006TvUpdate.setTextColor(RED);
                                    mSZ399006TvPercent.setText("+"+indexBean.getRate()+"%");
                                    mSZ399006TvUpdate.setText("+"+indexBean.getPrice()+"");
                                }else{
                                    mSZ399006TvIndex.setTextColor(GREEN);
                                    mSZ399006TvPercent.setTextColor(GREEN);
                                    mSZ399006TvUpdate.setTextColor(GREEN);
                                    mSZ399006TvPercent.setText(indexBean.getRate()+"%");
                                    mSZ399006TvUpdate.setText(indexBean.getPrice()+"");
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mSH000001TvTitle = ButterKnife.findById(mSH000001GL,R.id.item_market_tv_title);
        mSH000001TvIndex = ButterKnife.findById(mSH000001GL,R.id.item_market_tv_index);
        mSH000001TvUpdate = ButterKnife.findById(mSH000001GL,R.id.item_market_tv_update);
        mSH000001TvPercent = ButterKnife.findById(mSH000001GL,R.id.item_market_tv_percent);

        mSZ399001TvTitle = ButterKnife.findById(mSZ399001GL,R.id.item_market_tv_title);
        mSZ399001TvIndex = ButterKnife.findById(mSZ399001GL,R.id.item_market_tv_index);
        mSZ399001TvUpdate = ButterKnife.findById(mSZ399001GL,R.id.item_market_tv_update);
        mSZ399001TvPercent = ButterKnife.findById(mSZ399001GL,R.id.item_market_tv_percent);

        mSZ399006TvTitle = ButterKnife.findById(mSZ399006GL,R.id.item_market_tv_title);
        mSZ399006TvIndex = ButterKnife.findById(mSZ399006GL,R.id.item_market_tv_index);
        mSZ399006TvUpdate = ButterKnife.findById(mSZ399006GL,R.id.item_market_tv_update);
        mSZ399006TvPercent = ButterKnife.findById(mSZ399006GL,R.id.item_market_tv_percent);

        mSH000300TvTitle = ButterKnife.findById(mSH000300GL,R.id.item_market_tv_title);
        mSH000300TvIndex = ButterKnife.findById(mSH000300GL,R.id.item_market_tv_index);
        mSH000300TvUpdate = ButterKnife.findById(mSH000300GL,R.id.item_market_tv_update);
        mSH000300TvPercent = ButterKnife.findById(mSH000300GL,R.id.item_market_tv_percent);

        mSH000016TvTitle = ButterKnife.findById(mSH000016GL,R.id.item_market_tv_title);
        mSH000016TvIndex = ButterKnife.findById(mSH000016GL,R.id.item_market_tv_index);
        mSH000016TvUpdate = ButterKnife.findById(mSH000016GL,R.id.item_market_tv_update);
        mSH000016TvPercent = ButterKnife.findById(mSH000016GL,R.id.item_market_tv_percent);

        mSH000905TvTitle = ButterKnife.findById(mSH000905GL,R.id.item_market_tv_title);
        mSH000905TvIndex = ButterKnife.findById(mSH000905GL,R.id.item_market_tv_index);
        mSH000905TvUpdate = ButterKnife.findById(mSH000905GL,R.id.item_market_tv_update);
        mSH000905TvPercent = ButterKnife.findById(mSH000905GL,R.id.item_market_tv_percent);
    }

    @Override
    protected void initListeners() {

    }
}

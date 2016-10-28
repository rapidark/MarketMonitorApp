package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sse.monitor.R;
import com.sse.monitor.bean.IndexDetailBean;
import com.sse.monitor.bean.IndexMinuteBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.di.components.IndexComponent;
import com.sse.monitor.mms.MmsUtil;
import com.sse.monitor.presenter.IndexDetailPresenter;
import com.sse.monitor.presenter.iview.IndexDetailView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/10/24.
 */
public class IndexDetailFragment extends BaseFragment implements IndexDetailView {
    private final static String TAG = "IndexDetailFragment";

    @Inject
    IndexDetailPresenter mIndexDetailPresenter;

    @BindView(R.id.index_detail_tv_index)
    TextView mIndexDetailTvIndex;
    @BindView(R.id.index_detail_tv_open_price)
    TextView mIndexDetailTvOpenPrice;
    @BindView(R.id.index_detail_tv_close_price)
    TextView mIndexDetailTvClosePrice;
    @BindView(R.id.index_detail_tv_price)
    TextView mIndexDetailTvPrice;
    @BindView(R.id.index_detail_tv_rate)
    TextView mIndexDetailTvRate;
    @BindView(R.id.index_detail_tv_turnover)
    TextView mIndexDetailTvTurnover;
    @BindView(R.id.index_detail_tv_amplitude)
    TextView mIndexDetailTvAmplitude;
    @BindView(R.id.index_detail_tv_max_price)
    TextView mIndexDetailTvMaxPrice;
    @BindView(R.id.index_detail_tv_min_prive)
    TextView mIndexDetailTvMinPrive;
    @BindView(R.id.index_detail_tv_volume)
    TextView mIndexDetailTvVolume;
    @BindView(R.id.index_detail_line_chart)
    LineChart mIndexDetailLineChart;
    @BindView(R.id.index_detail_header)
    LinearLayout mIndexDetailHeader;
    @BindView(R.id.index_detail_middle)
    LinearLayout mIndexDetailMiddle;
    @BindView(R.id.index_detail_chart_tv_max_price)
    TextView mIndexDetailChartTvMaxPrice;
    @BindView(R.id.index_detail_chart_tv_middle_price)
    TextView mIndexDetailChartTvMiddlePrice;
    @BindView(R.id.index_detail_chart_tv_min_price)
    TextView mIndexDetailChartTvMinPrice;
    @BindView(R.id.index_detail_chart_tv_max_rate)
    TextView mIndexDetailChartTvMaxRate;
    @BindView(R.id.index_detail_chart_tv_min_rate)
    TextView mIndexDetailChartTvMinRate;
    @BindView(R.id.index_detail_tv_title)
    TextView mIndexDetailTvTitle;
    @BindView(R.id.index_detail_rl_line_chart)
    RelativeLayout mIndexDetailRlLineChart;

    @BindColor(R.color.main_gray)
    int GRAY;
    @BindColor(R.color.red)
    int RED;
    @BindColor(R.color.green)
    int GREEN;
    @BindColor(R.color.black)
    int BLACK;

    float mDrawChartPercent = 0f;
    float mMaxYAxis = 0f;
    float mMinYAxis = 0f;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(IndexComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mIndexDetailPresenter.attachView(this);
        if (savedInstanceState == null) {
            this.mIndexDetailPresenter.loadStockData();
            this.mIndexDetailPresenter.loadStockMinuteData();
        }

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_detail;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        //设置网格
        mIndexDetailLineChart.setDrawBorders(true);
        mIndexDetailLineChart.setBorderColor(GRAY);
        //设置描述
        mIndexDetailLineChart.setDescription(null);
        //设置数据为空时提示
        mIndexDetailLineChart.setNoDataText("");
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mIndexDetailLineChart.setTouchEnabled(true);
        //设置是否可以拖拽，缩放
        mIndexDetailLineChart.setDragEnabled(true);
        mIndexDetailLineChart.setScaleEnabled(false);
        //Y轴设置
        YAxis yAxisLeft = mIndexDetailLineChart.getAxisLeft();
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setDrawZeroLine(false);
        yAxisLeft.setLabelCount(5, true);
        mIndexDetailLineChart.getAxisRight().setEnabled(false);
        //X轴设置
        XAxis xAxis = mIndexDetailLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(240);
        xAxis.setDrawLabels(false);
        xAxis.setLabelCount(5, true);
        xAxis.setTextSize(0);
        xAxis.setSpaceMax(0);
        //图例设置
        mIndexDetailLineChart.getLegend().setEnabled(false);
        //图间隙设置
        mIndexDetailLineChart.setViewPortOffsets(0, 0, 0, 0);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void renderIndexDetail(IndexDetailBean indexDetailBean) {
        mIndexDetailTvTitle.setText(indexDetailBean.getSymbol() + "(" + indexDetailBean.getSecurityId() + ")");

        //今开
        mIndexDetailTvOpenPrice.setText("今开\n" + MmsUtil.getDefaultString(indexDetailBean.getOpenPrice()));
        //昨收
        mIndexDetailTvClosePrice.setText("昨收\n" + MmsUtil.getDefaultString(indexDetailBean.getClosePrice()));
        //成交额
        String turnover;
        if (indexDetailBean.getTurnover() <= 10000) {
            turnover = indexDetailBean.getTurnover() + "万";
        } else {
            turnover = MmsUtil.getDefaultString(indexDetailBean.getTurnover() / 10000.00) + "亿";
        }
        mIndexDetailTvTurnover.setText("成交额\n" + turnover);
        //振幅
        mIndexDetailTvAmplitude.setText("振幅\n" + MmsUtil.getDefaultString(indexDetailBean.getAmplitude()) + "%");
        //指数
        mIndexDetailTvIndex.setText(MmsUtil.getDefaultString(indexDetailBean.getIndex()));
        //涨跌
        mIndexDetailTvPrice.setText(MmsUtil.getDefaultString(indexDetailBean.getPrice()));
        //涨跌百分比
        mIndexDetailTvRate.setText(MmsUtil.getDefaultString(indexDetailBean.getRate()) + "%");
        //最高
        mIndexDetailTvMaxPrice.setText("最高 " + MmsUtil.getDefaultString(indexDetailBean.getMaxPrice()));
        //最低
        mIndexDetailTvMinPrive.setText("最低 " + MmsUtil.getDefaultString(indexDetailBean.getMinPrice()));
        //成交量
        String volume;
        if (indexDetailBean.getVolume() <= 10000) {
            volume = indexDetailBean.getVolume() + "";
        } else if (indexDetailBean.getVolume() <= 100000000) {
            volume = MmsUtil.getDefaultString(indexDetailBean.getVolume() / 10000.00) + "万";
        } else {
            volume = MmsUtil.getDefaultString(indexDetailBean.getVolume() / 1000000000.00) + "亿";
        }
        mIndexDetailTvVolume.setText("成交量 " + volume);
        if (indexDetailBean.getPrice() > 0) {
            mIndexDetailHeader.setBackgroundColor(RED);
        } else if (indexDetailBean.getPrice() == 0) {
            mIndexDetailHeader.setBackgroundColor(BLACK);
        } else {
            mIndexDetailHeader.setBackgroundColor(GREEN);
        }

        //设定图表最高、最低值
        if(indexDetailBean.getMaxPrice()!=0&&indexDetailBean.getMinPrice()!=0){
            float up = indexDetailBean.getMaxPrice() / indexDetailBean.getClosePrice() - 1f;
            float down = 1f - indexDetailBean.getMinPrice() / indexDetailBean.getClosePrice();
            mDrawChartPercent = up > down ? up : down;
            BigDecimal b = new BigDecimal(mDrawChartPercent);
            mDrawChartPercent = b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
            mMaxYAxis = indexDetailBean.getClosePrice() * (1 + mDrawChartPercent);
            mMinYAxis = indexDetailBean.getClosePrice() * (1 - mDrawChartPercent);
            while (mMaxYAxis <= indexDetailBean.getMaxPrice() || mMinYAxis >= indexDetailBean.getMinPrice()) {
                mDrawChartPercent += 0.0002f;
                mMaxYAxis = indexDetailBean.getClosePrice() * (1 + mDrawChartPercent);
                mMinYAxis = indexDetailBean.getClosePrice() * (1 - mDrawChartPercent);
            }
        }else{
            mDrawChartPercent = 0.0002f;
            mMaxYAxis = indexDetailBean.getClosePrice() * (1 + mDrawChartPercent);
            mMinYAxis = indexDetailBean.getClosePrice() * (1 - mDrawChartPercent);
        }


        mIndexDetailChartTvMiddlePrice.setText(MmsUtil.getDefaultString(indexDetailBean.getClosePrice()));

    }

    @Override
    public void renderIndexMinute(List<IndexMinuteBean> indexMinuteBeanList) {
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < indexMinuteBeanList.size(); i++) {
            Entry entry = new Entry();
            entry.setX(i);
            entry.setY(indexMinuteBeanList.get(i).getIndex());
            yVals.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(yVals, null);
        lineDataSet.setColor(RED);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        mIndexDetailLineChart.setData(lineData);

        YAxis yAxisLeft = mIndexDetailLineChart.getAxisLeft();
        yAxisLeft.setAxisMaximum(mMaxYAxis);
        yAxisLeft.setAxisMinimum(mMinYAxis);

        mIndexDetailChartTvMaxPrice.setText(String.format("%.2f", mMaxYAxis));
        mIndexDetailChartTvMaxRate.setText(String.format("%.2f", mDrawChartPercent * 100f) + "%");
        mIndexDetailChartTvMinPrice.setText(String.format("%.2f", mMinYAxis));
        mIndexDetailChartTvMinRate.setText("-" + String.format("%.2f", mDrawChartPercent * 100f) + "%");

        mIndexDetailLineChart.invalidate();

        mIndexDetailRlLineChart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mIndexDetailPresenter.detachView();
    }
}

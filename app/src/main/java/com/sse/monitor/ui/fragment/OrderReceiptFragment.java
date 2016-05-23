package com.sse.monitor.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;

import com.shiki.recyclerview.FGORecyclerView;
import com.sse.monitor.R;
import com.sse.monitor.bean.OrderBean;
import com.sse.monitor.core.BaseFragment;
import com.sse.monitor.di.components.OrderReceiptComponent;
import com.sse.monitor.presenter.OrderReceiptPresenter;
import com.sse.monitor.presenter.iview.OrderReceiptView;
import com.sse.monitor.ui.adapter.OrderReceiptAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnCheckedChanged;

/**
 * Created by Eric on 2016/5/16.
 */
public class OrderReceiptFragment extends BaseFragment implements OrderReceiptView {
    @Bind(R.id.fgo_recycler_view)
    FGORecyclerView rvReceipt;
    @Bind(R.id.chk_allcheck)
    CheckBox chkAllCheck;
    @Inject
    OrderReceiptAdapter orderReceiptAdapter;
    @Inject
    OrderReceiptPresenter orderReceiptPresenter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        this.rvReceipt.setAdapter(this.orderReceiptAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(OrderReceiptComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.orderReceiptPresenter.attachView(this);
        if (savedInstanceState == null)
            this.orderReceiptPresenter.loadOrderData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_receipt;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        rvReceipt.setHasFixedSize(true);
        rvReceipt.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initListeners() {
        this.orderReceiptAdapter.setOnItemCheckListener(new OrderReceiptAdapter.OnItemCheckListener() {
            @Override
            public void onOrderItemChecked(CheckBox cbItem) {
                OrderBean order = (OrderBean) cbItem.getTag();
                order.setIsSelected(cbItem.isChecked());
            }
        });
    }

    @OnCheckedChanged(R.id.chk_allcheck)
    void changeOrderData() {
        this.orderReceiptAdapter.setOrderDataChecked(chkAllCheck.isChecked());
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.orderReceiptPresenter.detachView();
    }

    @Override
    public void renderOrderList(List<OrderBean> orderData) {
        if (orderData != null) {
            this.orderReceiptAdapter.setOrderData(orderData);
        }
    }
}

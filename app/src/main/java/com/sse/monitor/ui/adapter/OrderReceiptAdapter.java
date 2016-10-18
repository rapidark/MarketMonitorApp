package com.sse.monitor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.sse.monitor.R;
import com.sse.monitor.bean.OrderBean;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/5/16.
 */
public class OrderReceiptAdapter extends FGORecyclerViewAdapter<OrderReceiptAdapter.ReceiptViewHolder> {
    public interface OnItemCheckListener {
        void onOrderItemChecked(CheckBox cbItem);
    }

    private List<OrderBean>orderData;
    private final LayoutInflater layoutInflater;
    private OnItemCheckListener onItemCheckListener;

    @Inject
    public OrderReceiptAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orderData = Collections.emptyList();
    }

    public void setOrderData(List<OrderBean> orderData) {
        this.orderData = orderData;
        this.notifyDataSetChanged();
    }

    public void setOrderDataChecked(Boolean isChecked) {
        for (OrderBean order:orderData) {
            order.setIsSelected(isChecked);
        }
        this.notifyDataSetChanged();
    }

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public ReceiptViewHolder getViewHolder(View view) {
        return new ReceiptViewHolder(view);
    }

    @Override
    public ReceiptViewHolder onCreateViewHolder(ViewGroup parent) {
        final View view = this.layoutInflater.inflate(R.layout.item_order_receipt, parent, false);
        return new ReceiptViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return orderData.size();
    }

    @Override
    public void onBindViewHolder(ReceiptViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= orderData.size() : position < orderData.size()) && (customHeaderView != null ? position > 0 : true)) {
            final int index = customHeaderView != null ? position - 1 : position;
            final OrderBean order = orderData.get(index);
            holder.tvLogisticCode.setText("物流单号：" + order.getLogisticCode());
            holder.tvOrdCode.setText("订单编号：" + order.getOrdCode());
            holder.tvDeliveryCode.setText("配送单号：" + order.getDeliveryCode());
            holder.tvDeliveryAddr.setText("配送地址：" + order.getDeliveryAddr());
            holder.tvRecipient.setText("收件人：" + order.getRecipient());
            holder.tvOrdTel.setText("联系电话：" + order.getOrdTel());
            holder.tvExprItem.setText("快递物品：" + order.getExprItem());
            holder.chkSelected.setChecked(order.getIsSelected() == null ? false : order.getIsSelected());
            holder.chkSelected.setTag(order);
            holder.chkSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderReceiptAdapter.this.onItemCheckListener.onOrderItemChecked((CheckBox) v);
                }
            });
        }
    }

    static class ReceiptViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_logisticCode) TextView tvLogisticCode;
        @BindView(R.id.tv_ordCode) TextView tvOrdCode;
        @BindView(R.id.tv_deliveryCode) TextView tvDeliveryCode;
        @BindView(R.id.tv_deliveryAddr) TextView tvDeliveryAddr;
        @BindView(R.id.tv_recipient) TextView tvRecipient;
        @BindView(R.id.tv_ordTel) TextView tvOrdTel;
        @BindView(R.id.tv_exprItem) TextView tvExprItem;
        @BindView(R.id.chk_selected) CheckBox chkSelected;

        public ReceiptViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

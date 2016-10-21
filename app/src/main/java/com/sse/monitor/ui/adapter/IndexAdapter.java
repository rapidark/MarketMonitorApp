package com.sse.monitor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sse.monitor.R;
import com.sse.monitor.bean.IndexBean;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/10/19.
 */
public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {
    private List<IndexBean> mIndexData;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    @Inject
    public IndexAdapter(Context context) {
        this.mLayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mIndexData = Collections.emptyList();
        this.mContext = context;
    }

    public void setIndexData(List<IndexBean> indexData) {
        this.mIndexData = indexData;
        this.notifyDataSetChanged();
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.mLayoutInflater.inflate(R.layout.item_index, parent, false);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        //Log.d("IndexAdapter","onBindViewHolder position:"+position);
        IndexBean indexBean = mIndexData.get(position);
        if(indexBean.getSymbol().startsWith("IC")){
            holder.tvTitle.setText("中证"+indexBean.getSymbol().substring(2));
        }else if(indexBean.getSymbol().startsWith("IF")){
            holder.tvTitle.setText("沪深"+indexBean.getSymbol().substring(2));
        }else if(indexBean.getSymbol().startsWith("IH")){
            holder.tvTitle.setText("上证"+indexBean.getSymbol().substring(2));
        }else{
            holder.tvTitle.setText(indexBean.getSymbol());
        }

        holder.tvIndex.setText(String.format("%.2f", indexBean.getIndex()));
        holder.tvPrice.setText(String.format("%.2f", indexBean.getPrice()));
        holder.tvRate.setText(String.format("%.2f", indexBean.getRate()) + "%");
        if(indexBean.getPrice()>0){
            holder.tvIndex.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.tvRate.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.tvPrice.setText("+"+holder.tvPrice.getText());
            holder.tvRate.setText("+"+holder.tvRate.getText());
        }else if(indexBean.getPrice()==0){
            holder.tvIndex.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tvRate.setTextColor(mContext.getResources().getColor(R.color.black));
        }else{
            holder.tvIndex.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.tvRate.setTextColor(mContext.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return mIndexData.size();
    }

    static class IndexViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_index_tv_title) TextView tvTitle;
        @BindView(R.id.item_index_tv_index) TextView tvIndex;
        @BindView(R.id.item_index_tv_price) TextView tvPrice;
        @BindView(R.id.item_index_tv_rate) TextView tvRate;

        public IndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

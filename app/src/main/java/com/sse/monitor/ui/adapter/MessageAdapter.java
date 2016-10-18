package com.sse.monitor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.sse.monitor.R;
import com.sse.monitor.bean.MessageBean;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/5/9.
 */
public class MessageAdapter extends FGORecyclerViewAdapter<MessageAdapter.MessageViewHolder> {
    public interface OnItemClickListener {
        void onMessageItemClicked(MessageBean messageBean);
    }

    private List<MessageBean> messageData;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    public MessageAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messageData = Collections.emptyList();
    }

    public void updateMessage(MessageBean message) {
        MessageBean newMessage = this.messageData.get(message.getIndex());
        newMessage.setMessageFlag("1");
        this.notifyItemChanged(message.getPosition());
    }

    public void setMessageData(List<MessageBean> messageData) {
        this.messageData = messageData;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MessageViewHolder getViewHolder(View view) {
        return new MessageViewHolder(view);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent) {
        final View view = this.layoutInflater.inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return messageData.size();
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= messageData.size() : position < messageData.size()) && (customHeaderView != null ? position > 0 : true)) {
            final int index = customHeaderView != null ? position - 1 : position;
            final MessageBean message = messageData.get(index);
            holder.tvMsgContent.setText(message.getMessageContent());
            holder.tvMsgDate.setText("发布时间：" + message.getMessageDate());
            holder.tvMsgTitle.setText("公告标题：" + message.getMessageTitle());
            holder.tvMsgUser.setText("发布人：" + message.getMessageName());
            if (message.getMessageFlag().equals("1")) {
                holder.tvMsgFlag.setVisibility(View.GONE);
            } else {
                holder.tvMsgFlag.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MessageAdapter.this.onItemClickListener != null) {
                        message.setIndex(index);
                        message.setPosition(position);
                        MessageAdapter.this.onItemClickListener.onMessageItemClicked(message);
                    }
                }
            });
        }
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_msgDate) TextView tvMsgDate;
        @BindView(R.id.tv_msgUser) TextView tvMsgUser;
        @BindView(R.id.tv_msgTitle) TextView tvMsgTitle;
        @BindView(R.id.tv_msgContent) TextView tvMsgContent;
        @BindView(R.id.tv_MsgFlag) TextView tvMsgFlag;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

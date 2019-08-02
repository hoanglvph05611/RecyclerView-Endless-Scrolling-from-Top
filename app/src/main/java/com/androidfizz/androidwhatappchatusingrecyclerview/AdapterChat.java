package com.androidfizz.androidwhatappchatusingrecyclerview;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Vishnu on 3/11/2018.
 */

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_MY_MSG = 1;
    private static final int VIEW_OTHERS_MSG = 2;
    private static final int VIEW_LOAD_MORE = 3;
    private static final String TAG = AdapterChat.class.toString();
    private List<ModelMessage> modelMessageList;
    boolean isLoading = false, isMoreDataAvailable = true;

    AdapterChat(List<ModelMessage> modelMessageList) {
        this.modelMessageList = modelMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (modelMessageList.get(position) == null)
            return VIEW_LOAD_MORE;
        else if (modelMessageList.get(position).getMsgType() == VIEW_MY_MSG) {
            return VIEW_MY_MSG;
        } else {
            return VIEW_OTHERS_MSG;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_MY_MSG) {
            return new MyChatViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_my_msg, parent, false));
        } else if (viewType == VIEW_OTHERS_MSG)
            return new OthersChatViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.others_chat_msg, parent, false));
        else
            return new LoadMore(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_load_more, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position <= 1 && isMoreDataAvailable && !isLoading && onLoadMoreListner != null  ) {
            isLoading = true;
            onLoadMoreListner.onLoadMore(position);
        }

        ModelMessage single = modelMessageList.get(position);

        if (holder.getItemViewType() == VIEW_MY_MSG) {
            MyChatViewHolder myChatvHolder = (MyChatViewHolder) holder;
            myChatvHolder.tvMsg.setText(single.getMsg());
            myChatvHolder.tvTime.setText(single.getDateCreated());

        } else if (holder.getItemViewType() == VIEW_OTHERS_MSG) {
            OthersChatViewHolder othersChatvHolder = (OthersChatViewHolder) holder;
            othersChatvHolder.tvID.setText("+91 88282 " + single.getMsgID());
            othersChatvHolder.tvName.setText(single.getName());
            othersChatvHolder.tvMsg.setText(single.getMsg());
            othersChatvHolder.tvTime.setText(single.getDateCreated());
        }

    }

    @Override
    public int getItemCount() {
        return null != modelMessageList ? modelMessageList.size() : 0;
    }


    class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg, tvTime;

        MyChatViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tvMsg);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    class OthersChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID, tvName, tvMsg, tvTime;

        OthersChatViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tvName = itemView.findViewById(R.id.tvName);
            tvMsg = itemView.findViewById(R.id.tvMsg);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    class LoadMore extends RecyclerView.ViewHolder {

        LoadMore(View itemView) {
            super(itemView);
        }
    }

    void sendMsg(String msg) {
        int msgID = new Random().nextInt(10000 - 1000) + 1000;
        String msgTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
        modelMessageList.add(new ModelMessage(msgID, VIEW_MY_MSG, "", msg, msgTime));
        notifyItemInserted(modelMessageList.size() - 1);
    }

    //ON_LOAD_MORE
    private OnLoadMoreListner onLoadMoreListner;

    interface OnLoadMoreListner {
        void onLoadMore(int pos);
    }

    public void setOnLoadMoreListner(OnLoadMoreListner onLoadMoreListner) {
        this.onLoadMoreListner = onLoadMoreListner;
    }


    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }


    public void notifiyRangeChanged(int size) {
        isLoading = false;
        notifyItemRangeInserted(0,size);
    }
    public void notifiyListChanged() {
        isLoading = false;
        notifyDataSetChanged();
    }
}

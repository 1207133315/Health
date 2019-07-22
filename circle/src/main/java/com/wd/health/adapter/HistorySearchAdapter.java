package com.wd.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.health.R;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;//item点击监听接口

    private List<String> histotyList = new ArrayList<String>();

    public HistorySearchAdapter(Context context, List<String> histotyList) {
        this.mContext = context;
        this.histotyList = histotyList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private Button deleteImg;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.search_history_item_tv);
            deleteImg = (Button) itemView.findViewById(R.id.search_history_item_img);
            this.itemView = itemView;
        }
    }

    @Override
    public HistorySearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.history_search_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HistorySearchAdapter.ViewHolder holder, int position) {
        holder.nameTv.setText(histotyList.get(position));
        holder.nameTv.setTag(histotyList.get(position));
        holder.deleteImg.setTag(histotyList.get(position));
        holder.nameTv.setOnClickListener(this);
        holder.deleteImg.setOnClickListener(this);
    }

    public void onClick(View v) {
        int i = v.getId();//点击历史纪录名称时调用
//点击删除按钮时调用
            if (i == R.id.search_history_item_tv) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemNameTvClick(v, (String) v.getTag());
                }

            } else if (i == R.id.search_history_item_img) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemDeleteImgClick(v, (String) v.getTag());
                }

            } else {
        }
    }

    /**
     * 设置item点击监听器
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return histotyList.size();
    }

    /**
     * item点击接口
     */
    public interface OnItemClickListener {
        void onItemNameTvClick(View v, String name);//点击历史纪录名称时
        void onItemDeleteImgClick(View v, String name);//点击删除按钮时
    }

}

package com.wd.health.activity.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wd.health.R;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.model.Message;

/**
 * com.wd.health.activity.adapter
 *
 * @author 李宁康
 * @date 2019 2019/08/02 20:06
 */
public class JG_details_Adapter extends RecyclerView.Adapter {

    private OnItemClickListener mOnItemClickListener = null;

    private Context MyContext;
    private List<Message> mList;

    public JG_details_Adapter(Context context) {
        this.MyContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(MyContext);
        View view = from.inflate(R.layout.itme, parent, false);
        return new JG_details_holder(view, MyContext,mOnItemClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JG_details_holder holder1 = (JG_details_holder) holder;
        if (mList!=null||mList.size()>0){
            holder1.setHolderData(mList.get(position),position);
            //将position保存在itemView的Tag中，以便点击时进行获取   ----------------------
            holder.itemView.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {//-------------
        this.mOnItemClickListener = listener;
    }


    public void setData(List<Message> data) {
        this.mList = data;
    }

    public void removeItem(int position) {
        mList.remove(position);
    }

    //define interface
    public interface OnItemClickListener {  //--------------------------
        void onItemClick(View view, int position);
    }

}


package com.wd.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.health.bean.CircleFindDepartmentBean;
import com.wd.health.circle.R;
import com.wd.health.bean.CircleBingZhengBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleWritBingZhengAdapter extends RecyclerView.Adapter<CircleWritBingZhengAdapter.MyViewHolder> {
    Context context;
    List<CircleBingZhengBean> mList;
    private MyViewHolder myViewHolder;

    public CircleWritBingZhengAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CircleWritBingZhengAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circle_writ_item_bingzheng_layout, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleWritBingZhengAdapter.MyViewHolder holder, int position) {
        CircleBingZhengBean circleBingZhengBean = mList.get(position);
        String name = circleBingZhengBean.getName();
        int department_id = circleBingZhengBean.getId();
        myViewHolder.text_name.setText(name);
        myViewHolder.text_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.showCall(department_id, name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<CircleBingZhengBean> bingzheng_result) {
        if (!bingzheng_result.isEmpty()) {
            mList.clear();
        }
        mList.addAll(bingzheng_result);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.circle_writ_item_bingzheng_text);

        }
    }

    public interface Call {
        void showCall(int id, String name);
    }

    public Call call;

    public void setCall(Call call) {
        this.call = call;
    }

}

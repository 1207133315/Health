package com.wd.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.health.bean.CircleFindDepartmentBean;
import com.wd.health.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleWritDepartmentAdapter extends RecyclerView.Adapter<CircleWritDepartmentAdapter.MyViewHolder> {
    Context context;
    List<CircleFindDepartmentBean> mList;
    private MyViewHolder myViewHolder;

    public CircleWritDepartmentAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CircleWritDepartmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circle_writ_item_department_layout, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleWritDepartmentAdapter.MyViewHolder holder, int position) {
        CircleFindDepartmentBean circleFindDepartmentBean = mList.get(position);
        String departmentName = circleFindDepartmentBean.getDepartmentName();
        int department_id = circleFindDepartmentBean.getId();
        myViewHolder.text_name.setText(departmentName);
        myViewHolder.text_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.showCall(department_id, departmentName);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<CircleFindDepartmentBean> keshi_result) {
        mList.addAll(keshi_result);

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.circle_writ_item_text);

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

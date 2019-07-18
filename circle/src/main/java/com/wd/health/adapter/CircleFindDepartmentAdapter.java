package com.wd.health.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bw.health.bean.CircleFindDepartmentBean;
import com.bw.health.bean.Result;
import com.wd.health.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleFindDepartmentAdapter extends RecyclerView.Adapter<CircleFindDepartmentAdapter.MyViewHolder> {
    Context context;
    List<CircleFindDepartmentBean> mList;
    private MyViewHolder myViewHolder;

    public CircleFindDepartmentAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CircleFindDepartmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circle_item_finddepartment_layout, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleFindDepartmentAdapter.MyViewHolder holder, int position) {
        CircleFindDepartmentBean circleFindDepartmentBean = mList.get(position);
        String departmentName = circleFindDepartmentBean.getDepartmentName();
        Long department_id = circleFindDepartmentBean.getId();
        myViewHolder.radio1_text.setText(departmentName);


        myViewHolder.radio1_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.showCall(department_id, departmentName);
            }
        });
       /* myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.showCall(mList.get(position).id,departmentName);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void getData(List<CircleFindDepartmentBean> result) {
        mList.addAll(result);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox radio1_text;
        LinearLayout back;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            radio1_text = itemView.findViewById(R.id.item1_radio1_text);
            back = itemView.findViewById(R.id.back);
        }
    }

    public interface Call {
        void showCall(Long id, String name);
    }

    public Call call;

    public void setCall(Call call) {
        this.call = call;
    }

}

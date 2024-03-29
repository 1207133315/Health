package com.wd.health.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.core.WDApplication;
import com.bw.health.util.DateUtils;
import com.wd.health.mine.R;
import com.wd.health.activity.CircleCommentActivity;
import com.wd.health.bean.PatientsCircleBean;
import com.wd.health.bean.RecordListBean;
import com.wd.health.utils.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/30
 * @Description: 我的病友圈
 */
public class ShowCircleAdapter extends RecyclerView.Adapter<ShowCircleAdapter.ViewHolder> {
    private Context context;
    private List<PatientsCircleBean> mList = new ArrayList<>();

    public ShowCircleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.patients_circle_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PatientsCircleBean patientsCircleBean = mList.get(position);
        String title = patientsCircleBean.getTitle();
        String detail = patientsCircleBean.getDetail();
        int sickCircleId = patientsCircleBean.getSickCircleId();
        long releaseTime = patientsCircleBean.getReleaseTime();

        holder.title_text.setText(title);
        holder.detail_text.setText(detail);

/*
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date(releaseTime));*/

        Date dateOld = new Date(releaseTime);
        String s = DateTimeUtil.dateToStr(dateOld);

        String[] split = s.split("-");
      //  Log.i("sp", split[0]);
       // Log.i("sp", split[1]);
      //  Log.i("sp", split[2]);

        holder.year_text.setText(split[0] + "年");
        holder.month_text.setText(split[1] + "月");
        holder.data_text.setText(split[2] + "");



        int datecompareAfter = compareDate(new Date(releaseTime), dateOld);
        int daecompareBefore = compareDate(dateOld, getOneYear());
        if (datecompareAfter == -1 && daecompareBefore == -1) {

            //如果不是在一年以内,则弹出提示
            holder.year_text.setVisibility(View.VISIBLE);
        }else{
            //在一年以内做的逻辑
            if (position==0){
                holder.year_text.setVisibility(View.VISIBLE);
            }else {
                holder.year_text.setVisibility(View.GONE);
            }
        }


        //点击查看评论
        holder.show_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CircleCommentActivity.class);
                intent.putExtra("id", String.valueOf(sickCircleId));
                context.startActivity(intent);

                call.ShowCall();
                //Toast.makeText(context, "查看更多评论", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // 比较时间
    public int compareDate(Date d1, Date d2) {
        if (d1.getTime() > d2.getTime()) {
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
            return -1;
        } else {// 相等
            return 0;
        }
    }
    //當前時間加1年
    public Date getOneYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.getTime();
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<PatientsCircleBean> result_mycircle) {
        mList.addAll(result_mycircle);
    }

    public void clear() {
        mList.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView year_text;
        private final TextView month_text;
        private final TextView data_text;
        private final TextView title_text;
        private final TextView detail_text;
        private final TextView show_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year_text = itemView.findViewById(R.id.patients_circle_year);
            month_text = itemView.findViewById(R.id.patients_circle_month);
            data_text = itemView.findViewById(R.id.patients_circle_data);
            title_text = itemView.findViewById(R.id.patients_circle_title);
            detail_text = itemView.findViewById(R.id.patients_circle_detail);
            show_text = itemView.findViewById(R.id.patients_circle_show);
        }
    }

    public Call call;

    public void setCall(Call call) {
        this.call = call;
    }

    public interface Call {
        void ShowCall();
    }

}

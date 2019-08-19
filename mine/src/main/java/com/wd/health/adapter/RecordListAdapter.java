package com.wd.health.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.health.core.WDApplication;
import com.bw.health.util.DateUtils;
import com.wd.health.mine.R;
import com.wd.health.activity.MyWallteActivity;
import com.wd.health.bean.RecordListBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * com.wd.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/26 20:09
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {
    private Context context=WDApplication.getContext();
    private List<RecordListBean> list=new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.wallet_itme,parent,false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         RecordListBean recordListBean = list.get(position);
         if (recordListBean.direction==1){
             holder.price.setTextColor(R.color.red);
             holder.price.setText("+"+recordListBean.changeNum);
         }else {
             holder.price.setTextColor(R.color.blue);
             holder.price.setText("-"+recordListBean.changeNum);
         }
         switch (recordListBean.type){
             case 1:
                 holder.title.setText("签到");
                 break;
             case 2:
                 holder.title.setText("病友圈首评");
                 break;
             case 3:
                 holder.title.setText("首发病友圈");
                 break;
             case 4:
                 holder.title.setText("完善档案");
                 break;
             case 5:
                 holder.title.setText("健康测评");
                 break;
             case 6:
                 holder.title.setText("悬赏消费");
                 break;
             case 7:
                 holder.title.setText("悬赏奖励");
                 break;
             case 8:
                 holder.title.setText("邀请奖励");
                 break;
             case 9:
                 holder.title.setText("问诊消费");
                 break;
             case 10:
                 holder.title.setText("问诊收入");
                 break;
             case 11:
                 holder.title.setText("观看资讯");
                 break;
             case 12:
                 holder.title.setText("送礼物");
                 break;
             case 13:
                 holder.title.setText("绑定身份证");
                 break;
             case 14:
                 holder.title.setText("绑定银行卡");
                 break;
                 case 15:
                 holder.title.setText("充值");
                 break;
             case 16:
                 holder.title.setText("提现");
                 break;


         }
        try {
            holder.time.setText(DateUtils.dateTransformer(recordListBean.createTime,"yyyy.mm.ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<RecordListBean> result) {
        if (result!=null)
            list.addAll(result);
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);
            price=itemView.findViewById(R.id.price);
        }
    }
}

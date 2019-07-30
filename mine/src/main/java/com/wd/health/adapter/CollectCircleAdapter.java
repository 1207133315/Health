package com.wd.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.exception.ApiException;
import com.bw.health.util.DateUtils;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.bean.CollectCircleBean;
import com.wd.health.presenter.UnCirclePresenter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * com.wd.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/26 11:26
 */
public class CollectCircleAdapter extends RecyclerView.Adapter<CollectCircleAdapter.ViewHolder> {
    private List<CollectCircleBean> list=new ArrayList<>();
    private Context context;
    private LoginBean loginBean;
    public CollectCircleAdapter(Context context) {

        this.context = context;
        final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()>0)
            loginBean = loginBeans.get(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.circlesearch_item_layout1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CollectCircleBean collectCircleBean = list.get(position);
        holder.circle_list_title.setText(collectCircleBean.title);
        try {
            holder.circle_list_time.setText(DateUtils.dateTransformer(collectCircleBean.createTime,null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.circle_list_detail.setText(collectCircleBean.disease);
        holder.circle_list_jianyi_num.setText(collectCircleBean.commentNum+"");
        holder.circle_list_shoucang_num.setText(collectCircleBean.collectionNum+"");
       holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            private float moveX;
            private float x;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();
                        if (x>moveX){
                            if (x-moveX>=60){
                               holder. shanchu.setVisibility(View.VISIBLE);
                            }else {
                                holder.shanchu.setVisibility(View.GONE);
                            }
                        }else {
                            holder.shanchu.setVisibility(View.GONE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });
        holder.shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.unCirclePresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),list.get(position).sickCircleId);
                list.remove(position);
                holder.shanchu.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size()  ;
    }

    public void clear() {
        list.clear();
    }

    public void setList(List<CollectCircleBean> list) {
        this.list .addAll(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView circle_list_title;
        TextView circle_list_time;
        TextView circle_list_detail;
        TextView circle_list_shoucang_num;
        TextView circle_list_jianyi_num;
        ImageView shanchu;
       UnCirclePresenter unCirclePresenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle_list_title=itemView.findViewById(R.id.circle_list_title);
            circle_list_time=itemView.findViewById(R.id.circle_list_time);
            circle_list_detail=itemView.findViewById(R.id.circle_list_detail);
            circle_list_shoucang_num=itemView.findViewById(R.id.circle_list_shoucang_num);
            circle_list_jianyi_num=itemView.findViewById(R.id.circle_list_jianyi_num);
            shanchu=itemView.findViewById(R.id.shanchu1);
            unCirclePresenter = new UnCirclePresenter(new UnCircle());
        }
    }

    public class UnCircle implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(context, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(context, ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

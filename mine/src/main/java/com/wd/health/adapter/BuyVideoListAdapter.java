package com.wd.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.exception.ApiException;
import com.bw.health.util.DateUtils;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.bean.CollectVideoBean;
import com.wd.health.presenter.DeleteVideoBuyPresenter;
import com.wd.health.presenter.UnVideoPresenter;
import com.wd.health.view.MyVideo1Player;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * com.wd.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/26 08:55
 */
public class BuyVideoListAdapter extends RecyclerView.Adapter<BuyVideoListAdapter.ViewHolder> {
    private Context context;
    private List<CollectVideoBean> list =new ArrayList<>();;
    private LoginBean loginBean;
    public BuyVideoListAdapter(Context context) {
        this.context = context;
        final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()>0)
            loginBean = loginBeans.get(0);
    }

    public void setList(List<CollectVideoBean> list) {
        if (list!=null)
        this.list.addAll(list);
    }
    public void clear(){
        list.clear();
    }

    private String url;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.video_itme2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CollectVideoBean collectVideoBean = list.get(position);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,594);
        holder.itemView.setLayoutParams(layoutParams);
        url=list.get(position).originalUrl;
        holder.video.setUp(url,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "饺子请闭眼");
        holder.title.setText(collectVideoBean.title);
         long createTime = collectVideoBean.createTime;
        try {
            final String s = DateUtils.dateTransformer(createTime, null);
            holder.time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (holder.video.isPlay()){
            holder.stop.setVisibility(View.GONE);
        }else {
            holder.stop.setVisibility(View.VISIBLE);
        }
        holder.video.setSetclick(new MyVideo1Player.setclick() {
            @Override
            public void click() {
                if (holder.video.isPlay()){
                    holder.stop.setVisibility(View.VISIBLE);
                    holder.video.goOnPlayOnPause();
                }else {
                    holder.stop.setVisibility(View.GONE);
                   holder.video.startVideo();
                }
            }
        });


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.deleteVideoBuyPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),list.get(position).videoId);
                list.remove(position);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MyVideo1Player video;

        TextView title;


        @BindView(R2.id.remove)
        TextView remove;
        @BindView(R2.id.time)
        TextView time;
        ImageView stop;
        DeleteVideoBuyPresenter deleteVideoBuyPresenter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            video=itemView.findViewById(R.id.video);
            title=itemView.findViewById(R.id.title);
            stop=itemView.findViewById(R.id.stop);
            ButterKnife.bind(this,itemView);
            deleteVideoBuyPresenter = new DeleteVideoBuyPresenter(new DeleteVideoBuy());
        }
    }
    public class DeleteVideoBuy implements DataCall<Result>{
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

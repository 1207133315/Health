package com.wd.health.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.adapter.PlateListAdapter;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.kd.easybarrage.Barrage;
import com.kd.easybarrage.BarrageView;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.bean.CommentBean;
import com.wd.health.bean.VideoBean;
import com.wd.health.presenter.AddVideoPresenter;
import com.wd.health.presenter.CommentListPresenter;
import com.wd.health.view.MyVideoPlayer;

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
 * @date 2019 2019/07/16 21:40
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private List<VideoBean> list = new ArrayList<>();
    private BarrageView barrageView;
    private List<Barrage> mBarrages = new ArrayList<>();

    private int lastItem = -1;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    public String content;

    public void setContent(String content) {
        this.content = content;
    }

    private String url;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.shipin_itme, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoBean videoBean = list.get(position);


        if (videoBean.whetherBuy == 2) {
            url = videoBean.shearUrl;
            holder.buy2.setVisibility(View.VISIBLE);
            holder.buy.setVisibility(View.VISIBLE);
            holder.pinglun.setVisibility(View.GONE);

        } else {
            url = videoBean.originalUrl;
            holder.buy2.setVisibility(View.INVISIBLE);
            holder.buy.setVisibility(View.GONE);
            holder.pinglun.setVisibility(View.VISIBLE);

        }
        holder.video.setUp(url,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "");
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        if (videoBean.whetherCollection == 2) {
            holder.shoucang.setChecked(false);
        } else {
            holder.shoucang.setChecked(true);
        }

        holder.shoucang.setTag(videoBean);
        holder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VideoBean tag = (VideoBean) view.getTag();
                final CheckBox checkBox = (CheckBox) view;
                if (tag.whetherCollection == 2) {
                    sCclick.click(tag, position);
                    // notifyDataSetChanged();
                } else {
                    holder.shoucang.setChecked(true);
                    // notifyDataSetChanged();
                }


            }
        });
        if (position == 0) {
            holder.video.startVideo();
        }
        holder.title.setText(videoBean.title);
        holder.text.setText(videoBean.abstracts);
        if (videoBean.buyNum >= 10000) {
            holder.num.setText((videoBean.buyNum) / 10000 + "万人");
        } else {
            holder.num.setText(videoBean.buyNum + "人");
        }
        if (holder.video.isPlay()) {
            holder.stop.setVisibility(View.GONE);
        }
        holder.video.setSetclick(new MyVideoPlayer.setclick() {
            @Override
            public void click() {
                if (holder.video.isPlay()) {
                    holder.video.goOnPlayOnPause();
                    holder.stop.setVisibility(View.VISIBLE);
                } else {
                    holder.stop.setVisibility(View.GONE);
                    holder.video.goOnPlayOnResume();
                }
            }
        });

        holder.buy.setTag(list.get(position));
        //购买视频
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoBean tag = (VideoBean) view.getTag();
                buyClick.click(tag, position);
            }
        });
        //购买视频
        holder.buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyClick.click(list.get(position), position);
            }
        });


        holder.danmu.setTag(list.get(position));
        holder.danmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final VideoBean tag = (VideoBean) view.getTag();
                final CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    barrageView = holder.danmu_content;
                    holder.commentListPresenter.reqeust(tag.id);
                    holder.danmu_content.setVisibility(View.VISIBLE);
                } else {
                    mBarrages.clear();
                    holder.danmu_content.destroy();
                    holder.danmu_content.setVisibility(View.GONE);
                }

            }
        });
        holder.pinglun.setTag(list.get(position));
        holder.pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoBean tag = (VideoBean) view.getTag();
                if (pingLun != null)
                    pingLun.click(tag, holder.danmu_content, position);
                if (content != null) {
                    holder.danmu_content.addBarrage(new Barrage(content,R.color.colorAccent));
                }

            }
        });

    }

    private PingLun pingLun;

    public void setPingLun(PingLun pingLun) {
        this.pingLun = pingLun;
    }

    public interface PingLun {
        void click(VideoBean videoBean, BarrageView barrageView, int index);
    }

    private DanMu danMu;

    public void setDanMu(DanMu danMu) {
        this.danMu = danMu;
    }


    public interface DanMu {
        void click(VideoBean videoBean);
    }

    public int getLastItem() {
        return lastItem;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<VideoBean> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    private BuyClick buyClick;

    public void setBuyClick(BuyClick buyClick) {
        this.buyClick = buyClick;
    }

    public interface BuyClick {
        void click(VideoBean videoBean, int index);
    }

    private SCclick sCclick;

    public void setsCclick(SCclick sCclick) {
        this.sCclick = sCclick;
    }

    public interface SCclick {
        void click(VideoBean videoBean, int i);
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.video)
        public MyVideoPlayer video;
        @BindView(R2.id.tit)
        TextView title;
        @BindView(R2.id.text)
        TextView text;
        @BindView(R2.id.buy)
        ImageView buy;
        @BindView(R2.id.buy2)
        LinearLayout buy2;
        @BindView(R2.id.shoucang)
        CheckBox shoucang;
        @BindView(R2.id.danmu)
        CheckBox danmu;
        @BindView(R2.id.num)
        TextView num;
        @BindView(R2.id.pinglun)
        ImageView pinglun;

        public BarrageView danmu_content;

        @BindView(R2.id.stop)
        public ImageView stop;
        @BindView(R2.id.re_layout)
        RelativeLayout re_layout;
        CommentListPresenter commentListPresenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            danmu_content = itemView.findViewById(R.id.danmu_content);
            commentListPresenter = new CommentListPresenter(new Comment());

        }

        public void start() {
            video.startVideo();
        }
    }

    public class Comment implements DataCall<Result<List<CommentBean>>> {
        @Override
        public void success(Result<List<CommentBean>> data, Object... args) {
            mBarrages.clear();
            for (int i = 0; i < data.getResult().size(); i++) {
                Barrage barrage = new Barrage(data.getResult().get(i).content, R.color.white, true);
                mBarrages.add(barrage);
            }
            if (barrageView != null) {
                barrageView.setBarrages(mBarrages);
            }
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(context, "" + data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}

package com.wd.health;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.DaoSession;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.adapter.CategoryListAdapter;
import com.wd.health.adapter.VideoAdapter;
import com.wd.health.bean.LeimuBean;
import com.wd.health.bean.VideoBean;
import com.wd.health.presenter.AddVideoPresenter;
import com.wd.health.presenter.CategoryListPresenter;
import com.wd.health.presenter.VideoListPresenter;
import com.wd.health.view.MyLinearLayoutManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;


/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/16 16:58
 */

@Route(path = "/ShiPinFrag/")
public class ShiPinFragment extends WDFragment {

    @BindView(R2.id.recycler)
    XRecyclerView recycler;
    private VideoListPresenter videoListPresenter;
    private LoginBean user;
    private VideoAdapter videoAdapter;
    @BindView(R2.id.show)
    ImageView show;
    @BindView(R2.id.show2)
    ImageView show2;
    @BindView(R2.id.toprecycler)
    RecyclerView toprecycler;
    private CategoryListPresenter categoryListPresenter;
    private CategoryListAdapter categoryListAdapter;
    private long id = 1;
    private int page = 1;
    @BindView(R2.id.top)
    RelativeLayout top;
    private PagerSnapHelper snapHelper;
    private MyLinearLayoutManager linearLayoutManager;
    private int height;
    int totalDy = 0;

    @Override
    public String getPageName() {
        return "视频";
    }

    private List<VideoBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.shipin_frag_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        final LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
        user = userDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).unique();

        videoListPresenter = new VideoListPresenter(new VideoList());

        linearLayoutManager = new MyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(getContext());
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler);
        recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                videoListPresenter.reqeust(user.getId(), user.getSessionId(), id, page, 5);
            }

            @Override
            public void onLoadMore() {
                page++;
                videoListPresenter.reqeust(user.getId(), user.getSessionId(), id, page, 5);
            }
        });
        recycler.setAdapter(videoAdapter);
        recycler.refresh();

        WindowManager manager = getActivity().getWindowManager();
        height = manager.getDefaultDisplay().getHeight();
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private View lastItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy>0){
                    totalDy+=dy;
                }

                if (dy<0){
                     int abs = Math.abs(dy);
                    totalDy+=abs;
                }

                if (totalDy>height){



                        Log.i("lnk", "下滑: "+dy);

                }

            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    // if (totalDy>=height){
                    //显示回到顶部按钮
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                    if (viewHolder != null && viewHolder instanceof VideoAdapter.ViewHolder) {
                                      if (totalDy>=height){
                                          JZVideoPlayer.releaseAllVideos();
                                          ((VideoAdapter.ViewHolder) viewHolder).video.startVideo();
                                      }




                                      /*if (lastItem==null){//
                                          lastItem = view;
                                          JZVideoPlayer.releaseAllVideos();
                                          ((VideoAdapter.ViewHolder) viewHolder).video.startVideo();
                                      }else{
                                          if (lastItem.equals(view)){

                                          }else{
                                              JZVideoPlayer.releaseAllVideos();
                                              ((VideoAdapter.ViewHolder) viewHolder).video.startVideo();
                                              //啥都不用做
                                          }


                                      }*/
                    }

                    // }
                    totalDy=0;

                    //获取RecyclerView滑动时候的状态
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {//拖动中

                }


            }
        });

        toprecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryListPresenter = new CategoryListPresenter(new CategoryList());
        categoryListPresenter.reqeust();
        categoryListAdapter = new CategoryListAdapter(getContext());
        toprecycler.setAdapter(categoryListAdapter);

        categoryListAdapter.setCall(new CategoryListAdapter.Call() {
            @Override
            public void dataCall(LeimuBean leimuBean) {
                JZVideoPlayer.releaseAllVideos();
                id = leimuBean.id;
                recycler.refresh();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setVisibility(View.GONE);
                toprecycler.setVisibility(View.VISIBLE);
            }
        });

        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iSkill.eat(true);
                top.setVisibility(View.VISIBLE);
            }
        });

        getHomeActivity();
    }
    AddVideoPresenter addVideoPresenter;
    @Override
    public void onResume() {
        super.onResume();
        addVideoPresenter = new AddVideoPresenter(new AddVideo());
        LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
         List<LoginBean> loginBeans = userDao.loadAll();
         videoAdapter.setsCclick(new VideoAdapter.SCclick() {
             @Override
             public void click(VideoBean videoBean) {
                 if (loginBeans.size()>0){
                     final LoginBean user = loginBeans.get(0);
                     addVideoPresenter.reqeust(user.getId(),user.getSessionId(),videoBean.id);
                 }else {
                     intentByRouter("/LoginActivity/");
                 }
             }
         });


    }

    public class AddVideo implements DataCall<Result> {
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            if (data.getDisplayMessage().equals("请先登录")){
                intentByRouter("/LoginActivity/");
            }

        }
    }

    private void getHomeActivity() {


    }


    private ISkill iSkill;

    public void setiSkill(ISkill iSkill) {
        this.iSkill = iSkill;
    }

    public static interface ISkill {
        void eat(boolean isShow);
    }

    public class CategoryList implements DataCall<Result<List<LeimuBean>>> {
        @Override
        public void success(Result<List<LeimuBean>> data, Object... args) {
            categoryListAdapter.setlist(data.getResult());
            categoryListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    public class VideoList implements DataCall<Result<List<VideoBean>>> {


        @Override
        public void success(Result<List<VideoBean>> data, Object... args) {
            recycler.refreshComplete();
            recycler.loadMoreComplete();
            if (page == 1) {
                videoAdapter.clear();
            }
            list = data.getResult();
            videoAdapter.setList(data.getResult());
            videoAdapter.notifyDataSetChanged();
            toprecycler.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            JZVideoPlayer.releaseAllVideos();
        } else {
            top.setVisibility(View.GONE);
            View view = snapHelper.findSnapView(linearLayoutManager);
            RecyclerView.ViewHolder viewHolder = recycler.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof VideoAdapter.ViewHolder) {

                //JZVideoPlayer.releaseAllVideos();
                ((VideoAdapter.ViewHolder) viewHolder).video.startVideo();


            }
        }
    }
}

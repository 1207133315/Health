package com.wd.health;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.health.HomeFrag;
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
import com.kd.easybarrage.Barrage;
import com.kd.easybarrage.BarrageView;
import com.wd.health.adapter.CategoryListAdapter;
import com.wd.health.adapter.VideoAdapter;
import com.wd.health.bean.CommentBean;
import com.wd.health.bean.LeimuBean;
import com.wd.health.bean.VideoBean;

import com.wd.health.presenter.AddCommonPresenter;
import com.wd.health.presenter.AddVideoPresenter;
import com.wd.health.presenter.CategoryListPresenter;
import com.wd.health.presenter.CommentListPresenter;
import com.wd.health.presenter.MyHBPresenter;
import com.wd.health.presenter.VideoBuyPresenter;
import com.wd.health.presenter.VideoListPresenter;
import com.wd.health.view.MyLinearLayoutManager;
import com.wd.health.view.MyShowBottomView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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


    public void something(){}
    @BindView(R2.id.recycler)
    XRecyclerView recycler;
    private VideoListPresenter videoListPresenter;
    private LoginBean user;
    private VideoAdapter videoAdapter;
    @BindView(R2.id.show)
    ImageView show;
    @BindView(R2.id.show2)
    MyShowBottomView show2;
    @BindView(R2.id.toprecycler)
    RecyclerView toprecycler;

    private CategoryListPresenter categoryListPresenter;
    private CategoryListAdapter categoryListAdapter;
    private long id = 1;
    private int page = 1;

    private PagerSnapHelper snapHelper;
    private LinearLayoutManager linearLayoutManager;
    private int height;
    int totalDy = 0;
    private VideoBuyPresenter videoBuyPresenter;
    private PopupWindow popupWindow;
    private MyHBPresenter myHBPresenter;
    private AddCommonPresenter addCommonPresenter;

    @BindView(R2.id.edit)
    RelativeLayout edit;
    @BindView(R2.id.content)
    EditText content;
    @BindView(R2.id.send)
    ImageView send;
    @BindView(R2.id.layout_main)
    RelativeLayout layout_main;
    @Override
    public String getPageName() {
        return "视频";
    }

    private List<VideoBean> mList=new ArrayList<>();
    private List<Barrage> mBarrages = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.shipin_frag_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        addCommonPresenter = new AddCommonPresenter(new ShiPinFragment.AddCommon());
        videoListPresenter = new VideoListPresenter(new VideoList());
        addVideoPresenter = new AddVideoPresenter(new AddVideo());
        videoBuyPresenter = new VideoBuyPresenter(new VideoBuy());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(getContext());
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

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
                                          ((VideoAdapter.ViewHolder) viewHolder).danmu_content.destroy();
                                          if (((VideoAdapter.ViewHolder) viewHolder).video.isPlay()){

                                              ((VideoAdapter.ViewHolder) viewHolder).stop.setVisibility(View.GONE);
                                          }

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



        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* iSkill.eat(true);*/
//                top.setVisibility(View.VISIBLE);
//                handler.sendEmptyMessageDelayed(1,5000);
                EventBus.getDefault().postSticky(true);
            }
        });

        myHBPresenter = new MyHBPresenter(new MyHB());

        show.setOnTouchListener(new View.OnTouchListener() {
            private float moveY;
            private float moveX;
            private float y;
            private float x;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = motionEvent.getX();
                        moveY = motionEvent.getY();
                        if (moveY-y<240){
                         ViewGroup.LayoutParams layoutParams = show.getLayoutParams();//取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
                        layoutParams.height=(int)(264+(moveY-y));
                    show.setLayoutParams(layoutParams);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (moveY-y>100) {
                            show.setVisibility(View.GONE);
                            toprecycler.setVisibility(View.VISIBLE);
                        }
                        ViewGroup.LayoutParams layoutParams1 = show.getLayoutParams();//取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
                        layoutParams1.height=264;
                        show.setLayoutParams(layoutParams1);
                    moveY=0;
                        break;
                }

                return true;
            }
        });
        final LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
        //user = userDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).unique();
        final List<LoginBean> loginBeans = userDao.loadAll();
        user = loginBeans.get(0);
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
    }

    private int myHb=0;


    public  class AddCommon implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            edit.setVisibility(View.GONE);
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public class MyHB implements DataCall<Result<Integer>>{
        @Override
        public void success(Result<Integer> data, Object... args) {
            myHb=data.getResult();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            if (data.getDisplayMessage().equals("请先登陆")){
                intentByRouter("/LoginActivity/");
            }
        }
    }

    AddVideoPresenter addVideoPresenter;



    @Override
    public void onResume() {


        super.onResume();


        final LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
        //user = userDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).unique();
        final List<LoginBean> loginBeans = userDao.loadAll();
        user = loginBeans.get(0);
        //发表评论


        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height1) {
                //2 获取父控件的属性并且设置好属性
                RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 120);
                buttonLayoutParams.setMargins(0, height-height1-120, 0, 0);
                edit.setLayoutParams(buttonLayoutParams);
            }

            @Override
            public void keyBoardHide(int height1) {
                RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 120);
                buttonLayoutParams.setMargins(0, height-120, 0, 0);
                edit.setLayoutParams(buttonLayoutParams);
                edit.setVisibility(View.GONE);
            }
        });
            user = loginBeans.get(0);
            videoAdapter.setPingLun(new VideoAdapter.PingLun() {
                @Override
                public void click(VideoBean videoBean, BarrageView barrageView, int index) {
                    if (loginBeans.size()<=0){
                        intentByRouter("/LoginActivity/");
                        return;
                    }
                        edit.setVisibility(View.VISIBLE);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String s = content.getText().toString();
                            if (s.length()<0||s.equals("")){
                                Toast.makeText(getActivity(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                            }else {
                                addCommonPresenter.reqeust(user.getId(),user.getSessionId(),videoBean.id,s);
                                barrageView.addBarrage(new Barrage(s,R.color.colorAccent));
                                videoAdapter.setContent(s);
                            }
                        }
                    });

                }
            });



         videoAdapter.setsCclick(new VideoAdapter.SCclick() {
             @Override
             public void click(VideoBean videoBean,int i) {

                 if (loginBeans.size()<=0){
                     intentByRouter("/LoginActivity/");
                     return;
                 }
                 LoginBean user = loginBeans.get(0);
                 addVideoPresenter.reqeust(user.getId(),user.getSessionId(),videoBean.id);


             }
         });



         //购买视频
        videoAdapter.setBuyClick(new VideoAdapter.BuyClick() {
            @Override
            public void click(VideoBean videoBean,int i) {
                index=i;
                if (loginBeans.size()>0){
                    myHBPresenter.reqeust(loginBeans.get(0).getId(),loginBeans.get(0).getSessionId());
                    LoginBean user = loginBeans.get(0);
                    showBuyPop(videoBean,user);

                }else {
                    intentByRouter("/LoginActivity/");
                }
            }
        });


    }

    private PingLun pingLun;

    public void setPingLun(PingLun pingLun) {
        this.pingLun = pingLun;
    }

    public interface PingLun{
        void click(VideoBean videoBean,BarrageView barrageView,int index);
    }
    private int index;
    public void showBuyPop(VideoBean videoBean,LoginBean userInfo){
        View view=View.inflate(getContext(),R.layout.buy_pop_layout,null);
        TextView goBuy=view.findViewById(R.id.goBuy);
        ImageView down=view.findViewById(R.id.down);
        RelativeLayout poo_bg=view.findViewById(R.id.pop_bg);
        TextView price=view.findViewById(R.id.price);
        TextView myPrice=view.findViewById(R.id.myPrice);
        TextView goAdd=view.findViewById(R.id.goAdd);
        price.setText(videoBean.price+"H币");
        myPrice.setText("我的H币:"+myHb+", 余额不足?");

        goBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myHb>=videoBean.price) {
                    videoBuyPresenter.reqeust(userInfo.getId(), userInfo.getSessionId(), videoBean.id, (int)videoBean.price);
                }else {
                    Toast.makeText(getActivity(), "余额不足,请充值", Toast.LENGTH_SHORT).show();
                }
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setHeight(960);
        popupWindow.showAtLocation(getView().findViewById(R.id.layout_main),
                Gravity.BOTTOM, 0, 0);
    }

    public class VideoBuy implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();

        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();

            if (data.getDisplayMessage().equals("网络异常")){
                popupWindow.dismiss();
                myHBPresenter.reqeust(user.getId(),user.getSessionId());
                mList.get(index).whetherBuy=1;

            }
            if (data.getDisplayMessage().equals("请先登陆")){
                intentByRouter("/LoginActivity/");
            }

        }
    }

    public class AddVideo implements DataCall<Result> {
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            recycler.refresh();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            if (data.getDisplayMessage().equals("请先登陆")){
                intentByRouter("/LoginActivity/");
            }

        }
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
            if (mList.size()>0){
                mList.clear();
            }
            mList.addAll(data.getResult());
            videoAdapter.setList(mList);
            videoAdapter.notifyDataSetChanged();
            toprecycler.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            JZVideoPlayer.releaseAllVideos();
        } else {
            View view = snapHelper.findSnapView(linearLayoutManager);
            RecyclerView.ViewHolder viewHolder = recycler.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof VideoAdapter.ViewHolder) {
                //JZVideoPlayer.releaseAllVideos();
                ((VideoAdapter.ViewHolder) viewHolder).video.startVideo();
            }
        }
    }

}

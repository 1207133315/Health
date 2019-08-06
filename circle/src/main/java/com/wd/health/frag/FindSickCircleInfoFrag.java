package com.wd.health.frag;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.CircleListBean;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.activity.CircleWritActivity;
import com.wd.health.activity.FindSickCircleInfoActivity;
import com.wd.health.adapter.CircleCommentListAdapter;
import com.wd.health.adapter.ImageAdapter;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.CircleInfoBean;
import com.wd.health.bean.WaiBuBean;
import com.wd.health.presenter.CircleCancelShouCangPresenter;
import com.wd.health.presenter.CircleCommentListPresenter;
import com.wd.health.presenter.CircleInfoPresenter;
import com.wd.health.presenter.CircleShouCangPresenter;
import com.wd.health.presenter.DoTaskPresenter;
import com.wd.health.presenter.PublishCommentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;


public class FindSickCircleInfoFrag extends WDFragment {


    private CircleInfoPresenter circleInfoPresenter;
    //----病友圈详情--------------------------
    private TextView title_circleinfo;
    private TextView bingzheng;
    private TextView keshi;
    private TextView bingzhenginfo;
    private TextView yiyuan;
    private TextView zhiliaotime;
    private TextView zhiliaojingli;
    private TextView caina_time;
    private CheckBox pinglun;
    private List<CircleCommentListBean> comment_result;
    private CircleCommentListAdapter circleCommentListAdapter;
    private String edText_pinglun;
    private PublishCommentPresenter publishCommentPresenter;
    private String sessionId;
    private CircleCommentListPresenter circleCommentListPresenter;
    private Long id_user;
    public int page = 1;
    private XRecyclerView circle_pop_rc1;
    private int collectionFlag;//是否收藏
    private CheckBox shoucang;
    private TextView shouna_num;
    private TextView shoucang_num;
    private RelativeLayout circle_pop_wupinglun_layout;
    private RelativeLayout circle_pop_youpinglun_layout;
    private int sickCircleId;
    private int sickCircleId1;
    private DoTaskPresenter presenter;
    private RecyclerView image_bingli;


    //----病友圈详情--------------------------
    private List<ImageView> mList = new ArrayList<>();

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circleinfo_frag_layout;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            SharedPreferences flag = getActivity().getSharedPreferences("flag", MODE_PRIVATE);
            flag.edit().putBoolean("flag", false).commit();
        }
    }

    @Override
    protected void initView() {
        int sickCircleId_w = WaiBuBean.getSickCircleId_w();
        Log.i("kkk", sickCircleId_w + "");

        //EventBus 注册
        EventBus.getDefault().register(this);
        //----病友圈详情--------------------------
        //获取控件
        //标题
        title_circleinfo = getView().findViewById(R.id.circleinfo_farg_title);
        //病症
        bingzheng = getView().findViewById(R.id.circleinfo_farg_bingzheng);
        //科室
        keshi = getView().findViewById(R.id.circleinfo_farg_keshi);
        //病症详情
        bingzhenginfo = getView().findViewById(R.id.circleinfo_farg_bingzhengInfo);
        //-------------------治疗经历----------------------
        //医院
        yiyuan = getView().findViewById(R.id.circleinfo_farg_yiyuan);
        //治疗时间
        zhiliaotime = getView().findViewById(R.id.circleinfo_farg_time);
        //治疗结果
        zhiliaojingli = getView().findViewById(R.id.circleinfo_farg_zhiliaojinli);
        //相关图片
        image_bingli = getView().findViewById(R.id.circleinfo_farg_bingli);
        //收藏
        shoucang = getView().findViewById(R.id.circleinfo_farg_shoucang);
        //收藏数
        shoucang_num = getView().findViewById(R.id.circleinfo_farg_shoucang_num);
        // shoucang_num.setText(collectionNum + "");
        //评论
        pinglun = getView().findViewById(R.id.circleinfo_farg_shouna);
        //评论数
        shouna_num = getView().findViewById(R.id.circleinfo_farg_shouna_num);
        //采纳人头像
        ImageView image_caina = getView().findViewById(R.id.circleinfo_farg_caina_image);
        //采纳人昵称
        TextView caina_name = getView().findViewById(R.id.circleinfo_farg_caina_name);
        //采纳时间
        caina_time = getView().findViewById(R.id.circleinfo_farg_caina_time);
        //采纳建议
        TextView caina_info = getView().findViewById(R.id.circleinfo_farg_caina_info);

        //做任务
        presenter = new DoTaskPresenter(new DoTask());

        //关联presenter -----获取病友圈id------
        circleInfoPresenter = new CircleInfoPresenter(new CircleInfoCall());
        circleInfoPresenter.reqeust(sickCircleId1 + "");
        Log.i("jjj", sickCircleId1 + "");


        //----------------------点击评论查看评论列表--------------------------------
        pinglun.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                View view = View.inflate(getActivity(), R.layout.circle_pop_layout, null);
                PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pop.setFocusable(true);
                pop.setTouchable(true);
                pop.setOutsideTouchable(true);
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);

                //无评论页面
                circle_pop_wupinglun_layout = view.findViewById(R.id.circle_pop_wupinglun);
                //有评论页面
                circle_pop_youpinglun_layout = view.findViewById(R.id.circle_pop_youpinglun);

                //点击无评论输入框 显示有评论页面
                EditText circle_pop_edText_wu = view.findViewById(R.id.circle_pop_edText_wu);

                circle_pop_edText_wu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        circle_pop_wupinglun_layout.setVisibility(View.GONE);
                        circle_pop_youpinglun_layout.setVisibility(View.VISIBLE);
                    }
                });

                //关闭弹窗  无评论
                ImageView circle_pop_close = view.findViewById(R.id.circle_pop_close);
                circle_pop_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });

                //关闭弹窗  有评论
                ImageView circle_pop_close2 = view.findViewById(R.id.circle_pop_close2);
                circle_pop_close2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });

                circle_pop_rc1 = view.findViewById(R.id.circle_pop_rc1);
                //关联 评论列表的p层
                circleCommentListPresenter = new CircleCommentListPresenter(new CircleCommentListCall());
                circleCommentListPresenter.reqeust(sickCircleId1 + "", page + "", "10");

                //布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                circle_pop_rc1.setLayoutManager(linearLayoutManager);
                //适配器
                circleCommentListAdapter = new CircleCommentListAdapter(getActivity());
                circle_pop_rc1.setAdapter(circleCommentListAdapter);
                circleCommentListAdapter.setCall(new CircleCommentListAdapter.Call() {
                    @Override
                    public void showCall() {
                        pop.dismiss();
                    }
                });

                circleCommentListAdapter.setDatas(new CircleCommentListAdapter.Data() {
                    @Override
                    public void showData() {
                        intentByRouter("/LoginActivity/");
                    }
                });

                //上拉加载 下拉刷新
                circle_pop_rc1.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        page = 1;
                        circleCommentListPresenter.reqeust(sickCircleId1 + "", page + "", "10");

                    }

                    @Override
                    public void onLoadMore() {
                        page++;
                        circleCommentListPresenter.reqeust(sickCircleId1 + "", "" + page++, "10");

                    }
                });


                //获取评论输入框
                EditText ed_pinglun = view.findViewById(R.id.circle_pop_edText2);
                ed_pinglun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edText_pinglun = ed_pinglun.getText().toString().trim();
                        publishCommentPresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(sickCircleId1), edText_pinglun);
                        ed_pinglun.setText("");
                    }
                });


            }
        });
        //----------------------点击评论查看评论列表----------------尾巴----------------


        //----------------发表评论---------头部------------------
        //获取数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(getActivity(), LoginBeanDao.TABLENAME).getLoginBeanDao();
        List<LoginBean> loginBeans = loginBeanDao.loadAll();
        sessionId = loginBeans.get(0).getSessionId();
        id_user = loginBeans.get(0).getId();
       /* Log.i("qqq", sessionId);
        Log.i("qqq", id_user + "");*/

        //发表病友圈评论关联p
        publishCommentPresenter = new PublishCommentPresenter(new PublishCommentCall());


        //----------------发表评论---------尾巴------------------


        //----病友圈详情---------尾巴-----------------------------------------------


        //---------新手浮层引导-----------头部----------------------------------------
        RelativeLayout yindao = getView().findViewById(R.id.circleinfo_farg_yindao1);
        //1.获取sp 对象（ 存储文件的名字，存储的文件权限）
        SharedPreferences sp = getActivity().getSharedPreferences("ydy", Context.MODE_PRIVATE);
        //5判断是不是第一次
        if (sp.getBoolean("第一次", false)) {
            yindao.setVisibility(View.GONE);
            return;//停止继续执行
        }
        //2获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        //3存储值
        edit.putBoolean("第一次", true);
        //4提交
        edit.commit();
        yindao.setVisibility(View.VISIBLE);
        yindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yindao.setVisibility(View.GONE);
            }
        });
        //---------新手浮层引导--------------尾巴-------------------------------------
    }

    //-----------------------订阅--------------------
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getCircleListBean(CircleListBean circleListBean) {
        //获取病友圈id
        sickCircleId1 = circleListBean.getSickCircleId();
        Log.i("sss", sickCircleId1 + "");
        EventBus.getDefault().removeAllStickyEvents();

    }


    //--------病友圈详情----成功----失败---------------------------------------------------
    class CircleInfoCall implements DataCall<Result<CircleInfoBean>> {


        @Override
        public void success(Result<CircleInfoBean> data, Object... args) {
            CircleInfoBean result_info = data.getResult();
          /* ;
            Log.i("jjj", result_info.getDisease());*/

            //标题
            String title = result_info.getTitle();
            title_circleinfo.setText(title);
            Log.i("jjj", result_info.getTitle());
            //病症描述
            String disease = result_info.getDisease();
            bingzheng.setText(disease);
            //科室
            String department = result_info.getDepartment();
            // keshi.setText(department);
            //病症详情
            String detail = result_info.getDetail();
            bingzhenginfo.setText(detail);
            //治疗医院
            String treatmentHospital = result_info.getTreatmentHospital();
            yiyuan.setText(treatmentHospital);
            //治疗开始时间
            long treatmentStartTime = result_info.getTreatmentStartTime();
            //治疗结束时间
            long treatmentEndTime = result_info.getTreatmentEndTime();
            //开始时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.DD");
            String startTime = simpleDateFormat.format(new Date(treatmentStartTime));
            //结束时间
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM.DD");
            String endTime = simpleDateFormat1.format(new Date(treatmentEndTime));

            zhiliaotime.setText(startTime + "" + "-" + endTime + "");
            //治疗过程描述
            String treatmentProcess = result_info.getTreatmentProcess();
            zhiliaojingli.setText(treatmentProcess);
            //相关图片
            String picture = result_info.getPicture();
            //收藏数
            int collectionNum = result_info.getCollectionNum();
            shoucang_num.setText(collectionNum + "");
            //评论数
            int commentNum = result_info.getCommentNum();
            shouna_num.setText(commentNum + "");
            //采纳用户昵称
            String adoptNickName = result_info.getAdoptNickName();
            //采纳用户头像
            String adoptHeadPic = result_info.getAdoptHeadPic();
            //采纳建议
            String adoptComment = result_info.getAdoptComment();
            //采纳时间
            long adoptTime = result_info.getAdoptTime();
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy.MM.DD");
            String cainaTime = simpleDateFormat2.format(new Date(adoptTime));
            caina_time.setText(cainaTime + "");
            //是否收藏病友圈
            collectionFlag = result_info.getCollectionFlag();
            //病友圈id
            sickCircleId = result_info.getSickCircleId();

            if (picture != null) {
                String[] images = picture.split(",");
                int imageCount = images.length;
                int colNum;//列数
                if (imageCount == 1) {
                    colNum = 1;
                } else if (imageCount == 2 || imageCount == 4) {
                    colNum = 2;
                } else {
                    colNum = 3;
                }
                ImageAdapter imageAdapter = new ImageAdapter();
                imageAdapter.addAll(Arrays.<Object>asList(images));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                gridLayoutManager.setSpanCount(colNum);
                image_bingli.setLayoutManager(gridLayoutManager);
                image_bingli.setAdapter(imageAdapter);
            }


            //--------------收藏----取消收藏------------------------------
          /*  if (collectionFlag==1){
                shoucang.setBackgroundResource(R.drawable.common_button_collection_small_n);
            }else {
                shoucang.setBackgroundResource(R.drawable.common_button_collection_small_s);

            }*/


            if (collectionFlag == 1) {
                shoucang.setChecked(true);
                shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result_info.setCollectionFlag(2);
                        CircleCancelShouCangPresenter circleCancelShouCangPresenter = new CircleCancelShouCangPresenter(new CircleCancelShowCangCall());
                        circleCancelShouCangPresenter.reqeust(String.valueOf(id_user), sessionId, sickCircleId + "");

                    }
                });

            } else {
                shoucang.setChecked(false);
                shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        result_info.setCollectionFlag(1);
                        CircleShouCangPresenter circleShouCangPresenter = new CircleShouCangPresenter(new CircleShowCangCall());
                        circleShouCangPresenter.reqeust(String.valueOf(id_user), sessionId, sickCircleId + "");
                        //Toast.makeText(getActivity(), "已收藏，不可重复收藏", Toast.LENGTH_SHORT).show();
                    }
                });

            }


            //--------------收藏----取消收藏---------尾巴---------------------


        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    //--------病友圈详情----成功----失败-----尾巴------------------------------------------------


//----------------------点击评论查看评论列表--成功----失败--------------------------------

    class CircleCommentListCall implements DataCall<Result<List<CircleCommentListBean>>> {

        @Override
        public void success(Result<List<CircleCommentListBean>> data, Object... args) {
            comment_result = data.getResult();

            if (comment_result.size() == 0 && page == 1) {
                circle_pop_wupinglun_layout.setVisibility(View.VISIBLE);
                circle_pop_youpinglun_layout.setVisibility(View.GONE);
            } else {
                circle_pop_wupinglun_layout.setVisibility(View.GONE);
                circle_pop_youpinglun_layout.setVisibility(View.VISIBLE);
            }


            circle_pop_rc1.loadMoreComplete();
            circle_pop_rc1.refreshComplete();
            if (page == 1) {
                circleCommentListAdapter.clear();
            }

            circleCommentListAdapter.getData(comment_result);
            circleCommentListAdapter.notifyDataSetChanged();


        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

//----------------------点击评论查看评论列表--成功----失败---------尾巴-----------------------

    //----------------发表评论-----成功----失败----头部------------------
    class PublishCommentCall implements DataCall<Result> {

        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(getActivity(), "发表评论成功！", Toast.LENGTH_SHORT).show();
            // circleCommentListPresenter.reqeust(sickCircleId_jj + "");
            presenter.reqeust(id_user.intValue(), sessionId,1002);
        }

        @Override
        public void fail(ApiException data, Object... args) {
            String displayMessage = data.getDisplayMessage();

            if (displayMessage.equals("请先登陆")) {
                intentByRouter("/LoginActivity/");
            }
        }
    }

    //----------------发表评论-----成功----失败----尾巴------------------

    //----------收藏-----成功----失败-------------------------

    class CircleShowCangCall implements DataCall<Result> {

        @Override
        public void success(Result data, Object... args) {

            String message = data.getMessage();
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "病友圈收藏成功！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    //----------收藏-----成功----失败----尾巴---------------------

    //----------取消收藏-----成功----失败-------------------------

    class CircleCancelShowCangCall implements DataCall<Result> {

        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(getActivity(), "取消收藏！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    //----------取消收藏-----成功----失败----尾巴---------------------

    public class DoTask implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;

            Toast.makeText(getActivity(),  result.getResult().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus 反注册
        EventBus.getDefault().unregister(this);
    }
}

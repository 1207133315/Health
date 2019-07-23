package com.wd.health.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.wd.health.adapter.CircleCommentListAdapter;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.CircleInfoBean;
import com.wd.health.presenter.CircleCommentListPresenter;
import com.wd.health.presenter.CircleInfoPresenter;
import com.wd.health.presenter.PublishCommentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.AbstractDaoSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FindSickCircleInfoFrag extends WDFragment {
    public CircleListBean circleListBean;
    private CircleInfoPresenter circleInfoPresenter;
    private int sickCircleId_jj;
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
    public int page=1;
    private XRecyclerView circle_pop_rc1;


    //----病友圈详情--------------------------


    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circleinfo_frag_layout;
    }

    @Override
    protected void initView() {
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
        ImageView image_bingli = getView().findViewById(R.id.circleinfo_farg_bingli);
        // Glide.with(getActivity()).load(picture).into(image_bingli);

        //收藏
        CheckBox shoucang = getView().findViewById(R.id.circleinfo_farg_shoucang);
        //收藏数
        TextView shoucang_num = getView().findViewById(R.id.circleinfo_farg_shoucang_num);
        // shoucang_num.setText(collectionNum + "");
        //评论
        pinglun = getView().findViewById(R.id.circleinfo_farg_shouna);
        //评论数
        TextView shouna_num = getView().findViewById(R.id.circleinfo_farg_shouna_num);
        // shouna_num.setText(commentNum + "");

        //采纳人头像
        ImageView image_caina = getView().findViewById(R.id.circleinfo_farg_caina_image);
        // Glide.with(getActivity()).load(adoptHeadPic).into(image_caina);

        //采纳人昵称
        TextView caina_name = getView().findViewById(R.id.circleinfo_farg_caina_name);
        //  caina_name.setText(adoptNickName+"");
        //采纳时间
        caina_time = getView().findViewById(R.id.circleinfo_farg_caina_time);

        //采纳建议
        TextView caina_info = getView().findViewById(R.id.circleinfo_farg_caina_info);
        //  caina_info.setText(adoptComment+"");

        //关联presenter -----获取病友圈id------
        circleInfoPresenter = new CircleInfoPresenter(new CircleInfoCall());
        circleInfoPresenter.reqeust(String.valueOf(sickCircleId_jj));
        // Log.i("jjj", sickCircleId_jj + "");

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
                circleCommentListPresenter.reqeust(sickCircleId_jj + "",page+"","10");

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
                        page=1;
                        circleCommentListPresenter.reqeust(sickCircleId_jj + "",page+"","10");

                    }

                    @Override
                    public void onLoadMore() {
                        page++;
                        circleCommentListPresenter.reqeust(sickCircleId_jj + "",""+page++,"10");

                    }
                });


                //获取评论输入框
                EditText ed_pinglun = view.findViewById(R.id.circle_pop_edText2);
                ed_pinglun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edText_pinglun = ed_pinglun.getText().toString().trim();
                        publishCommentPresenter.reqeust(String.valueOf(id_user), sessionId, sickCircleId_jj + "", edText_pinglun);
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


        //----病友圈详情---------尾巴-----------------


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


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getCircleListBean(CircleListBean circleListBean) {
        this.circleListBean = circleListBean;
        sickCircleId_jj = circleListBean.getSickCircleId();

    }


    //--------病友圈详情----成功----失败---------------------------------------------------
    class CircleInfoCall implements DataCall<Result<CircleInfoBean>> {


        @Override
        public void success(Result<CircleInfoBean> data, Object... args) {
            CircleInfoBean result_info = data.getResult();
          /*  Log.i("jjj", result_info.getTitle());
            Log.i("jjj", result_info.getDisease());*/

            //标题
            String title = result_info.getTitle();
            title_circleinfo.setText(title);
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
            //评论数
            int commentNum = result_info.getCommentNum();
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
            circle_pop_rc1.loadMoreComplete();
            circle_pop_rc1.refreshComplete();
            if (page==1){
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
            circleCommentListPresenter.reqeust(sickCircleId_jj + "");

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus 注册
        EventBus.getDefault().unregister(this);
    }
}

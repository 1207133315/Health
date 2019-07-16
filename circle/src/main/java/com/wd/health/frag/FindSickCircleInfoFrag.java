package com.wd.health.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.CircleListBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.wd.health.R;
import com.wd.health.bean.CircleInfoBean;
import com.wd.health.presenter.CircleInfoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class FindSickCircleInfoFrag extends WDFragment {
    private CircleListBean circleListBean;

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

        //---------新手浮层引导---------------------------------------------------
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
        //---------新手浮层引导---------------------------------------------------


        //----病友圈详情--------------------------
        //关联presenter -----获取病友圈id------
        CircleInfoPresenter circleInfoPresenter = new CircleInfoPresenter(new CircleInfoCall());
        int sickCircleId = circleListBean.getSickCircleId();
        circleInfoPresenter.reqeust(String.valueOf(sickCircleId));

        //获取控件
        //标题
        String title = circleListBean.getTitle();
        TextView title_circleinfo = getView().findViewById(R.id.circleinfo_farg_title);
        title_circleinfo.setText(title);
        //病症
        TextView bingzheng = getView().findViewById(R.id.circleinfo_farg_bingzheng);
        //科室
        TextView keshi = getView().findViewById(R.id.circleinfo_farg_keshi);
        //病症详情
        TextView bingzhenginfo = getView().findViewById(R.id.circleinfo_farg_bingzhengInfo);
        //-------------------治疗经历----------------------
        //医院
        TextView yiyuan = getView().findViewById(R.id.circleinfo_farg_yiyuan);
        //治疗时间
        TextView zhiliaotime = getView().findViewById(R.id.circleinfo_farg_time);
        //治疗结果
        TextView zhiliaojingli = getView().findViewById(R.id.circleinfo_farg_zhiliaojinli);
        //相关图片
        ImageView image_bingli = getView().findViewById(R.id.circleinfo_farg_bingli);
        //收藏
        CheckBox shoucang = getView().findViewById(R.id.circleinfo_farg_shoucang);
        //收藏数
        TextView shoucang_num = getView().findViewById(R.id.circleinfo_farg_shoucang_num);
        //评论
        CheckBox pinglun = getView().findViewById(R.id.circleinfo_farg_shouna);
        //评论数
        TextView shouna_num = getView().findViewById(R.id.circleinfo_farg_shouna_num);
        //采纳人头像
        ImageView image_caina = getView().findViewById(R.id.circleinfo_farg_caina_image);
        //采纳人昵称
        TextView caina_name = getView().findViewById(R.id.circleinfo_farg_caina_name);
        //采纳时间
        TextView caina_time = getView().findViewById(R.id.circleinfo_farg_caina_time);
        //采纳建议
        TextView caina_info = getView().findViewById(R.id.circleinfo_farg_caina_info);


        //----病友圈详情--------------------------

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getCircleListBean(CircleListBean circleListBean) {
        this.circleListBean = circleListBean;
    }

    //--------病友圈详情----成功----失败---------------------------------------------------
    class CircleInfoCall implements DataCall<Result<CircleInfoBean>> {

        @Override
        public void success(Result<CircleInfoBean> data, Object... args) {
            CircleInfoBean result = data.getResult();
            Toast.makeText(getActivity(), ""+result, Toast.LENGTH_SHORT).show();
            String title = result.getTitle();//标题
            String department = result.getDepartment();//科室
            String disease = result.getDisease();//病症描述
            String detail = result.getDetail();//病症详情
            String treatmentHospital = result.getTreatmentHospital();//治疗医院
            long treatmentStartTime = result.getTreatmentStartTime();//治疗开始时间
            long treatmentEndTime = result.getTreatmentEndTime();//治疗结束时间
            String treatmentProcess = result.getTreatmentProcess();//治疗过程描述
            String picture = result.getPicture();//相关图片
            int collectionNum = result.getCollectionNum();//收藏数
            int commentNum = result.getCommentNum();//评论数
            String adoptNickName = result.getAdoptNickName();//采纳用户昵称
            String adoptHeadPic = result.getAdoptHeadPic();//采纳用户头像
            String adoptComment = result.getAdoptComment();//采纳建议
            long adoptTime = result.getAdoptTime();//采纳时间

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    //--------病友圈详情----成功----失败-----------------------------------------------------

}

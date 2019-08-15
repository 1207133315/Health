package com.wd.health.messagelist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.messagelist.R;
import com.wd.health.messagelist.R2;
import com.wd.health.messagelist.bean.MessageBean;
import com.wd.health.messagelist.presenter.FindUserNoticeReadNumPresenter;
import com.wd.health.messagelist.presenter.ModifyAllStatusPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/MessageActivity/")
public class MessageActivity extends WDActivity {

    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.cha)
    ImageView cha;
    @BindView(R2.id.system_informationimg)
    ImageView systemInformationimg;
    @BindView(R2.id.num)
    TextView num;
    @BindView(R2.id.xitong)
    RelativeLayout xitong;
    @BindView(R2.id.wenzhenimg)
    ImageView wenzhenimg;
    @BindView(R2.id.num2)
    TextView num2;
    @BindView(R2.id.wenzhen)
    RelativeLayout wenzhen;
    @BindView(R2.id.Hbiimg)
    ImageView Hbiimg;
    @BindView(R2.id.num3)
    TextView num3;
    @BindView(R2.id.Hbi)
    RelativeLayout Hbi;
    @BindView(R2.id.rela)
    RelativeLayout rela;
    @BindView(R2.id.qnyd)
    TextView qnyd;
    private FindUserNoticeReadNumPresenter findUserNoticeReadNumPresenter;
    private List<LoginBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        findUserNoticeReadNumPresenter = new FindUserNoticeReadNumPresenter(new FindUserNoticeReadNum());
        list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().build().list();
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        findUserNoticeReadNumPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId());
    }

    @OnClick({R2.id.back, R2.id.cha, R2.id.xitong, R2.id.wenzhen, R2.id.Hbi,R2.id.qnyd})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.cha) {
            rela.setVisibility(View.GONE);
        } else if (i == R.id.xitong) {
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        } else if (i == R.id.wenzhen) {
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("type", 2);
            startActivity(intent);
        } else if (i == R.id.Hbi) {
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        }else if (i == R.id.qnyd){
            ModifyAllStatusPresenter modifyAllStatusPresenter = new ModifyAllStatusPresenter(new ModifyAllStatus());
            modifyAllStatusPresenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId());
        }
    }
    class ModifyAllStatus implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            onResume();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Log.d("ModifyAllStatus", data.getDisplayMessage());
        }
    }
    class FindUserNoticeReadNum implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result<List<MessageBean>> result = (Result<List<MessageBean>>) data;
            List<MessageBean> result1 = result.getResult();
            for (MessageBean messageBean : result1) {
                if (messageBean.getNoticeType() == 1) {
                    int notReadNum = messageBean.getNotReadNum();
                    if (notReadNum == 0) {
                        num.setVisibility(View.GONE);
                    } else {
                        num.setVisibility(View.VISIBLE);
                        num.setText(notReadNum + "");
                    }
                }
                if (messageBean.getNoticeType() == 2) {
                    int notReadNum = messageBean.getNotReadNum();
                    if (notReadNum == 0) {
                        num2.setVisibility(View.GONE);
                    } else {
                        num2.setVisibility(View.VISIBLE);
                        num2.setText(notReadNum + "");
                    }
                }
                if (messageBean.getNoticeType() == 3) {
                    int notReadNum = messageBean.getNotReadNum();
                    if (notReadNum == 0) {
                        num3.setVisibility(View.GONE);
                    } else {
                        num3.setVisibility(View.VISIBLE);
                        num3.setText(notReadNum + "");
                    }
                }
            }
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}

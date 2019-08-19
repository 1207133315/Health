package com.wd.health.messagelist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.messagelist.R;
import com.wd.health.messagelist.R2;
import com.wd.health.messagelist.adapter.MessageitemAdapter;
import com.wd.health.messagelist.bean.MessageDetailsBean;
import com.wd.health.messagelist.presenter.FindInquiryNoticeListPresenter;
import com.wd.health.messagelist.presenter.FindSystemNoticeListPresenter;
import com.wd.health.messagelist.presenter.FndHealthyCurrencyNoticeListPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailsActivity extends WDActivity {
    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.recy)
    RecyclerView recy;
    @BindView(R2.id.title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void initView() {
        List<LoginBean> list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        switch (type) {
            case 1:
                title.setText("系统消息");
                FindSystemNoticeListPresenter findSystemNoticeListPresenter = new FindSystemNoticeListPresenter(new FindSystemNoticeList());
                findSystemNoticeListPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), 1, 10);
                break;
            case 2:
                title.setText("问诊消息");
                FindInquiryNoticeListPresenter findInquiryNoticeListPresenter = new FindInquiryNoticeListPresenter(new FindInquiryNoticeList());
                findInquiryNoticeListPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), 1, 10);
                break;
            case 3:
                title.setText("H币入账消息");
                FndHealthyCurrencyNoticeListPresenter fndHealthyCurrencyNoticeListPresenter = new FndHealthyCurrencyNoticeListPresenter(new FndHealthyCurrencyNoticeList());
                fndHealthyCurrencyNoticeListPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), 1, 10);
                break;
            default:
                break;
        }
    }


    @OnClick(R2.id.back)
    public void onViewClicked() {
        finish();
    }
    class FndHealthyCurrencyNoticeList implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result<List<MessageDetailsBean>> result = (Result<List<MessageDetailsBean>>) data;
            List<MessageDetailsBean> result1 = result.getResult();
            MessageitemAdapter messageitemAdapter = new MessageitemAdapter(R.layout.messageitem, result1);
            recy.setLayoutManager(new LinearLayoutManager(MessageDetailsActivity.this, RecyclerView.VERTICAL, false));
            recy.setAdapter(messageitemAdapter);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    class FindInquiryNoticeList implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result<List<MessageDetailsBean>> result = (Result<List<MessageDetailsBean>>) data;
            List<MessageDetailsBean> result1 = result.getResult();
            MessageitemAdapter messageitemAdapter = new MessageitemAdapter(R.layout.messageitem, result1);
            recy.setLayoutManager(new LinearLayoutManager(MessageDetailsActivity.this, RecyclerView.VERTICAL, false));
            recy.setAdapter(messageitemAdapter);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    class FindSystemNoticeList implements DataCall {
        @Override
        public void success(Object data, Object... args) {
            Result<List<MessageDetailsBean>> result = (Result<List<MessageDetailsBean>>) data;
            List<MessageDetailsBean> result1 = result.getResult();
            MessageitemAdapter messageitemAdapter = new MessageitemAdapter(R.layout.messageitem, result1);
            recy.setLayoutManager(new LinearLayoutManager(MessageDetailsActivity.this, RecyclerView.VERTICAL, false));
            recy.setAdapter(messageitemAdapter);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void destoryData() {

    }
}

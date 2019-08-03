package com.wd.health.activity.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.bw.health.util.RsaCoder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wd.health.R;
import com.wd.health.activity.adapter.JG_details_Adapter;
import com.wd.health.activity.bean.MessageBean;
import com.wd.health.activity.bean.UserRecordBean;
import com.wd.health.activity.message.GlobalEventListener;
import com.wd.health.activity.presenter.FindMessagePresenter;
import com.wd.health.activity.presenter.NowWZPresenter;
import com.wd.health.activity.presenter.PushTextPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@Route(path = "/IMActivity/")
public class IMActivity extends WDActivity implements View.OnClickListener {

    private NowWZPresenter nowWZPresenter;
    private PushTextPresenter pushTextPresenter;
    private LoginBean loginBean;

    private String userName1;
    private Conversation mConversation;
    private String userName;
    /**
     * 小美
     */
    private TextView name;
    private ImageView back;
    private RelativeLayout top;
    private ImageView voice;
    private EditText context;
    private ImageView expression;
    private ImageView send;
    LinearLayout layout_main;
    RecyclerView recyclerView;
    private FindMessagePresenter findMessagePresenter;
    private JG_details_Adapter mAdapter;
    private String doctorName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @SuppressLint({"WrongViewCast", "WrongConstant"})
    @Override
    protected void initView() {
        recyclerView=findViewById(R.id.recycler);
        layout_main=findViewById(R.id.layout_main);
        name = (TextView) findViewById(R.id.name);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        top = (RelativeLayout) findViewById(R.id.top);
        voice = (ImageView) findViewById(R.id.voice);
        voice.setOnClickListener(this);
        context = (EditText) findViewById(R.id.context);
        expression = (ImageView) findViewById(R.id.expression);
        expression.setOnClickListener(this);
        send = (ImageView) findViewById(R.id.send);
        send.setOnClickListener(this);
        userName1 = getIntent().getExtras().getString("userName");
        doctorName = getIntent().getExtras().getString("name");
        name.setText(doctorName);
        try {
            userName = RsaCoder.decryptByPublicKey(userName1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nowWZPresenter = new NowWZPresenter(new NowWZ());
        pushTextPresenter = new PushTextPresenter(new PushText());
        findMessagePresenter = new FindMessagePresenter(new FindMessage());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new JG_details_Adapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size() <= 0) {
            intentByRouter("/LoginActivity/");
        } else {
            //创建单聊会话
            mConversation = Conversation.createSingleConversation(userName, "b5f102cc307091e167ce52e0");
            //进入单聊会话
            JMessageClient.enterSingleConversation(userName, "b5f102cc307091e167ce52e0");
            //设置消息接收 监听
            GlobalEventListener.setJG(this, false);
            //JMessageClient.enterSingleConversation(this.userName);
            loginBean = loginBeans.get(0);
            nowWZPresenter.reqeust(loginBean.getId(), loginBean.getSessionId());

        }
    }


    public class FindMessage implements DataCall<Result<List<MessageBean>>>{
        @Override
        public void success(Result<List<MessageBean>> data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.voice) {
        } else if (i == R.id.expression) {
        } else if (i == R.id.send) {
        } else {
        }
    }


    public class NowWZ implements DataCall<Result<UserRecordBean>> {
        @Override
        public void success(Result<UserRecordBean> data, Object... args) {
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context.getText().toString().length()>0&&!context.getText().toString().equals("")){


                    Message message = mConversation.createSendMessage(new TextContent(context.getText().toString()));
                    JMessageClient.sendMessage(message);
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                Toast.makeText(IMActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                initData();

                            } else {
                                Toast.makeText(IMActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                     // pushTextPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),data.getResult().recordId,context.getText(),1,data.getResult().doctorId);
                    }else {
                        Snackbar.make(layout_main,"内容不能为空！",BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            });


        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    public class PushText implements DataCall<Result> {
        @Override
        public void success(Result data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void destoryData() {

    }
    private boolean one;
    public void initData() {
      /*  List<Conversation> msgList = JMessageClient.getConversationList();*/
        /*if (msgList != null) {
            if (msgList.size() > 0) {
                if (msgList.get(position) != null) {
                    conversation = msgList.get(position);
                    //重置会话未读消息数
                    conversation.resetUnreadCount();

                }
            }
        }*/

        if (mConversation != null) {
          /*  title.setText(mConversation.getTitle() == null ? "" : mConversation.getTitle());*/
            UserInfo info = (UserInfo) mConversation.getTargetInfo();
            userName = info.getUserName();
            //userName = "f8443445-a7ef-47d8-8005-b0d57851b396";  //todo 可自定义

            //使列表滚动到底部
            if (mConversation.getAllMessage() != null) {
                if (mConversation.getAllMessage().size() > 0) {
                    mAdapter.setData(mConversation.getAllMessage());
                    //设置刷新不闪屏
                    ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                    if (one) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.notifyItemInserted(mConversation.getAllMessage().size() - 1);

                    }
                    recyclerView.scrollToPosition(mConversation.getAllMessage().size() - 1);

                }
            }



        }
        one=false; // 代表不是第一次initData

    }


}

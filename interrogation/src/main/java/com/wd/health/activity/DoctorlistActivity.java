package com.wd.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.activity.bean.UserRecordBean;
import com.wd.health.activity.presenter.EndWZPresenter;
import com.wd.health.activity.presenter.NowWZPresenter;
import com.wd.health.adapter.Myadapter;
import com.wd.health.adapter.MylistAdapter;
import com.wd.health.bean.DepartmentBean;
import com.wd.health.bean.Doctor;
import com.wd.health.Myrecycler;
import com.wd.health.R2;
import com.wd.health.presenter.ConsultDoctorPresenter;
import com.wd.health.presenter.FindDepartmentPresenter;
import com.wd.health.presenter.FindDoctorListPresenter;
import com.wd.health.presenter.FindDoctorListPresenter2;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

@Route(path = "/DoctorlistActivity/")
public class DoctorlistActivity extends WDActivity implements View.OnClickListener {


    @BindView(R2.id.head)
    SimpleDraweeView head;
    @BindView(R2.id.lingdang)
    ImageView lingdang;
    @BindView(R2.id.recy)
    RecyclerView recy;
    @BindView(R2.id.zonghe)
    RadioButton zonghe;
    @BindView(R2.id.haoping)
    RadioButton haoping;
    @BindView(R2.id.zixunshu)
    RadioButton zixunshu;
    @BindView(R2.id.price)
    RadioButton price;
    @BindView(R2.id.paixu)
    ImageView paixu;
    @BindView(R2.id.recy2)
    Myrecycler recy2;
    @BindView(R2.id.right)
    RelativeLayout right;
    @BindView(R2.id.left)
    RelativeLayout left;
    @BindView(R2.id.dang)
    TextView dang;
    @BindView(R2.id.zong)
    TextView zong;

    @BindView(R2.id.img)
    ImageView img;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.zhiwu)
    TextView zhiwu;
    @BindView(R2.id.yiyuan)
    TextView yiyuan;
    @BindView(R2.id.haopinglv)
    TextView haopinglv;
    @BindView(R2.id.fuwu)
    TextView fuwu;
    @BindView(R2.id.more)
    ImageView more;
    @BindView(R2.id.zixun)
    Button zixun;
    @BindView(R2.id.money)
    TextView money;
    @BindView(R2.id.gang)
    TextView gang;
    @BindView(R2.id.rg)
    RadioGroup rg;

    private int id;
    int position = 1;
    private FindDepartmentPresenter findDepartmentPresenter;
    private FindDoctorListPresenter findDoctorListPresenter;
    private List<Doctor> list1;
    private Myadapter myadapter;
    private Long userid;
    private String sessionId;
    private List<DepartmentBean> list;
    private boolean t = false;
    private FindDoctorListPresenter2 presenter2;
    private ConsultDoctorPresenter consultDoctorPresenter;
    /**
     * 有正在进行的咨询,是否结束
     */
    private TextView mTop;
    /**
     * 取消
     */
    private TextView mQuxiao;
    /**
     * 确认
     */
    private TextView mQueren;
    RelativeLayout dialog;
    private EndWZPresenter endWZPresenter;
    private NowWZPresenter nowWZPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctorlist;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("position");
        List<LoginBean> list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        for (LoginBean loginBean : list) {
            head.setImageURI(loginBean.getHeadPic());
        }
        dialog=findViewById(R.id.dialog);
        findDepartmentPresenter = new FindDepartmentPresenter(new FindDepartment());
        findDoctorListPresenter = new FindDoctorListPresenter(new FindDoctorList());
        consultDoctorPresenter = new ConsultDoctorPresenter(new Consult());
        presenter2 = new FindDoctorListPresenter2(new FindDoctorList());
        findDepartmentPresenter.reqeust();
        List<LoginBean> list1 = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        userid = list1.get(0).getId();
        sessionId = list1.get(0).getSessionId();
        Log.d("DoctorlistActivity2", "id:" + id + "/" + sessionId);
        findDoctorListPresenter.reqeust(userid.intValue(), sessionId, id, 1, "1", 1, 10);
        mTop = (TextView) findViewById(R.id.top);
        mQuxiao = (TextView) findViewById(R.id.quxiao);
        mQuxiao.setOnClickListener(this);
        mQueren = (TextView) findViewById(R.id.queren);
        mQueren.setOnClickListener(this);
        endWZPresenter = new EndWZPresenter(new EndWZ());
        nowWZPresenter = new NowWZPresenter(new NowWZ());

    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.lingdang, R2.id.zonghe, R2.id.haoping, R2.id.zixunshu, R2.id.price, R2.id.right, R2.id.left, R2.id.more})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lingdang) {
        } else if (i == R.id.zonghe) {
            for (DepartmentBean bean : list) {
                if (bean.isChecked) {
                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) bean.id, 1, "1", 1, 10);
                    break;
                }
            }
        } else if (i == R.id.haoping) {
            for (DepartmentBean bean : list) {
                if (bean.isChecked) {
                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) bean.id, 2, "1", 1, 10);
                    break;
                }
            }
        } else if (i == R.id.zixunshu) {
            for (DepartmentBean bean : list) {
                if (bean.isChecked) {
                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) bean.id, 3, "1", 1, 10);
                    break;
                }
            }
        } else if (i == R.id.price) {
            for (DepartmentBean bean : list) {
                if (bean.isChecked) {
                    if (t) {
                        findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) bean.id, 4, "1", 1, 10);
                        t = false;
                        paixu.setImageResource(R.mipmap.common_button_descending_s);
                        break;
                    } else {
                        presenter2.reqeust(userid.intValue(), sessionId, (int) bean.id, 4, 1, 10);
                        t = true;
                        paixu.setImageResource(R.mipmap.common_button_sequence_n);
                        break;
                    }

                }
            }
        } else if (i == R.id.left) {
            if (position > 0) {
                position--;
                for (Doctor doctor : list1) {
                    doctor.setSelect(false);
                }
                list1.get(position).setSelect(true);

                if (position % 3 == 2) {
                    if (position - 2 >= 0) {
                        recy2.smoothScrollToPosition(position - 2);
                        for (Doctor doctor : list1) {
                            doctor.setSelect(false);
                        }
                        list1.get(position).setSelect(true);
                    } else {
                        for (Doctor doctor : list1) {
                            doctor.setSelect(false);
                        }
                        list1.get(2).setSelect(true);
                        recy2.smoothScrollToPosition(2);
                        position = 2;
                    }

                }
                dang.setText(position + 1 + "");
                name.setText(list1.get(position).getDoctorName());
                zhiwu.setText(list1.get(position).getJobTitle());
                yiyuan.setText(list1.get(position).getInauguralHospital());
                haopinglv.setText(list1.get(position).getPraise());
                fuwu.setText("服务患者数" + list1.get(position).getServerNum());
                if (list1.get(position).getImagePic() != null && list1.get(position).getImagePic() != "") {
                    Glide.with(this).load(list1.get(position).getImagePic()).into(img);
                } else {
                    Glide.with(DoctorlistActivity.this).load(R.drawable.aaa).into(img);
                }
                money.setText(list1.get(position).getServicePrice() + "H币/次");
                myadapter.notifyDataSetChanged();
            }
        } else if (i == R.id.right) {
            if (position < list1.size() - 1) {
                position++;
                for (Doctor doctor : list1) {
                    doctor.setSelect(false);
                }
                list1.get(position).setSelect(true);
                if (position % 3 == 0) {
                    if (position + 3 <= list1.size() - 1) {
                        recy2.smoothScrollToPosition(position + 2);
                        for (Doctor doctor : list1) {
                            doctor.setSelect(false);
                        }
                        list1.get(position).setSelect(true);
                    } else {
                        for (Doctor doctor : list1) {
                            doctor.setSelect(false);
                        }
                        list1.get(position).setSelect(true);
                        recy2.smoothScrollToPosition(list1.size() - 1);
                    }

                }
                dang.setText(position + 1 + "");
                setdata(position);
                myadapter.notifyDataSetChanged();
            }
        } else if (i == R.id.more) {//医生详情
            Intent intent = new Intent(this, DoctordetailActivity.class);
            intent.putExtra("bean", list1.get(position).getDoctorId());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {

    }

    public class NowWZ implements DataCall<Result<UserRecordBean>>{
        @Override
        public void success(Result<UserRecordBean> data, Object... args) {

            mQuxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setVisibility(View.GONE);
                }
            });

            mQueren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
                    endWZPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),data.getResult().recordId);
                }
            });
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }



    public class EndWZ implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(DoctorlistActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            JMessageClient.exitConversation();
            dialog.setVisibility(View.GONE);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class FindDepartment implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            if (data != null && data != "") {
                Result<List<DepartmentBean>> result = (Result<List<DepartmentBean>>) data;
                list = result.getResult();
                for (DepartmentBean bean : list) {
                    if (id == bean.id) {
                        bean.isChecked = true;
                    }
                }
                MylistAdapter adapter = new MylistAdapter(R.layout.recy1, list);
                recy.setLayoutManager(new LinearLayoutManager(DoctorlistActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recy.setAdapter(adapter);
                for (int i = 0; i < list.size(); i++) {
                    if (id == list.get(i).id) {
                        recy.smoothScrollToPosition(i);
                    }

                }
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for (int i = 0; i < list.size(); i++) {
                            if (position == i) {
                                list.get(i).isChecked = true;
                                int checkedRadioButtonId = rg.getCheckedRadioButtonId();
                                if (checkedRadioButtonId == R.id.zonghe) {
                                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) list.get(i).id, 1, "1", 1, 10);
                                } else if (checkedRadioButtonId == R.id.haoping) {
                                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) list.get(i).id, 2, "1", 1, 10);
                                } else if (checkedRadioButtonId == R.id.zixunshu) {
                                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) list.get(i).id, 3, "1", 1, 10);
                                } else if (checkedRadioButtonId == R.id.price) {
                                    findDoctorListPresenter.reqeust(userid.intValue(), sessionId, (int) list.get(i).id, 4, "1", 1, 10);
                                }

                            } else {
                                list.get(i).isChecked = false;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    public class Consult implements DataCall<Result<String>> {
        @Override
        public void success(Result<String> data, Object... args) {

            Bundle bundle = new Bundle();
            bundle.putInt("id", doctorId);
            bundle.putString("name",doctorName);
            bundle.putString("userName",data.doctorUserName);
            intentByRouter("/IMActivity/", bundle);
        }

        @Override
        public void fail(ApiException data, Object... args) {
           /// Toast.makeText(DoctorlistActivity.this, "" + data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            if (data.getDisplayMessage().equals("有正在沟通中的咨询")) {
                dialog.setVisibility(View.VISIBLE);
                LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
                nowWZPresenter.reqeust(loginBean.getId(), loginBean.getSessionId());
            }
        }
    }
    private String doctorName;
    //设置数据的方法
    public void setdata(int position) {
        if (list1.size() > 0 && list1 != null) {
            doctorId = list1.get(position).getDoctorId();

            name.setText(list1.get(position).getDoctorName());
            zhiwu.setText(list1.get(position).getJobTitle());
            yiyuan.setText(list1.get(position).getInauguralHospital());
            haopinglv.setText("好评率 " + list1.get(position).getPraise());
            fuwu.setText("服务患者数" + list1.get(position).getServerNum());
            if (list1.get(position).getImagePic() != null && list1.get(position).getImagePic() != "") {
                Glide.with(DoctorlistActivity.this).load(list1.get(position).getImagePic()).into(img);
            } else {
                Glide.with(DoctorlistActivity.this).load(R.drawable.aaa).into(img);
            }
            money.setText(list1.get(position).getServicePrice() + "H币/次");
            zixun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doctorName=list1.get(position).getDoctorName();
                     LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
                    consultDoctorPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(), doctorId);

                }
            });

        } else {
            name.setText("");
            zhiwu.setText("");
            yiyuan.setText("");
            haopinglv.setText("");
            fuwu.setText("");
            if (list1.get(position).getImagePic() != null && !list1.get(position).getImagePic() .equals("") ) {
                Glide.with(DoctorlistActivity.this).load(list1.get(position).getImagePic()).into(img);
            } else {
                Glide.with(DoctorlistActivity.this).load(R.drawable.aaa).into(img);
            }

            money.setText("");
        }

    }

    int doctorId;

    public class FindDoctorList implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result<List<Doctor>> result = (Result<List<Doctor>>) data;
            list1 = result.getResult();
            if (list1 != null && list1.size() > 0) {
                if (list1.size() > 2) {
                    list1.get(1).setSelect(true);
                    recy2.setLayoutManager(new LinearLayoutManager(DoctorlistActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    myadapter = new Myadapter(R.layout.item, list1);
                    recy2.setAdapter(myadapter);
                    dang.setText("2");
                    zong.setText(list1.size() + "");
                    setdata(1);
                    position=1;
                } else if(list1.size()>1){
                    list1.get(1).setSelect(true);
                    recy2.setLayoutManager(new LinearLayoutManager(DoctorlistActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    myadapter = new Myadapter(com.wd.health.R.layout.item, list1);
                    recy2.setAdapter(myadapter);
                    dang.setText("1");
                    zong.setText("1");
                    setdata(1);
                    position = 1;
                } else {
                    list1.get(0).setSelect(true);
                    recy2.setLayoutManager(new LinearLayoutManager(DoctorlistActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    myadapter = new Myadapter(R.layout.item, list1);
                    recy2.setAdapter(myadapter);
                    dang.setText("1");
                    zong.setText("1");
                    setdata(0);
                    position = 0;
                }
            }

        }

        @Override
        public void fail(ApiException data, Object... args) {
            Log.d("FindDoctorList2", data.getDisplayMessage());
        }
    }
}

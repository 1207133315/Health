package com.wd.doctor.interrogation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.doctor.interrogation.adapter.Myadapter;
import com.wd.doctor.interrogation.adapter.MylistAdapter;
import com.wd.doctor.interrogation.bean.DepartmentBean;
import com.wd.doctor.interrogation.bean.Doctor;
import com.wd.doctor.interrogation.presenter.FindDepartmentPresenter;
import com.wd.doctor.interrogation.presenter.FindDoctorListPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/DoctorlistActivity/")
public class DoctorlistActivity extends WDActivity {


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
    @BindView(R2.id.jiage)
    RelativeLayout jiage;
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
    private int id;
    int position = 1;
    int p = 1;
    private FindDepartmentPresenter findDepartmentPresenter;
    private FindDoctorListPresenter findDoctorListPresenter;
    private List<Doctor> list1;
    private Myadapter myadapter;

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
        findDepartmentPresenter = new FindDepartmentPresenter(new FindDepartment());
        findDoctorListPresenter = new FindDoctorListPresenter(new FindDoctorList());
        findDepartmentPresenter.reqeust();
        List<LoginBean> list1 = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        Long userid = list1.get(0).getId();
        String sessionId = list1.get(0).getSessionId();
        Log.d("DoctorlistActivity2", "id:" + id + "/" + sessionId);
        findDoctorListPresenter.reqeust(userid.intValue(), sessionId, id, 1, 1, 1, 10);
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.lingdang, R2.id.zonghe, R2.id.haoping, R2.id.zixunshu, R2.id.price, R2.id.right, R2.id.left})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lingdang) {
        } else if (i == R.id.zonghe) {
        } else if (i == R.id.haoping) {
        } else if (i == R.id.zixunshu) {
        } else if (i == R.id.price) {
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
                dang.setText(position+1+"");
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
                dang.setText(position+1+"");
                myadapter.notifyDataSetChanged();
            }
        }
    }

    public class FindDepartment implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result<List<DepartmentBean>> result = (Result<List<DepartmentBean>>) data;
            List<DepartmentBean> list = result.getResult();
            for (DepartmentBean bean : list) {
                if (id == bean.id) {
                    bean.isChecked = true;
                }
            }
            MylistAdapter adapter = new MylistAdapter(R.layout.recy1, list);
            recy.setLayoutManager(new LinearLayoutManager(DoctorlistActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recy.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    for (int i = 0; i < list.size(); i++) {
                        if (position == i) {
                            list.get(i).isChecked = true;
                        } else {
                            list.get(i).isChecked = false;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

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
                    zong.setText(list1.size()+"");
                } else {
                    list1.get(0).setSelect(true);
                    recy2.setLayoutManager(new LinearLayoutManager(DoctorlistActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    myadapter = new Myadapter(R.layout.item, list1);
                    recy2.setAdapter(myadapter);
                    dang.setText("1");
                    zong.setText(list1.size());
                }
            }

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}

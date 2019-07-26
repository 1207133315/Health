package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.CircleFindDepartmentBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.exception.ApiException;
import com.suke.widget.SwitchButton;
import com.wd.health.R;
import com.wd.health.adapter.CircleFindDepartmentAdapter;
import com.wd.health.adapter.CircleWritBingZhengAdapter;
import com.wd.health.adapter.CircleWritDepartmentAdapter;
import com.wd.health.bean.CircleBingZhengBean;
import com.wd.health.presenter.CircleBingZhengPresenter;
import com.wd.health.presenter.CircleFindDepartmentPresenter;

import java.util.List;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/24
 * @Description: 发布病友圈
 */
@Route(path = "/CircleWritActivity/")
public class CircleWritActivity extends AppCompatActivity {

    private PopupWindow pop1;
    private PopupWindow pop2;
    private RecyclerView pop_writ_keshi_rc1;
    private CircleWritDepartmentAdapter circleWritDepartmentAdapter;
    private View keshi_xian;
    private ImageView keshi_iamge;
    private ImageView keshi_iamge2;
    private EditText keshi_edText;
    public CircleBingZhengPresenter circleBingZhengPresenter;
    private RecyclerView pop_writ_bingzheng_rc2;
    private CircleWritBingZhengAdapter circleWritBingZhengAdapter;
    private View bingzheng_xian;
    private ImageView bingzheng_iamge;
    private ImageView bingzheng_iamge2;
    private EditText bingzheng_edText;
    private CircleFindDepartmentPresenter circleFindDepartmentPresenter;
    private int id_bingzheng=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_writ);
        //获取控件
        //----------点击弹出popwindow获取科室--------------------------
        keshi_xian = findViewById(R.id.circle_writ_keshi_xian);
        keshi_iamge = findViewById(R.id.circle_writ_keshi_image);
        keshi_iamge2 = findViewById(R.id.circle_writ_keshi_image2);
        keshi_edText = findViewById(R.id.circle_writ_keshi_edText);
        //----------点击弹出popwindow获取科室对应的病症--------------------------

        bingzheng_xian = findViewById(R.id.circle_writ_bingzheng_xian);
        bingzheng_iamge = findViewById(R.id.circle_writ_bingzheng_image);
        bingzheng_iamge2 = findViewById(R.id.circle_writ_bingzheng_image2);
        bingzheng_edText = findViewById(R.id.circle_writ_bingzheng_edText);


        //----------点击弹出popwindow获取科室--------------------------

        keshi_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(CircleWritActivity.this, R.layout.circle_writ_pop_department_layout, null);
                pop1 = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pop1.showAsDropDown(keshi_xian);
                keshi_iamge.setVisibility(View.GONE);
                keshi_iamge2.setVisibility(View.VISIBLE);

                //关联 presenter
                circleFindDepartmentPresenter = new CircleFindDepartmentPresenter(new KeshiCall());
                circleFindDepartmentPresenter.reqeust();


                //RecyclerView
                pop_writ_keshi_rc1 = view.findViewById(R.id.pop_writ_keshi_rc1);
                //布局管理器
                GridLayoutManager gridLayoutManager = new GridLayoutManager(CircleWritActivity.this, 4);
                pop_writ_keshi_rc1.setLayoutManager(gridLayoutManager);
                //适配器
                circleWritDepartmentAdapter = new CircleWritDepartmentAdapter(CircleWritActivity.this);
                pop_writ_keshi_rc1.setAdapter(circleWritDepartmentAdapter);
                circleWritDepartmentAdapter.setCall(new CircleWritDepartmentAdapter.Call() {
                    @Override
                    public void showCall(int id, String name) {
                        keshi_edText.setText(name);
                        // Log.i("id", id + "");
                        id_bingzheng = id;
                        pop1.dismiss();
                        keshi_iamge2.setVisibility(View.GONE);
                        keshi_iamge.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        //关闭popwindow
        shut();
        //----------点击弹出popwindow获取科室-----尾巴---------------------

        //----------点击弹出popwindow获取科室对应的病症--------------------------

        bingzheng_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = View.inflate(CircleWritActivity.this, R.layout.circle_writ_pop_bingzheng_layout, null);
                pop2 = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                pop2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pop2.showAsDropDown(bingzheng_xian);
                bingzheng_iamge.setVisibility(View.GONE);
                bingzheng_iamge2.setVisibility(View.VISIBLE);
                //关联 病症presenter
                circleBingZhengPresenter = new CircleBingZhengPresenter(new BingZhengCall());
                if (String.valueOf(id_bingzheng).equals("-1")) {
                    Log.i("id", id_bingzheng + "");
                    Toast.makeText(CircleWritActivity.this, "请选择科室", Toast.LENGTH_SHORT).show();
                } else {
                    circleBingZhengPresenter.reqeust(id_bingzheng + "");
                }

                //获取RecyclerView
                pop_writ_bingzheng_rc2 = view.findViewById(R.id.pop_writ_bingzheng_rc2);
                //布局管理器
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(CircleWritActivity.this, 4);
                pop_writ_bingzheng_rc2.setLayoutManager(gridLayoutManager1);
                //适配器
                circleWritBingZhengAdapter = new CircleWritBingZhengAdapter(CircleWritActivity.this);
                pop_writ_bingzheng_rc2.setAdapter(circleWritBingZhengAdapter);
                circleWritBingZhengAdapter.setCall(new CircleWritBingZhengAdapter.Call() {
                    @Override
                    public void showCall(int id, String name) {
                        bingzheng_edText.setText(name);
                        pop2.dismiss();
                        bingzheng_iamge2.setVisibility(View.GONE);
                        bingzheng_iamge.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        bingzheng_iamge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bingzheng_iamge2.setVisibility(View.GONE);
                bingzheng_iamge.setVisibility(View.VISIBLE);
                pop2.dismiss();
            }
        });

        //----------点击弹出popwindow获取科室对应的病症-----尾巴---------------------



        //----------点击弹出popwindow获取科室对应的病症--------------------------



        //----------点击弹出popwindow获取科室对应的病症--------------------------






        //---------------开关显示 隐藏------------------------
        LinearLayout hbi_layout = findViewById(R.id.circle_writ_layout_hbi);
        SwitchButton switchButton = findViewById(R.id.switchbutton);

        switchButton.setChecked(false);
        switchButton.isChecked();
        switchButton.toggle();
        switchButton.toggle(true);
        switchButton.setShadowEffect(true);
        switchButton.setEnabled(true);
        switchButton.setEnableEffect(false);


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    hbi_layout.setVisibility(View.VISIBLE);
                } else {
                    hbi_layout.setVisibility(View.GONE);
                }
            }
        });

        //---------------开关显示 隐藏------尾巴------------------


    }
    //----------关闭科室popwindow---------------------------

    private void shut() {
        keshi_iamge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keshi_iamge2.setVisibility(View.GONE);
                keshi_iamge.setVisibility(View.VISIBLE);
                pop1.dismiss();
            }
        });
    }
    //----------关闭科室popwindow---------------------------


    //----------点击获取科室------------成功-----失败---------------
    class KeshiCall implements DataCall<Result<List<CircleFindDepartmentBean>>> {

        @Override
        public void success(Result<List<CircleFindDepartmentBean>> data, Object... args) {
            List<CircleFindDepartmentBean> keshi_result = data.getResult();
            circleWritDepartmentAdapter.setData(keshi_result);
            circleWritDepartmentAdapter.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    //----------点击获取科室------------成功-----失败----尾巴---------


    //----------点击获取科室对应的病状------------成功-----失败---------------
    class BingZhengCall implements DataCall<Result<List<CircleBingZhengBean>>> {

        @Override
        public void success(Result<List<CircleBingZhengBean>> data, Object... args) {
            List<CircleBingZhengBean> bingzheng_result = data.getResult();
            circleWritBingZhengAdapter.setData(bingzheng_result);
            circleWritBingZhengAdapter.notifyDataSetChanged();


        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    //----------点击获取科室对应的病状------------成功-----失败----尾巴-----------


}

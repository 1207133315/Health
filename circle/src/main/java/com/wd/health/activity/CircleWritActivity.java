package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.TimePickerView;
import com.bw.health.bean.CircleFindDepartmentBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.suke.widget.SwitchButton;
import com.wd.health.R;
import com.wd.health.adapter.CircleWritBingZhengAdapter;
import com.wd.health.adapter.CircleWritDepartmentAdapter;
import com.wd.health.bean.CircleBingZhengBean;
import com.wd.health.presenter.CircleBingZhengPresenter;
import com.wd.health.presenter.CircleFindDepartmentPresenter;
import com.wd.health.presenter.PublishSickCirclePresenter;
import com.wd.health.presenter.WardMateSctxPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int id_bingzheng;//科室id
    private TimePickerView pvTime;
    private TimePickerView pvTime1;
    private int departmentId_id;
    private ImageView start_time_image;
    private EditText start_time_edText;
    private ImageView end_time_image;
    private EditText end_time_edText;
    private PopupWindow pop_image;
    private RecyclerView bingli_image1;
    private String title_text;
    private String disease_text;
    private String detail_text;
    private String treatmentHospital_text;
    private String treatmentStartTime_text;
    private String treatmentEndTime_text;
    private String treatmentProcess_text;
    private EditText treatmentHospital_edText;
    private EditText detail_edText;
    private EditText treatmentProcess_edText;
    private EditText title_edText;

    private int maxSelectNum = 6;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int themeId;

    private ArrayList<String> mList = new ArrayList<>();
    private WardMateSctxPresenter wardMateSctxPresenter;

    private String sessionId;
    private Long id_user;
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
        detail_edText = findViewById(R.id.circle_writ_bingzhenginfo_edText);
        //----------点击弹出popwindow获取开始时间--------------------------
        start_time_image = findViewById(R.id.circle_writ_start_time_image);
        start_time_edText = findViewById(R.id.circle_writ_start_time_edText);
        //----------点击弹出popwindow获取结束时间--------------------------
        end_time_image = findViewById(R.id.circle_writ_end_time_image);
        end_time_edText = findViewById(R.id.circle_writ_end_time_edText);

        //标题
        title_edText = findViewById(R.id.circle_writ_title_edText);
        treatmentHospital_edText = findViewById(R.id.circle_writ_yiyuan_edText);
        treatmentProcess_edText = findViewById(R.id.circle_writ_zhiliaoguocheng_edText);


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
                if (String.valueOf(id_bingzheng).equals("0")) {
                    Log.i("id", id_bingzheng + "");
                    Toast.makeText(CircleWritActivity.this, "请先选择科室!!", Toast.LENGTH_SHORT).show();
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
                        departmentId_id = id;
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

        //----------点击弹出popwindow获取开始时间--------------------------

        //时间选择器
        //选中事件回调
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                start_time_edText.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        //点击选择时间
        start_time_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();

            }
        });

        //----------点击弹出popwindow获取开始时间--尾巴------------------------
        //----------点击弹出popwindow获取结束时间--------------------------


        //时间选择器
        //选中事件回调
        pvTime1 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                end_time_edText.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();

        //点击选择时间
        end_time_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime1.setDate(Calendar.getInstance());
                pvTime1.show();

            }
        });

        //----------点击弹出popwindow获取结束时间--尾巴------------------------

        //---------------------长按为图片排序------------------------------



        bingli_image1 = findViewById(R.id.circle_writ_bingli_image1);


        //关联p
        wardMateSctxPresenter = new WardMateSctxPresenter(new SendImageViewCall());





        //---------------------长按为图片排序--------尾巴----------------------

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


        //-------------发送我的病友圈----------------------
        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(CircleWritActivity.this, LoginBeanDao.TABLENAME).getLoginBeanDao();

           id_user = loginBeanDao.loadAll().get(0).getId();
         sessionId = loginBeanDao.loadAll().get(0).getSessionId();


        //发送
        Button circle_writ_send = findViewById(R.id.circle_writ_send);
        circle_writ_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_text = title_edText.getText().toString();
                Log.i("json", "标题:" + title_text);
                //科室id
                Log.i("json", "科室id" + id_bingzheng + "");
                //病症描述
                disease_text = bingzheng_edText.getText().toString();
                Log.i("json", "病症描述:" + disease_text);
                //病症详情
                detail_text = detail_edText.getText().toString();
                Log.i("json", "病症详情:" + detail_text);
                //治疗医院
                treatmentHospital_text = treatmentHospital_edText.getText().toString();
                Log.i("json", "治疗医院:" + treatmentHospital_text);
                //治疗开始时间2018-3-26
                treatmentStartTime_text = start_time_edText.getText().toString();
                Log.i("json", "开始时间:" + treatmentStartTime_text + "");
                //治疗结束时间
                treatmentEndTime_text = end_time_edText.getText().toString();
                Log.i("json", "结束时间:" + treatmentEndTime_text + "");
                //治疗过程描述
                treatmentProcess_text = treatmentProcess_edText.getText().toString();
                Log.i("json", "治疗过程描述:" + treatmentProcess_text + "");
                //悬赏额度无时为0


                Map<String, String> map = new HashMap<>();
                map.put("title", title_text);
                map.put("departmentId", String.valueOf(id_bingzheng));
                map.put("disease", disease_text);
                map.put("detail", detail_text);
                map.put("treatmentHospital", treatmentHospital_text);
                map.put("treatmentStartTime", treatmentStartTime_text);
                map.put("treatmentEndTime", treatmentEndTime_text);
                map.put("treatmentProcess", treatmentProcess_text);
                map.put("amount", String.valueOf(10));

                //String ctions = JsonUtil.parseMapToJson(map);
                Gson gson = new Gson();
                String json = gson.toJson(map);
                // Log.i("json", "--" + json + "");
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                //关联presenter
                PublishSickCirclePresenter publishSickCirclePresenter = new PublishSickCirclePresenter(new SendCircleCall());
                publishSickCirclePresenter.reqeust(String.valueOf(id_user), sessionId, body);

            }
        });


        //-------------发送我的病友圈----尾巴------------------

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


    //------设置时间格式------
    private String getTime(Date date) {
        //可根据需要自行截取数据显示
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    //-----------发送病友圈----成功--失败--------------------------------
    class SendCircleCall implements DataCall<Result<Integer>> {

        @Override
        public void success(Result<Integer> data, Object... args) {
            Integer result = data.getResult();
            wardMateSctxPresenter.reqeust(String.valueOf(id_user),sessionId,String.valueOf(result),mList);
            Toast.makeText(CircleWritActivity.this, "发布成功" + result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    //-----------发送病友圈----成功--失败--尾巴--------------------------------

    //------------------点击发送图片-----------------------------------------------------



    //------------------点击发送图片----------尾巴-------------------------------------------

    //------------------点击发送图片-----成功失败-------------------------------------------

    class SendImageViewCall implements DataCall<Result>{

        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(CircleWritActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

            Toast.makeText(CircleWritActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }





    //------------------点击发送图片-----成功失败-----尾巴--------------------------------------
}

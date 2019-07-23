package com.wd.health.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.wd.health.R;
import com.wd.health.adapter.CircleSearchAdapter;
import com.wd.health.adapter.HistorySearchAdapter;
import com.wd.health.bean.CircleSearchBean;
import com.wd.health.presenter.CircleSearchPresenter;
import com.wd.health.utils.HistorySearchUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: 郭亚杰
 * @Date:2019/7/19
 * @Description: 根据关键字搜索病友圈
 */
@Route(path = "/FindSickCircleInfoActivity/")
public class FindSickCircleInfoActivity extends WDActivity {

    private ImageView circle_back;//返回按钮
    private EditText searchEdit;//搜索EditText
    private TextView searchTv;//搜索按钮，不过是以TextView形式
    private RecyclerView histotyRecycler;//历史纪录列表
    private TextView historyEmptyTv;//清空历史纪录按钮
    private LinearLayout histotySearchLayout;//历史记录整个布局

    private HistorySearchAdapter adapter;//适配器

    private ArrayList<String> histotyList = new ArrayList<String>();//历史纪录数组
    private LinearLayout circleSearchLayout; //根据关键字搜索页面
    private CircleSearchPresenter circleSearchPresenter;
    private RecyclerView search_rc2;
    private CircleSearchAdapter circleSearchAdapter;
    private LinearLayout search_wu_layout;//搜索无内容页面
    private TextView search_wu_text;
    private String keyWordText;//获取输入框内容


    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_sick_circle_info;
    }

    @Override
    protected void initView() {

        //----------------搜索历史-------------------------------
        //获取控件
        //返回
        circle_back = findViewById(R.id.ciecle_search_back);
        //输入框
        searchEdit = findViewById(R.id.circle_search_edText);
        //搜索按钮
        searchTv = findViewById(R.id.circle_search_button);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initViews();//初始化组件
        initHistoryRecycler();//初始化historyRecyclerView
        getHistoryList();//得到历史记录数组
        setSearchTvListener();//设置搜索按钮监听器
        setHistoryEmptyTvListener();//设置清空记录按钮监听器


        //点击返回
        circle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //----------------搜索历史----尾巴-----------------------

        //----------------根据关键字搜索------------------------------

        //根据关键字搜索页面
        circleSearchLayout = findViewById(R.id.circle_search_layout);
        //获取搜索页面的RecyclerView
        search_rc2 = findViewById(R.id.circle_search_rc2);
        //关联根据关键字搜索presenter
        circleSearchPresenter = new CircleSearchPresenter(new CircleSearchCall());

        //布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        search_rc2.setLayoutManager(linearLayoutManager1);
        //适配器
        circleSearchAdapter = new CircleSearchAdapter(this);
        search_rc2.setAdapter(circleSearchAdapter);


        //----------------根据关键字搜索----尾巴-----------------------


        //----------------搜索无内容------------------------------
          //搜索无内容页面
        search_wu_layout = findViewById(R.id.circle_search_layout_wu);
        search_wu_text = findViewById(R.id.circle_search_wu_text);

        //----------------搜索无内容------------------------------


    }

    //----------------搜索历史-------------------------------
    private void setHistoryEmptyTvListener() {
        historyEmptyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistorySearchUtil.getInstance(FindSickCircleInfoActivity.this)
                        .deleteAllHistorySearch();
                getHistoryList();
                adapter.notifyDataSetChanged();//刷新列表
                showViews();
            }
        });
    }

    private void setSearchTvListener() {

        //点击搜索按钮
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistorySearchUtil.getInstance(FindSickCircleInfoActivity.this)
                        .putNewSearch(searchEdit.getText().toString());//保存记录到数据库
                getHistoryList();
                adapter.notifyDataSetChanged();
                showViews();
                Toast.makeText(FindSickCircleInfoActivity.this, "此条记录已保存到数据库",
                        Toast.LENGTH_SHORT).show();

                //根据关键字搜索时切换页面
                circleSearchLayout.setVisibility(View.VISIBLE);
                histotySearchLayout.setVisibility(View.GONE);

                //获取输入框内容
                keyWordText = searchEdit.getText().toString().trim();
                circleSearchPresenter.reqeust(keyWordText);
                search_wu_text.setText("抱歉！没有找到 “"+keyWordText+"” 的相关病友圈");

            }
        });
    }

    /**
     * 设置历史记录界面可见性，即记录为空时，不显示清空历史记录按钮等view
     */
    private void showViews() {
        if (histotyList.size() > 0) {
            histotySearchLayout.setVisibility(View.VISIBLE);
        } else {
            histotySearchLayout.setVisibility(View.GONE);
        }
    }

    private void initHistoryRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        histotyRecycler.setLayoutManager(linearLayoutManager);
        histotyRecycler.setNestedScrollingEnabled(false);//解决滑动冲突
        adapter = new HistorySearchAdapter(this, histotyList);
        histotyRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new HistorySearchAdapter.OnItemClickListener() {
            @Override
            public void onItemNameTvClick(View v, String name) {
                //点击历史记录条目后 进行关键字搜索
               // searchEdit.setText(name);
                circleSearchPresenter.reqeust(name);
                search_wu_text.setText("抱歉！没有找到 “"+name+"” 的相关病友圈");

            }

            @Override
            public void onItemDeleteImgClick(View v, String name) {
                HistorySearchUtil.getInstance(FindSickCircleInfoActivity.this)
                        .deleteHistorySearch(name);
                getHistoryList();
                adapter.notifyDataSetChanged();
                showViews();


            }
        });
    }

    //布局控件
    private void initViews() {
        historyEmptyTv = (TextView) findViewById(R.id.history_empty_tv);
        histotyRecycler = (RecyclerView) findViewById(R.id.history_search_recycler);
        histotySearchLayout = (LinearLayout) findViewById(R.id.history_search_layout);
    }

    private void getHistoryList() {
        histotyList.clear();
        histotyList.addAll(HistorySearchUtil.getInstance(this)
                .queryHistorySearchList());
        adapter.notifyDataSetChanged();
        showViews();
    }

    //----------------搜索历史----尾巴-----------------------

    //----------------根据关键字搜索------------------------------
    class CircleSearchCall implements DataCall<Result<List<CircleSearchBean>>> {

        @Override
        public void success(Result<List<CircleSearchBean>> data, Object... args) {
            List<CircleSearchBean> search_result = data.getResult();
              if (search_result.size()==0){
                  //无内容页面显示
                 search_wu_layout.setVisibility(View.VISIBLE);
                 //搜索关键字页面隐藏
                 circleSearchLayout.setVisibility(View.GONE);
                   //历史记录页面隐藏
                  histotySearchLayout.setVisibility(View.GONE);

              }else {
                  //无内容页面隐藏
                  search_wu_layout.setVisibility(View.GONE);
                  //搜索关键字页面显示
                  circleSearchLayout.setVisibility(View.VISIBLE);
                  //历史记录页面隐藏
                  histotySearchLayout.setVisibility(View.GONE);
              }
            circleSearchAdapter.getData(search_result);
            circleSearchAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    //----------------根据关键字搜索----尾巴-----------------------

    @Override
    protected void destoryData() {

    }
}

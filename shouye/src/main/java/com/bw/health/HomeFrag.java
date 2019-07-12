package com.bw.health;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.RadioButton;

import com.bw.health.bean.BannerBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.ShowBannerPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * com.bw.health
 *
 * @author 李宁康
 * @date 2019 2019/07/11 14:16
 */
public class HomeFrag extends WDFragment {

    /* @BindView(R2.id.bookView)
     BookPageView bookPageView;*/
    @BindView(R2.id.one)
    RadioButton one;
    @BindView(R2.id.two)
    RadioButton two;
    @BindView(R2.id.three)
    RadioButton three;
    @BindView(R2.id.four)
    RadioButton four;
    @BindView(R2.id.five)
    RadioButton five;
    private ShowBannerPresenter showBannerPresenter;


    @Override
    public String getPageName() {
        return "首页";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_frag_layout;
    }

    @SuppressLint({"ResourceAsColor", "InvalidR2Usage"})
    @Override
    protected void initView() {
        showBannerPresenter = new ShowBannerPresenter(new Banner());

        showBannerPresenter.reqeust();
        one.setChecked(true);

    }
    @SuppressLint({"ResourceAsColor", "InvalidR2Usage"})
    @OnClick({R2.id.one,R2.id.two,R2.id.three,R2.id.four,R2.id.five})
    public void click(View v){
        int i = v.getId();
        if (i == R.id.one) {

        }else if (i==R.id.two){

        }else if (i==R.id.three){

        }else if (i==R.id.four){

        }else if (i==R.id.five){

        }
    }


    public class Banner implements DataCall<Result<List<BannerBean>>> {
        @Override
        public void success(Result<List<BannerBean>> data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}

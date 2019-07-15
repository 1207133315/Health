package com.bw.health.prenster;

import android.app.Application;

import com.bw.health.Irequset;
import com.bw.health.activity.LoginActivity;
import com.bw.health.bean.LoginBean;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDApplication;
import com.bw.health.core.WDPresenter;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.DaoSession;
import com.bw.health.dao.LoginBeanDao;

import java.util.List;

import io.reactivex.Observable;

public class UpdateUserPwdPresenter extends WDPresenter<Irequset> {
    public UpdateUserPwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {

        return iRequest.updateUserPwd((int)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }
}

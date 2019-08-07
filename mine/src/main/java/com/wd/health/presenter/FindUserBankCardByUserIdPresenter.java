package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class FindUserBankCardByUserIdPresenter extends WDPresenter<Minerequest> {
    public FindUserBankCardByUserIdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findUserBankCardByUserId((int)args[0],(String)args[1]);
    }
}

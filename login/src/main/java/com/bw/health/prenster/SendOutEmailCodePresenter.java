package com.bw.health.prenster;

import com.bw.health.Irequset;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

public class SendOutEmailCodePresenter extends WDPresenter<Irequset> {
    public SendOutEmailCodePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.sendEmailCode((String) args[0]);
    }
}

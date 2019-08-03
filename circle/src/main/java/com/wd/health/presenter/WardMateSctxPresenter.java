package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.luck.picture.lib.entity.LocalMedia;
import com.wd.health.model.CircleIRquest;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author:zhaolingling
 * @E-mail： 2212096722@qq.com
 * @Date： 2019/7/22 11:24
 * @Description：上传头像
 */
public class WardMateSctxPresenter extends WDPresenter<CircleIRquest> {

    public WardMateSctxPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        List<LocalMedia> list = (List<LocalMedia>) args[3];
        if (list.size()>1) {
            for (int i = 1; i < list.size(); i++) {
                File file = new File(String.valueOf(list.get(i)));
                builder.addFormDataPart("picture", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        return iRequest.SctxId((String) args[0],(String)args[1],(String)args[2],builder.build());
    }
}
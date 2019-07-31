package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/30 20:37
 */
public class UpArchivesPicPresenter extends WDPresenter<Minerequest> {
    public UpArchivesPicPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        ArrayList<File> files = (ArrayList<File>) args[2];
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            builder.addFormDataPart("picture",files.get(i).getName(),RequestBody.create(MediaType.parse("multipart/octet-stream"),files.get(i)));
        }
        return iRequest.upArchivesPic((long)args[0],(String)args[1],builder.build());
    }
}

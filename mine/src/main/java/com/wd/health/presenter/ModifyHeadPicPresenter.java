package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Irequest;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ModifyHeadPicPresenter extends WDPresenter<Irequest> {
    public ModifyHeadPicPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), (File) args[2]);
        MultipartBody.Part MultipartFile =
                MultipartBody.Part.createFormData("image", ((File) args[2]).getName(), requestFile);
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        File file=new File((String) args[2]);
//        builder.addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("multipart/octet-stream"),file));
        return iRequest.modifyHeadPic((int)args[0],(String) args[1],MultipartFile);
    }
}

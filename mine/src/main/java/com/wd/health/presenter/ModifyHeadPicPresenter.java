package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class ModifyHeadPicPresenter extends WDPresenter<Minerequest> {
    public ModifyHeadPicPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {

//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        File file=new File((String) args[2]);
//        builder.addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("multipart/octet-stream"),file));
        return iRequest.modifyHeadPic((int)args[0],(String) args[1],(MultipartBody.Part) args[2]);
    }
}

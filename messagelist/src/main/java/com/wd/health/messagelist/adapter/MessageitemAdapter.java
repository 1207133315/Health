package com.wd.health.messagelist.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.health.messagelist.R;
import com.wd.health.messagelist.bean.MessageDetailsBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class MessageitemAdapter extends BaseQuickAdapter<MessageDetailsBean,BaseViewHolder> {
    public MessageitemAdapter(int layoutResId, @Nullable List<MessageDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageDetailsBean item) {
        helper.setText(R.id.content,item.getContent());
        long createTime = item.getCreateTime();
        String format = "yyyy年MM月dd日 HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        helper.setText(R.id.data,simpleDateFormat.format(new Date(Long.valueOf(createTime))));
    }
}

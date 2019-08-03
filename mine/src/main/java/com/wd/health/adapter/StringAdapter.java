package com.wd.health.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.health.R;

import java.util.List;

import androidx.annotation.Nullable;

public class StringAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public StringAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text,item);
    }
}

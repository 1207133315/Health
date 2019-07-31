package com.bw.health.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageLoader;


/**
 * @Author： 付尚凯
 * @Date： 2019/6/19 10:30
 * @Description: 图片加载器
 * @Email： 2024468244@qq.com
 */
public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .override(210,210)
                .centerCrop()
                .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                .into(imageView);
    }

}

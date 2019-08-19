package com.wd.health.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.mine.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * com.wd.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/31 10:47
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> list=new ArrayList<>();
    private Context context;
    FileInputStream fs = null;
    public ImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position==0){
            holder.img.setImageURI(Uri.parse("file://"+list.get(position)));
            holder.close.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCall.click(list.get(position));
                }
            });
        }else {
            holder.img.setImageURI(Uri.parse(list.get(position)));
            holder.close.setVisibility(View.VISIBLE);
            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    closeCall.click(position);
                    notifyDataSetChanged();
                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<String> urls) {
        list.addAll(urls);
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img;
        ImageView close;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            close=itemView.findViewById(R.id.close);
        }
    }
    private  ClickCall clickCall;

    public void setClickCall(ClickCall clickCall) {
        this.clickCall = clickCall;
    }

    public interface ClickCall{
        void click(String url);
    }

    private  CloseCall closeCall;

    public void setCloseCall(CloseCall closeCall) {
        this.closeCall = closeCall;
    }

    public interface CloseCall{
        void click(int i);
    }
}

package com.bw.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.activity.SearchActivity;
import com.bw.health.bean.RemenBean;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.RemenBeanDao;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.bw.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/22 16:06
 */
public class RemenAdapter extends RecyclerView.Adapter<RemenAdapter.ViewHolder> {
    private Context context;
    private final List<RemenBean> remenBeans;
    private final RemenBeanDao remenBeanDao;

    public RemenAdapter(Context context) {
        this.context = context;
        remenBeanDao = DaoMaster.newDevSession(context, RemenBeanDao.TABLENAME).getRemenBeanDao();
        remenBeans = remenBeanDao.loadAll();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lishi_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (remenBeans.size()>0){
                holder.name.setText(remenBeans.get(position).name);
                holder.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        remenBeanDao.deleteByKey(remenBeans.get(position).id);
                        remenBeans.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return remenBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.name)
        TextView name;
        @BindView(R2.id.remove)
        ImageView close;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

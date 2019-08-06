package com.wd.health.activity.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.health.R;

import androidx.appcompat.app.AlertDialog;

import static com.bw.health.core.WDApplication.getContext;

/**
 * com.wd.health.activity.view
 *
 * @author 李宁康
 * @date 2019 2019/08/05 19:14
 */
public class DialogManager {
    private AlertDialog.Builder builder;
    private ImageView mIcon;
    //    private ImageView mVoice;
    private TextView mLable;

    private Context context;

    private Dialog dialog;//用于取消AlertDialog.Builder

    /**
     * 构造方法 传入上下文
     */
    public DialogManager(Context context) {
        this.context = context;
    }

    //音量图片
    private ImageView soundVolumeImg = null;
    //对话框背景
    private RelativeLayout soundVolumeLayout = null;
    // 显示录音的对话框
    public void showRecordingDialog() {
        dialog = new Dialog(getContext(), R.style.SoundVolumeStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.tt_sound_volume_dialog);
        dialog.setCanceledOnTouchOutside(true);
        soundVolumeImg = (ImageView) dialog.findViewById(R.id.sound_volume_img);
        soundVolumeLayout = (RelativeLayout) dialog.findViewById(R.id.sound_volume_bk);
        dialog.show();
    }

    public void recording() {
        if (dialog != null && dialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
//            GlideUtil.loadSquarePicture(R.drawable.gif_record, mIcon);
            mIcon.setImageResource(R.mipmap.sound_volume_default_bk);
            mLable.setText("手指上滑，取消发送");
        }
    }

    // 显示想取消的对话框
    public void wantToCancel() {
        if (dialog != null && dialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.sound_volume_cancel_bk);
            mLable.setText("松开手指，取消发送");
        }
    }

    public void updateTime(int time) {
        if (dialog != null && dialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
            mLable.setText(time + "s");
        }
    }

    // 显示时间过短的对话框
    public void tooShort() {
        if (dialog != null && dialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
            mLable.setText("录音时间过短");
        }
    }

    // 显示取消的对话框
    public void dimissDialog() {
        if (dialog != null && dialog.isShowing()) { //显示状态
            dialog.dismiss();
            dialog = null;
        }
    }

    // 显示更新音量级别的对话框
    public void updateVoiceLevel(int level) {
        if (dialog != null && dialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
//          在这里没有做操作
//可以再这里根据音量大小，设置图片，实现效果；
        }
    }
}

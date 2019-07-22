package com.wd.health.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bw.health.core.WDActivity;
import com.wd.health.R;
import com.wd.health.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PingmuActivity extends WDActivity {


    @BindView(R2.id.progress)
    SeekBar progress;
    @BindView(R2.id.back)
    ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pingmu;
    }

    @Override
    protected void initView() {
        setScreenMode(0);
        progress.setMax(255);
        progress.setProgress(getScreenBrightness());

        //设置系统亮度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (!Settings.System.canWrite(getApplicationContext())) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PingmuActivity.this.startActivity(intent);
                    } else {
                        saveBrightness(PingmuActivity.this, i);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        } else {
            progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    saveBrightness(PingmuActivity.this, i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

    }

    @Override
    protected void destoryData() {

    }

    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    private void setScreenMode(int paramInt) {
        try {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 获得当前屏幕亮度值 0--255
     */
    private int getScreenBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    private void setScreenBrightness(int paramInt) {
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    /**
     * 保存当前的系统亮度值，并使之生效
     */
    public static void saveBrightness(Context context, int brightness) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(uri, null);
    }


    @OnClick(R2.id.back)
    public void onViewClicked() {
        finish();
    }
}

package com.wd.health.view;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bw.health.core.WDApplication;
import com.danikula.videocache.HttpProxyCacheServer;
import com.wd.health.video.R;


import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 作者： ch
 * 时间： 2018/8/17 0017-下午 5:14
 * 描述：
 * 来源：
 */


public class MyVideoPlayer extends JZVideoPlayerStandard {
    private RelativeLayout rl_touch_help;
    private ImageView iv_start;
    private LinearLayout ll_start;
    public  static MyVideoPlayer myVideoPlayer;
    private Context context;


    public MyVideoPlayer(Context context) {
        super(context);
        this.context = context;
        myVideoPlayer = this;
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void onAutoCompletion() {

        thumbImageView.setVisibility(View.GONE);

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            onStateAutoComplete();
            setUp((String) getCurrentUrl(), JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN);
        } else {
            super.onAutoCompletion();
            setUp((String) getCurrentUrl(), JZVideoPlayer.CURRENT_STATE_NORMAL);
        }

       // onStatePlaying();
       // JZMediaManager.start();
        startVideo();
        //Video2Fragment.video2Fragment.setstartVideo();
       // goOnPlayOnResume();
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {

        if (url.startsWith("http")) {
            HttpProxyCacheServer proxy = WDApplication.getProxy(context);
            String proxyUrl = proxy.getProxyUrl(url);
            super.setUp(proxyUrl, screen, objects);
        } else {
            super.setUp(url, screen, objects);
        }
    }

    @Override
    public void init(final Context context) {
        super.init(context);

        rl_touch_help = findViewById(R.id.rl_touch_help);
        ll_start = findViewById(R.id.ll_start);
        iv_start = findViewById(R.id.iv_start);
        resetPlayView();

        rl_touch_help.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPlayView();
                setclick.click();

            }
        });


    }

    @Override
    public void startVideo() {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            initTextureView();
            addTextureView();
            AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            JZMediaManager.setDataSource(dataSourceObjects);
            JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex));
            JZMediaManager.instance().positionInList = positionInList;
            onStatePreparing();
            ll_start.setVisibility(VISIBLE);
        } else {
            super.startVideo();
            ll_start.setVisibility(GONE);
        }
        resetPlayView();
    }

    @Override
    public void startWindowTiny() {
    }

    private void resetPlayView() {

    }

    /**
     * 是否播放
     *
     * @return
     */
    public boolean isPlay() {
        if (currentState == CURRENT_STATE_PREPARING || currentState == CURRENT_STATE_PLAYING || currentState == -1) {
            return true;
        }
        return false;
    }

    private setclick setclick;

    public void setSetclick(setclick setclick) {
        this.setclick = setclick;
    }

    public interface setclick{
        void click();
    }
}

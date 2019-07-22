package com.bw.health.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.core.WDActivity;

import butterknife.BindView;

/**
 * com.bw.health.activity
 *
 * @author 李宁康
 * @date 2019 2019/07/21 20:21
 */
public class BannerDetailActivity extends WDActivity {
    @BindView(R2.id.webView)
    WebView webView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bannerdetail;
    }

    @Override
    protected void initView() {
         String url = getIntent().getStringExtra("url");
         webView.setWebViewClient(new WebViewClient());
         webView.setWebChromeClient(new WebChromeClient());
         WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    protected void destoryData() {

    }
}

package com.example.mxy1217;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.mxy1217.base.BaseActivity;
import com.example.mxy1217.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {
    @BindView(R.id.wv_content)
    WebView wvContent;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void initData() {
        wvContent.loadUrl("https://wapbaike.baidu.com/item/%E8%85%BE%E8%AE%AF/112204");
        wvContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_web;
    }

}

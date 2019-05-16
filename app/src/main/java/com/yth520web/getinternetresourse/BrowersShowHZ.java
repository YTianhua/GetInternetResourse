package com.yth520web.getinternetresourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowersShowHZ extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browers_show_hz);
        WebView mWebView = (WebView)findViewById(R.id.mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        Intent mInt = getIntent();
        String position = mInt.getStringExtra("position");
        String url = "https://one-piece.cn"+position;
        mWebView.loadUrl(url);
    }

}

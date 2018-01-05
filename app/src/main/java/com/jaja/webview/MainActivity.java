package com.jaja.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private String htmlData = "<!DOCTYPE HTML>\n\t\t\t<html>\n\t\t\t\t<head>\n\t\t\t\t\t<meta name='viewport' content='width=320, initial-scale=1'>\n\t\t\t\t\t<style type='text/css'>\n\t\t\t\t\t\tbody{color:555;}\n\t\t\t\t\t\t#body{margin:0;padding:0 10px 0 10px;font-weight:300;}\n\t\t\t\t\t\timg{max-width:100%;margin:auto;display:block;}\n\t\t\t\t\t\t.set_show_title{font-size:24px;font-weight:300;margin-top:10px;margin-bottom:12px;}\n\t\t\t\t\t\t.set_show_time_editor{color:#999999;font-size:17px;font-weight:300;margin-bottom:28px;}\n\t\t\t\t\t</style>\n\t\t\t\t</head>\n\t\t\t\t<body id='body'>\n\t\t<div class='set_show_title'>\n\t\t\t2016杭州设计师设计大赛\n\t\t</div>\n\t\t<div class='set_show_time_editor'>\n\t\t\t<span>2015-12-24</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>JAJAHOME<span>\n\t\t</div><p>2016杭州设计师设计大赛</p><p>由过++组织的2016杭州设计师设计大赛在2016年3月18日开始到4月20日</p><p>结束。比赛设特等奖一名，一等奖三名，优胜奖十名，奖品丰盛，望设计</p><p>师踊跃报名参加。</p></body>\n\t\t\t</html>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);

        WebSettings s = webView.getSettings();
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);
        s.setSupportZoom(true);
        s.setBuiltInZoomControls(true);

        webView.addJavascriptInterface(new WebJs(this),"android");
        webView.setWebChromeClient(new WebChromeClient());//支持特殊的js
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("print", "请求加载的url" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i("print", "网页开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("print", "网页加载结束");
            }
        });//所有请求在本地的webview打开

        webView.loadUrl("file:///android_asset/login.html");

       // webView.loadData(htmlData, "text/html;charset=utf-8", null);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

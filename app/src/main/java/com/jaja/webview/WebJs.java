package com.jaja.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by ${Terry} on 2018/1/5.
 */
public class WebJs {

    private Context mContext;

    public WebJs(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void jsOnClick(String username, String password) {
        Toast.makeText(mContext, "这是android代码" + username + "-" + password, Toast.LENGTH_SHORT).show();
    }

}

package com.jaja.webview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


/**
 * 类描述：带有进度条的webview
 * 创建人：admin
 * 创建时间：2016/5/30 14:46
 * 修改人：admin
 * 修改时间：2016/5/30 14:46
 * 修改备注：
 */
public class ProgressWebView extends LinearLayout {

    /**
     * 网页拨打电话动作
     */
    private final String TEL_ACTION = "tel:";
    /**
     * 关闭当前页面动作
     */
    private final String CLOSE_ACTION = "webview://close";

    private OnWebViewActionListener onWebViewActionListener;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Context mContext;

    public ProgressWebView(Context context) {
        super(context);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 设置监听
     *
     * @param onWebViewActionListener onWebViewActionListener
     */
    public void setOnWebViewActionListener(OnWebViewActionListener onWebViewActionListener) {
        this.onWebViewActionListener = onWebViewActionListener;
    }

    protected void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
        initProressView();
        mWebView = new WebView(context);
        addView(mWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        initWebView();
    }

    private void initProressView() {
        mProgressBar = (ProgressBar) LayoutInflater.from(mContext).inflate(R.layout.progress_bar_linear, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 4, getResources().getDisplayMetrics()));
    }

    private void initWebView() {
        WebSettings s = mWebView.getSettings();
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //设置网页缩放至屏幕大小
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        //设置WebView是否保存表单数据，默认true，保存数据。
        s.setSaveFormData(true);
        //设置脚本是否允许自动打开弹窗，默认false，不允许
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        //让webview支持运行js
        s.setJavaScriptEnabled(true);
        //设置是否开启定位功能，默认true，开启定位
        s.setGeolocationEnabled(true);
        //设置WebView保存地理位置信息数据路径，指定的路径Application具备写入权限
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        s.setDomStorageEnabled(true);
        //是否支持网页缩放
        s.setSupportZoom(true);
        s.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //链接中包含拨打电话功能
                if (url.toLowerCase().contains(TEL_ACTION)) {
                    if (url.length() > 5) {
                        String tel = url.toLowerCase().replace(TEL_ACTION, "").trim();
                        Log.e("tel", tel);
                        if (!StringUtil.isEmpty(tel) && null != onWebViewActionListener) {
                            onWebViewActionListener.onPhoneCall(tel);
                            return false;
                        }
                    }
                } else if (url.equals(CLOSE_ACTION)) {
                    ((Activity) getContext()).finish();
                }
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    if (mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }

            }
        });
    }

    /**
     * 设置页面可以缓存
     */
    public void setCacheSetting() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void loadUrl(String url) {
        getWebView().loadUrl(url);
    }

    public void loadData(String data) {
        getWebView().loadData(data, "text/html; charset=UTF-8", null);
    }

    /**
     * 销毁webview  使播放音乐的H5及时停止
     */
    public void stop() {
        mWebView.destroy();
    }

    /**
     * 页面和应用之间交互
     */
    public interface OnWebViewActionListener {
        //拨打电话
        void onPhoneCall(String tel);
    }
}

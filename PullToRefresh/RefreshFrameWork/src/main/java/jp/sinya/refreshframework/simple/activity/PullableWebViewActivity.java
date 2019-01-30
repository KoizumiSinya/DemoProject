package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.simple.listener.MyListener;


public class PullableWebViewActivity extends Activity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_webview);
        ((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(new MyListener());
        webView = (WebView) findViewById(R.id.content_view);
        webView.loadUrl("http://blog.csdn.net/zhongkejingwang");
    }
}

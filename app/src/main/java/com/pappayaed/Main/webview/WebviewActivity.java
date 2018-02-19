package com.pappayaed.Main.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pappayaed.R;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webview);


        String data = getIntent().getStringExtra("data");
        String mimetype = getIntent().getStringExtra("mimetype");
        String name = getIntent().getStringExtra("name");


//        byte[] database = Base64.decode(data, Base64.DEFAULT);

//        String urlStr = "http://example.com/my.jpg";
//        String mimeType = "text/html";
//        String encoding = null;
//        String pageData = "<img src=\"data:image/jpeg;base64," + image64 + "\" />";

//        String image64 = Base64.encodeToString(database, Base64.DEFAULT);

        WebView wv = new WebView(this);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        wv.getSettings().setSupportMultipleWindows(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
//        wv.getSettings().setJavaScriptEnabled(true);
//        wv.getSettings().setLoadWithOverviewMode(true);
//        wv.getSettings().setUseWideViewPort(true);
//        wv.loadData(database.toString(), mimetype, null);

//        wv.loadData(data, mimetype + "; charset=utf-8", "base64");

        wv.loadData(data, mimetype, "base64");


        setContentView(wv);

    }
}

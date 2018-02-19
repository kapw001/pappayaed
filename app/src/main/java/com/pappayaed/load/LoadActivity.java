package com.pappayaed.load;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pappayaed.App.App;
import com.pappayaed.R;

import static com.pappayaed.R.id.webview;

public class LoadActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        webView = (WebView) findViewById(webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().getBuiltInZoomControls();
        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());

//        Byte[] bytes=new Byte[1000*1000];
//
//        byte[] base64File = getIntent().getByteExtra("base64File",new byte[1000 * 1000]);
        byte[] base64File = App.getApp().getBase64FIle();

//        byte[] decodedString = Base64.decode(base64File, Base64.DEFAULT);
//        webView.loadData(base64File.toString(), "application/pdf", "utf-8");

        String base64 = Base64.encodeToString(base64File, Base64.DEFAULT);
        webView.loadData(base64, "data:application/pdf;base64, charset=utf-8", null);
//        webView.loadData(base64, "application/pdf", "utf-8");
//        String pdfURL = "http://www.expertagent.co.uk/asp/in4glestates/{16D968D6-198E-4E33-88F4-8A85731CE605}/{05c36123-4df0-4d7d-811c-8b6686fdd526}/external.pdf";
//        webView.loadUrl(
//                "http://docs.google.com/gview?embedded=true&url=" + pdfURL);

//        webView.loadUrl(
//                "http://docs.google.com/gview?embedded=true&url=" + base64);

//        String pdfURL = "http://dl.dropboxusercontent.com/u/37098169/Course%20Brochures/AND101.pdf";
//        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfURL);


    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Toast.makeText(LoadActivity.this, "Page Started", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Toast.makeText(LoadActivity.this, "Finished", Toast.LENGTH_SHORT).show();
        }
    }
}


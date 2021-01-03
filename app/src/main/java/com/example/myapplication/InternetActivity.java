package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class InternetActivity extends AppCompatActivity {
    public String url;

    //OnCreate->Activity 시작 시 시작되는 함수
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //레이아웃 setting
        setContentView(R.layout.internet);

        this.url = getIntent().getExtras().getString("url");

        WebView webView1 = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);         // 자바스크립트 사용
        webSettings.setSupportMultipleWindows(true);    // 새창 띄우기 허용
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 자바스크립트 새창 띄우기 허용
        webSettings.setLoadWithOverviewMode(true);      // 메타태그 허용
        webSettings.setUseWideViewPort(true);           // 화면 사이즈 맞추기 허용
        webSettings.setSupportZoom(false);              // 화면줌 허용 여부
        webSettings.setBuiltInZoomControls(false);      // 화면 확대 축소 허용 여부
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);        // 브라우저 노캐쉬
        webSettings.setDomStorageEnabled(true);         // 로컬저장소 허용

        webView1.loadUrl(this.url);
        webView1.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(final WebView view, boolean dialog,
                                          boolean userGesture, Message resultMsg) {
                //WebView newWebView = new WebView(this);
                WebView newWebView = new WebView((MainActivity) MainActivity.context_main);
                WebView.WebViewTransport transport
                        = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }
                });
                return true;
            }
        });
        //webView1.setWebChromeClient(new WebChromeClient());  //웹뷰에 크롬 사용 허용. 이 부분이 없으면 크롬에서 alert가 뜨지 않음
        webView1.setWebViewClient(new WebViewClientClass());

    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //Log.d("WebViewClient URL : " , request.getUrl().toString());
            view.loadUrl(request.getUrl().toString());
            return true;
            //return super.shouldOverrideUrlLoading(view, request);
        }
    }
}

package com.example.comicuniverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exmple.comicuniverse.R;

public class DetailChapter extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chapter);
        webView=findViewById(R.id.id_ChapterWed);
        Intent intent=getIntent();
        String url= intent.getExtras().getString("Chapter");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
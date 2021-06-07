package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ExamTimeTable extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_time_table);

        webView= findViewById(R.id.simpleWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://lp.moe.gov.my/index.php/makluman-spm/971-pindaan-tarikh-pa-2020");
    }
}
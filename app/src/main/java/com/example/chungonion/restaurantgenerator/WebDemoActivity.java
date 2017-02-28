package com.example.chungonion.restaurantgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebDemoActivity extends AppCompatActivity {

    private WebView webViewDemo;
    private Button button1;
    private Button button2;
    private Button button3;
    private final String URL1="http://www.spc.edu.hk";
    private final String URL2="http://www.polyu.edu.hk";
    private final String URL3="http://www.pokeguide.com";


    private void buttonInitlalise(){
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_demo);
        webViewDemo = (WebView) findViewById(R.id.webViewDemo);

        buttonInitlalise();

        //default websetting
        WebSettings webSettings = webViewDemo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewDemo.setWebViewClient(new WebViewClient());
        webViewDemo.loadUrl(URL3);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case (R.id.button1):

                    webViewDemo.loadUrl(URL1);
                    break;
                case (R.id.button2):

                    webViewDemo.loadUrl(URL2);
                    break;
                case (R.id.button3):

                    webViewDemo.loadUrl(URL3);
                    break;

            }
        }
    };
}

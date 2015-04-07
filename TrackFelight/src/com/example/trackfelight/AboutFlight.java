package com.example.trackfelight;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutFlight extends Activity {

	
	   private WebView browser;

	   @Override		
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_about_flight);
	      
	      browser = (WebView)findViewById(R.id.webView1);
	      browser.setWebViewClient(new MyBrowser());
	      Intent intent = getIntent();
	      
	      String url = intent.getStringExtra("url");
	      browser.getSettings().setLoadsImagesAutomatically(true);
	      browser.getSettings().setJavaScriptEnabled(true);
	      //browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	      browser.loadUrl(url);
	   }


	   
	   private class MyBrowser extends WebViewClient {
	      @Override
	      public boolean shouldOverrideUrlLoading(WebView view, String url) {
	         view.loadUrl(url);
	         return true;
	      }
	   }

	   
}

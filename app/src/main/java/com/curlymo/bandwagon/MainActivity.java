package com.curlymo.bandwagon;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.curlymo.bandwagon.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable LocalStorage
        webSettings.setDomStorageEnabled(true);

        // Allow Autoplay of audio
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        // Geolocation
        webSettings.setGeolocationEnabled(true);

        // Add App Name to UserAgent
        webSettings.setUserAgentString(
                webSettings.getUserAgentString()
                + " "
                + getString(R.string.user_agent_suffix)
        );

        // Hardware Acceleration
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView.loadUrl("http://bandwagon.audio");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;
            case R.id.quit:
                android.os.Process.killProcess(android.os.Process.myPid());
                super.onDestroy();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

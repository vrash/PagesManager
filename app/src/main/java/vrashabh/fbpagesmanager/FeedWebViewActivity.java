package vrashabh.fbpagesmanager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class FeedWebViewActivity extends ActionBarActivity {

    WebView wvfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_web_view);
        setTitle("Webview fallback");
        wvfb = (WebView) findViewById(R.id.feedWebView);

        wvfb.getSettings().setJavaScriptEnabled(true);
        wvfb.getSettings().setLoadWithOverviewMode(true);
        wvfb.getSettings().setUseWideViewPort(true);
        wvfb.getSettings().setBuiltInZoomControls(true);
        wvfb.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvfb.setWebViewClient(new FBWebClient());
        wvfb.loadUrl("http://m.facebook.com");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FBWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}

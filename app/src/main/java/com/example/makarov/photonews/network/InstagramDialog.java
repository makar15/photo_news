package com.example.makarov.photonews.network;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by makarov on 09.12.15.
 */
public class InstagramDialog extends Dialog {

    private String mRequestToken;
    private String mAuthUrl;
    private WebView mWebView;

    public InstagramDialog(Context context, String url) {
        super(context);
        mAuthUrl = url;

        setWebView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setWebView() {
        mWebView = new WebView(getContext());
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new AuthWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mAuthUrl);
    }


    private class AuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(AuthenticationNetwork.CALLBACK_URL)) {
                System.out.println(url);
                String parts[] = url.split("=");
                mRequestToken = parts[1];         //Это наш маркер запроса.
                InstagramDialog.this.dismiss();
                return true;
            }
            return false;
        }
    }

    public String getRequestToken() {
        return mRequestToken;
    }

}

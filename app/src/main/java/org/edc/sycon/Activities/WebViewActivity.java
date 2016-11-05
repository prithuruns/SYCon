package org.edc.sycon.Activities;

import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.edc.sycon.Constants;
import org.edc.sycon.Fragments.DialogFragment;
import org.edc.sycon.R;

public class WebViewActivity extends ActionBarActivity
        implements DialogFragment.DialogListener {

    public static final String SPEAKER_PROFILE = "speaker_profile";

    DialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constants.URL);
        final boolean speakerProfile = intent.getBooleanExtra(SPEAKER_PROFILE, false);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                    if (!speakerProfile) {
                        mDialogFragment = DialogFragment.newInstance(
                                "Redirect",
                                "You may be redirected to your browser for secure payment.",
                                "Okay",
                                ""
                        );
                        mDialogFragment.show(getFragmentManager(), null);
                    }
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl(url);
    }

    @Override
    public void onDialogPositiveClick(android.app.DialogFragment dialog) {
        mDialogFragment.dismiss();
    }

    @Override
    public void onDialogNeutralClick(android.app.DialogFragment dialog) {
        mDialogFragment.dismiss();
    }
}

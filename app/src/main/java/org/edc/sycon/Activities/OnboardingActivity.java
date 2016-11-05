package org.edc.sycon.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.edc.sycon.Constants;
import org.edc.sycon.R;

import io.fabric.sdk.android.Fabric;

public class OnboardingActivity extends AppCompatActivity {

    private TwitterLoginButton mLoginButton;
    private TwitterSession mTwitterSession;
    private Button mNoLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, 0);
        boolean userOnboarded = preferences.getBoolean(Constants.USER_ONBOARDED, false);

        if (!userOnboarded) {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
            Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
            setContentView(R.layout.activity_onboarding);
            mLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
            mLoginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    mTwitterSession = result.data;
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.d("TwitterKit", "Login with Twitter failure", exception);
                }
            });
            mNoLoginButton=(Button) findViewById(R.id.no_twitter_login);
            mNoLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences settings = getSharedPreferences(Constants.PREFERENCES, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean(Constants.USER_HAS_TWITTER, false);
                    final Intent intent= new Intent(OnboardingActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            final Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        mLoginButton.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // We need an Editor object to make preference changes.
            // All objects are from android.context.Context
            SharedPreferences settings = getSharedPreferences(Constants.PREFERENCES, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constants.USER_ONBOARDED, true);
            editor.putBoolean(Constants.USER_HAS_TWITTER, true);
            editor.putString(Constants.USER_NAME, mTwitterSession.getUserName());
            editor.putLong(Constants.USER_ID, mTwitterSession.getUserId());

            // Commit the edits!
            editor.apply();

            final Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
            intent.putExtra(Constants.USER_JUST_ONBOARDED,true);
            startActivity(intent);
            finish();
        }
    }


}

package org.edc.sycon.Activities;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

import org.edc.sycon.Constants;
import org.edc.sycon.Event.EventBus;
import org.edc.sycon.Event.PageChangedEvent;
import org.edc.sycon.Fragments.ArchivesFragment;
import org.edc.sycon.Fragments.CentralFragment;
import org.edc.sycon.Fragments.RightFragment;
import org.edc.sycon.R;
import org.edc.sycon.View.VerticalPager;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        org.edc.sycon.Fragments.DialogFragment.DialogListener,
        CentralFragment.CentralFragmentListener {
    /**
     * Start page index. 0 - top page, 1 - central page, 2 - bottom page.
     */

    private static final int CENTRAL_PAGE_INDEX = 1;
    private VerticalPager mVerticalPager;
    public static final String YEAR = "year";
    private DialogFragment dialogFragment;

    private int mDialogType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        setContentView(R.layout.activity_main);
        findViews();
        boolean userJustOnboarded = getIntent().getBooleanExtra(Constants.USER_JUST_ONBOARDED, false);
        if (userJustOnboarded) {
            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, 0);
            String userName = preferences.getString(Constants.USER_NAME, "");
            Long userId = preferences.getLong(Constants.USER_ID, 0L);
            String message = "";
            dialogFragment =
                    new org.edc.sycon.Fragments.DialogFragment().newInstance(
                            getResources().getString(R.string.twitter_logged_in_title),
                            message,
                            "Okay",
                            "");
            dialogFragment.show(getFragmentManager(), null);
        }
        //TODO:Add a disclaimer to say you aren't supporting twitter
        //TODO:and that you aren't going to manipulate their data
    }


    @Override
    public void onPause() {
        EventBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getInstance().register(this);
    }

    @Subscribe
    public void onLocationChanged(PageChangedEvent event) {
        mVerticalPager.setPagingEnabled(event.hasVerticalNeighbors());
    }

    private void findViews() {
        mVerticalPager = (VerticalPager) findViewById(R.id.activity_main_vertical_pager);
        Log.d(getClass().getName(), "" + mVerticalPager);
        initViews();
    }

    private void initViews() {
        snapPageWhenLayoutIsReady(mVerticalPager, CENTRAL_PAGE_INDEX);
    }

    private void snapPageWhenLayoutIsReady(final View pageView, final int page) {
        /*
         * VerticalPager is not fully initialized at the moment, so we want to snap to the central page only when it
		 * layout and measure all its pages.
		 */
        pageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mVerticalPager.snapToPage(page, VerticalPager.PAGE_SNAP_DURATION_INSTANT);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    // recommended removeOnGlobalLayoutListener method is available since API 16 only
                    pageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    removeGlobalOnLayoutListenerForJellyBean(pageView);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            private void removeGlobalOnLayoutListenerForJellyBean(final View pageView) {
                pageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        switch (mDialogType) {
            case 1:
                //TODO:To change this when the bookmyshow link comes up.
                dialogFragment.dismiss();
                break;
            case 2:
                dialogFragment.dismiss();
                break;
            case 3:
                //TODO: Redirect to google form
                dialogFragment.dismiss();
                String url="http://goo.gl/forms/aMkpMFDgNv";
                Intent intent= new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra(Constants.URL,url);
                intent.putExtra(WebViewActivity.SPEAKER_PROFILE,true);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        switch (mDialogType) {
            case 1:
                dialogFragment.dismiss();
                break;
            case 2:
                dialogFragment.dismiss();
                break;
            case 3:
                dialogFragment.dismiss();
                break;
        }
    }

    @Override
    public void displayDialog(String title, String message, int type) {
        mDialogType = type;
        if (type==1) {
            dialogFragment =
                    new org.edc.sycon.Fragments.DialogFragment().newInstance(
                            title,
                            message,
                            "Okay",
                            "");
            dialogFragment.show(getFragmentManager(), null);
        }
        else
        {
            dialogFragment =
                    new org.edc.sycon.Fragments.DialogFragment().newInstance(
                            title,
                            message,
                            "Yes",
                            "No");
            dialogFragment.show(getFragmentManager(), null);
        }
    }
}

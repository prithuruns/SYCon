package org.edc.sycon.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.edc.sycon.Activities.OnboardingActivity;
import org.edc.sycon.Activities.WebViewActivity;
import org.edc.sycon.Constants;
import org.edc.sycon.R;
import org.edc.sycon.View.ThemedNumberPicker;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Fragment to manage the central page of the 5 pages application navigation (top, center, bottom, left, right).
 */
public class CentralFragment extends Fragment implements View.OnClickListener {

    ThemedNumberPicker mNumberPicker1, mNumberPicker2, mNumberPicker3, mNumberPicker4;

    Timer mTimer;
    TimerTask mTimerTask;
    int[] mDateForSycon = {29, 9, 0, 0};
    private boolean mUserHasTwitter = false;
    private boolean mCrossedDate = false;
    CentralFragmentListener mListener;

    Button mRegisterButton, mBookTicketsButton;
    ImageButton mTweetButton;

    public CentralFragment() {
    }

    public static CentralFragment newInstance() {
        CentralFragment fragment = new CentralFragment();
        return fragment;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (CentralFragmentListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity
                    + " must implement CentralFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main_page, container, false);
        mNumberPicker1 = (ThemedNumberPicker) fragmentView.findViewById(R.id.numberPicker1);
        mNumberPicker2 = (ThemedNumberPicker) fragmentView.findViewById(R.id.numberPicker2);
        mNumberPicker3 = (ThemedNumberPicker) fragmentView.findViewById(R.id.numberPicker3);
        mNumberPicker4 = (ThemedNumberPicker) fragmentView.findViewById(R.id.numberPicker4);
        mRegisterButton = (Button) fragmentView.findViewById(R.id.register);
        mTweetButton = (ImageButton) fragmentView.findViewById(R.id.tweet);
        mBookTicketsButton = (Button) fragmentView.findViewById(R.id.bookTickets);

        mRegisterButton.setOnClickListener(this);
        mBookTicketsButton.setOnClickListener(this);
        mTweetButton.setOnClickListener(this);

        mNumberPicker1.setEnabled(false);
        mNumberPicker2.setEnabled(false);
        mNumberPicker3.setEnabled(false);
        mNumberPicker4.setEnabled(false);

        mNumberPicker1.setMaxValue(100);
        mNumberPicker2.setMaxValue(23);
        mNumberPicker3.setMaxValue(59);
        mNumberPicker4.setMaxValue(59);
        mNumberPicker1.setMinValue(0);
        mNumberPicker2.setMinValue(0);
        mNumberPicker3.setMinValue(0);
        mNumberPicker4.setMinValue(0);

        SharedPreferences settings = getActivity().getSharedPreferences(Constants.PREFERENCES, 0);
        mUserHasTwitter = settings.getBoolean(Constants.USER_ONBOARDED, false);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        runThread();
    }

    private void runThread() {
        new Thread() {
            public void run() {

                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Calendar cd = Calendar.getInstance();
                                int date = cd.get(Calendar.DATE);
                                int hour = cd.get(Calendar.HOUR_OF_DAY);
                                int minute = cd.get(Calendar.MINUTE);
                                int second = cd.get(Calendar.SECOND);
                                int year = cd.get(Calendar.YEAR);

                                //only execute timer if the year is 2016
                                if (year == 2016) {
                                    if (Calendar.MONTH != Calendar.MARCH) {

                                        mNumberPicker1.setVisibility(View.GONE);
                                        mNumberPicker2.setVisibility(View.GONE);
                                        mNumberPicker3.setVisibility(View.GONE);
                                        mNumberPicker4.setVisibility(View.GONE);
                                        return;
                                    }

                                    if (date == mDateForSycon[0]) {
                                        if (hour >= mDateForSycon[1]) {
                                            mCrossedDate = true;
                                            mBookTicketsButton.setText("Feedback");
                                            return;
                                        }
                                    } else if (date > 29) {
                                        mCrossedDate = true;
                                        mBookTicketsButton.setText("Feedback");
                                        return;
                                    }

                                    date = mDateForSycon[0] - date;
                                    if (hour > mDateForSycon[1]) {
                                        date--;
                                        hour = 24 + 9 - hour;
                                    } else {
                                        hour = mDateForSycon[1] - hour;
                                    }
                                    if (minute > mDateForSycon[2]) {
                                        hour--;
                                        minute = 60 - minute;
                                    }

                                    if (second > mDateForSycon[3]) {
                                        minute--;
                                        second = 60 - second;
                                    }
                                    // here set the number pickers
                                    mNumberPicker1.setValue(date);
                                    mNumberPicker1.setMaxValue(date);
                                    mNumberPicker2.setValue(hour);
                                    mNumberPicker3.setValue(minute);
                                    mNumberPicker4.setValue(second);
                                    //TODO: set functions for when data is 0 and all.
                                } else {
                                    mNumberPicker1.setVisibility(View.GONE);
                                    mNumberPicker2.setVisibility(View.GONE);
                                    mNumberPicker3.setVisibility(View.GONE);
                                    mNumberPicker4.setVisibility(View.GONE);
                                }
                            }
                        }
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

                runThread();
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    @Override
    public void onClick(View v) {
        if (v == mRegisterButton) {
            //to avail OD
            String title = "Registration";
            String message = "If you are from SSN and would like to avail OD, please click yes, else, click no.";
            mListener.displayDialog(title, message, Constants.REGISTER);
        } else if (v == mTweetButton) {
            //TODO:Show tweets from twitter also... somehow.
            //open a tweet composer,
            //two variations
            //1.Twitter Tweet composer

            if (mUserHasTwitter) {
                TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                        .text(" #SYCon");
                builder.show();
            } else {
                final Intent intent = new Intent(getActivity(), OnboardingActivity.class);
                startActivity(intent);
            }

            //2.TwitterKit Tweet Composer
//            final TwitterSession session = TwitterCore.getInstance().getSessionManager()
//                    .getActiveSession();
//            final Intent intent = new ComposerActivity.Builder(getActivity())
//                    .session(session)
//                    .createIntent();
//            startActivity(intent);

        } else if (v == mBookTicketsButton) {
            //to verify that you have booked your tickets through bookmyshow

            if (!mCrossedDate) {
                String url = "https://in.bookmyshow.com/chennai/events/sycon-2016/ET00040084";
                Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.URL, url);
                startActivity(intent);
            } else {
                String url = "http://goo.gl/forms/FY9GJc6uHP";
                Intent intent= new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra(Constants.URL,url);
                intent.putExtra(WebViewActivity.SPEAKER_PROFILE,true);
                startActivity(intent);
            }
        }
    }


    public interface CentralFragmentListener {

        void displayDialog(String title, String message, int type);
    }
}

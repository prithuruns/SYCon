package org.edc.sycon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.edc.sycon.Activities.WebViewActivity;
import org.edc.sycon.Constants;
import org.edc.sycon.R;

/**
 * Fragment to manage the right page of the 5 pages application navigation (top, center, bottom, left, right).
 */
public class RightFragment extends Fragment implements View.OnClickListener {

	private Button[] speaker= new Button[10];
    private int mNoOfSpeakers= 9;
    public static final int[] ids= {R.id.event_speaker_1,
            R.id.event_speaker_2,
            R.id.event_speaker_3,
            R.id.event_speaker_4,
            R.id.event_speaker_5,
            R.id.event_speaker_6,
            R.id.event_speaker_7,
            R.id.event_speaker_8,
            R.id.event_speaker_9,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_schedule, container, false);
        for (int i=0;i<mNoOfSpeakers;i++)
        {
            speaker[i]= (Button) fragmentView.findViewById(ids[i]);
            speaker[i].setOnClickListener(this);
        }
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            default:
                final Intent intent= new Intent(getActivity(), WebViewActivity.class);
                String url="http://www.ssnlakshya.org/syc_desc/index.html";
                intent.putExtra(Constants.URL,url);
                intent.putExtra(WebViewActivity.SPEAKER_PROFILE,true);
                startActivity(intent);
                break;
        }
    }

}

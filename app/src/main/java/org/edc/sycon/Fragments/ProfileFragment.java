package org.edc.sycon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.edc.sycon.Activities.YoutubeActivity;
import org.edc.sycon.Constants;
import org.edc.sycon.R;

import java.text.SimpleDateFormat;

public class ProfileFragment extends Fragment {
    private static final String SPEAKER = "speaker";

    private String[] videos2015 =
            {"crwXB3NDaAs",
                    "sEBXuqUPweA",
                    "n3x0BqtSXuY",
                    "9AZiVH1UUtY",
                    "6pp2njCrysY",
                    "LYU7_lD0iL0",
                    "K_E222EuhuU",
                    "tY8EH2EQEZE",
                    "FNFqyNJM5tk"};
    private int imageResources[] = {R.drawable.commodore_shekar,
            R.drawable.lakshmi_potluri,
            R.drawable.vivek_karunakaran,
            R.drawable.arjun_rajagopalan,
            R.drawable.arun_krishnamurthy,
            R.drawable.pradeep_kumar,
            R.drawable.staccato,
            R.drawable.m_mahadevan,
            R.drawable.rajendran_dandapani
    };

    private int speaker;

    ImageView mImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(int param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(SPEAKER, param1);
        fragment.setArguments(args);
        Log.w("Profile Fragment", "new Instance: " + param1);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            speaker = getArguments().getInt(SPEAKER);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mImageView=(ImageView) view.findViewById(R.id.speaker_image);
        Button buttonlink= (Button) view.findViewById(R.id.speaker_button);
        buttonlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getActivity(), YoutubeActivity.class);
                i.putExtra(Constants.VIDEO_LINK, videos2015[speaker]);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(getContext())
                .load(imageResources[speaker])
                .resize(300,424)
                .into(mImageView);
//        mImageView.setImageResource(imageResources[speaker]);
        Log.d("Profile Fragment", "Resume: ");
    }

    @Override
    public void onPause() {
        super.onDetach();
        mImageView.setImageResource(0);
        Log.d("Profile Fragment","Pause: ");
    }
}

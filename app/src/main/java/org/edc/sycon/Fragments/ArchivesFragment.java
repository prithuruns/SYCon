package org.edc.sycon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;

import org.edc.sycon.Activities.ArchivesActivity;
import org.edc.sycon.Activities.MainActivity;
import org.edc.sycon.Activities.YoutubeActivity;
import org.edc.sycon.Constants;
import org.edc.sycon.R;

import java.util.ArrayList;
import java.util.List;

public class ArchivesFragment extends Fragment {
    private static final String POSITION = "position";
    private int position;
    String TAG = getClass().getName();

    public ArchivesFragment() {
        // Required empty public constructor
    }

    public static ArchivesFragment newInstance(int position) {
        ArchivesFragment fragment = new ArchivesFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.bottom_fragment, container, false);
        if (position == 0) {
            ((Button) view.findViewById(R.id.clickOn)).setText("Click to view speaker profiles and videos from 2015!!");
        }
        else
        {
            ((Button) view.findViewById(R.id.clickOn)).setText("Click to view images from 2015!!");
        }
        ((Button) view.findViewById(R.id.clickOn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent= new Intent(getActivity(),ArchivesActivity.class);
                intent.putExtra(ArchivesActivity.ARCHIVES_POSITION,position);
                startActivity(intent);
            }
        });
        return view;
    }
}

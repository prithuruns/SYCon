package org.edc.sycon.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.astuetz.PagerSlidingTabStrip;

import org.edc.sycon.R;

/**
 * Fragment to manage the top page of the 5 pages application navigation (top, center, bottom, left, right).
 */
public class TopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_sponsors, container, false);
        return fragmentView;
    }
}

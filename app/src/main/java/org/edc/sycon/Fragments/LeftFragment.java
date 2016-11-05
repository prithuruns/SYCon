package org.edc.sycon.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.edc.sycon.R;

/**
 * Fragment to manage the left page of the 5 pages application navigation (top, center, bottom, left, right).
 */
public class LeftFragment extends Fragment implements View.OnClickListener {

    ImageButton mFBLakshya, mWebsiteLakshya, mFBSycon;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_aboutus, container, false);

        mFBLakshya = (ImageButton) fragmentView.findViewById(R.id.about_us_lakshya_facebook);
        mFBLakshya.setOnClickListener(this);
        mWebsiteLakshya = (ImageButton) fragmentView.findViewById(R.id.about_us_lakshya_website);
        mWebsiteLakshya.setOnClickListener(this);
        mFBSycon = (ImageButton) fragmentView.findViewById(R.id.about_us_sycon_facebook);
        mFBSycon.setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == mFBLakshya) {
            try {
                getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/135652126559972"));
            } catch (Exception e) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/lakshyassn/?fref=ts"));
            }
        } else if (v == mWebsiteLakshya) {
            Uri uri = Uri.parse("http://www.ssnlakshya.org/index.html");
            intent = new Intent(Intent.ACTION_VIEW, uri);
        } else {
            try {
                getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1451961815033061"));

            } catch (Exception e) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/events/991393030954294/"));
            }
        }
        startActivity(intent);
    }
}

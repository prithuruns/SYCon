package org.edc.sycon.Fragments;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import org.edc.sycon.R;


/**
 * Fragment to manage the bottom page of the 5 pages application navigation (top, center, bottom, left, right).
 */
public class BottomFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_archives, container, false);

		MyAdapter adapter= new MyAdapter(getActivity().getSupportFragmentManager());
		ViewPager pager = (ViewPager) fragmentView.findViewById(R.id.pager);
		pager.setAdapter(adapter);

        pager.setCurrentItem(0);
		// Bind the tabs to the ViewPager
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) fragmentView.findViewById(R.id.tabs);
		tabs.setViewPager(pager);

		return fragmentView;
	}


	public static class MyAdapter extends FragmentPagerAdapter {

        private final String[] titles= {"Speakers","Images"};

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			return ArchivesFragment.newInstance(position);
		}
	}

}

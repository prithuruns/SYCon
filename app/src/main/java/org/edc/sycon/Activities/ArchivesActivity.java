package org.edc.sycon.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;

import org.edc.sycon.Fragments.ProfileFragment;
import org.edc.sycon.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ArchivesActivity extends AppCompatActivity {

    Timer mTimer;
    TimerTask mTimerTask;
    public static final String ARCHIVES_POSITION="archives_position";
    int flipInterval = 2000;
    ImageView viewFlipper,viewFlipper2;
    int position,imageCounter1=0,imageCounter2=8;
    int imagesArray[] = {
            R.drawable.images_a,
            R.drawable.images_b,
            R.drawable.images_c,
            R.drawable.images_d,
            R.drawable.images_e,
            R.drawable.images_f,
            R.drawable.images_g,
            R.drawable.images_h,
            R.drawable.images_i,
            R.drawable.images_j,
            R.drawable.images_k,
            R.drawable.images_l,
            R.drawable.images_m,
            R.drawable.images_n,
            R.drawable.images_o,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        position= intent.getIntExtra(ARCHIVES_POSITION,0);
        if (position == 0) {
            setContentView(R.layout.content_archives);

            MyAdapter adapter= new MyAdapter(this.getSupportFragmentManager());
            ViewPager pager = (ViewPager) findViewById(R.id.pagerIn);
            pager.setAdapter(adapter);

            pager.setCurrentItem(0);
            PagerSlidingTabStrip tabs= (PagerSlidingTabStrip) findViewById(R.id.tabsIn);
            tabs.setViewPager(pager);

        } else {
            setContentView(R.layout.content_archives_images);
            viewFlipper = (ImageView) findViewById(R.id.iv1);
            viewFlipper2 = (ImageView) findViewById(R.id.iv2);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (position==1) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (position==1) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {

                    runThread();
                }
            };
            mTimer.schedule(mTimerTask, 0, 2000);
        }

    }

    private void runThread() {
        new Thread() {
            public void run() {
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                //TODO:Add what you want
                                if (imageCounter1>=8)
                                {
                                    imageCounter1=0;
                                }
                                if (imageCounter2>=15)
                                {
                                    imageCounter2=8;
                                }
//                                Picasso.with(getBaseContext()).load(imagesArray[imageCounter1++]).into(viewFlipper);
//                                Picasso.with(getBaseContext()).load(imagesArray[imageCounter2++]).into(viewFlipper2);
                                viewFlipper.setImageResource(imagesArray[imageCounter1++]);
                                viewFlipper2.setImageResource(imagesArray[imageCounter2++]);
                            }
                        }
                );
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onStop() {
        if (position==1) {
            viewFlipper.destroyDrawingCache();
            viewFlipper2.destroyDrawingCache();
        }
        super.onStop();
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"Commodore Shekar", "Lakshmi Potluri", "Vivek Karunakaran",
                "Dr Arjun Rajagopalan", "Arun Krishnamurthy", "Pradeep Kumar",
                "Staccato", "M Mahadevan", "Rajendran Dandapani"};

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
            return ProfileFragment.newInstance(position);
        }
    }
}

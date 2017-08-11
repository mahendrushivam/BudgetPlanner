package com.example.hp.budgetplanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hp on 07-07-2016.
 */
public class MyFragmentManager extends FragmentPagerAdapter {
final static int NUM_ITEMS=4;


    MyFragmentManager(FragmentManager fragmentmanager)
    {
        super(fragmentmanager);

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new ImageSlider1();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new ImageSlider2();
            case 2: // Fragment # 1 - This will show SecondFragment
                return new ImageSlider3();
            case 3:
                return new ImageSlider4();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}

package com.example.hp.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ImageSlider extends AppCompatActivity {
    FragmentPagerAdapter freagmentadapter;
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        int count;
        Context context = getApplicationContext();
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        count = shared.getInt(LoginMainActivity.POSITION, 0);

        if(count==0)
        {

            final ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
            freagmentadapter = new MyFragmentManager(getSupportFragmentManager());
            viewpager.setAdapter(freagmentadapter);
            //count=1;
            viewpager.setOffscreenPageLimit(4);
        }
        else
        {
            Intent intent=new Intent(ImageSlider.this,LoginMainActivity.class);
            startActivity(intent);
            finish();
        }
        // viewpager.setCurrentItem(0);


    }
}

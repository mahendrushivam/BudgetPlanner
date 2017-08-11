package com.example.hp.budgetplanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class HomeScreenFragment extends Fragment {
    public int currentimageindex=0;
    Button makenewbudget;
    private int[] IMAGE_IDS = {
            R.mipmap.imageslide1, R.mipmap.imageslider2, R.mipmap.imageslider3,R.mipmap.imageslider4

    };

    boolean isAttached = false;

Animation rotateimage=null;
    public HomeScreenFragment() {
        // Required empty public constructor
    }

ImageView slidingimage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home_screen, container, false);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323434")));
        actionBar.setIcon(R.drawable.homescreenicon2);
        slidingimage=(ImageView)v.findViewById(R.id.imageslider);
        makenewbudget=(Button)v.findViewById(R.id.makenewbudget);
        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                if(isAttached)
                    AnimateandSlideShow();
            }
        };

        int delay = 200; // delay for 1 sec.

        int period = 8000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);

            makenewbudget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction trans=fragmentManager.beginTransaction();
                    Fragment fragment=new Makeanewbudget();
                    trans.replace(R.id.fragmentcontainer,fragment);
                    trans.addToBackStack(null);
                    trans.commit();
                }
            });

        return v;
    }

    private void AnimateandSlideShow() {
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
        currentimageindex++;
        rotateimage = AnimationUtils.loadAnimation(getContext(), R.anim.fademodified);
        slidingimage.startAnimation(rotateimage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }
}
package com.example.hp.budgetplanner;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutTheAppFragment extends Fragment {

ImageView image;
    public AboutTheAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_blank, container, false);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("About");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a74b62")));
        actionBar.setIcon(R.mipmap.budgetplannericon1);
        image=(ImageView)v.findViewById(R.id.budgeticon);

        Animation animation3=AnimationUtils.loadAnimation(getActivity(),R.anim.fade);
        animation3.setDuration(2000);
        image.setAnimation(animation3);


        //set1.setDuration(10000);


    return v;
    }

}

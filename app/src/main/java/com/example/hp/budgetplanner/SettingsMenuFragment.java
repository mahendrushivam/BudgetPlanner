package com.example.hp.budgetplanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsMenuFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction trans;
    public SettingsMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings_menu, container, false);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Settings Menu");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#639069")));
        actionBar.setIcon(R.drawable.settingsmenu);
        fragmentManager=getActivity().getSupportFragmentManager();
        trans=fragmentManager.beginTransaction();
        Button but1=(Button)v.findViewById(R.id.viewdetails);
        Button but2=(Button)v.findViewById(R.id.resetpassword);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ViewDetailsFragment fragment=new ViewDetailsFragment();
                    trans.replace(R.id.fragmentcontainer,fragment);
                trans.addToBackStack(null);
                    trans.commit();
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordFragment fragment=new ResetPasswordFragment();
                trans.replace(R.id.fragmentcontainer,fragment);
                trans.addToBackStack(null);
                trans.commit();

            }
        });
        return v;
    }



}
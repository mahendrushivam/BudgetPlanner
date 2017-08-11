package com.example.hp.budgetplanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CheckRegisterLayout extends AppCompatActivity {
    FragmentTransaction trans;
    FragmentManager fragmentManager;
    Fragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_register_layout);
        fragmentManager = getSupportFragmentManager();
        trans = fragmentManager.beginTransaction();
        frag = new ViewDetailsFragment();
        trans.add(R.id.fragmentcontainer, frag);
        trans.addToBackStack(null);
        trans.commit();
    }
}

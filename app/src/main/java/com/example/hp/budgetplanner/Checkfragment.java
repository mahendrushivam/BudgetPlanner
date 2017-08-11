package com.example.hp.budgetplanner;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class Checkfragment extends AppCompatActivity {
    FragmentTransaction trans;
    FragmentManager fragmentManager;
    Fragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkfragment);
        fragmentManager = getSupportFragmentManager();
         trans = fragmentManager.beginTransaction();
        frag = new GridCategoryFragment();
        trans.add(R.id.fragmentcontainer, frag);
       trans.addToBackStack(null);
        trans.commit();

    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
}

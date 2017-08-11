package com.example.hp.budgetplanner;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

public class LoggedInMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public static String USERNAME="USERNAME";
    SharedPreferences.Editor editor;
    int count1=0;
    public static final String POSITION_CATEGORY = "POSITION_CATEGOTY";
    SharedPreferences shared;
    FragmentManager fragmentManager;
    com.github.clans.fab.FloatingActionMenu menu1;
    com.github.clans.fab.FloatingActionButton but1,but2,but3;
    FragmentTransaction trans;
    Fragment fragment=null;
    int count=1;
    android.support.v7.app.ActionBar actionBar;

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        TextView usern=(TextView)findViewById(R.id.usernamedisplay);
        Context context = getApplicationContext();
        DataBaseHelper dataBaseHelper=new DataBaseHelper(this);
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        i=getIntent();

        count = shared.getInt(LoginMainActivity.POSITION, 0);
        count1 = shared.getInt(POSITION_CATEGORY, 0);
        if(count1==0)
        {
            dataBaseHelper.insertvalues();
        }

        String usergettext=i.getExtras().getString("USERNAMEDISPLAY");


        editor=shared.edit();
        editor.putString(USERNAME, usergettext);
        editor.commit();
        //userdisplay.setText("shivam");
        actionBar.setTitle("Home");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323434")));
        actionBar.setIcon(R.drawable.homescreenicon2);
        menu1=(com.github.clans.fab.FloatingActionMenu)findViewById(R.id.fabmenu);
        but1=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab1);
        but2=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab2);
        but3=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab3);
        context =getApplicationContext();
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        //ActionBar action=(ActionBar)getActivity().getActionBar();
        count1=1;
        editor=shared.edit();
        editor.putInt(POSITION_CATEGORY, count1);
        editor.commit();
        fragmentManager=getSupportFragmentManager();
        if(fragment==null)
        {fragment=new HomeScreenFragment();
        trans= fragmentManager.beginTransaction();
        trans.replace(R.id.fragmentcontainer, fragment);
       //trans.addToBackStack(null);
        trans.commit();}
            but1.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            fragment = new Makeanewbudget();
                                            trans=fragmentManager.beginTransaction();
                                            trans.replace(R.id.fragmentcontainer, fragment);
                                            trans.addToBackStack(null);
                                            trans.commit();
                                        }
                                    }

            );
        but2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                trans=fragmentManager.beginTransaction();
                fragment = new GridCategoryFragment();
                trans.replace(R.id.fragmentcontainer, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        but3.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                trans=fragmentManager.beginTransaction();
                fragment = new BudgetReportsList();
                trans.replace(R.id.fragmentcontainer, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        //Toast.makeText(getApplicationContext(),"usergettext :: "+usergettext ,Toast.LENGTH_LONG).show();

        //super.onStop();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.usernamedisplay);
        nav_user.setText(usergettext);
        navigationView.setItemIconTintList(null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_in_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {

            fragmentManager=getSupportFragmentManager();
            fragment=new SettingsMenuFragment();
            trans= fragmentManager.beginTransaction();
            trans.replace(R.id.fragmentcontainer, fragment);
            trans.addToBackStack(null);
            trans.commit();

return true;
        }
        else if(id==R.id.addexpenseiconbar)
        {
            fragmentManager=getSupportFragmentManager();
            fragment=new AddaNewItemFragment();
            trans= fragmentManager.beginTransaction();
            trans.replace(R.id.fragmentcontainer, fragment);
            trans.addToBackStack(null);
            trans.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

                if(fragment==null)
                    fragment=new HomeScreenFragment();
        if (id == R.id.home) {

            fragment=new HomeScreenFragment();
            // Handle the camera action
        } else if (id == R.id.newbudget) {
            fragment=new Makeanewbudget();

        } else if (id == R.id.nav_slideshow) {
            fragment=new BudgetReportsList();

        } else if (id == R.id.categorylist) {

            fragment=new GridCategoryFragment();

        }
        else if (id == R.id.newexpense) {
            fragment=new AddaNewItemFragment();

        }

        else if (id == R.id.abouttheapp) {
            fragment=new AboutTheAppFragment();

        } else if (id == R.id.logout) {

            Context context1=getApplicationContext();
            SharedPreferences shared2=PreferenceManager.getDefaultSharedPreferences(context1);
            String newusername="";
            SharedPreferences.Editor edit1=shared2.edit();
            edit1.putString(LoggedInMainActivity.USERNAME, newusername);
            edit1.commit();
            Intent intent=new Intent(LoggedInMainActivity.this,LoginMainActivity.class);
            startActivity(intent);
            //finish();

        }

      trans= fragmentManager.beginTransaction();
        trans.replace(R.id.fragmentcontainer, fragment);
        trans.addToBackStack(null);
        trans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(getFragmentManager().getBackStackEntryCount() >0 ) {

            getFragmentManager().popBackStack();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }
    }



}


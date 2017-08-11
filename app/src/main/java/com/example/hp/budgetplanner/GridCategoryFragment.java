package com.example.hp.budgetplanner;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridCategoryFragment extends Fragment {
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    int count;
    DataBaseHelper dataBaseHelper;

String []str={"Food","Healthcare","Education","Transportation","Clothing/BeautyCare","Social","Savings","Bills"};
    Integer[] img={R.drawable.food,R.drawable.medic,R.drawable.education,R.drawable.transportation,R.drawable.beautysalon,R.drawable.social,R.drawable.savings,R.drawable.fees};
    GridView gridView;
    public GridCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_grid_category, container, false);
        android.support.v7.app.ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Category List");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cccc")));
        actionBar.setIcon(R.drawable.categorylisticon2);
        Context context = getActivity();
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        //ColorDrawable color=new ColorDrawable(Color.parseColor("#99cccc"));
        //((LoggedInMainActivity) getActivity()).setActionBarTitle("Your title");
        //actionBar.setBackgroundDrawable(color);
        //getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        dataBaseHelper=new DataBaseHelper(getActivity());

       /* count = shared.getInt(CategoryWiseList.POSITION_CATEGORY, 0);
        if(count==0)
        {
            dataBaseHelper.insertvalues();
        }*/
        gridView=(GridView)v.findViewById(R.id.gridview);
        gridView.setAdapter(new MyArrayAdapter(getActivity(), R.layout.gridviewcategory, R.id.txt6, str));
        //gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         registerForContextMenu(gridView);


        return v;
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.categoryselectmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.viewsubcategory :
            {int position=menuInfo.position;
                String categname=str[position];
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction trans=fragmentManager.beginTransaction();
                CategoryWiseList category=new CategoryWiseList();
                category.categoryname=categname;
                trans.replace(R.id.fragmentcontainer,category);
                trans.addToBackStack(null);
                trans.commit();
                return true;}
            case R.id.opencategorydata:
                return true;
        }
        return super.onContextItemSelected(item);
    }





    public class MyArrayAdapter extends ArrayAdapter<String>
    {
        public  MyArrayAdapter(Context cont,int row_item,int text1,String [] str)
    {
        super(cont,row_item,text1,str);

    }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position,convertView,parent);
            ViewHolder holder = (ViewHolder)row.getTag();
            if(holder==null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            holder.imageView.setImageResource(img[position]);
            holder.txt2.setText(str[position]);
            return row;
        }
    }
    class ViewHolder
    {
        ImageView imageView;
        TextView txt2;
        ViewHolder(View row)
        {
            imageView = (ImageView)row.findViewById(R.id.img);
            txt2=(TextView)row.findViewById(R.id.txt6);
        }
    }
    }



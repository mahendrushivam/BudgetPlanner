package com.example.hp.budgetplanner;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddaNewItemFragment extends Fragment {
ListView  categorylist,subcategorylist;
    String [] categoryname={"Food","Healthcare","Education","Transportation","Clothing/BeautyCare","Social","Savings","Bills"};
    Integer [] imageid={R.drawable.food4,R.drawable.medic4,R.drawable.education4,R.drawable.transportation4,R.drawable.beautysalon4,R.drawable.social4,R.drawable.savings4,R.drawable.fees4};
    ArrayList<String> subcategoryname=null;
    String getcategoryname,getsubcategoryname="any";
    DataBaseHelper dataBaseHelper;
    Button additembutton;
    public AddaNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Add New Expense");
        actionBar.setIcon(R.drawable.addexpenseicon);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9ee1fb")));

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_adda_new_item, container, false);
        categorylist=(ListView)v.findViewById(R.id.categorylist);
        subcategorylist=(ListView)v.findViewById(R.id.subcategorylist);
        dataBaseHelper=new DataBaseHelper(getActivity());
        categorylist.setAdapter(new CategoryListAdapter(getActivity(),R.layout.categorylistlayout,R.id.getcatname,categoryname));
        //subcategoryname=dataBaseHelper.subcategory("Food");
        additembutton=(Button)v.findViewById(R.id.additembutton);
        getcategoryname="Food";
        subcategoryname = dataBaseHelper.subcategory("Food");
        subcategorylist.setAdapter(new SubCategoryListAdapter(getActivity(), R.layout.subcategorylist, R.id.subcatname, subcategoryname));
        categorylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getcategoryname = categoryname[position];
                subcategoryname = dataBaseHelper.subcategory(getcategoryname.trim());
                subcategorylist.setAdapter(new SubCategoryListAdapter(getActivity(), R.layout.subcategorylist, R.id.subcatname, subcategoryname));
            }
        });


        additembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getsubcategoryname.equals("any"))
                {
                    Snackbar.make(v,"Please Select the Subcategory",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                }
                else
                {
                //Toast.makeText(getActivity(),getcategoryname+getsubcategoryname,Toast.LENGTH_LONG).show();
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction trans=fragmentManager.beginTransaction();
                    AddItemFragment2 fragment=new AddItemFragment2();
                    fragment.getcategoryname=getcategoryname;
                    fragment.getsubcategoryname=getsubcategoryname;
                    trans.replace(R.id.fragmentcontainer,fragment);
                    trans.addToBackStack(null);
                    trans.commit();

            }
        }});



    return v;}
    public class CategoryListAdapter extends ArrayAdapter<String>
    {
        public  CategoryListAdapter(Context cont,int row_item,int text1,String [] str)
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
            holder.imageView.setImageResource(imageid[position]);
            holder.txt2.setText(categoryname[position]);
            return row;
        }
    }
    class ViewHolder
    {
        ImageView imageView;
        TextView txt2;
        ViewHolder(View row)
        {
            imageView = (ImageView)row.findViewById(R.id.getcatimage);
            txt2=(TextView)row.findViewById(R.id.getcatname);
        }
    }

    public class SubCategoryListAdapter extends ArrayAdapter<String>
    {
        public  SubCategoryListAdapter(Context cont,int row_item,int text1,ArrayList<String> str)
        {
            super(cont,row_item,text1,str);

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position,convertView,parent);
            ViewHolder1 holder = (ViewHolder1)row.getTag();
            if(holder==null)
            {
                holder = new ViewHolder1(row);
                row.setTag(holder);
            }
            holder.subcatname.setText(String.valueOf(subcategoryname.get(position)));
            holder.subcatradio.setTag(position);
            holder.subcatradio.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int position=(Integer)v.getTag();
                    getsubcategoryname=subcategoryname.get(position);
                }
            });
            return row;
        }
    }
    class ViewHolder1
    {

        TextView subcatname;
        RadioButton subcatradio;
        ViewHolder1(View row)
        {
           subcatname=(TextView)row.findViewById(R.id.subcatname);
            subcatradio=(RadioButton)row.findViewById(R.id.subcategoryradio);
        }
    }


}

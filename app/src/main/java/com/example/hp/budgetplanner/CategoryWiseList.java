package com.example.hp.budgetplanner;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoryWiseList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //public static final String POSITION_CATEGORY = "POSITION_CATEGOTY";
    public String categoryname;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    int count;
    ListView listview;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> subcategory=null;
    TextView category;
    ImageView imageview;
    Integer [] categoryicon={R.drawable.food1,R.drawable.medic1,R.drawable.education1,R.drawable.transportation1,R.drawable.beautysalon1,R.drawable.social1,R.drawable.savings1,R.drawable.fees1};
    // TODO: Rename and change types of parameters
    int position;

    public CategoryWiseList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_category_wise_list, container, false);
        dataBaseHelper=new DataBaseHelper(getActivity());
        category=(TextView)v.findViewById(R.id.category);
        imageview=(ImageView)v.findViewById(R.id.categoryicon);
        android.support.v7.app.ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Category List");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cccc")));
        actionBar.setIcon(R.drawable.categorylisticon2);
        /*Context context = getActivity();
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        //ActionBar action=(ActionBar)getActivity().getActionBar();
            count=1;
        editor=shared.edit();
        editor.putInt(POSITION_CATEGORY, count);
        editor.commit();*/
        category.setText(categoryname);

            listview=(ListView)v.findViewById(R.id.listview);
            position=dataBaseHelper.categoryiconposition(categoryname);
            subcategory=dataBaseHelper.subcategory(categoryname);
            imageview.setImageResource(categoryicon[position]);
            listview.setAdapter(new MyArrayAdapter(getActivity(),R.layout.subcategorylistlayout,R.id.subcategoryname,subcategory));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    final View add_view = getActivity().getLayoutInflater().inflate(R.layout.deletealertdialogbox, null, false);
                    TextView deltxt=(TextView)add_view.findViewById(R.id.deltext);
                    String deltxtstr=deltxt.getText().toString().trim()+ " "+subcategory.get(position);
                    deltxt.setText(deltxtstr);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Delete Subcategory")
                            .setIcon(R.drawable.remove)
                            .setView(add_view)
                            .setCancelable(true)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String new_item = subcategory.get(position).toString().trim();
                                    subcategory.remove(new_item);
                                    dataBaseHelper.removesubcategory(categoryname, new_item);
                                    ((ArrayAdapter) listview.getAdapter()).notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            });







            Button addbut=(Button)v.findViewById(R.id.addbut);
        addbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View add_view = getActivity().getLayoutInflater().inflate(R.layout.alertdialog, null, false);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Add a new Subcategory")
                        .setIcon(R.drawable.addicon1)
                        .setView(add_view)
                        .setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editText = (EditText) add_view.findViewById(R.id.edittext);
                                String new_item = editText.getText().toString().trim();
                                subcategory.add(new_item);
                                dataBaseHelper.addsubcategory(categoryname, new_item);
                                ((ArrayAdapter) listview.getAdapter()).notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        // Inflate the layout for this fragment
       return v;
    }

    public class MyArrayAdapter extends ArrayAdapter<String>
    {
        public  MyArrayAdapter(Context cont,int row_item,int text1,ArrayList<String> str)
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

            holder.txt2.setText(String.valueOf(subcategory.get(position)));
            return row;
        }
    }
    class ViewHolder
    {

        TextView txt2;
        ViewHolder(View row)
        {

            txt2=(TextView)row.findViewById(R.id.subcategoryname);
        }
    }


    }





package com.example.hp.budgetplanner;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetReportsList extends Fragment {

    public ArrayList<BudgetsNameList> budgetall = null;
    public ArrayList<BudgetsNameList> budgetmonth = null;
    ViewHolder holder;
    SharedPreferences shared;
    FragmentManager  fragmentManager;
    FragmentTransaction trans;
    //TextView budgetname, budgetamount, getbudgetmonth;
    ListView budgetlist;
    int positioncat;
    int positioncat1;
    DataBaseHelper dataBaseHelper;
    Spinner month;
    String username;
    String []arraymonth={"All","January","February","March","April","May","June","July","August","September","October","November","December"};
    public BudgetReportsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_budget_reports_list, container, false);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Budget Reports");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9fe29b")));
                actionBar.setIcon(R.mipmap.budgetreport);
        shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        username = shared.getString(LoggedInMainActivity.USERNAME, "");
        dataBaseHelper = new DataBaseHelper(getContext());
        budgetlist = (ListView) v.findViewById(R.id.budgetlist);
        month = (Spinner) v.findViewById(R.id.month);
        fragmentManager=getActivity().getSupportFragmentManager();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraymonth);
        adapter3.setDropDownViewResource(R.layout.simpledropdowndesign);
        month.setAdapter(adapter3);
        //budgetall = dataBaseHelper.getallbudgets(username);
        //budgetlist.setAdapter(new BudgetListAdapter(getActivity(), R.layout.budgletlistdisplay, R.id.budgetname, budgetall));
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = month.getSelectedItem().toString();
                if (choice.equals("All")) {
                    budgetall = dataBaseHelper.getallbudgets(username);
                    budgetlist.setAdapter(new BudgetListAdapter(getActivity(), R.layout.budgletlistdisplay, R.id.budgetname, budgetall));
                } else {
                    int countbudget = dataBaseHelper.getbudgetmonthcount(username, choice);
                    if (countbudget > 0) {
                        budgetmonth = dataBaseHelper.getallbudgetsmonth(username, choice);
                        budgetlist.setAdapter(new BudgetListAdapterMonth(getActivity(), R.layout.budgetlistmonthdisplay, R.id.budgetname, budgetmonth));
                    } else {
                        budgetmonth = dataBaseHelper.getallbudgetsmonth(username, choice);
                        budgetlist.setAdapter(new BudgetListAdapterMonth(getActivity(), R.layout.budgetlistmonthdisplay, R.id.budgetname, budgetmonth));
                        Snackbar.make(view, "No Budget created for this month (" + choice + ")", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        budgetlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {

                Toast.makeText(getActivity(),"You clicked at position",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }


    public class BudgetListAdapter extends ArrayAdapter<BudgetsNameList> {
        public BudgetListAdapter(Context cont, int row_item, int text1, ArrayList<BudgetsNameList> budget) {
            super(cont, row_item, text1, budget);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            holder = (ViewHolder) row.getTag();
            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            BudgetsNameList budg = budgetall.get(position);
            holder.getbudgetname1.setText(budg.getbudgetname());
            holder.getbudgetmonth1.setText(budg.getbudgetmonth());
            int getamount = budg.getbudgetamount();
            holder.getbudgetamount1.setText(String.valueOf(getamount));
            holder.popupoptions.setTag(position);
            holder.popupoptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                     positioncat = (Integer) v.getTag();

                    PopupMenu popup=new PopupMenu(getActivity(),v);
                    popup.getMenuInflater().inflate(R.menu.budgetoptionmenu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        public boolean onMenuItemClick(MenuItem item) {
                            int id=item.getItemId();
                            BudgetsNameList budgetdel=budgetall.get(positioncat);
                            boolean flag=idoperations(budgetdel,id,"All");
                            if(flag==true)
                                return true;
                            return false;
                        }
                    });

                    popup.show();//showing popup menu
                }
            });//closin
                    //Toast.makeText(getActivity(),"You clicked at position"+String.valueOf(position) ,Toast.LENGTH_SHORT).show();


            return row;
        }
    }

    class ViewHolder {
        TextView getbudgetname1;
        TextView getbudgetmonth1;
        TextView getbudgetamount1;
        ImageButton popupoptions;
        ViewHolder(View row) {
            getbudgetname1 = (TextView) row.findViewById(R.id.budgetname);
            getbudgetmonth1 = (TextView) row.findViewById(R.id.monthname);
            getbudgetamount1 = (TextView) row.findViewById(R.id.budgetamount);
            popupoptions=(ImageButton)row.findViewById(R.id.popupoption);
        }
    }

    public class BudgetListAdapterMonth extends ArrayAdapter<BudgetsNameList> {
        public BudgetListAdapterMonth(Context cont, int row_item, int text1, ArrayList<BudgetsNameList> budget) {
            super(cont, row_item, text1, budget);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ViewHolder1 holder = (ViewHolder1) row.getTag();
            if (holder == null) {
                holder = new ViewHolder1(row);
                row.setTag(holder);
            }
            BudgetsNameList budg = budgetmonth.get(position);
            holder.getbudgetname1.setText(budg.getbudgetname());
            //holder.getbudgetmonth1.setText( budg.getbudgetmonth());
            int getamount = budg.getbudgetamount();
            holder.getbudgetamount1.setText(String.valueOf(getamount));
            holder.popupoptions.setTag(position);
            holder.popupoptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    positioncat1 = (Integer) v.getTag();

                    PopupMenu popup = new PopupMenu(getActivity(), v);
                    popup.getMenuInflater().inflate(R.menu.budgetoptionmenu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            BudgetsNameList budgetdel = budgetmonth.get(positioncat1);
                            boolean flag=idoperations(budgetdel,id,"NotAll");
                            if(flag==true)
                            {
                                return true;
                            }
                            return false;}
                    });

                    popup.show();//showing popup menu
                }
            });//closin
            //Toast.makeText(getActivity(),"You clicked at position"+String.valueOf(position) ,Toast.LENGTH_SHORT).show();
            return row;
        }
    }

    class ViewHolder1 {
        TextView getbudgetname1;
       // TextView getbudgetmonth1;
        TextView getbudgetamount1;
        ImageButton popupoptions;

        ViewHolder1(View row) {
            getbudgetname1 = (TextView) row.findViewById(R.id.budgetname);
            //getbudgetmonth1 = (TextView) row.findViewById(R.id.monthname);
            getbudgetamount1 = (TextView) row.findViewById(R.id.budgetamount);
            popupoptions=(ImageButton) row.findViewById(R.id.popupoption);
        }
    }

    public boolean idoperations(BudgetsNameList budgetdel,int id,String str)
    {
        switch (id) {
                               case R.id.deletebudget: {

                                    String delbudgetname = budgetdel.getbudgetname();
                                    dataBaseHelper.deletebudget(username, delbudgetname);
                                   if(str.equals("NotAll"))
                                    budgetmonth.remove(positioncat1);
                                   else if(str.equals("All"))
                                       budgetall.remove(positioncat);
                                    ((ArrayAdapter) budgetlist.getAdapter()).notifyDataSetChanged();
                                    int count = dataBaseHelper.getbudgetname(username, delbudgetname);
                                    if (count == 0) {
                                        Toast.makeText(getActivity(), "Your Budget has been deleted", Toast.LENGTH_LONG).show();
                                    }
                                    return true;
                                }
                                    case R.id.editbudget:
                                    {
                                        String editbudgetname=budgetdel.getbudgetname().trim();
                                        String month=budgetdel.getbudgetmonth().trim();
                                        fragmentManager=getActivity().getSupportFragmentManager();
                                        Toast.makeText(getActivity(),editbudgetname,Toast.LENGTH_LONG).show();
                                        trans=fragmentManager.beginTransaction();
                                        EditbudgetReport fragment=new EditbudgetReport();
                                        fragment.getmonth=month.trim();
                                        fragment.getbudgetname=editbudgetname.trim();
                                        trans.replace(R.id.fragmentcontainer,fragment);
                                        trans.addToBackStack(null);
                                        trans.commit();
                                        return true;
                                    }

            case R.id.openbudget:
            {
                String editbudgetname=budgetdel.getbudgetname().trim();
                String month=budgetdel.getbudgetmonth().trim();
                int amountbudget=budgetdel.getbudgetamount();
                fragmentManager=getActivity().getSupportFragmentManager();
                //Toast.makeText(getActivity(),editbudgetname,Toast.LENGTH_LONG).show();
                trans=fragmentManager.beginTransaction();
                BudgetReportDetails fragment=new BudgetReportDetails();
                fragment.getbudgetmonth=month.trim();
                fragment.getbudgetname=editbudgetname.trim();
                fragment.getbudgetamount=amountbudget;
                trans.replace(R.id.fragmentcontainer,fragment);
                trans.addToBackStack(null);
                trans.commit();
                return true;
            }

        }
        return false;
    }
    }



package com.example.hp.budgetplanner;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class BudgetReportDetails extends Fragment {
ArrayList<BudgetReportCategory> budgetcat=null;
ListView listView;
    public static String getbudgetmonth,getbudgetname;
    public static int getbudgetamount;
    TextView budgetname,budgetmonth,budgetamount;
    SharedPreferences shared;
    String username;
    DataBaseHelper dataBaseHelper;
    Integer [] images={R.drawable.food3,R.drawable.medic3,R.drawable.education3,R.drawable.transportation3,R.drawable.beautysalon3,R.drawable.social3,R.drawable.savings3,R.drawable.fees3};
    public BudgetReportDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_budget_report_details, container, false);
        budgetamount=(TextView)v.findViewById(R.id.budgetamount);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Budget Details");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cfc6e4")));

        actionBar.setIcon(R.mipmap.budgetreport);
        budgetmonth=(TextView)v.findViewById(R.id.budgetmonth);
        budgetname=(TextView)v.findViewById(R.id.budgetname);
        listView=(ListView)v.findViewById(R.id.budgetreportdetails);
        shared= PreferenceManager.getDefaultSharedPreferences(getActivity());
        dataBaseHelper=new DataBaseHelper(getContext());
        username=shared.getString(LoggedInMainActivity.USERNAME,"");
        budgetmonth.setText(getbudgetmonth.trim());
        budgetname.setText(getbudgetname.trim());
        String amount=String.valueOf(getbudgetamount);
        budgetamount.setText(amount.trim());
        budgetcat=dataBaseHelper.getcategorywisedetails(getbudgetmonth.trim(),getbudgetname.trim(),username.trim());
        listView.setAdapter(new BudgetListAdapter(getActivity(),R.layout.budgetreportbacklayout,R.id.budgetcategory,budgetcat));
    return v;
    }

    public class BudgetListAdapter extends ArrayAdapter<BudgetReportCategory>
    {
        public  BudgetListAdapter(Context cont,int row_item,int text1,ArrayList<BudgetReportCategory> str)
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
            BudgetReportCategory budgetcategory=budgetcat.get(position);
            String getbudgetcategoryname=budgetcategory.getCategoryname();
            int getbudgetleftamount=budgetcategory.getLeftcatamount();
            int getbudgetspentamount=budgetcategory.getSpentamount();
            holder.budcat.setText(getbudgetcategoryname);
            int catposition=budgetcategory.getImageposition();
            holder.budspent.setText(String.valueOf(getbudgetspentamount));
            holder.budleft.setText(String.valueOf(getbudgetleftamount));
            holder.progresscat.setMax(budgetcategory.totcatamount);
            holder.progresscat.setProgress(getbudgetspentamount);
            holder.progresscat.setClickable(false);
            holder.imageView.setImageResource(images[catposition]);
            holder.popupoptions.setTag(position);
            holder.popupoptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position1=(Integer)v.getTag();
                    BudgetReportCategory budgetReportCategory1=budgetcat.get(position1);
                    FragmentManager fragment=getActivity().getSupportFragmentManager();
                    FragmentTransaction trans=fragment.beginTransaction();
                    ExpenseListFragment fragment1=new ExpenseListFragment();
                    fragment1.budgetname1=getbudgetname;
                    fragment1.totamount=budgetReportCategory1.getTotcatamount();
                    fragment1.leftamount=budgetReportCategory1.getLeftcatamount();
                    fragment1.spentamount=budgetReportCategory1.getSpentamount();
                    fragment1.categoryname=budgetReportCategory1.getCategoryname();
                    fragment1.catposition=budgetReportCategory1.getImageposition();
                    trans.replace(R.id.fragmentcontainer,fragment1);
                    trans.addToBackStack(null);
                    trans.commit();
                }
            });
            return row;
        }
    }
    class ViewHolder
    {
            TextView budcat,budleft,budspent;
            ProgressBar progresscat;
        ImageView imageView;
        ImageButton popupoptions;
        ViewHolder(View row)
        {
            budcat=(TextView)row.findViewById(R.id.budgetcategory);
            budleft=(TextView)row.findViewById(R.id.amountleft);
            progresscat=(ProgressBar)row.findViewById(R.id.progresscat);
            budspent=(TextView)row.findViewById(R.id.amountspent);
            imageView=(ImageView)row.findViewById(R.id.catimage);
            popupoptions=(ImageButton)row.findViewById(R.id.popupoption1);
        }
    }
}



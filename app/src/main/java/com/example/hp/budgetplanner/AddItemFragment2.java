package com.example.hp.budgetplanner;


import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment2 extends Fragment {
    String [] monthspinner={"January","February","March","April","May","June","July","August","September","October","November","December"};
    Integer [] yer={2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030};
    Integer [] day1={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    Spinner month,year,day;
    Drawable erroricon;
    AutoCompleteTextView budgetname;
    EditText categoryname,subcategoryname,expensename,expensedetails,expenseamount;
    Button addnewexpense;
    String username,getmonth;
    public static String getcategoryname;
    public  static  String getsubcategoryname;
    int getexpenseamount;
    DataBaseHelper dataBaseHelper;
    SharedPreferences shared;
    String expensedate;

    public AddItemFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_item_fragment2, container, false);
        shared= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=shared.getString(LoggedInMainActivity.USERNAME, "");
        dataBaseHelper=new DataBaseHelper(getActivity());
        erroricon=getResources().getDrawable(R.drawable.error2);
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        categoryname=(EditText)v.findViewById(R.id.categoryname);
        subcategoryname=(EditText)v.findViewById(R.id.subcategoryname);
        expensename=(EditText)v.findViewById(R.id.expensename);
        expenseamount=(EditText)v.findViewById(R.id.expenseamount);
        expensedetails=(EditText)v.findViewById(R.id.expensedetails);
        month=(Spinner)v.findViewById(R.id.spinnermonth);
        day=(Spinner)v.findViewById(R.id.spinnerday);
        year=(Spinner)v.findViewById(R.id.spinneryear);
        addnewexpense=(Button)v.findViewById(R.id.addexpensebut);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, monthspinner);
        adapter3.setDropDownViewResource(R.layout.simpledropdowndesign);
        month.setAdapter(adapter3);
        ArrayAdapter<Integer> adapter4 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, day1);
        adapter4.setDropDownViewResource(R.layout.simpledropdowndesign);
        day.setAdapter(adapter4);
        ArrayAdapter<Integer> adapter5 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, yer);
        adapter5.setDropDownViewResource(R.layout.simpledropdowndesign);
        year.setAdapter(adapter5);
        budgetname=(AutoCompleteTextView)v.findViewById(R.id.budgetname);
        categoryname.setText(getcategoryname.trim());
        subcategoryname.setText(getsubcategoryname.trim());
        final ArrayList<String> getallbudget=dataBaseHelper.getallbudgetnames(username.trim());
       budgetname.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.budgetnameautocomplete,getallbudget));
        addnewexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getbudgetnme = budgetname.getText().toString().trim();
                int amount=0;
                boolean flag10=false;
                if(budgetname.getText().toString().trim().equalsIgnoreCase(""))
                {   flag10=false;
                    Toast.makeText(getActivity(), "Enter BudgetName",Toast.LENGTH_SHORT).show();}
                else
                {
                int count1=dataBaseHelper.getbudgetname(username.trim(),budgetname.getText().toString().trim());
                if(count1==0) {
                    flag10 = false;
                    Toast.makeText(getActivity(),"Entered budgetname incorrect",Toast.LENGTH_SHORT).show();
                }
                else
                flag10=true;
                }
                boolean flag2 = false,flag1,flag8;
                flag1=false;
                flag8=false;

                boolean flag3, flag4, flag5, flag6, flag7;
                flag3 = validate(categoryname, "Categoryname");
                flag4 = validate(subcategoryname, "SubCategoryName");
                flag5 = validate(expensename, "Expense Name");
                flag6 = validate(expenseamount, "Expense Amount");
                flag7 = validate(expensedetails, "Expense Details");
                expensedate = day.getSelectedItem().toString() + ",";
                expensedate += month.getSelectedItem().toString() + ",";
                expensedate += year.getSelectedItem().toString();

                String getmonth = dataBaseHelper.getbudgetmonth(username, getbudgetnme);
                if (getmonth.equals(month.getSelectedItem().toString().trim()))
                    flag2 = true;
                else {
                    flag2 = false;
                    Toast.makeText(getActivity(), "Entered month doesnt match budget month", Toast.LENGTH_SHORT).show();
                }

                if(flag5==true && flag4==true && flag5==true && flag10==true)
                {
                    boolean flag9=dataBaseHelper.checkeventexists(username,categoryname.getText().toString().trim(),budgetname.getText().toString().trim(),expensename.getText().toString());
                    if(flag9==false)
                    {
                        flag8=true;
                    }
                    else
                    {   flag8=false;
                        expensename.setError("Entered Expensename already Exist for above mentioned category",erroricon);
                        expensename.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                expensename.setError(null);

                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                expensename.setError(null);

                            }
                        });

                    }
                }
                if (flag2 == true && flag3 == true && flag10==true &&  flag4 == true && flag5 == true  && flag6==true && flag7 == true && flag8==true) {
                   // int amount = Integer.valueOf(expenseamount.getText().toString().trim());
                    if(flag6==true)
                    {amount = Integer.valueOf(expenseamount.getText().toString().trim());
                        //Toast.makeText(getActivity(), username + getbudgetnme + amount + getcategoryname, Toast.LENGTH_LONG).show();
                        flag1=dataBaseHelper.checkleftamount(username.trim(), getbudgetnme, amount, getcategoryname.trim());}
                    if(flag1==true) {
                        dataBaseHelper.insertnewitem(username, getbudgetnme,categoryname.getText().toString().trim(),subcategoryname.getText().toString().trim(), expensename.getText().toString().trim(), expensedetails.getText().toString().trim(), expensedate, amount);
                        Snackbar.make(v,"Your Expense has been added ",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "You have already exceeded your Budget Limit For Category Name :: "+getcategoryname, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar.make(v, "There are some errors in your TextFields", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        return v;
    }


    public boolean validate(final EditText edittext,String str)
    {
        boolean flag=true;
        if(edittext.getText().toString().trim().equalsIgnoreCase("")){
            edittext.setError("Enter "+str,erroricon);
            flag=false;
        }

        else if(str.equals("Categoryname"))
        {
            boolean flag3=dataBaseHelper.checkcategoryexist(edittext.getText().toString().trim());
            if(flag3)
            {
                flag=true;
            }
            else
            {
                edittext.setError("Entered Categoryname doesn't exists",erroricon);
                flag=false;
            }
        }
        else if(str.equals("SubCategoryName"))
        {
            boolean flag4=dataBaseHelper.checksubcategoryexist(getcategoryname.trim(),edittext.getText().toString().trim());
            if(flag4)
            {
                flag=true;
            }
            else
            {
                edittext.setError("Entered Subcategory Name doesn't exists",erroricon);
                flag=false;
            }
        }



        edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edittext.setError(null);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                edittext.setError(null);

            }
        });


        return flag;
    }

}

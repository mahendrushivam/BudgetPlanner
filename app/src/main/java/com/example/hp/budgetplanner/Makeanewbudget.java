package com.example.hp.budgetplanner;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Makeanewbudget extends Fragment {
EditText budgetname,budgetamount;
    Button customizebudget,inbuiltbudget;
    Spinner budgetmonth;
    String budamt;
    DataBaseHelper dataBaseHelper;
    String username;
    FragmentManager fragmentManager;
    CustomizeBudget fragment;
    FragmentTransaction trans;
    SharedPreferences shared;

    String []month={"January","February","March","April","May","June","July","August","September","October","November","December"};
Integer healthamt,educamt,savingamt,totamt,billamt,socialamt,transamt,foodamt,clothbeauamt;
    Drawable erroricon;
    public Makeanewbudget() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_makeanewbudget, container, false);
        erroricon=getResources().getDrawable(R.drawable.error1);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("New Budget");
        actionBar.setIcon(R.drawable.makebudget);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6571f0")));
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        budgetname=(EditText)v.findViewById(R.id.budgetname);
        dataBaseHelper=new DataBaseHelper(getActivity());
        budgetamount=(EditText)v.findViewById(R.id.budgetamount);
        customizebudget=(Button)v.findViewById(R.id.customizebudget);
        inbuiltbudget=(Button)v.findViewById(R.id.inbuiltbudget);
        budgetmonth=(Spinner)v.findViewById(R.id.month);
        shared= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=shared.getString(LoggedInMainActivity.USERNAME, "");

        fragmentManager=getActivity().getSupportFragmentManager();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, month);
        adapter3.setDropDownViewResource(R.layout.simpledropdowndesign);
        budgetmonth.setAdapter(adapter3);
        inbuiltbudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean flag=validatefields();
                if(flag)
                Snackbar.make(v,"Your Budget has been created",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                    else
                {
                    Snackbar.make(v,"There are some errors in your textfields",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
            }
        });

        customizebudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new CustomizeBudget();
                boolean flag1,flag2;
                flag1=validate(budgetname, "Budgetname");
                flag2=validate(budgetamount,"Budgetamount");
                if(flag1==true && flag2==true)
                {fragment.budgetname=budgetname.getText().toString().trim();
                    fragment.budgetamount=budgetamount.getText().toString().trim();
                    String mon=budgetmonth.getSelectedItem().toString().trim();
                    fragment.month=mon;
                    trans=fragmentManager.beginTransaction();
                    trans.replace(R.id.fragmentcontainer,fragment);
                    trans.addToBackStack(null);
                    trans.commit();}
                    //Snackbar.make(v,"There are some errors in your textfields",Snackbar.LENGTH_SHORT).setAction("Action",null).show();}
                else
                {
                    Snackbar.make(v,"There are some errors in your textfields",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
            }
        });

    return v;
    }

    public void customizebudgetfunc()
    {



    }
    public boolean validatefields()
    {

        boolean flag1,flag3;
        boolean flag=false;
        flag1=validate(budgetname,"Budgetname");
        flag3=validate(budgetamount,"BudgetAmount");
        if(flag1==true && flag3==true)
        {
           totamt=Integer.valueOf(budgetamount.getText().toString().trim());
            healthamt=(12*totamt)/100;
            educamt=(20*totamt)/100;
            foodamt=(20*totamt)/100;
            transamt=(8*totamt)/100;
            savingamt=(12*totamt)/100;
            billamt=(10*totamt)/100;
            clothbeauamt=(9*totamt)/100;
            socialamt=(9*totamt)/100;
            String mon=budgetmonth.getSelectedItem().toString().trim();
            dataBaseHelper.insertnewbudget(budgetname.getText().toString().trim(), username.trim(), totamt, mon, healthamt, educamt, savingamt, billamt, transamt, foodamt, socialamt, clothbeauamt);
            //Toast.makeText(getActivity(),budgetname.getText().toString().trim()+String.valueOf(totamt)+ " "+ String.valueOf(healthamt),Toast.LENGTH_LONG).show();
                flag=true;
        }

    return flag;
    }
    public boolean validate(final EditText edittext,String str)
    {
        boolean flag=true;
        if(edittext.getText().toString().trim().equalsIgnoreCase("")){
            edittext.setError("Enter "+str,erroricon);
            flag=false;
        }
        else if(str.equals("Budgetname"))
        {int flag2=dataBaseHelper.getbudgetname(username,budgetname.getText().toString());
            if(flag2!=0)
        {
            edittext.setError("Enter " + str + " already exits", erroricon);
            //Toast.makeText(getActivity(),"true value"+username,Toast.LENGTH_LONG).show();
            flag=false;
        }
            else if(flag2==0)
            { flag=true;}

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

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
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditbudgetReport extends Fragment {
    int totamount,getbudgetotamount,healthamount,transamount,educationamount,foodamount,beautycareamount,socialamount,billsamount,savingsamount;
    EditText budgettotamount,healthamt,transportamount,educamount,foodamt,beautyclothamount,socialamt,savingamt,billamt;
    TextView budgetname;
    String username;
    public static String getbudgetname=null;
    DataBaseHelper dataBaseHelper;
    SharedPreferences shared;
    Button editbudget,savebudget;
    public static String getmonth;
    Drawable erroricon;
    public EditbudgetReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_editbudget_report, container, false);
    budgetname=(TextView)v.findViewById(R.id.budgetname);
        ActionBar actionBar=((LoggedInMainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Edit Budget");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6571f0")));
        actionBar.setIcon(R.mipmap.budgetreport);
            budgettotamount=(EditText)v.findViewById(R.id.budgetamount);
            healthamt=(EditText)v.findViewById(R.id.healthcare);
            transportamount=(EditText)v.findViewById(R.id.transportation);
        dataBaseHelper=new DataBaseHelper(getActivity());
            educamount=(EditText)v.findViewById(R.id.education);
            foodamt=(EditText)v.findViewById(R.id.food);
            editbudget=(Button)v.findViewById(R.id.editbudget);
            savebudget=(Button)v.findViewById(R.id.savebudget);
            beautyclothamount=(EditText)v.findViewById(R.id.clothbeauty);
            socialamt=(EditText)v.findViewById(R.id.social);
            savingamt=(EditText)v.findViewById(R.id.savings);
            billamt=(EditText)v.findViewById(R.id.bills);
        erroricon=getResources().getDrawable(R.drawable.error);
        //dataBaseHelper=new DataBaseHelper(getContext());
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        shared= PreferenceManager.getDefaultSharedPreferences(getContext());
        username=shared.getString(LoggedInMainActivity.USERNAME, "");
        //Toast.makeText(getActivity(),username,Toast.LENGTH_LONG).show();
        budgetname.setText(getbudgetname.trim());
        totamount=dataBaseHelper.getbudgettotamount(username, getbudgetname.trim());
        healthamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Healthcare");
        billsamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Bills");
        foodamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Food");
        transamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Transportation");
        educationamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Education");
        savingsamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Savings");
        socialamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Social");
        beautycareamount=dataBaseHelper.getbudgetreportdetails(username,getbudgetname,"Clothing/BeautyCare");
            // CATEGORY SET THE EDITTEXT WITH THE REQUIRED VALUES
        transportamount.setText(String.valueOf(transamount));
        educamount.setText(String.valueOf(educationamount));
        foodamt.setText(String.valueOf(foodamount));
        savingamt.setText(String.valueOf(savingsamount));
        socialamt.setText(String.valueOf(socialamount));
        healthamt.setText(String.valueOf(healthamount));
        beautyclothamount.setText(String.valueOf(beautycareamount));
        budgettotamount.setText(String.valueOf(totamount));
        billamt.setText(String.valueOf(billsamount));
        editbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodamt.setFocusableInTouchMode(true);
                foodamt.setClickable(true);
                budgettotamount.setClickable(true);
                budgettotamount.setFocusableInTouchMode(true);
                healthamt.setFocusableInTouchMode(true);
                healthamt.setClickable(true);
                educamount.setFocusableInTouchMode(true);
                educamount.setClickable(true);
                transportamount.setFocusableInTouchMode(true);
                transportamount.setClickable(true);
                savingamt.setFocusableInTouchMode(true);
                savingamt.setClickable(true);
                socialamt.setFocusableInTouchMode(true);
                socialamt.setClickable(true);
                beautyclothamount.setFocusableInTouchMode(true);
                beautyclothamount.setClickable(true);
                billamt.setFocusableInTouchMode(true);
                billamt.setClickable(true);

            }
        });

        savebudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag1,flag2,flag3,flag4,flag5,flag6,flag7,flag8,flag9;
                flag1=validate(healthamt,"Healthcare Amount");
                flag2=validate(foodamt,"Food Amount");
                flag3=validate(transportamount,"Transportation Amount");
                flag4=validate(budgettotamount,"Budget Amount");
                flag5=validate(socialamt,"Social Amount");
                flag6=validate(savingamt,"Savings Amount");
                flag7=validate(educamount,"Education Amount");
                flag8=validate(billamt,"Bills Amount");
                flag9=validate(beautyclothamount,"Clothing/ BeautyCare Amount");
                if(flag1==true && flag2==true && flag3==true && flag4==true && flag5==true && flag7==true && flag6==true && flag8==true && flag9==true)
                {
                    healthamount=Integer.valueOf(healthamt.getText().toString().trim());
                    foodamount=Integer.valueOf(foodamt.getText().toString().trim());
                    transamount=Integer.valueOf(transportamount.getText().toString().trim());
                    educationamount=Integer.valueOf(educamount.getText().toString().trim());
                    billsamount=Integer.valueOf(billamt.getText().toString().trim());
                    savingsamount=Integer.valueOf(savingamt.getText().toString().trim());
                    beautycareamount=Integer.valueOf(beautyclothamount.getText().toString().trim());
                    socialamount=Integer.valueOf(socialamt.getText().toString().trim());
                    getbudgetotamount=Integer.valueOf(budgettotamount.getText().toString().trim());
                    totamount=healthamount+foodamount+transamount+educationamount+billsamount+savingsamount+beautycareamount+socialamount;
                    if(getbudgetotamount<totamount)
                    {
                        Snackbar.make(v, "Total BudgetAmount exceeds the Value Entered in the fields", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }
                    else if(getbudgetotamount>totamount)
                    {
                        Snackbar.make(v,"Total BudgetAmount less than the Value Entered in the fields",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }
                    else if(getbudgetotamount==totamount)
                    {
                        dataBaseHelper.updatenewbudget(getbudgetname.trim(),username,totamount,getmonth.trim(),healthamount,educationamount,savingsamount,billsamount,transamount,foodamount,socialamount,beautycareamount);
                        Snackbar.make(v,"Your Budget has been Updated",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();
                    }

                }
            }
        });
    return v;}

    public boolean validate(final EditText edittext,String str) {
        boolean flag = true;
        if (edittext.getText().toString().trim().equalsIgnoreCase(""))
        {edittext.setError("Enter " + str, erroricon);
            flag = false;}
        else
            flag=true;
        return flag;
    }

}

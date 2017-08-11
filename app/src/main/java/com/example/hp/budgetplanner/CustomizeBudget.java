package com.example.hp.budgetplanner;


import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomizeBudget extends Fragment {
public static String budgetname;
    public static String budgetamount;
    int getbudgetamount;
    SharedPreferences shared;
    DataBaseHelper dataBaseHelper;
    public static  String month;
    String username;
    Drawable erroricon;
    int healthamt,educamt,savingamt,totamt,billamt,socialamt,transamt,foodamt,clothbeauamt;
    EditText healthamount,educationamount,savingsamount,billsamount,totalamount,socialamount,foodamount,clothbeautyamount,transportationamount;
    public CustomizeBudget() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getActivity(),budgetname+ budgetamount+month ,Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_customize_budget, container, false);
        //GET THE VALUES OF THE AMOUNT ENTERED WRT TO 8 CATEGORIES
        erroricon=getResources().getDrawable(R.drawable.error);
        dataBaseHelper=new DataBaseHelper(getContext());
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        healthamount=(EditText)v.findViewById(R.id.healthcare);
        shared= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=shared.getString(LoggedInMainActivity.USERNAME, "");
        //Toast.makeText(getActivity(),username+ budgetname+budgetamount+month,Toast.LENGTH_LONG).show();
        educationamount=(EditText)v.findViewById(R.id.education);
        transportationamount=(EditText)v.findViewById(R.id.transportation);
        foodamount=(EditText)v.findViewById(R.id.food);
        billsamount=(EditText)v.findViewById(R.id.bills);
        savingsamount=(EditText)v.findViewById(R.id.savings);
        socialamount=(EditText)v.findViewById(R.id.social);
        clothbeautyamount=(EditText)v.findViewById(R.id.clothbeauty);
        Button but=(Button)v.findViewById(R.id.createbudget);
        but.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                boolean flag1,flag2,flag3,flag4,flag5,flag6,flag7,flag8;
                flag1=validate(billsamount,"Bill Amount");
                flag2=validate(foodamount,"Food Amount");
                flag3=validate(transportationamount,"Transportation Amount");
                flag4=validate(educationamount,"Education Amount");
                flag5=validate(clothbeautyamount,"Clothing/BeautyCare Amount");
                flag6=validate(savingsamount,"Savings Amount");
                flag7=validate(socialamount,"Social Amount");
                flag8=validate(healthamount,"Healthcare Amount");
                if(flag1==true && flag2==true && flag3==true && flag4==true && flag5==true && flag6==true && flag7==true && flag7==true && flag8==true)
                {
                    healthamt=Integer.valueOf(healthamount.getText().toString().trim());
                    foodamt=Integer.valueOf(foodamount.getText().toString().trim());
                    transamt=Integer.valueOf(transportationamount.getText().toString().trim());
                    educamt=Integer.valueOf(educationamount.getText().toString().trim());
                    billamt=Integer.valueOf(billsamount.getText().toString().trim());
                    savingamt=Integer.valueOf(savingsamount.getText().toString().trim());
                    clothbeauamt=Integer.valueOf(clothbeautyamount.getText().toString().trim());
                    socialamt=Integer.valueOf(socialamount.getText().toString().trim());
                    totamt=healthamt+foodamt+transamt+educamt+billamt+savingamt+clothbeauamt+socialamt;
                    getbudgetamount=Integer.valueOf(budgetamount);
                    if(getbudgetamount<totamt)
                    {
                        Snackbar.make(v,"Total BudgetAmount exceeds the Value Entered in the fields",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }
                    else if(getbudgetamount>totamt)
                    {
                        Snackbar.make(v,"Total BudgetAmount less than the Value Entered in the fields",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }
                    else if(getbudgetamount==totamt)
                    {
                        dataBaseHelper.insertnewbudget(budgetname.trim(),username,totamt,month,healthamt,educamt,savingamt,billamt,transamt,foodamt,socialamt,savingamt);
                        int counter=dataBaseHelper.getbudgetname(username,budgetname.trim());
                        if(counter==1)
                        {
                            Snackbar.make(v,"Your Budget has been created successfully",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }
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

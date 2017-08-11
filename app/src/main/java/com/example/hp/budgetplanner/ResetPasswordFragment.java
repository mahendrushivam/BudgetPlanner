package com.example.hp.budgetplanner;


import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {

EditText currentpassword,newpassword,reenterpassword;
    //String getcurrentpassword,getnewpassword,getconfirmpassword;
    ImageButton button;
    DataBaseHelper dataBaseHelper;
    SharedPreferences shared;
    Drawable erroricon;
    String username;
    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_reset_password, container, false);
        erroricon=getResources().getDrawable(R.drawable.error);
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        currentpassword=(EditText)v.findViewById(R.id.currentpassword);
        newpassword=(EditText)v.findViewById(R.id.newpassword);
        reenterpassword=(EditText)v.findViewById(R.id.confirmpassword);
        button=(ImageButton)v.findViewById(R.id.locksubmit);
        Context context=getContext();
        shared= PreferenceManager.getDefaultSharedPreferences(context);
        username=shared.getString(LoggedInMainActivity.USERNAME, null);
        dataBaseHelper=new DataBaseHelper(getActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag1,flag2,flag3;
                flag1=validate(currentpassword,"CurrentPassword");
                flag2=validate(newpassword,"NewPassword");
                flag3=validate(reenterpassword,"ConfirmPassword");
                if(flag1==true && flag2==true && flag3==true)
                {
                    dataBaseHelper.updatepassword(newpassword.getText().toString().trim(),username.trim());
                    Snackbar.make(v, "Password changed Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
    return v;
    }
    //VALIDATION FUNCTION FOR THE ACTIVITY FIELDS
    public boolean validate(final EditText edittext,String str)
    {  boolean flag=true;
        if(edittext.getText().toString().trim().equalsIgnoreCase("")){
            edittext.setError("Enter "+str,erroricon);
            flag=false;
        }

        else if(str.equals("CurrentPassword"))
        {
            boolean flag2=false;

            flag2=dataBaseHelper.getuserpassword(username,currentpassword.getText().toString().trim());
            if(flag2)
            {
                flag=true;
            }
            else
            {edittext.setError("Entered Password incorrect",erroricon);
                flag=false;}

        }




        else if(str.equals("ConfirmPassword"))
        {
            String str1,str2;
            str1=edittext.getText().toString().trim();
            str2=newpassword.getText().toString().trim();
            if(str1.equals(str2))
            {
                flag=true;}
            else
            {
                edittext.setError("Entered Password doesn't matches",erroricon);
                newpassword.setError("Entered Password doesn't matches",erroricon);
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

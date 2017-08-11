package com.example.hp.budgetplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ViewDetailsFragment extends Fragment {
DataBaseHelper dataBaseHelper;
    SharedPreferences shared;
   // SharedPreferences.Editor editor;
    String username;
    String firstname,lastname,profession,email;
    EditText getfirstname,getlastname,getprofession,getemailid;
    Button edit,save;
    Drawable erroricon;

    public ViewDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_view_details, container, false);
        Context context=getContext();
        erroricon=getResources().getDrawable(R.drawable.error);
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        shared= PreferenceManager.getDefaultSharedPreferences(context);
        username=shared.getString(LoggedInMainActivity.USERNAME, null);
       // Toast.makeText(getActivity(),username,Toast.LENGTH_LONG).show();
       getfirstname=(EditText)v.findViewById(R.id.firstname);
       getlastname=(EditText)v.findViewById(R.id.lastname);
        getprofession=(EditText)v.findViewById(R.id.profession);
        getemailid=(EditText)v.findViewById(R.id.email);
        edit=(Button)v.findViewById(R.id.edit);
        save=(Button)v.findViewById(R.id.save);
        dataBaseHelper=new DataBaseHelper(getActivity());
        //  Cursor RETURNS THE VALUE FOR THE REQUIRED FIELDS
        Cursor cursor=dataBaseHelper.getuserdetails(username.trim());

        int count=cursor.getCount();
        cursor.moveToFirst();
        if(count==1)
        {
            firstname=cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            lastname=cursor.getString(cursor.getColumnIndex("LASTNAME"));
            profession=cursor.getString(cursor.getColumnIndex("PROFESSION"));
            email=cursor.getString(cursor.getColumnIndex("EMAILID"));
        }
        //SET THE FIELDS WITH THEIR REQUIRED DETAILS
        getfirstname.setText(firstname.trim());
        getlastname.setText(lastname.trim());
        getemailid.setText(email);
        getprofession.setText(profession.trim());
        // EDIT DETAILS SECTION BUTTON FUNCTIONING
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getfirstname.setClickable(true);
                getfirstname.setFocusableInTouchMode(true);
                getfirstname.setFocusable(true);
                getlastname.setClickable(true);
                getlastname.setFocusableInTouchMode(true);
                getlastname.setFocusable(true);
                getprofession.setClickable(true);
                getprofession.setFocusableInTouchMode(true);
                getprofession.setFocusable(true);
                getemailid.setClickable(true);
                getemailid.setFocusableInTouchMode(true);
                getemailid.setFocusable(true);


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag1,flag2,flag3,flag4;
                //save.setClipToOutline(true);
                flag1=validate(getfirstname,"Firstname");
                flag2=validate(getlastname,"Lastname");
                flag3=validate(getprofession,"Profession");
                flag4=validate(getemailid,"Email");
                if(flag1==true && flag2==true && flag3==true && flag4==true)
                {
                    dataBaseHelper.upgradeuser(getfirstname.getText().toString().trim(),getlastname.getText().toString().trim(),getprofession.getText().toString().trim(),getemailid.getText().toString().trim(),username.trim());
                    Snackbar.make(v,"Data Update Successfully",Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }
            }
        });
   return v;
    }


    public boolean validate(final EditText edittext,String str) {
        boolean flag = true;
        if (edittext.getText().toString().trim().equalsIgnoreCase("")) {
            edittext.setError("Enter " + str, erroricon);
            flag = false;
        } else if (str.equals("Firstname") || str.equals("Lastname") || str.equals("Profession")) {
            boolean flag1 = isValidateusername(edittext.getText().toString().trim());
            if (flag1) {
                flag = true;
            } else {
                edittext.setError("Entered " + str + " should contain only alphabets", erroricon);
                flag = false;
            }
        } else if (str.equals("Email")) {
            boolean flag2 = isValidEmailAddress(edittext.getText().toString().trim());
            if (flag2) {
                flag = true;
            } else {
                edittext.setError("Entered " + str + " has a wrong format", erroricon);
                flag = false;
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



    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean isValidateusername(String email) {
        String ePattern = "^[\\p{L} .'-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
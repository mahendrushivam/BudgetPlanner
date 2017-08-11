package com.example.hp.budgetplanner;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMainActivity extends AppCompatActivity {
    EditText firstname,lastname,email,password,confirmpassword,username,profession;
    String getfirstname,getlastname,getemail,getprofession,getpassword,getconfirmpassword;
    Drawable erroricon;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        erroricon=getResources().getDrawable(R.drawable.error);
        erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));
        firstname=(EditText)findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.emailid);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        username=(EditText)findViewById(R.id.username);
        profession=(EditText)findViewById(R.id.profession);
        dataBaseHelper=new DataBaseHelper(this);
        getfirstname=firstname.getText().toString().trim();
        getlastname=lastname.getText().toString().trim();
        getemail=email.getText().toString().trim();
        getprofession=profession.getText().toString().trim();
        getpassword=password.getText().toString().trim();
        getconfirmpassword=confirmpassword.getText().toString().trim();

    }

    public void submit(View v)
    {  boolean flag1,flag2,flag3,flag4,flag5,flag6,flag7;
        flag1=validate(firstname,"Firstname");
        flag2=validate(lastname, "Lastname");
        flag3=validate(username, "Username");
        flag4=validate(email, "Email");
        flag5=validate(password, "Pasword");
        flag6=validate(confirmpassword, "ConfirmPassword");
        flag7=validate(profession, "Profession");
        if(flag1==true && flag2==true && flag3==true && flag4==true && flag5==true && flag6==true && flag7==true)
        {

            dataBaseHelper.insertnewuser(firstname.getText().toString().trim(),lastname.getText().toString().trim(),username.getText().toString().trim(),email.getText().toString().trim(),password.getText().toString().trim(),profession.getText().toString().trim());
            int checkuser=dataBaseHelper.checkuser(username.getText().toString().trim());
            if(checkuser==1)
            {
                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
            }

        }

    }
        public boolean validate(final EditText edittext,String str)
        {  boolean flag=true;
            if(edittext.getText().toString().trim().equalsIgnoreCase("")){
                edittext.setError("Enter "+str,erroricon);
                flag=false;
            }

            else if(str.equals("Firstname") || str.equals("Lastname") )
            {
                boolean flag1=isValidateusername(edittext.getText().toString().trim());
                if(flag1)
                {
                    flag=true;
                }
                else
                {
                    edittext.setError("Entered "+str +" should contain only alphabets",erroricon);
                    flag=false;
                }
            }
            else if(str.equals("Username"))
            {
                boolean flag5=isValidateusername(edittext.getText().toString().trim());
                if(flag5)
                {
                    flag=true;
                }
                else
                {
                    edittext.setError("Entered "+str +" should contain only alphabets",erroricon);
                    flag=false;
                }

                boolean flag4=isitnewuser(edittext.getText().toString().trim());
                if(flag4)
                {
                    flag=true;
                }
                else
                {
                    edittext.setError("Entered "+str +" already exists",erroricon);
                    flag=false;
                }

            }

            else if(str.equals("Email"))
            {
                boolean flag2=isValidEmailAddress(edittext.getText().toString().trim());
                if(flag2)
                {
                    flag=true;
                }
                else
                {
                    edittext.setError("Entered "+str +" has a wrong format",erroricon);
                    flag=false;
                }
            }
            else if(str.equals("ConfirmPassword"))
            {
                String str1,str2;
                str1=edittext.getText().toString().trim();
                str2=password.getText().toString().trim();
                if(str1.equals(str2))
                {
                    flag=true;}
                else
                {
                        edittext.setError("Entered Password doesn't matches",erroricon);
                        password.setError("Entered Password doesn't matches",erroricon);
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

    public boolean isitnewuser(String username)
    {boolean flag=false;
        int counter=dataBaseHelper.checkuser(username);
         if(counter==1)
             flag=false;
         else if(counter==0)
         flag=true;

        return flag;
    }
}

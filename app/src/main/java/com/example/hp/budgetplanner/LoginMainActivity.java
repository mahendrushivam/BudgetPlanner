package com.example.hp.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginMainActivity extends AppCompatActivity {
        int count;
    boolean flag1,flag2;
    String email,password;
    EditText getusername,getpassword;
    Button signup,signin;
    DataBaseHelper dataBaseHelper;
    Drawable erroricon;
    SharedPreferences shared;
    String username1;
    SharedPreferences.Editor editor;
    public static final String POSITION="POSITION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        Context context = getApplicationContext();
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        username1 = shared.getString(LoggedInMainActivity.USERNAME,"").trim();
        getusername = (EditText) findViewById(R.id.username);

        dataBaseHelper=new DataBaseHelper(this);
        if(username1.equals(null) || username1.equals(""))
        {
            erroricon = getResources().getDrawable(R.drawable.error);
            erroricon.setBounds(new Rect(0, 0, erroricon.getIntrinsicWidth(), erroricon.getIntrinsicHeight()));

            getpassword = (EditText) findViewById(R.id.password);
            signin = (Button) findViewById(R.id.signin);
            signup = (Button) findViewById(R.id.signup);}
            else
            {
                Intent intent = new Intent(LoginMainActivity.this, LoggedInMainActivity.class);
                intent.putExtra("USERNAMEDISPLAY",username1.trim());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    /*@Override
    protected void onFinish() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
        super.onResume();
    }*/


    public void signinbut(View v)

    {
        flag1=validate(getusername,"Username");
        flag2=validate(getpassword,"Password");
            if(flag1==true && flag2==true)
            {


                Context context = getApplicationContext();

                Intent intent=new Intent(LoginMainActivity.this,LoggedInMainActivity.class);
                intent.putExtra("USERNAMEDISPLAY", getusername.getText().toString().trim());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

    }

    public boolean validate(final EditText edittext,String str)
    {  boolean flag=true;
        if(edittext.getText().toString().trim().equalsIgnoreCase("")){
            edittext.setError("Enter "+str,erroricon);
            flag=false;
        }

        else if(str.equals("Username"))
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

            boolean flag3=isitexistinguser(edittext.getText().toString().trim());
            if(flag3)
            {
                flag=true;
            }
            else
            {
                edittext.setError("Entered "+str +" doesn't exists",erroricon);
                flag=false;
            }
        }

        else if(str.equals("Password"))
        {
            boolean flag2=dataBaseHelper.getuserpassword(getusername.getText().toString().trim(),edittext.getText().toString().trim());
                if(flag2)
                {
                    flag=true;
                }
            else
                {
                    flag=false;
                    edittext.setError("Entered Password incorrect",erroricon);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
    public void signupbut(View v )
    {
        Intent intent=new Intent(LoginMainActivity.this,RegisterMainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Context context = getApplicationContext();
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        count=1;


        editor=shared.edit();
        editor.putInt(POSITION,count);
        editor.commit();

    }

    public boolean isValidateusername(String email) {
        String ePattern = "^[\\p{L} .'-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public boolean isitexistinguser(String username)
    {boolean flagvalue=false;

        int count=dataBaseHelper.checkuser(username);
        if(count==1)
            flagvalue=true;
        else
        flagvalue=false;

        return flagvalue;
    }
}

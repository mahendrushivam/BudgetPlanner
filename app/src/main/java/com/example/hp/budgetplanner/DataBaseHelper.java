package com.example.hp.budgetplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class DataBaseHelper extends SQLiteOpenHelper  {



    public final static int DATABASE_VERSION=1;
    public final static String DATABASE_NAME="PracticeCategory5";

    DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql3="CREATE TABLE IF NOT EXISTS LOGINTABLE (ID INTEGER AUTO_INCREMENT,FIRSTNAME VARCHAR(30),LASTNAME VARCHAR(30),PROFESSION VARCHAR(35),EMAILID VARCHAR(50),USERNAME VARCHAR(30) NOT NULL,PASSWORD VARCHAR(30) NOT NULL ,PRIMARY KEY(ID)) ";
        db.execSQL(sql3);
        String sql="CREATE TABLE IF NOT EXISTS CATEGORYLIST (ID INTEGER AUTO_INCREMENT,CATEGORYNAME VARCHAR(50) ,CATEGORYIMAGE INT,PRIMARY KEY(ID,CATEGORYNAME))";
        db.execSQL(sql);
        String sql2="CREATE TABLE IF NOT EXISTS SUBCATEGORYLIST(ID INTEGER AUTO_INCREMENT,CATEGORYNAME VARCHAR(50),SUBCATEGORYNAME VARCHAR(50) NOT NULL,PRIMARY KEY(ID),FOREIGN KEY (CATEGORYNAME) REFERENCES CATEGORYLIST(CATEGORYNAME))";
        db.execSQL(sql2);
        String sql5="CREATE TABLE IF NOT EXISTS BUDGETNAMELIST (ID INTEGER AUTO_INCREMENT,USERNAME VARCHAR(30) NOT NULL,MONTH VARCHAR(20),BUDGETNAME VARCHAR(50),BUDGETTOTAMOUNT INT NOT NULL ,PRIMARY KEY(ID)" +
                "FOREIGN KEY (USERNAME) REFERENCES LOGINTABLE(USERNAME))";
        db.execSQL(sql5);
         String sql4="CREATE TABLE IF NOT EXISTS CATEGORYBUDGETLIST (ID INTEGER AUTO_INCREMENT ,BUDGETNAME VARCHAR(50),USERNAME VARCHAR(30) NOT NULL,MONTH VARCHAR(20),CATEGORYNAME VARCHAR(50),CATEGORYTOTAMT INT DEFAULT 0,CATEGORYSPENTAMT INT DEFAULT 0 ,CATEGORYLEFTAMT INT DEFAULT 0 ,CATEGORYIMAGE INT ,PRIMARY KEY(ID,BUDGETNAME)," +
                "FOREIGN KEY (CATEGORYNAME) REFERENCES CATEGORYLIST(CATEGORYNAME)," +
                "FOREIGN KEY (USERNAME) REFERENCES LOGINTABLE(USERNAME)" +
                 "FOREIGN KEY (BUDGETNAME) REFERENCES BUDGETNAMELIST(BUDGETNAME))";
            db.execSQL(sql4);
        String sql6="CREATE TABLE IF NOT EXISTS EXPENSESLIST (ID INTEGER AUTO_INCREMENT, EXPENSENAME VARCHAR(50),EXPENSEAMOUNT INT DEFAULT 0 ,CATEGORYNAME VARCHAR(50),SUBCATEGORYNAME VARCHAR(50),USERNAME VARCHAR(30) NOT NULL,EXPENSEDETAILS VARCHAR(200),EXPENSEDATE VARCHAR(50),BUDGETNAME VARCHAR(50),PRIMARY KEY(ID)," +
                "FOREIGN KEY (CATEGORYNAME) REFERENCES CATEGORYLIST(CATEGORYNAME)," +
                " FOREIGN KEY (USERNAME) REFERENCES LOGINTABLE(USERNAME), " +
                "FOREIGN KEY(BUDGETNAME) REFERENCES BUDGETNAMELIST(BUDGETNAME))";
        db.execSQL(sql6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertnewbudget(String budgetname,String username,int budgettotamount,String month,int healthamount,int educationamount,int savingsamount,int billsamount,int transportationamount,int foodamount,int socialamount,int beautycare)

    {
            SQLiteDatabase db=this.getWritableDatabase();
            String sql;
        sql="INSERT INTO BUDGETNAMELIST(BUDGETNAME,USERNAME,BUDGETTOTAMOUNT,MONTH) VALUES ('"+budgetname+"','"+username+"',"+budgettotamount+",'"+month+"');";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Food',"+foodamount+",0,"+foodamount+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Healthcare',"+healthamount+",1,"+healthamount+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Education',"+educationamount+",2,"+educationamount+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Transportation',"+transportationamount+",3,"+transportationamount+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Clothing/BeautyCare',"+beautycare+",4,"+beautycare+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Social',"+socialamount+",5,"+socialamount+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Savings',"+savingsamount+",6,"+savingsamount+");";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYBUDGETLIST (BUDGETNAME,USERNAME,MONTH,CATEGORYNAME,CATEGORYTOTAMT,CATEGORYIMAGE,CATEGORYLEFTAMT) VALUES ('"+budgetname+"','"+username+"','"+month+"','Bills',"+billsamount+",7,"+billsamount+");";
        db.execSQL(sql);
        db.close();
    }

    public void updatenewbudget(String budgetname,String username,int budgettotamount,String month,int healthamount,int educationamount,int savingsamount,int billsamount,int transportationamount,int foodamount,int socialamount,int beautycare)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        String sql="UPDATE BUDGETNAMELIST SET BUDGETTOTAMOUNT="+budgettotamount+" WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+healthamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Healthcare';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+educationamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Education';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+savingsamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Savings';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+foodamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Food';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+socialamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Social';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+billsamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Bills';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+transportationamount+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Transportation';";
        db.execSQL(sql);
        sql="UPDATE CATEGORYBUDGETLIST SET CATEGORYTOTAMT="+beautycare+"  WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"' AND CATEGORYNAME='Clothing/BeautyCare';";
        db.execSQL(sql);
            }

    public int  getbudgetname(String username,String budgetname)
    {   //boolean flag=false;
        int count=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM BUDGETNAMELIST WHERE USERNAME='"+username.trim()+"'AND BUDGETNAME='"+budgetname+"';";
        Cursor cursor=db.rawQuery(sql,null);
        count=cursor.getCount();
        db.close();
        return count;
    }


    public void insertnewuser(String firstname,String lastname,String username,String email,String password,String profession)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="INSERT INTO LOGINTABLE(FIRSTNAME,LASTNAME,PROFESSION,EMAILID,USERNAME,PASSWORD) VALUES ('"+firstname+"','"+lastname.trim()+"','"+ profession +"','"+email+"','"+username+ "','"+ password +"')";
        db.execSQL(sql);
        db.close();

    }

    public int checkuser(String username)
    {   int count=0;
            SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT USERNAME FROM LOGINTABLE WHERE USERNAME='"+username.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        count=cursor.getCount();
        db.close();

        return count;
    }

    public ArrayList<BudgetsNameList> getallbudgets(String username)
    {
        ArrayList<BudgetsNameList> array=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String budname,budmonth;
        int budamount=0;
        String sql="SELECT * FROM BUDGETNAMELIST WHERE USERNAME='"+username.trim()+"' ORDER BY(BUDGETNAME);";
        Cursor cursor=db.rawQuery(sql,null);
        int count=cursor.getCount();
        cursor.moveToFirst();
        while(count>0)
        {

            BudgetsNameList budget=new BudgetsNameList();
            budname=cursor.getString(cursor.getColumnIndex("BUDGETNAME"));
            budmonth=cursor.getString(cursor.getColumnIndex("MONTH"));
            budamount=cursor.getInt(cursor.getColumnIndex("BUDGETTOTAMOUNT"));
            budget.setbudgetamount(budamount);
            budget.setBudgetmonth(budmonth.trim());
            budget.setbudgetname(budname);
            array.add(budget);
            cursor.moveToNext();
            count--;
        }
        return array;

    }

    public void deletebudget(String username,String budgetname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql;
        sql="DELETE FROM EXPENSESLIST WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        sql="DELETE FROM CATEGORYBUDGETLIST WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username+"';";
        db.execSQL(sql);
        sql="DELETE FROM BUDGETNAMELIST WHERE BUDGETNAME='"+budgetname.trim()+"' AND USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<BudgetsNameList> getallbudgetsmonth(String username,String month)
    {
        ArrayList<BudgetsNameList> array=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String budname,budmonth;
        int budamount=0;
        String sql="SELECT * FROM BUDGETNAMELIST WHERE USERNAME='"+username.trim()+"'AND MONTH='"+month+"' ORDER BY(BUDGETNAME);";
        Cursor cursor=db.rawQuery(sql, null);
        int count=cursor.getCount();
        cursor.moveToFirst();
        while(count>0)
        {

            BudgetsNameList budget=new BudgetsNameList();
            budname=cursor.getString(cursor.getColumnIndex("BUDGETNAME"));
            budmonth=cursor.getString(cursor.getColumnIndex("MONTH"));
            budamount=cursor.getInt(cursor.getColumnIndex("BUDGETTOTAMOUNT"));
            budget.setbudgetamount(budamount);
            budget.setBudgetmonth(budmonth.trim());
            budget.setbudgetname(budname);
            array.add(budget);
            cursor.moveToNext();
            count--;
        }
        db.close();
        return array;

    }

    public int getbudgettotamount(String username,String budgetname)
    {
        Cursor cursor;
        int count=0;
        int budgettotamount=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM BUDGETNAMELIST WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"';";
        cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        count=cursor.getCount();
        if(count==1)
        {
           budgettotamount=cursor.getInt(cursor.getColumnIndex("BUDGETTOTAMOUNT"));
        }

        return budgettotamount;

    }


    public ArrayList<BudgetReportCategory> getcategorywisedetails(String month,String budgetname,String username)
    {
        ArrayList<BudgetReportCategory> array=new ArrayList<>();
        String sql;
        Cursor cursor;
        String getbudgetname,getbudgetmonth,getcategoryname;
        int cattotamount,catleftamount,catspentamount,catposition;
        int count=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String []category={"Food","Healthcare","Education","Transportation","Clothing/BeautyCare","Social","Savings","Bills"};
        for(int i=0;i<8;i++)
        {
            sql="SELECT * FROM CATEGORYBUDGETLIST WHERE CATEGORYNAME='"+category[i].trim()+"' AND USERNAME='"+username.trim()+"' AND MONTH='"+month.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"';";
            cursor=db.rawQuery(sql,null);
            count=cursor.getCount();
            cursor.moveToFirst();
            if(count==1)
            {
                BudgetReportCategory budgetreport=new BudgetReportCategory();
                getbudgetname=cursor.getString(cursor.getColumnIndex("BUDGETNAME"));
                getbudgetmonth=cursor.getString(cursor.getColumnIndex("MONTH"));
                getcategoryname=cursor.getString(cursor.getColumnIndex("CATEGORYNAME"));
                cattotamount=cursor.getInt(cursor.getColumnIndex("CATEGORYTOTAMT"));
                catleftamount=cursor.getInt(cursor.getColumnIndex("CATEGORYLEFTAMT"));
                catspentamount=cursor.getInt(cursor.getColumnIndex("CATEGORYSPENTAMT"));
                catposition=cursor.getInt(cursor.getColumnIndex("CATEGORYIMAGE"));
                budgetreport.setbudgetname(getbudgetname);
                budgetreport.setbudgetmonth(getbudgetmonth);
                budgetreport.setCategoryname(getcategoryname);
                budgetreport.setImageposition(catposition);
                budgetreport.setTotcatamount(cattotamount);
                budgetreport.setSpentamount(catspentamount);
                budgetreport.setLeftcatamount(catleftamount);
                array.add(budgetreport);
            }
        }
            db.close();
        return array;
    }


    public int getbudgetreportdetails(String username,String budgetname,String categoryname)
    {
        Cursor cursor;
        int amount=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT CATEGORYTOTAMT FROM CATEGORYBUDGETLIST WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname+"' AND CATEGORYNAME='"+categoryname.trim()+"';";
        cursor=db.rawQuery(sql,null);
      int count=cursor.getCount();
      cursor.moveToFirst();
        if(count==1)
        {
            amount=cursor.getInt(cursor.getColumnIndex("CATEGORYTOTAMT"));
        }
        db.close();
        return amount;
    }

    public void insertnewitem(String username,String budgetname,String categoryname,String subcategoryname,String expensename,String expensedetails,String expensedate,int expenseamount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="INSERT INTO EXPENSESLIST (USERNAME,BUDGETNAME,CATEGORYNAME,SUBCATEGORYNAME,EXPENSENAME,EXPENSEAMOUNT,EXPENSEDATE,EXPENSEDETAILS) VALUES('"+username.trim()+"','"+budgetname.trim()+"','"+categoryname.trim()+"','"+subcategoryname.trim()+"','"+expensename.trim()+"',"+expenseamount+",'"+expensedate.trim()+"','"+expensedetails.trim()+"')";
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<String> getallbudgetnames(String username)
    {
        ArrayList<String> arrayname=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        String budgetname;
        String sql="SELECT * FROM BUDGETNAMELIST WHERE USERNAME='"+username.trim()+"';";
        Cursor cursor=db.rawQuery(sql, null);
        int count=cursor.getCount();
        cursor.moveToFirst();
        while(count>0)
        {
            budgetname=cursor.getString(cursor.getColumnIndex("BUDGETNAME"));
            arrayname.add(budgetname);
            cursor.moveToNext();
            count--;

        }

        return arrayname;
    }

    public String getbudgetmonth(String username,String budgetname)
    {
        String month="";
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM BUDGETNAMELIST WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        int count=cursor.getCount();
        cursor.moveToFirst();
        if(count==1)
        {
            month=cursor.getString(cursor.getColumnIndex("MONTH"));

        }
        month.trim();
        return month;


    }

    public boolean checkleftamount(String username,String budgetname,int expenseamount,String categoryname)
    {   boolean flag=false;
        SQLiteDatabase db=this.getReadableDatabase();
        int leftamount=0,spentamount=0,totalamount=0;
        String sql="SELECT * FROM CATEGORYBUDGETLIST WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname+"' AND CATEGORYNAME='"+categoryname+"';";
        Cursor cursor=db.rawQuery(sql, null);
        int count=cursor.getCount();
        cursor.moveToFirst();
        if(count==1)
        {
            totalamount=cursor.getInt(cursor.getColumnIndex("CATEGORYTOTAMT"));
            //leftamount=cursor.getInt(cursor.getColumnIndex("CATEGORYLEFTAMT"));
            spentamount=cursor.getInt(cursor.getColumnIndex("CATEGORYSPENTAMT"));
            //leftamount=cursor.getInt()
        }
        else
        {return false;}

        db.close();
        leftamount=totalamount-spentamount;


        if(leftamount>=expenseamount)
        {spentamount=(spentamount+expenseamount);
            leftamount=totalamount-spentamount;
            db=this.getWritableDatabase();
            String sql2="UPDATE CATEGORYBUDGETLIST SET CATEGORYLEFTAMT="+leftamount+" WHERE USERNAME='"+username.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"' AND BUDGETNAME='"+budgetname+"';";
            db.execSQL(sql2);
            String sql3="UPDATE CATEGORYBUDGETLIST SET CATEGORYSPENTAMT="+spentamount+" WHERE USERNAME='"+username.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"' AND BUDGETNAME='"+budgetname+"';";
            db.execSQL(sql3);
            db.close();
            flag=true;
        }
        else if(leftamount<=spentamount)
        {
            flag=false;
        }


    return flag;}

    public int getbudgetmonthcount(String username,String month) {
        ArrayList<BudgetsNameList> array = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM BUDGETNAMELIST WHERE USERNAME='" + username.trim() + "' AND MONTH='" + month + "' ORDER BY(BUDGETNAME);";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        return count;
    }

    public boolean checkcategoryexist(String categoryname)
    {
        boolean flag=false;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM CATEGORYLIST WHERE CATEGORYNAME='"+categoryname.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        int count=cursor.getCount();
        if(count>0)
        {
            flag=true;

        }
        else
        flag=false;
        db.close();
        return flag;
    }

    public boolean checksubcategoryexist(String categoryname,String subcategoryname)
    {
        boolean flag=false;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM SUBCATEGORYLIST WHERE CATEGORYNAME='"+categoryname.trim()+"' AND SUBCATEGORYNAME='"+subcategoryname.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        int count=cursor.getCount();
        if(count>0)
        {
            flag=true;

        }
        else
            flag=false;
        db.close();
        return flag;
    }

    public boolean checkeventexists(String username,String categoryname,String budgetname,String expensename)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        boolean flag;
        String sql="SELECT * FROM EXPENSESLIST WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"' AND EXPENSENAME='"+expensename.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        int count=cursor.getCount();
        if(count==1)
        {
            flag=true;
            //Toast.makeText(getActivity())
        }
        else
        flag=false;
        db.close();
        return  flag;
    }

    public ArrayList<ExpenseDetails> getexpensedetailsall(String username,String budgetname,String categoryname)
    {
        ArrayList<ExpenseDetails> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM EXPENSESLIST WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        int count=cursor.getCount();
        cursor.moveToFirst();
        while(count>0)
        {
            ExpenseDetails expenseDetails=new ExpenseDetails();
            String expensename=cursor.getString(cursor.getColumnIndex("EXPENSENAME"));
            String expensedate=cursor.getString(cursor.getColumnIndex("EXPENSEDATE"));
            String expensedetails=cursor.getString(cursor.getColumnIndex("EXPENSEDETAILS"));
            int expamount=cursor.getInt(cursor.getColumnIndex("EXPENSEAMOUNT"));
            String subcategory=cursor.getString(cursor.getColumnIndex("SUBCATEGORYNAME"));
            String categoryname1=cursor.getString(cursor.getColumnIndex("CATEGORYNAME"));
            String budgetname1=cursor.getString(cursor.getColumnIndex("BUDGETNAME"));
            expenseDetails.setbudgetname(budgetname1.trim());
            expenseDetails.setexpensecategory(categoryname1.trim());
            expenseDetails.setexpenseamount(expamount);
            expenseDetails.setexpensename(expensename.trim());
            expenseDetails.setexpensedate(expensedate.trim());
            expenseDetails.setexpensedetails(expensedetails.trim());
            expenseDetails.setexpensesubcategory(subcategory.trim());
            arrayList.add(expenseDetails);
            cursor.moveToNext();
            count--;
        }
        db.close();
        return arrayList;
    }

    public void deleteexpensename(String username,String budgetname,String categoryname,String expensename,int expenseamount,int leftamount,int spentamount,String subcategoryname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        int lefamount=leftamount+expenseamount;
        int spenamount=spentamount-expenseamount;
        String sql1="UPDATE CATEGORYBUDGETLIST SET CATEGORYLEFTAMT="+lefamount+" WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"';";
        String sql2="UPDATE CATEGORYBUDGETLIST SET CATEGORYSPENTAMT="+spenamount+" WHERE USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"';";
        db.execSQL(sql1);
        db.execSQL(sql2);
        String sql3="DELETE FROM EXPENSESLIST WHERE EXPENSENAME='"+expensename.trim()+"' AND USERNAME='"+username.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"' AND SUBCATEGORYNAME='"+subcategoryname.trim()+"';";
        db.execSQL(sql3);
        db.close();
    }

    public int getleftamount(String username,String categoryname,String budgetname)
    {
        int leftamount=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM CATEGORYBUDGETLIST WHERE USERNAME='"+username.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        int count=cursor.getCount();
        if(count==1)
        {
            leftamount=cursor.getInt(cursor.getColumnIndex("CATEGORYLEFTAMT"));
        }
        return  leftamount;
    }

    public int getspentamount(String username,String categoryname,String budgetname)
    {
        int spentamount=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM CATEGORYBUDGETLIST WHERE USERNAME='"+username.trim()+"' AND CATEGORYNAME='"+categoryname.trim()+"' AND BUDGETNAME='"+budgetname.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        int count=cursor.getCount();
        if(count==1)
        {
            spentamount=cursor.getInt(cursor.getColumnIndex("CATEGORYSPENTAMT"));
        }
        return  spentamount;
    }

    public boolean getuserpassword(String username,String currentpassword)
    {   int count=0;
        boolean flag=false;
        String password="";
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT PASSWORD FROM LOGINTABLE WHERE USERNAME='"+username.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        currentpassword.trim();
        count=cursor.getCount();
        if(count==1)
        {
           password=cursor.getString(cursor.getColumnIndex("PASSWORD"));
            password.trim();
            if(password.equals(currentpassword.trim()))
            {
                flag=true;
            }
        }
        db.close();

     return flag;
    }

    public void upgradeuser(String firstname,String lastname,String profession,String emailid,String username)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="UPDATE LOGINTABLE SET FIRSTNAME='"+firstname+"'  WHERE USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        sql="UPDATE LOGINTABLE SET LASTNAME='"+lastname+"'  WHERE USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        sql="UPDATE LOGINTABLE SET PROFESSION='"+profession+"'  WHERE USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
         sql="UPDATE LOGINTABLE SET EMAILID='"+emailid+"'  WHERE USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        db.close();
    }

    public void updatepassword(String password,String username)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="UPDATE LOGINTABLE SET PASSWORD='"+password+"'  WHERE USERNAME='"+username.trim()+"';";
        db.execSQL(sql);
        db.close();
    }



    public Cursor getuserdetails(String username)
    {
      SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT FIRSTNAME,LASTNAME,PROFESSION,EMAILID FROM LOGINTABLE WHERE USERNAME='"+username.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);

        return cursor;
    }
    public void insertvalues()
    {    SQLiteDatabase db=this.getWritableDatabase();
        String sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Food',0)";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Healthcare',1)";
        db.execSQL(sql);
       sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Education',2)";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Transportation',3)";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Clothing/BeautyCare',4)";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Social',5)";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Savings',6)";
        db.execSQL(sql);
        sql="INSERT INTO CATEGORYLIST (CATEGORYNAME,CATEGORYIMAGE) VALUES ('Bills',7)";
        db.execSQL(sql);

        // Inserting Data Into the subCategories When You open for the first Time
             //Inserting Data into Food Category Items
        String sql1;
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Breakfast')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Lunch')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Dinner')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Mid-Night Snack')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Refreshment')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Fruit')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Food','Ingredients')";
        db.execSQL(sql1);


        //Inserting Data Into the Healthcare

        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Healthcare','Medical fee')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Healthcare','Drugs')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Healthcare','Medicines')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Healthcare','Physical Checkup')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Healthcare','Heath Insurance')";
        db.execSQL(sql1);

        //Inserting Data into Education
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Education','Stationary')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Education','Tutoring Fee')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Education','Tution')";
        db.execSQL(sql1);


        //INserting data into Transportation

        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Transportation','Bus')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Transportation','Car')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Transportation','Taxi')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Transportation','Airplane')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Transportation','Subway')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Transportation','High Speed Rail')";
        db.execSQL(sql1);

        //Inserting Data into Beauty/Salon CAREFields
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Haircut')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Accessories')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Bags')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Cosmetics')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Hair Spa')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Shirt')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Pants')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Jacket')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Clothing/BeautyCare','Shoes')";
        db.execSQL(sql1);


        // INSERTING DATA INTO SOCIAL

        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Gifts')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Wedding')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Movie')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Travelling')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Party')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Fitness Activity')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Shopping')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Social','Exhibition')";
        db.execSQL(sql1);

            // INSERTING DATA INTO SAVINGS

        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Savings','Fixed Deposit')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Savings','Insurance')";
        db.execSQL(sql1);
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Savings','Stock Investment')";
        db.execSQL(sql1);


        // INSERTING DATA INTO FEES FIELD
        sql1="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES('Bills','Electricity Bill')";
        db.execSQL(sql1);
        db.close();
    }


    public ArrayList<String> subcategory(String categoryname)
    {   SQLiteDatabase db=this.getReadableDatabase();
       ArrayList<String> array=new ArrayList<String>();
        String category=categoryname.trim();
        String sql="SELECT SUBCATEGORYNAME FROM SUBCATEGORYLIST WHERE CATEGORYNAME='"+category+"';";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.getCount()==0)
        {
            array.add("Add a new SubCategory");

        }
        else
        {
            int count=cursor.getCount();
            {cursor.moveToFirst();
                while(count>0)
                {
                    String str=cursor.getString(cursor.getColumnIndex("SUBCATEGORYNAME"));
                    array.add(str);
                    cursor.moveToNext();
                    count--;
                }
            }
        }

        db.close();
        return array;
    }


    public int categoryiconposition(String category)
    { int position;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT CATEGORYIMAGE FROM CATEGORYLIST WHERE CATEGORYNAME='" +category+"';";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.getCount()>0)
        {cursor.moveToFirst();
        position=cursor.getInt(cursor.getColumnIndex("CATEGORYIMAGE"));}

            else
        position=0;

        return position;
    }


    protected void addsubcategory(String category,String subcategory)
    {
       SQLiteDatabase db=this.getWritableDatabase();
        String sql;
        sql="INSERT INTO SUBCATEGORYLIST (CATEGORYNAME,SUBCATEGORYNAME) VALUES ('"+category+"','"+subcategory.trim()+"')";
        db.execSQL(sql);
db.close();
    }

    protected void removesubcategory(String category,String subcategory)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql;
        sql="DELETE FROM SUBCATEGORYLIST WHERE SUBCATEGORYNAME='"+subcategory.trim()+"' AND CATEGORYNAME='"+category.trim()+"';";
        db.execSQL(sql);
        db.close();
    }


}

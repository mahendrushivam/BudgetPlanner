package com.example.hp.budgetplanner;



/**
 * Created by hp on 18-07-2016.
 */
public class ExpenseDetails  {

    String expensename;
    String expensedate;
    int expenseamount;
    String expensesubcategory;
    String expensecategory;
    String expensebudgetname;
    String expensedetails;
    public void setexpensename(String expensename)
    {
        this.expensename=expensename;
    }
    public void setexpensedate(String date)
    {
        this.expensedate=date;
    }
    public void setexpenseamount(int amount)
    {
        this.expenseamount=amount;
    }
    public void setexpensedetails(String expensedetails)
    {
        this.expensedetails=expensedetails;
    }
    public void setexpensecategory(String categoryname)
    {
        this.expensecategory=categoryname;
    }
    public void setexpensesubcategory(String subcategory)
    {
        this.expensesubcategory=subcategory;
    }
    public void setbudgetname(String budgetname)
    {
        this.expensebudgetname=budgetname;
    }
    public String getexpensename()
    {
        return expensename;
    }
    public String getexpensedate()
    {
        return expensedate;
    }
    public String getexpensedetails()
    {
        return expensedate;
    }
    public int getexpenseamount()
    {
        return expenseamount;
    }
    public String getsubcategoryname()
    {
        return  expensesubcategory;
    }
}

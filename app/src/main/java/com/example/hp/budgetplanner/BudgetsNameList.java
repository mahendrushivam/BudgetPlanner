package com.example.hp.budgetplanner;

/**
 * Created by hp on 15-07-2016.
 */
public class BudgetsNameList {


    String budgetname;
    int budgetamount;
    String budgetmonth;

    public void setbudgetname(String budname) {
        budgetname = budname;
    }

    public void setbudgetamount(int amount) {
        budgetamount = amount;
    }

    public void setBudgetmonth(String budgetmonth) {
        this.budgetmonth = budgetmonth;
    }

    public String getbudgetname() {
        return budgetname;
    }

    public int getbudgetamount() {
        return budgetamount;
    }

    public String getbudgetmonth() {
        return budgetmonth;
    }
}

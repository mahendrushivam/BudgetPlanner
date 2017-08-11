package com.example.hp.budgetplanner;

/**
 * Created by hp on 16-07-2016.
 */
public class BudgetReportCategory
    {

    String budgetname;
        String budgetmonth;
    String categoryname;
    int totcatamount;
    int leftcatamount;
    int spentamount;
        int imageposition;
        public void setbudgetmonth(String budgetmonth)
        {
            this.budgetmonth=budgetmonth;
        }
    public void setbudgetname(String budgetname)
    {
        this.budgetname=budgetname;
    }
        public void setImageposition(int position)
        {
            this.imageposition=position;
        }

    public void setCategoryname(String categoryname)
    {
        this.categoryname=categoryname;
    }
    public void setTotcatamount(int totcatamount)
    {
        this.totcatamount=totcatamount;
    }
    public void setSpentamount(int spentamount)
    {
        this.spentamount=spentamount;
    }

    public void setLeftcatamount(int leftcatamount)
    {
        this.leftcatamount=leftcatamount;
    }

    public String getbudgetname()
    {
        return budgetname;
    }

    public String getCategoryname()
    {
        return categoryname;
    }

    public int getTotcatamount()
    {
        return totcatamount;
    }

    public int getLeftcatamount()
    {
        return leftcatamount;
    }

    public int getSpentamount()
    {
        return spentamount;
    }
        public String getbudgetmonth()
        {
            return budgetmonth;
        }
        public int getImageposition()
        {
            return imageposition;
        }

}

package com.example.hp.budgetplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ExpenseListFragment extends Fragment {
public static String categoryname,budgetname1;
    public static int leftamount,totamount,spentamount,catposition;
    TextView setcategoryname,setleftamount,setspentamount;
    ArrayList<ExpenseDetails> expense=null;
    ImageView catimage;
    int positiondel;
    ListView  expenselistview;
    ProgressBar progress;
    Integer [] imagesid={R.drawable.food2,R.drawable.medic2,R.drawable.education2,R.drawable.transportation2,R.drawable.beautysalon2,R.drawable.social2,R.drawable.savings2,R.drawable.fees2};

    SharedPreferences shared;
    String username;
    DataBaseHelper dataBaseHelper;
    public ExpenseListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_expense_list, container, false);
        dataBaseHelper=new DataBaseHelper(getContext());
        shared= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=shared.getString(LoggedInMainActivity.USERNAME, "");
    setcategoryname=(TextView)v.findViewById(R.id.expensecategory);
        setcategoryname.setText(categoryname.trim());
        progress=(ProgressBar)v.findViewById(R.id.progressexp);
        progress.setMax(totamount);
        catimage=(ImageView)v.findViewById(R.id.catexpimage);
        progress.setProgress(spentamount);
        setleftamount=(TextView)v.findViewById(R.id.expamountleft);
        setspentamount=(TextView)v.findViewById(R.id.expamountspent);
        setleftamount.setText(String.valueOf(leftamount));
        setspentamount.setText((String.valueOf(spentamount)));
        catimage.setImageResource(imagesid[catposition]);
        expense=dataBaseHelper.getexpensedetailsall(username.trim(),budgetname1.trim(),categoryname.trim());
        expenselistview=(ListView)v.findViewById(R.id.expenselist);
        expenselistview.setAdapter(new Expenselistadapter(getActivity(),R.layout.expensedetailslayout,R.id.expensename,expense));
        return v;
    }
    public class Expenselistadapter extends ArrayAdapter<ExpenseDetails> {
        public Expenselistadapter(Context cont, int row_item, int text1, ArrayList<ExpenseDetails> expensedetail) {
            super(cont, row_item, text1, expensedetail);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ViewHolder1 holder = (ViewHolder1) row.getTag();
            if (holder == null) {
                holder = new ViewHolder1(row);
                row.setTag(holder);
            }
            ExpenseDetails expdet=expense.get(position);
            String expname=expdet.getexpensename();
            String expdate=expdet.getexpensedate();
            int expamount=expdet.getexpenseamount();
            String subcatexp=expdet.getsubcategoryname();
            holder.exppopup.setTag(position);
            holder.expensename.setText(expname.trim());
            holder.expensedate.setText(expdate.trim());
            holder.expensesubcategory.setText(subcatexp.trim());
            String expeamount=String.valueOf(expamount);
            holder.expenseamount.setText(expeamount.trim());
            holder.exppopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiondel = (Integer) v.getTag();

                    PopupMenu popup = new PopupMenu(getActivity(), v);
                    popup.getMenuInflater().inflate(R.menu.expensemenu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            ExpenseDetails expdel = expense.get(positiondel);
                            if (id == R.id.expensedelete) {
                                int leftexp=expdel.getexpenseamount();
                                String expname=expdel.getexpensename();
                                String expsubcategory=expdel.getsubcategoryname();
                                int actleftamount=Integer.valueOf(setleftamount.getText().toString().trim());
                                int actspentamount=Integer.valueOf(setspentamount.getText().toString().trim());
                                dataBaseHelper.deleteexpensename(username.trim(), budgetname1.trim(), categoryname.trim(), expname.trim(), leftexp, actleftamount, actspentamount, expsubcategory.trim());
                                int updateleftamount=dataBaseHelper.getleftamount(username.trim(), categoryname.trim(), budgetname1.trim());
                                setleftamount.setText(String.valueOf(updateleftamount));
                                int updatespentamount=dataBaseHelper.getspentamount(username.trim(),categoryname.trim(),budgetname1.trim());
                                setspentamount.setText(String.valueOf(updatespentamount));
                                expense.remove(positiondel);
                                progress.setProgress(updatespentamount);
                                ((ArrayAdapter) expenselistview.getAdapter()).notifyDataSetChanged();
                                Toast.makeText(getContext(),"Selected Expense has been Deleted",Toast.LENGTH_SHORT).show();
                                return true;
                            }

                            return false;
                        }
                    });

                    popup.show();//showing popup menu
                }
            });//closin

            return row;
        }
    }

    class ViewHolder1 {
        TextView expensename,expensedate,expensesubcategory,expenseamount;
        ImageButton exppopup;

        ViewHolder1(View row)
        {
            expensename=(TextView)row.findViewById(R.id.expensename);
            expensedate=(TextView)row.findViewById(R.id.expensedate);
            expenseamount=(TextView)row.findViewById(R.id.expenseamount);
            expensesubcategory=(TextView)row.findViewById(R.id.expensesubcategory);
            exppopup=(ImageButton)row.findViewById(R.id.expensepopup);
        }
    }
}
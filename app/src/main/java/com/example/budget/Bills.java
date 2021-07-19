package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Bills extends AppCompatActivity implements New_bill.NewBillListener, Services.FragmentListener,
        All.AllFragmentListener, Taxes.TaxFragmentListener,EMI.EMIFragmentListener, com.example.budget.Others.OthersFragmentListener {
    public ArrayList<card_bills> bills;
    public Tabs_Adaptor adaptor;
    private ViewPager pager;
    public TabLayout types;
    public TabItem All,Services,Taxes,EMI,Others;
    public dataBase dataBase;
    public SQLiteDatabase sq;
    public String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dataBase = new dataBase(this);
        User = getIntent().getStringExtra("User");
        createTabs();
        HighlightCurrentTab(0);

    }

    public void createTabs() {
        pager = findViewById(R.id.Bills_pager);
        types = findViewById(R.id.types);
        All = findViewById(R.id.All);
        Services = findViewById(R.id.Services);
        Taxes = findViewById(R.id.Taxes);
        EMI = findViewById(R.id.EMI);
        Others = findViewById(R.id.Others);

        LoadData();

        adaptor = new Tabs_Adaptor(getSupportFragmentManager(),bills,this,this,
                this,this,this);
        pager.setAdapter(adaptor);
        pager.setOffscreenPageLimit(5);

        types.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                types.getTabAt(position).select();
                HighlightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void HighlightCurrentTab(int position){
        for (int i = 0; i < types.getTabCount(); i++) {
            TabLayout.Tab tab = types.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adaptor.getTabView(i,this));
        }
        TabLayout.Tab tab = types.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adaptor.getSelectedTabView(position,this));
    }

    public void newBill(View view){
        New_bill dialog = new New_bill();
        dialog.show(getSupportFragmentManager(), "NewBill");
    }

    @Override
    public void add(String name, int price, int date, int month, int year, int period,int type) {
        Calendar cal = new GregorianCalendar(year,month-1,1);
        if (month>12){
            Toast.makeText(this,"Month can not be greater than 12",Toast.LENGTH_SHORT).show();
        }
        else if (date>cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
            Toast.makeText(this,"this month have only "+ cal.getActualMaximum(Calendar.DAY_OF_MONTH) +" days",Toast.LENGTH_SHORT).show();
        }
        else if ((Calendar.YEAR > year)||(Calendar.YEAR == year && Calendar.MONTH > month) || (Calendar.YEAR == year && Calendar.MONTH == month && Calendar.DATE > date)){
            Toast.makeText(this,"Deadline should be in future",Toast.LENGTH_SHORT).show();
        }
        else {
            card_bills ob = new card_bills(name,price,period,date,month,year,type,"UnPaid",R.drawable.bills);
            adaptor.updateFragment(ob,ob.type);
        }
    }

    @Override
    public void delete(String name) {
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getBill_name().equals(name)){
                card_bills ob = bills.get(i);
                adaptor.Delete(ob);
                break;
            }
        }
    }

    @Override
    public void delete1(String name,int type) {
        adaptor.Delete1(name,type);
        }

    @Override
    public void statusChange(String name, int type) {
        adaptor.Status(name,type);
        sq = dataBase.getWritableDatabase();
        Cursor cr = sq.rawQuery("SELECT * FROM UserStatus",null);
        ContentValues cv1 = new ContentValues();
        cr.moveToFirst();
        do {
            if (cr.getString(6).equals(User)){
                if (type == 0){
                    int money = cr.getInt(5);
                    for (int i = 0 ; i< bills.size();i++) {
                        if (bills.get(i).getBill_name().equals(name)) {
                            money = money + bills.get(i).getPrice();
                            cv1.put("Service",money);
                            break;
                        }
                    }
                }else if (type == 1) {
                    int money = cr.getInt(4);
                    for (int i = 0 ; i< bills.size();i++) {
                        if (bills.get(i).getBill_name().equals(name)) {
                            money = money + bills.get(i).getPrice();
                            cv1.put("Taxes",money);
                            break;
                        }
                    }
                }else if (type == 2) {
                    int money = cr.getInt(2);
                    for (int i = 0 ; i< bills.size();i++) {
                        if (bills.get(i).getBill_name().equals(name)) {
                            money = money + bills.get(i).getPrice();
                            cv1.put("EMI",money);
                            break;
                        }
                    }
                }else if (type == 3) {
                    int money = cr.getInt(3);
                    for (int i = 0 ; i< bills.size();i++) {
                        if (bills.get(i).getBill_name().equals(name)) {
                            money = money + bills.get(i).getPrice();
                            cv1.put("Others",money);
                            break;
                        }
                    }
                }
                sq.update("UserStatus",cv1,"Username=?",new String[]{User});
            }
        }while(cr.moveToNext());
        cr.close();
    }

    @Override
    public void delete2(String name) {
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getBill_name().equals(name)){
                card_bills ob = bills.get(i);
                adaptor.Delete2(ob);
                break;
            }
        }
    }

    @Override
    public void delete3(String name) {
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getBill_name().equals(name)){
                card_bills ob = bills.get(i);
                adaptor.Delete3(ob);
                break;
            }
        }
    }

    @Override
    public void delete4(String name) {
        for (int i = 0; i < bills.size(); i++){
           if (bills.get(i).getBill_name().equals(name)){
                card_bills ob = bills.get(i);
                adaptor.Delete2(ob);
                break;
            }
        }
    }

    @Override
    public void StatusChangeAll(String name) {
        adaptor.changeAll(name);
    }

    public void LoadData(){
        sq = dataBase.getReadableDatabase();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<card_bills>>(){}.getType();
        Cursor cr = sq.rawQuery("Select * From UserBills" , null);
        cr.moveToFirst();
        do {
            if (cr.getString(1).equals(User)){
                String json = cr.getString(2);
                bills = gson.fromJson(json,type);
                break;
            }
        }while(cr.moveToNext());
        cr.close();
        if (bills == null)
            bills = new ArrayList<>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    public void saveData(){
        Gson gson = new Gson();
        String json = gson.toJson(bills);
        dataBase.updateTable3(User,json);
    }
}

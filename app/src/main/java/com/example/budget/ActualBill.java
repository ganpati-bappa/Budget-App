package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class ActualBill extends AppCompatActivity implements BuyListAdapter.TouchListener , Dialogue.DialogueListener {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    public BuyListAdapter adapter;
    public ArrayList<BuyList> bill;
    public TextView t1;
    public int cost = 0;
    public ImageView img;
    public FloatingActionButton fb1;
    public dataBase db;
    public Context context;
    public String User;
    public ArrayList<inventory> inv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_bill);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new dataBase(this);
        inv = new ArrayList<>();
        fb1 = findViewById(R.id.fb11);
        img = findViewById(R.id.empty_cart);
        img.setVisibility(View.GONE);
        context = this;
        User = getIntent().getStringExtra("User");

        Gson gson = new Gson();
        String json = getIntent().getStringExtra("ActualBill");
        Type type = new TypeToken<ArrayList<BuyList>>(){}.getType();
        bill = gson.fromJson(json,type);

        if (bill.isEmpty()) {
            fb1.setTranslationY(1500f);
            img.setVisibility(View.VISIBLE);
        }

        recyclerView = findViewById(R.id.ItemBill);
        manager = new LinearLayoutManager(this);
        adapter = new BuyListAdapter(bill,this);
        adapter.setTouchListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        t1 = findViewById(R.id.cost123);
        for (int i = 0; i < bill.size(); i++ ) {
            cost = cost + bill.get(i).getB_cost() * bill.get(i).getB_quantity();
        }
        t1.setText("Rs " + cost);
    }

    @Override
    public void changeBill(int state, int Cost) {
        if (state == 1)
            cost = cost + Cost;
        else
            cost = cost - Cost;
        t1.setText("Rs " + cost);
    }

    public void Buy(View view) {
        final SQLiteDatabase sq = db.getWritableDatabase();
        final Gson gson = new Gson();
        final String json = gson.toJson(bill);
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Are You Confirm ",Snackbar.LENGTH_SHORT);
        snackbar.setAction("Confirm", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("BroughtProducts", json);
                cv.put("TotalCost", cost);
                cv.put("LoadDate", Calendar.DATE);
                sq.update("UserShopping", cv, "Username=?", new String[]{User});
                Cursor cr = sq.rawQuery("SELECT * From UserInventory ",null);
                cr.moveToFirst();
                String inventory = "" ;
                do {
                    if (cr.getString(2).equals(User)){
                        inventory = cr.getString(1);
                        break;
                    }
                }while(cr.moveToNext());
                Gson gs = new Gson();
                Type type = new TypeToken<ArrayList<inventory>>(){}.getType();
                inv = gs.fromJson(inventory,type);
                for (int i = 0; i < bill.size(); i++) {
                    for (int j = 0; j < inv.size(); j++) {
                        if (bill.get(i).getBuy_name().equals(inv.get(j).getItem_name())) {
                            inv.get(j).setQuantity(inv.get(j).getQuantity() + bill.get(i).getB_quantity());
                            break;
                        }
                    }
                }
                String js = gs.toJson(inv);
                db.updateTable2(User,js);
                Toast.makeText(context, "Bill Saved", Toast.LENGTH_SHORT).show();
                cr.close();

                Cursor c1 = sq.rawQuery("SELECT * FROM UserStatus", null);
                c1.moveToFirst();
                do {
                    if (c1.getString(6).equals(User)) {
                        int cost1 = c1.getInt(1);
                        cost1 = cost1 + cost;
                        ContentValues cv1 = new ContentValues();
                        cv1.put("Shop",cost1);
                        sq.update("UserStatus",cv1,"Username=?",new String[]{User});
                        break;
                    }
                }while(c1.moveToNext());
                c1.close();
            }
        });
        snackbar.show();
    }

    public void add(View view) {
        Dialogue dialog = new Dialogue();
        dialog.show(getSupportFragmentManager(), "Dialogue");
    }

    @Override
    public void applyTexts(String item_name, int i_quantity, int i_required, int i_cost) {
        boolean flag = true;
        for (int i = 0; i < bill.size(); i++) {
            if (bill.get(i).getBuy_name().equals(item_name)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            bill.add(new BuyList(item_name,i_quantity,i_cost));
            if (bill.size() == 1) {
                img.setVisibility(View.GONE);
                fb1.setTranslationY(0f);
            }
            adapter.notifyItemInserted(bill.size() - 1);
        }
        else
            Toast.makeText(context,"Item Already Exist",Toast.LENGTH_SHORT).show();
    }
}

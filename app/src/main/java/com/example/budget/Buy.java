package com.example.budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Buy extends AppCompatActivity implements BuyListAdapter.TouchListener{
    public ArrayList<inventory> m1;
    public ArrayList<BuyList> m2;
    public ArrayList<BuyList> m3;
    public String InventoryList;
    public BuyListAdapter adapter;
    public RecyclerView.LayoutManager manager;
    public RecyclerView buyList;
    public TextView t1;
    public int sum;
    Context context;
    dataBase dataBase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        context = this;
        dataBase = new dataBase(context);
        m2 = new ArrayList<>();
        m3 = new ArrayList<>();
        sum = 0;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String user = intent.getStringExtra("User");
        SQLiteDatabase sq = dataBase.getReadableDatabase();
        Cursor cr = sq.rawQuery("Select * From UserInventory",null);
        cr.moveToFirst();
        do {
            if (cr.getString(2).equals(user)){
                 InventoryList = cr.getString(1);
                break;
            }
        }while (cr.moveToNext());

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<inventory>>(){}.getType();
        m1 = gson.fromJson(InventoryList,type);
        if (m1 == null)
            m1 = new ArrayList<>();

        for (int i = 0; i < m1.size(); i++){
            if (m1.get(i).getQuantity() < m1.get(i).getQuantity_needed()){
                int quantity = m1.get(i).getQuantity_needed()-m1.get(i).getQuantity();
                m2.add(new BuyList(m1.get(i).getItem_name(), quantity, m1.get(i).getCost()));
                sum = sum + quantity * m1.get(i).getCost();
            }
        }

        t1 = findViewById(R.id.bill);
        t1.setText(String.valueOf(sum) + " Rs");
        buyList = findViewById(R.id.buyList);
        adapter = new BuyListAdapter(m2,this);
        adapter.setTouchListener(this);
        manager = new LinearLayoutManager(this);
        buyList.setHasFixedSize(true);
        buyList.setAdapter(adapter);
        buyList.setLayoutManager(manager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(buyList);

    }


    @Override
    public void changeBill(int state,int Cost) {
        if (state == 1)
            sum = sum + Cost;
        else
            sum = sum - Cost;
        t1.setText(String.valueOf(sum));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback (0,ItemTouchHelper.RIGHT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.RIGHT) {
                BuyList ob = m2.get(viewHolder.getAdapterPosition());
                m3.add(ob);
                m2.remove(ob);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            if (dX > 0) {
                final ColorDrawable background = new ColorDrawable(Color.rgb(23, 219, 36));
                background.setBounds((int) (viewHolder.itemView.getLeft() + dX), viewHolder.itemView.getTop(),
                        viewHolder.itemView.getLeft(), viewHolder.itemView.getBottom());
                background.draw(c);

                Drawable icon = ContextCompat.getDrawable(context,R.drawable.shop);
                icon.setBounds(viewHolder.itemView.getLeft()+20, viewHolder.itemView.getTop() +40,
                        viewHolder.itemView.getLeft() + 20 + icon.getIntrinsicWidth() ,
                        viewHolder.itemView.getTop() + 40 + icon.getIntrinsicHeight());
                icon.draw(c);

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(35);
                paint.setTypeface(Typeface.create("SERIF",Typeface.ITALIC));
                paint.setTextAlign(Paint.Align.CENTER);
                String t1 = "Add To Cart";
                c.drawText(t1, viewHolder.itemView.getLeft() + 175, viewHolder.itemView.getTop() + (viewHolder.itemView.getHeight() *3) / 5, paint);
            }
        }
    };

    public void actualBill(View v) {
        Intent intent = new Intent(this, ActualBill.class);
        Gson gson = new Gson();
        String json = gson.toJson(m3);
        String User = getIntent().getStringExtra("User");
        intent.putExtra("ActualBill",json);
        intent.putExtra("User",User);
        startActivity(intent);

    }
}

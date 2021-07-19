package com.example.budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;



public class list extends AppCompatActivity implements Dialogue.DialogueListener,
        EditDialogue.DialogueListener,FilteredDialogue.SearchListener {

    public RecyclerView recyclerView,recyclerView1;
    public RecyclerAdapter adapter;
    public ArrayList<inventory> myList;
    public RecyclerView.LayoutManager layoutManager;
    public Context context = this;
    public String user;
    public int position1;
    public ArrayList<inventory> filteredList;
    dataBase db = new dataBase(this);
    EditText search ;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        img = findViewById(R.id.imageView);
        user = getIntent().getStringExtra("User");
        LoadData();
        BuildRecycleView();


        if (adapter.getItemCount()==0)
            img.setVisibility(View.VISIBLE);
        else
            img.setVisibility(View.GONE);

        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView1.setVisibility(View.GONE);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(callback);
        itemTouchHelper1.attachToRecyclerView(recyclerView1);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                recyclerView.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.VISIBLE);
                filter(s.toString());
            }
        });
    }

    private void filter(String search){
         filteredList = new ArrayList<>();
        for (int i = 0; i< myList.size(); i++){
            if (myList.get(i).getItem_name().toLowerCase().contains(search.toLowerCase())){
                filteredList.add(myList.get(i));
            }
        }
        adapter = new RecyclerAdapter(filteredList,this);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapter);
    }

    @Override
    public void onPause(){
        super.onPause();
        SaveData();
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT){
                myList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                if (adapter.getItemCount()==0)
                    img.setVisibility(View.VISIBLE);
            }
            else if (direction == ItemTouchHelper.RIGHT){
                EditDialogue editDialogue = new EditDialogue();
                inventory object = myList.get(viewHolder.getAdapterPosition());

                Bundle bundle = new Bundle();
                bundle.putString("e_name",object.getItem_name());
                bundle.putInt("e_quantity",object.getQuantity());
                bundle.putInt("e_required",object.getQuantity_needed());
                bundle.putInt("e_cost",object.getCost());
                bundle.putInt("e_position",viewHolder.getAdapterPosition());

                editDialogue.setArguments(bundle);

                editDialogue.show(getSupportFragmentManager(),"Edit");
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            if (dX<0) {
                final ColorDrawable background1 = new ColorDrawable(Color.RED);
                background1.setBounds((int) (viewHolder.itemView.getLeft() + dX), viewHolder.itemView.getTop(), viewHolder.itemView.getRight(),
                        viewHolder.itemView.getBottom()-3);
                background1.draw(c);

                Drawable icon = ContextCompat.getDrawable(context, R.drawable.delete);
                icon.setBounds(viewHolder.itemView.getRight() - icon.getIntrinsicWidth(), viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4,
                        viewHolder.itemView.getRight(),
                        viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4 + icon.getIntrinsicHeight());
                icon.draw(c);

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(35);
                paint.setTextAlign(Paint.Align.CENTER);
                String inbox = "Delete";
                Typeface typeface = Typeface.create("SERIF",Typeface.BOLD);
                paint.setTypeface(typeface);
                c.drawText(inbox, viewHolder.itemView.getRight() - 110, viewHolder.itemView.getTop() + 3 * viewHolder.itemView.getHeight()/ 5, paint);
            }
            else {
                final ColorDrawable background2 = new ColorDrawable(Color.rgb(71,180,4));
                background2.setBounds((int) (viewHolder.itemView.getLeft()-dX),viewHolder.itemView.getTop(),
                        viewHolder.itemView.getRight(),viewHolder.itemView.getBottom()-3);
                background2.draw(c);

                Drawable icon = ContextCompat.getDrawable(context, R.drawable.edit);
                icon.setBounds(viewHolder.itemView.getLeft() , viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4,
                        viewHolder.itemView.getLeft()+ icon.getIntrinsicWidth(),
                        viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4 + icon.getIntrinsicHeight());
                icon.draw(c);

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(35);
                paint.setTextAlign(Paint.Align.CENTER);
                String edit = "Edit";
                Typeface typeface = Typeface.create("SERIF",Typeface.BOLD);
                paint.setTypeface(typeface);
                c.drawText(edit,viewHolder.itemView.getLeft()+ 90,viewHolder.itemView.getTop() + 3 * viewHolder.itemView.getHeight()/  5, paint);
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void BuildRecycleView(){
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(myList,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void add_new(View view){
        Dialogue dialogue = new Dialogue();
        dialogue.show(getSupportFragmentManager(),"Dialogue");
    }

    @Override
    public void applyTexts(String item_name,int  i_quantity, int i_required, int i_cost) {
        boolean flag = false;
        for (int i = 0 ; i < myList.size(); i++) {
            if (myList.get(i).getItem_name().equals(item_name)) {
                flag = true;
                break;
            }
        }
        if (flag)
            Toast.makeText(context,"Item Already Exists",Toast.LENGTH_SHORT).show();
        else {
            myList.add(new inventory(item_name,i_quantity,i_required,R.drawable.b,i_cost));
            img.setVisibility(View.GONE);
            adapter.notifyItemInserted(myList.size()-1);
        }
    }

    public void SaveData(){
        Gson gson = new Gson();
        String json = gson.toJson(myList);
        db.updateTable2(user,json);
    }

    public void LoadData(){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<inventory>>(){}.getType();
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor c = sq.rawQuery("Select * From UserInventory ",null);
        c.moveToFirst();
        do{
            if (c.getString(2).equals(user)){
                myList = gson.fromJson(c.getString(1),type);
                break;
            }
        }while(c.moveToNext());
        c.close();
        if (myList == null){
            myList = new ArrayList<>();
            img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void editItem(String e_name, int e_quantity, int e_required, int r_cost, int Position) {
        myList.remove(Position);
        adapter.notifyItemRemoved(Position);
        myList.add(Position,new inventory(e_name,e_quantity,e_required,R.drawable.b,r_cost));
        adapter.notifyItemInserted(Position);
    }

    @Override
    public void discard() {
        adapter.notifyDataSetChanged();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT){
                int position = viewHolder.getAdapterPosition();
                String name = filteredList.get(position).getItem_name();
                filteredList.remove(position);
                for (int i = 0 ; i < myList.size(); i++){
                    if (myList.get(i).getItem_name().equals(name)){
                        myList.remove(i);
                        break;
                    }
                }
            }
            else if (direction == ItemTouchHelper.RIGHT){
                int position = viewHolder.getAdapterPosition();
                inventory ob = filteredList.get(position);

                for(int i = 0; i < myList.size(); i++){
                    if (myList.get(i).getItem_name().equals(ob.getItem_name())){
                        position1 = i;
                        break;
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("ef_name",ob.getItem_name());
                bundle.putInt("ef_quantity",ob.getQuantity());
                bundle.putInt("ef_required",ob.getQuantity_needed());
                bundle.putInt("ef_cost",ob.getCost());
                bundle.putInt("ef_position1",position1);
                bundle.putInt("ef_position",position);

                FilteredDialogue dialogue = new FilteredDialogue();
                dialogue.setArguments(bundle);
                dialogue.show(getSupportFragmentManager(),"EditFilteredDialogue");

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            if (dX<0) {
                final ColorDrawable background1 = new ColorDrawable(Color.RED);
                background1.setBounds((int) (viewHolder.itemView.getLeft() + dX), viewHolder.itemView.getTop(), viewHolder.itemView.getRight(),
                        viewHolder.itemView.getBottom()-3);
                if (isCurrentlyActive)
                    background1.draw(c);

                Drawable icon = ContextCompat.getDrawable(context, R.drawable.delete);
                icon.setBounds(viewHolder.itemView.getRight() - icon.getIntrinsicWidth(), viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4,
                        viewHolder.itemView.getRight(),
                        viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4 + icon.getIntrinsicHeight());
                if (isCurrentlyActive)
                    icon.draw(c);

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(35);
                paint.setTextAlign(Paint.Align.CENTER);
                Typeface typeface = Typeface.create("SERIF",Typeface.BOLD);
                paint.setTypeface(typeface);
                String inbox = "Delete";
                if (isCurrentlyActive)
                    c.drawText(inbox, viewHolder.itemView.getRight() - 110, viewHolder.itemView.getTop() + 3 * viewHolder.itemView.getHeight()/ 5, paint);
            }
            else {
                final ColorDrawable background2 = new ColorDrawable(Color.rgb(71,180,4));
                background2.setBounds((int) (viewHolder.itemView.getLeft()-dX),viewHolder.itemView.getTop(),
                        viewHolder.itemView.getRight(),viewHolder.itemView.getBottom()-3);
                background2.draw(c);

                Drawable icon = ContextCompat.getDrawable(context, R.drawable.edit);
                icon.setBounds(viewHolder.itemView.getLeft() , viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4,
                        viewHolder.itemView.getLeft()+ icon.getIntrinsicWidth(),
                        viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 4 + icon.getIntrinsicHeight());
                icon.draw(c);

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(35);
                paint.setTextAlign(Paint.Align.CENTER);
                String edit = "Edit";
                Typeface typeface = Typeface.create("SERIF",Typeface.BOLD);
                paint.setTypeface(typeface);
                c.drawText(edit,viewHolder.itemView.getLeft()+ 90,viewHolder.itemView.getTop() + 3 * viewHolder.itemView.getHeight()/ 5, paint);
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void changeText(String name, int quantity, int required, int cost, int position,int position1) {
        myList.remove(position1);
        filteredList.remove(position);
        myList.add(position1 , new inventory(name,quantity,required,R.drawable.b,cost));
        filteredList.add(position , new inventory(name,quantity,required,R.drawable.b,cost));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void discard1() {
        adapter.notifyDataSetChanged();
    }
}

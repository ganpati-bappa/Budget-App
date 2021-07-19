package com.example.budget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public ArrayList<inventory> myList;
    public int lastPosition = -1;
    public Context context;

    public RecyclerAdapter(ArrayList<inventory> inventory, Context context){
        myList = inventory;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final inventory item = myList.get(position);

        holder.t1.setText(item.getItem_name());
        holder.t1.setHorizontallyScrolling(true);
        holder.t3.setText(String.valueOf(item.getQuantity()));
        holder.t2.setText(" Recommended: " + String.valueOf(item.getQuantity_needed()));
        if (item.getQuantity()>=item.getQuantity_needed())
            holder.t3.setTextColor(Color.rgb(0,180,0));
        else
            holder.t3.setTextColor(Color.RED);

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.t3.setText(String.valueOf(item.getQuantity()+1));
                myList.get(position).setQuantity(item.getQuantity()+1);
                if (item.getQuantity()>=item.getQuantity_needed())
                    holder.t3.setTextColor(Color.rgb(0,180,0));
                else
                    holder.t3.setTextColor(Color.RED);
            }
        });

        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.t3.setText(String.valueOf(item.getQuantity()-1));
                myList.get(position).setQuantity(item.getQuantity()-1);
                if (item.getQuantity()>=item.getQuantity_needed())
                    holder.t3.setTextColor(Color.rgb(0,180,0));
                else
                    holder.t3.setTextColor(Color.RED);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView t1,t2,t3;
        public ImageView img;
        public  ImageButton b1,b2;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           t1 = itemView.findViewById(R.id.item_name);
           t2 = itemView.findViewById(R.id.quantity_needed);
           t3 = itemView.findViewById(R.id.quantity);
           img = itemView.findViewById(R.id.e_logo);
           b1 = itemView.findViewById(R.id.up);
           b2 = itemView.findViewById(R.id.down);
       }
   }
}

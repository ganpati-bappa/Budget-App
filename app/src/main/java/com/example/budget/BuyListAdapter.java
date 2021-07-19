package com.example.budget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyListAdapter extends RecyclerView.Adapter<BuyListAdapter.ViewHolder> {
    public ArrayList<BuyList> buyLists;
    public Context context;
    public TouchListener touchListener;

    public interface TouchListener{
        void changeBill(int state,int Cost);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3;
        Button b1,b2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.b_name);
            t2 = itemView.findViewById(R.id.b_quantity);
            t3 = itemView.findViewById(R.id.b_cost);
            b1 = itemView.findViewById(R.id.b_up);
            b2 = itemView.findViewById(R.id.b_down);
        }
    }

    public BuyListAdapter(ArrayList<BuyList> m1, Context context){
        buyLists = m1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_buy,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final BuyList ob = buyLists.get(position);

        holder.t1.setText(ob.getBuy_name());
        holder.t1.setHorizontallyScrolling(true);
        holder.t2.setText(String.valueOf(ob.getB_quantity()));
        holder.t3.setText(String.valueOf(ob.b_cost)+" Rs per pc");

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = ob.getB_quantity();
                ob.setB_quantity(quantity + 1);
                holder.t2.setText(String.valueOf(quantity + 1));
                touchListener.changeBill( 1,ob.getB_cost());
            }
        });

        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = ob.getB_quantity();
                ob.setB_quantity(quantity - 1);
                holder.t2.setText(String.valueOf(quantity - 1));
                touchListener.changeBill(-1,ob.getB_cost());
            }
        });

    }

    @Override
    public int getItemCount() {
        return buyLists.size();
    }

    public void setTouchListener (TouchListener touchListener){
        this.touchListener = touchListener;
    }
}

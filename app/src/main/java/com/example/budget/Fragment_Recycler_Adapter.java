package com.example.budget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_Recycler_Adapter extends RecyclerView.Adapter<Fragment_Recycler_Adapter.ViewHolder> {

    public ArrayList<card_bills> bills;
    public String[] Months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    public BillListener listener;
    public Context context;
    public int[] Images = {R.drawable.services,R.drawable.taxes,R.drawable.emi,R.drawable.others};

    public Fragment_Recycler_Adapter(ArrayList<card_bills>list , BillListener listener, Context context){
        this.bills = list;
        this.listener = listener;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView t1,t2,t3 , date;
        public ImageView img;
        public LinearLayout l1;
        public boolean clicked = false;
        public ImageButton img1,img2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.Bills_name);
            t2 = itemView.findViewById(R.id.Price);
            t3 = itemView.findViewById(R.id.Status);
            date = itemView.findViewById(R.id.Date1);
            img = itemView.findViewById(R.id.bills_logo);
            l1 = itemView.findViewById(R.id.l11);
            img1 = itemView.findViewById(R.id.edit_bills);
            img2 = itemView.findViewById(R.id.delete_bill);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked == false){
                        clicked = true;
                        Animation animation = new TranslateAnimation(0f,0f,-50f,0f);
                        animation.setDuration(200);
                        animation.setInterpolator(new AccelerateDecelerateInterpolator());
                        l1.setAnimation(animation);
                        l1.setVisibility(View.VISIBLE);
                    }
                    else {
                        clicked = false;
                        l1.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Fragment_Recycler_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Fragment_Recycler_Adapter.ViewHolder holder, final int position) {
        final card_bills ob = bills.get(position);
        holder.t1.setText(ob.getBill_name());

        if (ob.getDuration() == 1)
            holder.t2.setText(String.valueOf(ob.getPrice()) + " Rs every month");
        else
            holder.t2.setText(String.valueOf(ob.getPrice())+ " Rs every " + ob.getDuration() + " month");

        holder.t3.setText(ob.getStatus());

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ob.getStatus().toLowerCase().equals("unpaid")){
                    Bills bills = (Bills) context;
                    Snackbar snackbar = Snackbar.make(bills.findViewById(android.R.id.content),ob.getBill_name()+" Bill Paid",Snackbar.LENGTH_LONG);
                    snackbar.setAction("Confirm", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.t3.setText("Paid");
                            holder.t3.setTypeface(null, Typeface.BOLD_ITALIC);
                            holder.t3.setBackgroundColor(Color.rgb(92,182,59));
                            ob.setStatus("Paid");
                            listener.statusChange(position,ob.getBill_name(),ob.type);
                        }
                    });
                    snackbar.show();
                }
            }
        });

        holder.l1.setVisibility(View.GONE);

        if (ob.getStatus().toLowerCase().equals("paid")){
            if ((Calendar.YEAR > ob.getYear())||(Calendar.YEAR == ob.getYear() && Calendar.MONTH > ob.getMonth())||
                    Calendar.YEAR == ob.getYear() && Calendar.MONTH == ob.getMonth() && Calendar.DATE >= ob.getMonth()){
                int increase_in_years = ob.getDuration()/12;
                int increase_in_months = ob.getDuration()%12;
                if (ob.getMonth() + increase_in_months > 12){
                    ob.setMonth((ob.getMonth() + increase_in_months)%12);
                    ob.setYear(ob.getYear() + increase_in_years + 1);
                }
                else {
                    ob.setMonth(ob.getMonth() + increase_in_months);
                    ob.setYear(ob.getYear() + increase_in_years);
                }
            }
        }

        if (ob.getStatus().toLowerCase().equals("paid")){
            holder.t3.setTypeface(null, Typeface.BOLD_ITALIC);
            holder.t3.setBackgroundColor(Color.rgb(92,182,59));
        }
        else{
            holder.t3.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.t3.setBackgroundColor(Color.rgb(255,99,71));
            holder.t3.setPadding(2,0,2,0);
        }

        if (ob.getStatus().toLowerCase().equals("unpaid"))
            holder.date.setText(String.valueOf("Due on " + ob.getDate()) + " " + Months[ob.getMonth()-1]+ " "+ ob.getYear());
        else
            holder.date.setVisibility(View.GONE);

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(position,ob.Bill_name,ob.type);
            }
        });

        holder.img.setImageResource(Images[ob.type]);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }
}

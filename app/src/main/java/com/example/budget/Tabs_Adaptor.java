package com.example.budget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class Tabs_Adaptor extends FragmentStatePagerAdapter {
    public String[] Fragments = {"All","Services","Taxes","EMI","Others"};
    public ArrayList<card_bills> bills;
    public Services.FragmentListener listener;
    public All.AllFragmentListener listener1;
    public Taxes.TaxFragmentListener listener2;
    public EMI.EMIFragmentListener listener3;
    public Others.OthersFragmentListener listener4;
    public All all;
    public Services services;
    public Taxes taxes;
    public EMI emi;
    public Others others;

    public Tabs_Adaptor(FragmentManager fm, ArrayList<card_bills> bills , Services.FragmentListener listener,
                        All.AllFragmentListener listener1, Taxes.TaxFragmentListener listener2,
                        EMI.EMIFragmentListener listener3, Others.OthersFragmentListener listener4) {
        super(fm);
        this.bills = bills;
        this.listener = listener;
        this.listener1 = listener1;
        this.listener2 = listener2;
        this.listener3 = listener3;
        this.listener4 = listener4;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            all = new All(bills,listener1);
            return all;
        }
        else if (position == 1){
            services = new Services(bills,listener);
            return services;
        }
        else if (position == 2){
            taxes = new Taxes(bills,listener2);
            return taxes;
        }
        else if (position == 3){
            emi = new EMI(bills,listener3);
            return emi;
        }
        else {
            others = new Others(bills,listener4);
            return others;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    public View getTabView(int position,Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.status_bills,null);
        TextView t1 = view.findViewById(R.id.status1);
        ImageView img = view.findViewById(R.id.dots);
        t1.setText(Fragments[position]);
        t1.setAlpha(0.75f);
        t1.setTextSize(18);
        img.setImageResource(R.drawable.dots);
        img.setVisibility(View.INVISIBLE);
        return view;
    }

    public View getSelectedTabView(int position, Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.status_bills,null);
        TextView t1 = view.findViewById(R.id.status1);
        ImageView img = view.findViewById(R.id.dots);
        t1.setText(Fragments[position]);
        t1.setTextColor(Color.WHITE);
        t1.setTextSize(22);
        t1.setTypeface(null, Typeface.BOLD);
        img.setImageResource(R.drawable.dots);
        img.setVisibility(View.VISIBLE);
        Animation animation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        img.setAnimation(animation);
        return view;
    }

    public void updateFragment(card_bills ob,int type){
        all.add(ob);
        if (type == 0)
            services.add(ob);
        else if (type == 1)
            taxes.add(ob);
        else if (type == 2)
            emi.add(ob);
        else
            others.add(ob);

    }

    public void Delete(card_bills ob){
        all.Delete(ob);
    }

    public void Delete1(String name,int type){
        if (type == 0)
            services.Delete(name);
        else if (type == 1)
            taxes.Delete(name);
        else if (type ==2)
            emi.Delete(name);
        else
            others.Delete(name);
    }

    public void Delete2(card_bills ob){
        all.Delete1(ob);
    }

    public void Delete3(card_bills ob){
        all.Delete2(ob);
    }

    public void Status(String name, int type){
        if (type == 0)
            services.change(name);
        else if (type == 1)
            taxes.change(name);
        else if (type == 2)
            emi.change(name);
        else
            others.change(name);
    }

    public void changeAll(String name){
        all.Change(name);
    }
}

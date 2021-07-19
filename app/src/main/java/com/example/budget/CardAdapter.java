package com.example.budget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class CardAdapter extends PagerAdapter {
    private List<CardClass> menu;
    private LayoutInflater lf;
    private Context context;
    public String User;

    public CardAdapter(List<CardClass> menu,Context context,String User){
        this.menu = menu;
        this.context = context;
        this.User = User;
    }
    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        lf = LayoutInflater.from(context);
        final View view = lf.inflate(R.layout.card,container,false);
        ImageView img;
        TextView t1,t2;
        img = view.findViewById(R.id.image2);
        t1 = view.findViewById(R.id.text);
        t2 = view.findViewById(R.id.t2);
        img.setImageResource(menu.get(position).getImage());
        t1.setText(menu.get(position).getText());
        t2.setText(menu.get(position).getText1());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu.get(position).getText().equals("Your List")) {
                   Intent intent = new Intent(view.getContext(),list.class);
                   intent.putExtra("User", User);
                   view.getContext().startActivity(intent);
                }
                else if (menu.get(position).getText().equals("Things To buy")){
                    Intent intent = new Intent(context,Buy.class);
                    intent.putExtra("User",User);
                    view.getContext().startActivity(intent);
                }
                else if (menu.get(position).getText().equals("Your Bills")){
                    Intent intent = new Intent(context, Bills.class);
                    intent.putExtra("User",User);
                    view.getContext().startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context,User.class);
                    intent.putExtra("User",User);
                    view.getContext().startActivity(intent);
                }
            }
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

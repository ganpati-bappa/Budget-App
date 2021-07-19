package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Welcome extends AppCompatActivity {
    ImageView img,sym;
    Animation anim1,anim2,anim3,anim4;
    TextView t1;
    ViewPager viewPager,v1;
    CardAdapter adapter;
    List<CardClass> menu;
    String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        User = intent.getStringExtra("User");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        img = findViewById(R.id.img);
        sym = findViewById(R.id.symbol);
        v1 = findViewById(R.id.ViewPager);
        t1 = findViewById(R.id.t1);
        anim1 = AnimationUtils.loadAnimation(this, R.anim.welcome);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.symbol);
        anim3 = AnimationUtils.loadAnimation(this, R.anim.t1);
        anim4 = AnimationUtils.loadAnimation(this, R.anim.relative);
        img.setAnimation(anim1);
        t1.setAnimation(anim3);
        sym.setAnimation(anim2);
        v1.setAnimation(anim4);

        menu = new ArrayList<>();
        menu.add(new CardClass(R.drawable.status, "Your Status", "Information anout your Bills , Savings , Inventory , Things to buy this month"));
        menu.add(new CardClass(R.drawable.bills, "Your Bills", "Includes Your Paid And Unpaid Bills of This Month"));
        menu.add(new CardClass(R.drawable.list, "Your List", "List of Items You have right now in your inventory"));
        menu.add(new CardClass(R.drawable.buy, "Things To buy", "Things To Buy and total money spend on buying this month"));

        adapter = new CardAdapter(menu, this,User);

        viewPager = findViewById(R.id.ViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setPadding(0, 150, 0, 0);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(-150);
    }
}
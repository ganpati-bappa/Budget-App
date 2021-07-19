package com.example.budget;

public class BuyList {
    String buy_name;
    int b_quantity,b_cost;

    public BuyList(String buy_name,int b_quantity,int b_cost){
        this.buy_name = buy_name;
        this.b_quantity = b_quantity;
        this.b_cost = b_cost;
    }

    public String getBuy_name(){
        return buy_name;
    }

    public int getB_quantity(){
        return b_quantity;
    }

    public int getB_cost(){
        return b_cost;
    }

    public void setB_quantity(int quantity){
        this.b_quantity = quantity;
    }

    public void setB_cost(int b_cost){
        this.b_cost = b_cost;
    }

}

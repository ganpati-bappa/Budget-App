package com.example.budget;

public class inventory {
    public String item_name;
    public int quantity,quantity_needed,cost;
    public int image;

    public inventory(String item_name,int quantity,int quantity_needed,int image,int cost){
        this.item_name = item_name;
        this.quantity = quantity;
        this.quantity_needed = quantity_needed;
        this.image = image;
        this.cost = cost;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity_needed() {
        return quantity_needed;
    }

    public void setQuantity_needed(int quantity_needed) {
        this.quantity_needed = quantity_needed;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
}



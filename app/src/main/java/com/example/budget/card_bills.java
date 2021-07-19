package com.example.budget;

public class card_bills {
    public String Bill_name,status;
    public int Price,image,duration,date,month,year,type;

    public card_bills(String Bill_name, int Price, int duration,int date,int month,int year,int type,String status,int image){
        this.Bill_name = Bill_name;
        this.Price = Price;
        this.duration = duration;
        this.date = date;
        this.month = month;
        this.year = year;
        this.type = type;
        this.status = status;
        this.image = image;
    }

    public String getBill_name() {
        return Bill_name;
    }

    public void setBill_name(String bill_name) {
        Bill_name = bill_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

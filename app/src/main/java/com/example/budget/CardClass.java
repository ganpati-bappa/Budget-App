package com.example.budget;

public class CardClass {
    private int image;
    String text;
    String text1;

    public CardClass(int image, String text,String text1) {
        this.image = image;
        this.text = text;
        this.text1 = text1;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText1() {
        return text1;
    }
}

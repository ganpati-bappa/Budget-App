package com.example.budget;

public interface BillListener {
    void delete( int position,String name,int type);
    void edit (int position ,String name , int type);
    void statusChange(int position,String name, int type);
}

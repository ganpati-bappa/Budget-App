package com.example.budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class All extends Fragment implements BillListener {
    public ArrayList<card_bills> bills;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    public Fragment_Recycler_Adapter adapter;
    public AllFragmentListener listener;

    public All(ArrayList<card_bills> bills, AllFragmentListener listener) {
        this.bills = bills;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_services,container,false);
        adapter = new Fragment_Recycler_Adapter(bills,this,getActivity());
        recyclerView = view.findViewById(R.id.bills_recycler1);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void add(card_bills ob){
        bills.add(ob);
        adapter.notifyItemInserted(bills.size()-1);
    }

    @Override
    public void delete(int position, String name,int type) {
        bills.remove(position);
        adapter.notifyItemRemoved(position);
        listener.delete1(name,type);
    }

    @Override
    public void edit(int position,String  name , int type) {

    }

    public void Delete(card_bills ob){
        bills.remove(ob);
        adapter.notifyDataSetChanged();
    }

    public interface AllFragmentListener {
        void delete1(String name,int type);
        void statusChange(String name, int type);
    }

    public void Delete1(card_bills ob){
        bills.remove(ob);
        adapter.notifyDataSetChanged();
    }

    public void Delete2(card_bills ob){
        bills.remove(ob);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void statusChange(int position, String name, int type) {
        adapter.notifyItemChanged(position);
        listener.statusChange(name,type);
    }

    public void Change(String name){
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getBill_name().equals(name)){
                bills.get(i).setStatus("Paid");
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}

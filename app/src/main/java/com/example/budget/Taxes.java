package com.example.budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Taxes extends Fragment implements BillListener{
    public ArrayList<card_bills> bills,taxes;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    public Fragment_Recycler_Adapter adapter;
    public TaxFragmentListener listener;

    public Taxes(ArrayList<card_bills> bills, TaxFragmentListener listener) {
        this.bills = bills;
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_services,container,false);
        taxes = new ArrayList<>();
        taxes = RequiredList();
        recyclerView = view.findViewById(R.id.bills_recycler1);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        adapter = new Fragment_Recycler_Adapter(taxes,this,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        return view;
    }

    public ArrayList<card_bills> RequiredList(){
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getType() ==  1)
                taxes.add(bills.get(i));
        }
        return taxes;
    }

    @Override
    public void delete(int position, String name,int type) {
        taxes.remove(position);
        adapter.notifyItemRemoved(position);
        listener.delete2(name);
    }

    @Override
    public void edit(int position, String name, int type) {

    }

    public interface TaxFragmentListener {
        void delete2(String name);
        void StatusChangeAll(String name);
    }

    public void add(card_bills ob){
        taxes.add(ob);
        adapter.notifyItemInserted(bills.size() - 1);
    }

    public void Delete(String name){
        for (int i = 0; i < taxes.size(); i++){
            if (taxes.get(i).getBill_name().equals(name)){
                taxes.remove(i);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void statusChange(int position, String name, int type) {
        listener.StatusChangeAll(name);
    }

    public void change(String name){
        for (int i = 0; i < taxes.size(); i++){
            if (taxes.get(i).getBill_name().equals(name)){
                taxes.get(i).setStatus("Paid");
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}

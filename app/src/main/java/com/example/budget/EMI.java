package com.example.budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class EMI extends Fragment implements BillListener {
    public ArrayList<card_bills> bills,emi;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    public Fragment_Recycler_Adapter adapter;
    public EMIFragmentListener listener;

    public EMI(ArrayList<card_bills> bills,EMIFragmentListener listener) {
        this.bills = bills;
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_services,container,false);
        emi = new ArrayList<>();
        emi = RequiredList();
        recyclerView = view.findViewById(R.id.bills_recycler1);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        adapter = new Fragment_Recycler_Adapter(emi,this,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        return view;
    }

    public ArrayList<card_bills> RequiredList(){
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getType() ==  2)
                emi.add(bills.get(i));
        }
        return emi;
    }

    @Override
    public void delete(int position, String name, int type) {
        emi.remove(position);
        adapter.notifyItemRemoved(position);
        listener.delete3(name);
    }

    @Override
    public void edit(int position, String name, int type) {

    }

    @Override
    public void statusChange(int position, String name, int type) {
        listener.StatusChangeAll(name);
    }

    public void add(card_bills ob){
        emi.add(ob);
        adapter.notifyItemInserted(bills.size() - 1);
    }

    public interface EMIFragmentListener {
        void delete3(String name);
        void StatusChangeAll(String name);
    }

    public void Delete(String name){
        for (int i = 0; i < emi.size(); i++){
            if (emi.get(i).getBill_name().equals(name)){
                emi.remove(i);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public void change(String name){
        for (int i = 0; i < emi.size(); i++){
            if (emi.get(i).getBill_name().equals(name)){
                emi.get(i).setStatus("Paid");
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}

package com.example.budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Services extends Fragment implements BillListener{
    public ArrayList<card_bills> bills,services;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    public Fragment_Recycler_Adapter adapter;
    public FragmentListener listener;

    public Services(ArrayList<card_bills> bills,FragmentListener listener) {
        this.bills = bills;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_services,container,false);
        services = new ArrayList<>();
        services = RequiredList();
        recyclerView = view.findViewById(R.id.bills_recycler1);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        adapter = new Fragment_Recycler_Adapter(services,this,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        return view;
    }

    public ArrayList<card_bills> RequiredList(){
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getType() ==  0)
                services.add(bills.get(i));
        }
        return services;
    }

    @Override
    public void delete(int position, String name,int type) {
        services.remove(position);
        adapter.notifyItemRemoved(position);
        listener.delete(name);
    }

    @Override
    public void edit(int position, String name,int type) {

    }

    @Override
    public void statusChange(int position, String name, int type) {
        listener.StatusChangeAll(name);
    }

    public interface FragmentListener {
        void delete(String name);
        void StatusChangeAll(String name);
    }

    public void Delete(String name){
        for (int i = 0; i < services.size(); i++){
            if (services.get(i).getBill_name().equals(name)){
                services.remove(i);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public void add(card_bills ob){
        services.add(ob);
        adapter.notifyItemInserted(bills.size() - 1);
    }

    public void change(String name){
        for (int i = 0; i < services.size(); i++){
            if (services.get(i).getBill_name().equals(name)){
                services.get(i).setStatus("Paid");
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

}

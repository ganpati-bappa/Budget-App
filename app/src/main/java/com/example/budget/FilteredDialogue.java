package com.example.budget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class FilteredDialogue extends AppCompatDialogFragment {
    public EditText Name,Quantity,Required,Cost;
    int position,position1;
    public SearchListener listener;

    public interface SearchListener{
        void changeText(String name , int quantity ,
                        int required , int cost , int position , int position1);
        void discard1();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit,null);

        Name = view.findViewById(R.id.e_name);
        Quantity = view.findViewById(R.id.e_Quantity);
        Required = view.findViewById(R.id.e_required);
        Cost = view.findViewById(R.id.e_cost);

        Bundle bundle = getArguments();
        Name.setText(bundle.getString("ef_name",""));
        Quantity.setText(String.valueOf(bundle.getInt("ef_quantity",0)));
        Required.setText(String.valueOf(bundle.getInt("ef_required",0)));
        Cost.setText(String.valueOf(bundle.getInt("ef_cost",0)));
        position = bundle.getInt("ef_position",0);
        position1 = bundle.getInt("ef_position1",0);

        builder.setView(view)
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.discard1();
                    }
                }).setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = Name.getText().toString();
                int quantity = Integer.parseInt(Quantity.getText().toString());
                int required = Integer.parseInt(Required.getText().toString());
                int cost = Integer.parseInt(Cost.getText().toString());
                listener.changeText(name,quantity,required,cost,position,position1);
            }
        });
        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.discard1();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (SearchListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement DialogueListener");
        }
    }
}

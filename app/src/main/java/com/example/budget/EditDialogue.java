package com.example.budget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class EditDialogue extends AppCompatDialogFragment {
    EditText Name,Quantity,Required,Cost;
    public DialogueListener listener;
    public int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit,null);

        Name = view.findViewById(R.id.e_name);
        Quantity = view.findViewById(R.id.e_Quantity);
        Required = view.findViewById(R.id.e_required);
        Cost = view.findViewById(R.id.e_cost);

        Bundle bundle = getArguments();
        Name.setText(bundle.getString("e_name",""));

        Quantity.setText(String.valueOf(bundle.getInt("e_quantity",0)));
        Required.setText(String.valueOf(bundle.getInt("e_required",0)));
        Cost.setText(String.valueOf(bundle.getInt("e_cost",0)));
        position = bundle.getInt("e_position",0);


        builder.setView(view)
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.discard();
                    }
                }).setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String e_name = Name.getText().toString();
                        int e_quantity = Integer.parseInt(Quantity.getText().toString());
                        int e_required = Integer.parseInt(Required.getText().toString());
                        int e_cost = Integer.parseInt(Cost.getText().toString());
                        listener.editItem(e_name,e_quantity,e_required,e_cost,position);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DialogueListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement DialogueListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.discard();
    }

    public interface DialogueListener{
        void editItem(String e_name, int e_quantity, int e_required,int r_cost,int Position);
        void discard();
    }

}

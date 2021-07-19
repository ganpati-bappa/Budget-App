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

public class Dialogue extends AppCompatDialogFragment {
    EditText name , quantity, required, cost;
    public DialogueListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialogue,null);
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String item_name = name.getText().toString();
                       int i_quantity = Integer.parseInt(quantity.getText().toString());
                       int i_required = Integer.parseInt(required.getText().toString());
                       int i_cost = Integer.parseInt(cost.getText().toString());
                       listener.applyTexts(item_name,i_quantity,i_required,i_cost);
                    }
                });
        name = view.findViewById(R.id.name);
        quantity = view.findViewById(R.id.quantity);
        required = view.findViewById(R.id.requirred);
        cost = view.findViewById(R.id.b_cost);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement DialogueListener");
        }
    }

    public interface DialogueListener {
        void applyTexts(String item_name,int i_quantity,
                        int i_required, int i_cost);
    }
}

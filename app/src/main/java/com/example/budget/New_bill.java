package com.example.budget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

public class New_bill extends AppCompatDialogFragment  {
    public EditText bill_name,period,date,month,year,price;
    public Spinner spin;
    public int type;
    public NewBillListener listener;
    String [] items = {"Services","Taxes","EMI","Others"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.new_bill,null);

        bill_name = view.findViewById(R.id.Bill_name);
        period = view.findViewById(R.id.DueTime);
        spin = view.findViewById(R.id.spinner);
        date = view.findViewById(R.id.Date);
        month = view.findViewById(R.id.Month);
        year = view.findViewById(R.id.Year);
        price = view.findViewById(R.id.Price);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,items);
        spin.setAdapter(adapter);

        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = bill_name.getText().toString();
                        int Price = Integer.parseInt(price.getText().toString());
                        int Date = Integer.parseInt(date.getText().toString());
                        int Month = Integer.parseInt(month.getText().toString());
                        int Year = Integer.parseInt(year.getText().toString());
                        int Period = Integer.parseInt(period.getText().toString());
                        type = spin.getSelectedItemPosition();

                        listener.add(name,Price,Date,Month,Year,Period,type);
                    }
                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NewBillListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " Must Implement ");
        }
    }

    public interface NewBillListener {
        void add(String name, int price , int date, int month, int year,int period,int type);
    }
}

package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    dataBase DataBase;
    Button b1;
    EditText e1,e2,e3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DataBase = new dataBase(this);
        b1 = findViewById(R.id.Register);
        e1 = findViewById(R.id.name);
        e2 = findViewById(R.id.username);
        e3 = findViewById(R.id.password);
    }


    public void register(View view){
                String name = e1.getText().toString();
                String Username = e2.getText().toString();
                String password = e3.getText().toString();

                boolean insertData = DataBase.addData(name,Username,password);
                if (insertData)
                    Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Registration.this, "Something Went Wrong Here", Toast.LENGTH_SHORT).show();
    }
}

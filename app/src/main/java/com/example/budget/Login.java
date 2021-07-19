package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText ed1,ed2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ed1 = findViewById(R.id.Userhandle);
        ed2 = findViewById(R.id.editText2);
    }

    public void enter(View view){
        String Username = ed1.getText().toString();
        String Password = ed2.getText().toString();
        dataBase db = new dataBase(this);
        Cursor cr = db.getInformation(db);
        boolean login = false;
        if (cr.moveToFirst()){
            do{
                if (Username.equals(cr.getString(2))&&Password.equals(cr.getString(3))) {
                    login = true;
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Welcome.class);
                    intent.putExtra("User",cr.getString(2));
                    startActivity(intent);
                }
            }while (cr.moveToNext());
            if (login == false)
                Toast.makeText(this,"User Does not Exists",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Please Register Before Login",Toast.LENGTH_SHORT).show();
        cr.close();
    }
}
package com.example.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;


public class dataBase extends SQLiteOpenHelper {
    public static final String Database_Name = "people.db";

    public static final String Table_Name = "UserData";
    public static final String Col1 = "id";
    public static final String Col2 = "Name";
    public static final String Col3 = "Username";
    public static final String Col4 = "Password";

    public static final String Table2 = "UserInventory";
    public static final String C1 = "id_Table2";
    public static String C2 = "Items";
    public static final String C3 = "User";

    public static final String Table3 = "UserBills";
    public static final String B1 = "id_Table3";
    public static  final String B2 = "UserName";
    public static final String B3 = "Bills";

    public static final String Table4 = "UserShopping";
    public static final String S1 = "id_Table4";
    public static final String S2 = "Username";
    public static final String S3 = "BroughtProducts";
    public static final String S4 = "TotalCost";
    public static final String S5 = "LoadDate";

    public static final String Table5 = "UserStatus";
    public static final String U1 = "id";
    public static final String U2 = "Shop";
    public static final String U3 = "EMI";
    public static final String U4 = "Others";
    public static final String U5 = "Taxes";
    public static final String U6 = "Service";
    public static final String U7 = "Username";


    public dataBase(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Table_Name + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name Text, Username Text ,Password Text)";

        String createTable1 = "CREATE TABLE " + Table2 + "(id INTEGER PRIMARY KEY  AUTOINCREMENT ," +
                " Items Text, User Text)";

        String createTable2 = " CREATE TABLE " + Table3 + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " UserName String , Bills String)";

        String createTable3 = " CREATE TABLE " + Table4 + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username String , BroughtProducts String , TotalCost INTEGER , LoadDate DATE )";

        String createTable4 = " CREATE TABLE " + Table5 + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Shop INTEGER , EMI INTEGER , Others INTEGER, Taxes INTEGER, Service INTEGER , Username String )";

        db.execSQL(createTable);
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserData");
        db.execSQL("DROP TABLE IF EXISTS UserInventory");
        db.execSQL("DROP TABLE IF EXISTS UserBills");
        db.execSQL("DROP TABLE IF EXISTS UserShopping");
        db.execSQL("DROP TABLE IF EXISTS UserStatus");
        onCreate(db);
    }

    public boolean addData(String Name, String Username, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, Name);
        contentValues.put(Col3, Username);
        contentValues.put(Col4, Password);

        long result = db.insert(Table_Name, null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(C3,Username);

        long result1 =  db.insert(Table2,null,contentValues1);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(B2,Username);

        long result2 = db.insert(Table3,null,contentValues2);

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(S2,Username);

        long result3 = db.insert(Table4,null,contentValues3);

        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(U7,Username);

        long result4 = db.insert(Table5,null,contentValues4);

        if (result == -1 || result1 == -1 || result2 == -1 || result3 == -1 || result4 == -1)
            return false;
        else
            return true;
    }

    public Cursor getInformation(dataBase db){
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor cr = sq.rawQuery("Select * From "+ Table_Name,null);
        return cr;
    }

    public void updateTable2(String user,String json){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C2,json);
        sq.update(Table2,cv,"User=?",new String[]{user});
    }

    public void updateTable3(String user,String json){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(B3,json);
        sq.update(Table3,cv,B2 +"=?",new String[]{user});
    }
}

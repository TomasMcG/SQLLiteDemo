package com.example.sqllitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //this is called the first time a database is accessed . There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_NAME TEXT, CUSTOMER_AGE INT, ACTIVE_CUSTOMER BOOL)";
        db.execSQL(createTableStatement);
    }

    //this is called if the database version changes. It prevents previous users apps from breaking when you change the database design. Updates Schema.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}

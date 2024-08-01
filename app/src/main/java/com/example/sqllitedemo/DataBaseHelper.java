package com.example.sqllitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //this is called the first time a database is accessed . There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL)";
        db.execSQL(createTableStatement);
    }

    //this is called if the database version changes. It prevents previous users apps from breaking when you change the database design. Updates Schema.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase(); //come from default properites of extends SQLiteOpenHelper
        //to insert data write, to retries data read
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModel.isActive()); //associate each hash value with value from get
        //auto increment id column in database, no need to do cv put here, would need to specify in content values if not auto increment
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        //returns positive number for success, negative if failure
        if (insert == -1) {
            return false;
        }
        else  {
            return true;
        }
      }

      public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();
        //need to get data from the database
            String queryString = "Select * From " + CUSTOMER_TABLE; //use the customer table constant
          SQLiteDatabase db = this.getReadableDatabase(); //this is a reference only need readable , not writable, writable locks data file so other processes cant access it, bottleneck
          Cursor cursor = db.rawQuery(queryString,null);//rawQuery has cursor return type, introduce local variable with lightbul, Cursor is the result set in SQLite,complex array of items
          //rawquery neesd sql string which is the sql statement and seleciton Args, ?s in statement that can be replaced, we will use null

          if(cursor.moveToFirst())  //if firest result in result set is true and exists, thenm there was results
          {
              //if results loop throught the cursor(result set) and create new customer objects. Put them into the reutrn list, new customer object for each row
              do{
                  //local variable of type to come from database
                  int customerID = cursor.getInt(0); //position 0
                  String customerName = cursor.getString(1);
                  int customerAge = cursor.getInt(2);
                  boolean customerActive = cursor.getInt(3) == 1 ? true: false; //no booleans, only ints of 0 or 1, take int and convert ot boolean , turnary operator, if position 3 equuals 1 then result is true, other false

                  CustomerModel newCustomer = new CustomerModel(customerID,customerName,customerAge,customerActive);
                  returnList.add(newCustomer);
              }
              while(cursor.moveToFirst()); //do it while there is new lines

          }
          else{
              //failure, do not add anything to the list , null list

          }

          //need to close conneciton to cursor and the db after yourself when done
          cursor.close();
          db.close();
          return returnList;

      }

    }


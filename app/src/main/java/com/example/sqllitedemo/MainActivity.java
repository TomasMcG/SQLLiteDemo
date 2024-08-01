package com.example.sqllitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age) ;
        et_name = findViewById(R.id.et_name);
        sw_activeCustomer = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerModel customerModel;
                //define variable with null value outside of try catch
try {
     customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
}
catch(Exception E){
    
    Toast.makeText(MainActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
//if fails provide fault values
    customerModel = new CustomerModel(-1,"error",0, false);
}

DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(customerModel);//add the created customer to the database with method
                Toast.makeText(MainActivity.this, "Success=" + success, Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<CustomerModel> everyone = dataBaseHelper.getEveryone();
                ArrayAdapter customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,everyone);
                //the type is a list, need list of customer model, mainActivity.this for conext, simple list 1 is a predefined adapter that gives 1 string per line,
                // this is ismplest array adapter, the last parameter is the list of items you want to show in array adapter.
                //associate the array dapater to control on screen
                lv_customerList.setAdapter(customerArrayAdapter);
                //can now see in the list view on the screen our list of customers

               // Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
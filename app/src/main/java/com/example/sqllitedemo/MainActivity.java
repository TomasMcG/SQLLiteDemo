package com.example.sqllitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;



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


         dataBaseHelper = new DataBaseHelper(MainActivity.this);
       // List<CustomerModel> everyone = dataBaseHelper.getEveryone(); old way for everyone
        //the type is a list, need list of customer model, mainActivity.this for conext, simple list 1 is a predefined adapter that gives 1 string per line,
        // this is ismplest array adapter, the last parameter is the list of items you want to show in array adapter.
        //associate the array dapater to control on screen
        showCustomersOnListView(dataBaseHelper);

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
                showCustomersOnListView(dataBaseHelper);
            }
        });
        //item click listener gives you a nubmer on which guy was clicked
        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //the adapterView is the parent,view is the list view the int i is position, long l is the id
                //position is which item was clicked in the list
                //parent of the listener is the list view,adapter view here. put in the i position, tells you which person clicked, error datatype, needcustomer model,ew have generic object,
                //because we have customermodel in the list can safely do a cast
                CustomerModel clickedCustomer = (CustomerModel) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedCustomer);
                //update the list view
                showCustomersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted" + clickedCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 dataBaseHelper = new DataBaseHelper(MainActivity.this);
                //List<CustomerModel> everyone = dataBaseHelper.getEveryone();



                //the type is a list, need list of customer model, mainActivity.this for conext, simple list 1 is a predefined adapter that gives 1 string per line,
                // this is ismplest array adapter, the last parameter is the list of items you want to show in array adapter.
                //associate the array dapater to control on screen
                showCustomersOnListView(dataBaseHelper);
                //can now see in the list view on the screen our list of customers

               // Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showCustomersOnListView(DataBaseHelper dataBaseHelper) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}
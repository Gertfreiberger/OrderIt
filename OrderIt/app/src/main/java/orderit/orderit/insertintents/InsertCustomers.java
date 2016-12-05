package orderit.orderit.insertintents;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import orderit.orderit.R;
import orderit.orderit.helpclasses.DatabaseHandler;

public class InsertCustomers extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHandler insert_customer_base_;
    private LinearLayout customer_layout_;
    private EditText text_box_customer_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        customer_layout_ = (LinearLayout) findViewById(R.id.linearlyout_insert_customer);
        text_box_customer_ = (EditText) findViewById(R.id.text_box_insert_customer);
        insert_customer_base_ = new DatabaseHandler(getApplicationContext());
        createCustomerList();

    }

    public void saveNewCustomer(View v) {
        String new_customer = text_box_customer_.getText().toString();
        new_customer  = new_customer.trim();
        if(new_customer.isEmpty()) {
            return;
        }

        insert_customer_base_.insertCustomer(new_customer);
        text_box_customer_.setText("");
        createCustomerList();
    }

    public void leaveInsertCustomer(View v) {
        //deleteDrinks();
        finish();
    }

    public void createCustomerList() {
        ArrayList<String> customer;
        customer = insert_customer_base_.readCustomer();

        customer_layout_.removeAllViews();

        for (int i = 0; i < customer.size(); i++){
            TextView view = new TextView(this);
            view.setText(customer.get(i));
            view.setTextSize(getResources().getDimension(R.dimen.text_size_for_lists));
            view.setPadding(0,5,0,5);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.BLACK);
            view.setOnClickListener(this);
            customer_layout_.addView(view);
        }
    }


    public void deleteCustomers() {
        ArrayList<String> customer;
        customer = insert_customer_base_.readCustomer();

        for (int i = 0; i < customer.size(); i++){
            insert_customer_base_.deleteCustomer(customer.get(i));
        }

        customer_layout_.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        final TextView v = (TextView) view;

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Kunden");
        dlg.setCancelable(false);
        dlg.setMessage(v.getText() + " wirklich löschen?");

        dlg.setPositiveButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dlg.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                insert_customer_base_.deleteCustomer(v.getText().toString());
                customer_layout_.removeView(v);
            }
        });

        dlg.create().show();
    }

}


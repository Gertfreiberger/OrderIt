package orderit.orderit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import orderit.orderit.helpclasses.Customer;
import orderit.orderit.helpclasses.DatabaseHandler;
import orderit.orderit.insertintents.InsertBottles;
import orderit.orderit.insertintents.InsertCustomers;
import orderit.orderit.insertintents.InsertDrinks;


public class OrderIt extends AppCompatActivity {

    private DatabaseHandler dbase_;
    private ArrayList<Customer> customer_list_;
    private LinearLayout orders_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_it);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orders_ = (LinearLayout) findViewById(R.id.content_orders);
        customer_list_ = new ArrayList<Customer>();
        dbase_ = new DatabaseHandler(getApplicationContext());
        initDBase();
        createCustomerList();
    }

    @Override
    public void onResume() {
        super.onResume();

        createCustomerList();
    }

    public void initDBase() {
        dbase_.insertDrink("Apfel");
        dbase_.insertDrink("Birne");
        dbase_.insertDrink("Haselnuss");
        dbase_.insertDrink("Advent");
        dbase_.insertDrink("Beeren");
        dbase_.insertBottle("Stern", "200ml");
        dbase_.insertBottle("Bär", "200ml");
        dbase_.insertBottle("Sonne", "200ml");
        dbase_.insertCustomer("Franz");
        dbase_.insertCustomer("Horst");
        dbase_.insertCustomer("Nelli");
    }

    public void createCustomerList() {

        orders_.removeAllViews();
        customer_list_.clear();
        ArrayList<String> customers;
        customers = dbase_.readCustomer();

        for(int i = 0; i < customers.size(); i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            orders_.addView(layout);
            customer_list_.add(new Customer(customers.get(i), layout, getApplicationContext()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_it, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_drinks) {
            Intent insert_drink = new Intent(this, InsertDrinks.class);
            startActivity(insert_drink);
        }
        else if (id == R.id.action_bottles) {
            Intent insert_bottle = new Intent(this, InsertBottles.class);
            startActivity(insert_bottle);
        }
        else if (id == R.id.action_customer) {
            Intent insert_customer = new Intent(this, InsertCustomers.class);
            startActivity(insert_customer);
        }

        return super.onOptionsItemSelected(item);
    }

}



package orderit.orderit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import orderit.orderit.helpclasses.Customer;
import orderit.orderit.helpclasses.DatabaseHandler;
import orderit.orderit.helpclasses.Order;
import orderit.orderit.insertintents.InsertBottles;
import orderit.orderit.insertintents.InsertCustomers;
import orderit.orderit.insertintents.InsertDrinks;
import orderit.orderit.insertintents.InsertOrder;
import orderit.orderit.overviewintents.OrderOverview;


public class OrderIt extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHandler dbase_;
    private ArrayList<Customer> customer_list_;
    private LinearLayout orders_;
    private AlertDialog.Builder build_;
    private Dialog dialog_description_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_it);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orders_ = (LinearLayout) findViewById(R.id.content_orders);
        customer_list_ = new ArrayList<Customer>();
        dbase_ = new DatabaseHandler(getApplicationContext());
        build_ = new AlertDialog.Builder(this);
        dialog_description_ = new Dialog(OrderIt.this);
        //initDBase();
        createCustomerList();
    }

    @Override
    public void onResume() {
        super.onResume();
        createCustomerList();
    }

    public void initDBase() {
        dbase_.insertDrink("Tussi");
        dbase_.insertDrink("Zirbe");
        dbase_.insertDrink("Haselnuss");
        dbase_.insertDrink("Advent");
        dbase_.insertDrink("Beeren");
        dbase_.insertBottle("Stern", "200ml");
        dbase_.insertBottle("Bär", "200ml");
        dbase_.insertBottle("Sonne", "200ml");
        dbase_.insertBottle("Engel", "200ml");
        dbase_.insertBottle("Bega", "100ml");
        dbase_.insertBottle("Bega", "500ml");
        dbase_.insertCustomer("Franz");
        dbase_.insertCustomer("Horst");
        dbase_.insertCustomer("Nelli");
    }

    public void createCustomerList() {

        orders_.removeAllViews();
        customer_list_.clear();
        final ArrayList<String> customers;
        customers = dbase_.readCustomer();

        for(int i = 0; i < customers.size(); i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setBackgroundColor(Color.BLACK);
            orders_.addView(layout);
            customer_list_.add(new Customer(customers.get(i), layout, getApplicationContext()));
        }

        for(int i= 0; i < customer_list_.size(); i++) {

            Button new_order = new Button(this);
            new_order.setOnClickListener(this);

            Button open_order = new Button(this);
            open_order.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Button butt = (Button) view;
                    for(Customer cust: customer_list_) {
                        if(butt.equals(cust.getOpenOrderButton())) {

                            if(cust.getStateOpen() == false) {
                                cust.setStateOpen(true);
                                cust.getOpenOrderButton().setText("-");
                                cust.getOpenOrderButton().setTextColor(Color.RED);
                                openOrders(cust);
                            }
                            else {
                                closeOrders(cust);
                            }
                        }
                    }
                }
            });
            customer_list_.get(i).createCustomer(open_order,new_order);
        }
    }

    public void openOrders(final Customer customer) {

        customer.getLayoutOrders().removeAllViews();
        customer.getOrdersToDelete().clear();
        customer.setOrders(dbase_.readOrdersforCustomer(customer.getName()));

        if(customer.getOrders().size() == 0) {
            closeOrders(customer);
            return;
        }

        for(Order order: customer.getOrders()) {

            Button delete_order = new Button(this);
            TextView order_view = new TextView(this);

            delete_order.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    final Button butt = (Button) view;


                    build_.setTitle("Kunde: " + customer.getName());
                    build_.setMessage("Bestellung " + customer.getOrdersToDelete().get(butt).convertOrderToString() + " wirklich löschen?");
                    build_.setCancelable(false);

                    build_.setPositiveButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    build_.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbase_.deleteOrder(customer.getOrdersToDelete().get(butt).getId());
                            openOrders(customer);
                        }
                    });
                    build_.create().show();
                }
            });

            order_view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    final TextView text_view = (TextView) view;


                    dialog_description_.setContentView(R.layout.edit_description);
                    dialog_description_.setTitle("Anmerkungen");
                    dialog_description_.setCancelable(false);
                    final EditText dest = (EditText) dialog_description_.findViewById(R.id.text_box_edit_dest);
                    dest.setText(customer.getOrderTextViews().get(text_view).getDescription());
                    Button save = (Button) dialog_description_.findViewById(R.id.button_edit_dest_save);
                    Button cancel = (Button) dialog_description_.findViewById(R.id.button_edit_dest_cancel);

                    save.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            customer.getOrderTextViews().get(text_view).setDescription(dest.getText().toString().trim());
                            dbase_.updateOrder(customer.getOrderTextViews().get(text_view));
                            openOrders(customer);
                            dialog_description_.dismiss();

                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            dialog_description_.dismiss();
                        }
                    });
                    dialog_description_.show();
                }
            });

            customer.openOrder(order,delete_order, order_view);
        }
    }

    public void closeOrders(Customer cust) {
        cust.closeOrders();
    }

    public void openOrderOverview(View v) {

        Intent overview = new Intent(this,OrderOverview.class);
        startActivity(overview);
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

    @Override
    public void onClick(View view) {

        Button butt = (Button) view;
        for(Customer cust: customer_list_) {
            if(butt.equals(cust.getNewOrderButton())) {
                Intent order = new Intent(this, InsertOrder.class);
                order.putExtra("customer_ordered", cust.getName());
                startActivity(order);
            }
        }
    }
}



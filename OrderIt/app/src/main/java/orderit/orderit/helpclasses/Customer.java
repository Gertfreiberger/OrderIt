package orderit.orderit.helpclasses;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.LinkAddress;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import orderit.orderit.OrderIt;
import orderit.orderit.R;
import orderit.orderit.insertintents.InsertOrder;


public class Customer implements View.OnClickListener{

    private String name_;
    private LinearLayout content_layout_;
    private LinearLayout layout_orders_;
    private Context context_;
    private DatabaseHandler dbase_orders_customer_;
    private Button open_order_;
    private HashMap<Button, Order> orders_to_delete_;
    private boolean state_open_;
    private ArrayList<Order> orders_;


    public Customer(String name, LinearLayout content_layout, Context context) {
        name_ = name;
        content_layout_ = content_layout;
        context_ = context;
        state_open_ = false;
        open_order_ = new Button(context_);
        orders_to_delete_ = new HashMap<Button, Order>();
        dbase_orders_customer_ = new DatabaseHandler(context_);
        initCustomer();

        layout_orders_ = new LinearLayout(context_);
        layout_orders_.setOrientation(LinearLayout.VERTICAL);
        layout_orders_.setBackgroundColor(Color.DKGRAY);
        layout_orders_.setPadding(0,15,0,15);
        content_layout_.addView(layout_orders_);

    }

    public void initCustomer() {

        LinearLayout customer_layout_buttons = new LinearLayout(context_);
        customer_layout_buttons.setOrientation(LinearLayout.HORIZONTAL);
        customer_layout_buttons.setWeightSum(5.0f);
        customer_layout_buttons.setBackgroundColor(Color.DKGRAY);
        content_layout_.addView(customer_layout_buttons);

        TextView text_view = new TextView(context_);
        text_view.setText(name_);
        text_view.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_customers_lists));
        text_view.setGravity(Gravity.CENTER);
        text_view.setTextColor(Color.WHITE);
        text_view.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3f));
        customer_layout_buttons.addView(text_view);

        Button new_order = new Button(context_);
        new_order.setText("*");
        new_order.setBackgroundColor(Color.WHITE);
        new_order.setTextColor(Color.GREEN);
        new_order.setHeight(context_.getResources().getDimensionPixelOffset(R.dimen.new_order_button));
        new_order.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_lists));
        new_order.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        new_order.setOnClickListener(this);
        customer_layout_buttons.addView(new_order);


        open_order_.setText("+");
        open_order_.setHeight(context_.getResources().getDimensionPixelOffset(R.dimen.new_order_button));
        open_order_.setGravity(Gravity.CENTER);
        open_order_.setBackgroundColor(Color.WHITE);
        open_order_.setTextColor(Color.GREEN);
        open_order_.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_lists));
        open_order_.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        open_order_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(state_open_ == false) {
                    state_open_ = true;
                    open_order_.setText("-");
                    open_order_.setTextColor(Color.RED);
                    openOrders();
                }
                else {
                    closeOrders();
                }
            }
        });
        customer_layout_buttons.addView(open_order_);
    }


    public void openOrders() {
        layout_orders_.removeAllViews();
        orders_to_delete_.clear();
        orders_ = dbase_orders_customer_.readOrders(name_);

        if(orders_.size() == 0) {
            closeOrders();
            return;
        }

        for(Order order: orders_) {

            LinearLayout order_layout_view = new LinearLayout(context_);
            order_layout_view.setOrientation(LinearLayout.HORIZONTAL);
            order_layout_view.setWeightSum(5.0f);
            order_layout_view.setBackgroundColor(Color.DKGRAY);
            order_layout_view.setPadding(0,2,0,2);
            layout_orders_.addView(order_layout_view);

            TextView text_view = new TextView(context_);
            text_view.setText(order.convertOrderToString());
            text_view.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_orders_lists));
            text_view.setTextColor(Color.WHITE);
            text_view.setGravity(Gravity.CENTER);
            text_view.setBackgroundColor(Color.GRAY);
            text_view.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 4f));
            order_layout_view.addView(text_view);

            LinearLayout order_layout_button = new LinearLayout(context_);
            order_layout_button.setOrientation(LinearLayout.VERTICAL);
            order_layout_button.setBackgroundColor(Color.GRAY);
            order_layout_button.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            order_layout_view.addView(order_layout_button);


            final Button delete_order = new Button(context_);
            delete_order.setText("X");
            delete_order.setTextColor(Color.WHITE);
            delete_order.setBackgroundColor(Color.RED);
            delete_order.setPadding(0,2,2,2);
            delete_order.setHeight(context_.getResources().getDimensionPixelOffset(R.dimen.delete_order_button_height));
            delete_order.setWidth(context_.getResources().getDimensionPixelOffset(R.dimen.delete_order_button_width));
            delete_order.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_lists));

            delete_order.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    final Button butt = (Button) view;
                    dbase_orders_customer_.deleteOrder(orders_to_delete_.get(butt).getId());
                    openOrders();
                    //((OrderIt)context_).alert(butt, name_, orders_to_delete_);
                }
            });

            order_layout_button.addView(delete_order);
            orders_to_delete_.put(delete_order,order);
        }

    }

    public void closeOrders() {
        state_open_ = false;
        open_order_.setText("+");
        open_order_.setTextColor(Color.GREEN);
        layout_orders_.removeAllViews();
    }

    public String getName() {
        return name_;
    }

    public LinearLayout getLayout() {
        return content_layout_;
    }

    @Override
    public void onClick(View view) {
        Intent order = new Intent(context_, InsertOrder.class);
        order.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        order.putExtra("customer_ordered", name_);
        context_.startActivity(order);
    }
}




package orderit.orderit.helpclasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.LinkAddress;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import orderit.orderit.R;
import orderit.orderit.insertintents.InsertOrder;


public class Customer implements View.OnClickListener{

    private String name_;
    private LinearLayout content_layout_;
    private Context context_;

    public Customer(String name, LinearLayout content_layout, Context context) {
        name_ = name;
        content_layout_ = content_layout;
        context_ = context;
        initCustomer();

    }

    public void initCustomer() {
        LinearLayout customer_layout_buttons = new LinearLayout(context_);
        customer_layout_buttons.setOrientation(LinearLayout.HORIZONTAL);
        content_layout_.addView(customer_layout_buttons);
        TextView text_view = new TextView(context_);
        text_view.setText(name_);
        text_view.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_customers_lists));
        text_view.setTextColor(Color.BLACK);
        customer_layout_buttons.addView(text_view);
        Button new_order = new Button(context_);
        new_order.setText("+");
        new_order.setWidth(context_.getResources().getDimensionPixelOffset(R.dimen.new_order_button));
        new_order.setHeight(context_.getResources().getDimensionPixelOffset(R.dimen.new_order_button));
        new_order.setTextSize(context_.getResources().getDimension(R.dimen.text_size_for_lists));
        new_order.setOnClickListener(this);
        customer_layout_buttons.addView(new_order);
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




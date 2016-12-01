package orderit.orderit.insertintents;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import orderit.orderit.R;
import orderit.orderit.helpclasses.Bottle;
import orderit.orderit.helpclasses.DatabaseHandler;

public class InsertBottles extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler insert_bottle_base_;
    private LinearLayout bottle_layout_;
    private EditText text_box_bottle_name_;
    private EditText text_box_bottle_size_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bottles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        insert_bottle_base_ = new DatabaseHandler(getApplicationContext());
        bottle_layout_ = (LinearLayout) findViewById(R.id.linearlyout_insert_bottle);
        text_box_bottle_name_ = (EditText) findViewById(R.id.text_box_insert_bottle);
        text_box_bottle_size_ = (EditText) findViewById(R.id.text_box_insert_bottle_size);
        createBottleList();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    public void saveNewBottle(View v) {
        String new_bottle = text_box_bottle_name_.getText().toString();
        String new_size = text_box_bottle_size_.getText().toString();
        new_bottle = new_bottle.trim();
        new_size = new_size.trim();
        if (new_bottle.isEmpty() || new_size.isEmpty()) {
            return;
        }
        insert_bottle_base_.insertBottle(new_bottle, new_size);
        text_box_bottle_name_.setText("");
        text_box_bottle_size_.setText("");
        createBottleList();
    }

    public void leaveInsertBottle(View v) {
        //deleteBottles();
        finish();
    }

    public void createBottleList() {
        ArrayList<Bottle> bottle_list;
        bottle_list = insert_bottle_base_.readBottles();

        bottle_layout_.removeAllViews();

        for (int i = 0; i < bottle_list.size(); i++) {
            TextView view = new TextView(this);
            view.setText(bottle_list.get(i).convertBottletoString());
            view.setTextSize(getResources().getDimension(R.dimen.text_size_for_lists));
            view.setPadding(0, 5, 0, 5);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.BLACK);
            view.setOnClickListener(this);
            bottle_layout_.addView(view);
        }
    }


    public void deleteBottles() {
        ArrayList<Bottle> bottles;
        bottles = insert_bottle_base_.readBottles();

        for (int i = 0; i < bottles.size(); i++) {
            insert_bottle_base_.deleteBottle(bottles.get(i).getBottleName(), bottles.get(i).getBottleSize());
        }

        bottle_layout_.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        TextView v = (TextView) view;
        String[] bottle_parameters = v.getText().toString().split(" ");
        insert_bottle_base_.deleteBottle(bottle_parameters[0], bottle_parameters[2]);
        bottle_layout_.removeView(view);
    }
}


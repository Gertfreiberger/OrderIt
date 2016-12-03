package orderit.orderit.insertintents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import orderit.orderit.R;
import orderit.orderit.helpclasses.Bottle;
import orderit.orderit.helpclasses.DatabaseHandler;

public class InsertOrder extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView list_view_drinks_bottles_;
    private DatabaseHandler insert_order_base_;
    private TextView orderd_drink_;
    private TextView orderd_bottle_;
    private EditText orderd_number_;
    private Button toggle_button_;
    private boolean state_drinks_;
    private ArrayAdapter<String> drinks_;
    private ArrayAdapter<String> bottles_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        state_drinks_ = false;
        list_view_drinks_bottles_ = (ListView) findViewById(R.id.listview_insert_order);
        orderd_drink_ = (TextView) findViewById(R.id.textview_insert_order_drink);
        orderd_bottle_ = (TextView) findViewById(R.id.textview_insert_order_bottle);
        orderd_number_ = (EditText) findViewById(R.id.text_box_insert_order);
        toggle_button_ = (Button) findViewById(R.id.button_insert_order_next);
        insert_order_base_ = new DatabaseHandler(getApplicationContext());
        list_view_drinks_bottles_.setOnItemClickListener(this);
        createDrinkList();
        createBottleList();
        list_view_drinks_bottles_.setAdapter(drinks_);
    }

    public void createDrinkList() {
        ArrayList<String> drinks = insert_order_base_.readDrinks();
        drinks_ = new ArrayAdapter<String>(this, R.layout.text_view_list_item_order, drinks);
    }

    public void createBottleList() {
        ArrayList<Bottle> bottles = insert_order_base_.readBottles();
        ArrayList<String> bottle_names = new ArrayList<String>();
        for (Bottle bottle: bottles) {
            bottle_names.add(bottle.convertBottletoString());
        }

        bottles_ = new ArrayAdapter<String>(this, R.layout.text_view_list_item_order, bottle_names);
    }

    public void leaveInsertOrder(View view) {
        String ordered_drink = orderd_drink_.getText().toString().trim();
        String ordered_bottle = orderd_bottle_.getText().toString().trim();
        String ordered_number = orderd_number_.getText().toString().trim();

        if(ordered_drink.isEmpty() | ordered_bottle.isEmpty() | ordered_number.isEmpty()) {
            return;
        }

        insert_order_base_.insertOrder(ordered_drink,ordered_bottle,getIntent().getStringExtra("customer_ordered"),ordered_number);
        //finish();
    }

    public void bottleNext(View view) {

        if(state_drinks_ == true) {
            state_drinks_ = false;
            list_view_drinks_bottles_.setAdapter(drinks_);
            toggle_button_.setText(R.string.button_order_bottles);
        }
        else {
            state_drinks_ = true;
            list_view_drinks_bottles_.setAdapter(bottles_);
            toggle_button_.setText(R.string.button_order_drinks);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(state_drinks_ == true) {
            orderd_bottle_.setText(adapterView.getItemAtPosition(i).toString());
        }
        else {
            orderd_drink_.setText(adapterView.getItemAtPosition(i).toString());
        }
    }
}

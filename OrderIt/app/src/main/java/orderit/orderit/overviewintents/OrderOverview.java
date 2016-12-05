package orderit.orderit.overviewintents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import orderit.orderit.R;
import orderit.orderit.helpclasses.DatabaseHandler;

public class OrderOverview extends AppCompatActivity {

    private ListView list_view_order_overview_;
    private ArrayAdapter<String> orders_;
    private DatabaseHandler order_overview_base_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list_view_order_overview_ = (ListView) findViewById(R.id.listview_order_overview);
        order_overview_base_ = new DatabaseHandler(getApplicationContext());
        createOrderOVerviewList();
        list_view_order_overview_.setAdapter(orders_);

    }

    public void createOrderOVerviewList() {
        ArrayList<String> drinks = order_overview_base_.readDrinks();
        orders_ = new ArrayAdapter<String>(this, R.layout.text_view_list_item_order, drinks);
    }

    public void closeOrderOverview(View v){
        finish();
    }

}

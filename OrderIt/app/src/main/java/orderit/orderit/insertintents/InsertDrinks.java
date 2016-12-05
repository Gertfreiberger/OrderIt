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

public class InsertDrinks extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHandler insert_drinks_base_;
    private LinearLayout drink_layout_;
    private EditText text_box_drink_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_drinks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drink_layout_ = (LinearLayout) findViewById(R.id.linearlyout_insert_drink);
        text_box_drink_ = (EditText) findViewById(R.id.text_box_insert_drink);
        insert_drinks_base_ = new DatabaseHandler(getApplicationContext());
        createDrinkList();
    }


    public void saveNewDrink(View v) {
        String new_drink = text_box_drink_.getText().toString();
        new_drink  = new_drink.trim();
        if(new_drink.isEmpty()) {
            return;
        }

        insert_drinks_base_.insertDrink(new_drink);
        text_box_drink_.setText("");
        createDrinkList();
    }

    public void leaveInsertDrink(View v) {
        //deleteDrinks();
        finish();
    }

    public void createDrinkList() {
        ArrayList<String> drinks;
        drinks = insert_drinks_base_.readDrinks();

        drink_layout_.removeAllViews();

        for (int i = 0; i < drinks.size(); i++){
            TextView view = new TextView(this);
            view.setText(drinks.get(i));
            view.setTextSize(getResources().getDimension(R.dimen.text_size_for_lists));
            view.setPadding(0,5,0,5);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.BLACK);
            view.setOnClickListener(this);
            drink_layout_.addView(view);
        }
    }


    public void deleteDrinks() {
        ArrayList<String> drinks;
        drinks = insert_drinks_base_.readDrinks();

        for (int i = 0; i < drinks.size(); i++){
            insert_drinks_base_.deleteDrink(drinks.get(i));
        }

        drink_layout_.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        final TextView v = (TextView) view;

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Schnäpse");
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
                insert_drinks_base_.deleteDrink(v.getText().toString());
                drink_layout_.removeView(v);
            }
        });

        dlg.create().show();
    }
}


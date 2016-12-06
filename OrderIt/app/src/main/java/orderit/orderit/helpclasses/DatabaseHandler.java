package orderit.orderit.helpclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;

import orderit.orderit.helpclasses.DatabaseContract;

/**
 * Created by Gert on 29.11.2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "OrderIt.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES_DRINKS =
            "CREATE TABLE " + DatabaseContract.drinks.TABLE_NAME + " (" +
                    DatabaseContract.drinks._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.drinks.COLUMN_NAME_DRINK + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES_BOTTLES =
            "CREATE TABLE " + DatabaseContract.bottles.TABLE_NAME + " (" +
                    DatabaseContract.bottles._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.bottles.COLUMN_NAME_BOTTLE + TEXT_TYPE + COMMA_SEP +
                    DatabaseContract.bottles.COLUMN_SIZE_BOTTLE + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES_CUSTOMER =
            "CREATE TABLE " + DatabaseContract.customer.TABLE_NAME + " (" +
                    DatabaseContract.customer._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.customer.COLUMN_NAME_CUSTOMER + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES_ORDER =
            "CREATE TABLE " + DatabaseContract.order.TABLE_NAME + " (" +
                    DatabaseContract.order._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.order.COLUMN_NAME_DRINK + TEXT_TYPE +  COMMA_SEP +
                    DatabaseContract.order.COLUMN_NAME_CUSTOMER + TEXT_TYPE + COMMA_SEP +
                    DatabaseContract.order.COLUMN_NAME_BOTTLE +TEXT_TYPE + COMMA_SEP +
                    DatabaseContract.order.COLUM_NAME_NUMBER + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES_DRINKS =
            "DROP TABLE IF EXISTS " + DatabaseContract.drinks.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_BOTTLES =
            "DROP TABLE IF EXISTS " + DatabaseContract.bottles.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_CUSTOMER =
            "DROP TABLE IF EXISTS " + DatabaseContract.customer.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_ORDER =
            "DROP TABLE IF EXISTS " + DatabaseContract.order.TABLE_NAME;

    private SQLiteDatabase db_;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_DRINKS);
        db.execSQL(SQL_CREATE_ENTRIES_BOTTLES);
        db.execSQL(SQL_CREATE_ENTRIES_CUSTOMER);
        db.execSQL(SQL_CREATE_ENTRIES_ORDER);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_DRINKS);
        db.execSQL(SQL_DELETE_ENTRIES_BOTTLES);
        db.execSQL(SQL_DELETE_ENTRIES_CUSTOMER);
        db.execSQL(SQL_DELETE_ENTRIES_ORDER);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SQLiteDatabase getDB() {
        return db_;
    }

    public void insertDrink(String name) {

        db_ = this.getWritableDatabase();


        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.drinks.COLUMN_NAME_DRINK, name);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db_.insert(DatabaseContract.drinks.TABLE_NAME, null, values);
    }

    public void deleteDrink(String name) {

        db_ = this.getWritableDatabase();

        // Define 'where' part of query.
        String selection = DatabaseContract.drinks.COLUMN_NAME_DRINK + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { name };
        // Issue SQL statement.
        db_.delete(DatabaseContract.drinks.TABLE_NAME, selection, selectionArgs);
    }

    public void insertBottle(String name, String size) {

        db_ = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.bottles.COLUMN_NAME_BOTTLE, name);
        values.put(DatabaseContract.bottles.COLUMN_SIZE_BOTTLE, size);

        db_.insert(DatabaseContract.bottles.TABLE_NAME, null, values);
    }

    public void deleteBottle(String name, String size) {

        db_ = this.getWritableDatabase();

        String selection = DatabaseContract.bottles.COLUMN_NAME_BOTTLE + " LIKE ? and " + DatabaseContract.bottles.COLUMN_SIZE_BOTTLE + " LIKE ?";
        String[] selectionArgs = { name, size };
        db_.delete(DatabaseContract.bottles.TABLE_NAME, selection, selectionArgs);
    }

    public void insertCustomer(String name) {

        db_ = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.customer.COLUMN_NAME_CUSTOMER, name);

        // Insert the new row, returning the primary key value of the new row
        db_.insert(DatabaseContract.customer.TABLE_NAME, null, values);
    }

    public void deleteCustomer(String name) {

        db_ = this.getWritableDatabase();

        String selection = DatabaseContract.customer.COLUMN_NAME_CUSTOMER + " LIKE ?";
        String[] selectionArgs = { name };
        db_.delete(DatabaseContract.customer.TABLE_NAME, selection, selectionArgs);
    }

    public void insertOrder(String drink, String bottle, String customer, String number) {

        db_ = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.order.COLUMN_NAME_DRINK, drink);
        values.put(DatabaseContract.order.COLUMN_NAME_BOTTLE, bottle);
        values.put(DatabaseContract.order.COLUMN_NAME_CUSTOMER, customer);
        values.put(DatabaseContract.order.COLUM_NAME_NUMBER, number);

        // Insert the new row, returning the primary key value of the new row
        db_.insert(DatabaseContract.order.TABLE_NAME, null, values);
    }

    public void deleteOrder(String id) {

        db_ = this.getWritableDatabase();

        String selection = DatabaseContract.order._ID + " LIKE ?";
        String[] selectionArgs = { id };
        db_.delete(DatabaseContract.order.TABLE_NAME, selection, selectionArgs);

    }

    public ArrayList<String> readDrinks() {

        db_ = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.drinks._ID,
                DatabaseContract.drinks.COLUMN_NAME_DRINK
        };

        // Filter results WHERE "title" = 'My Title'
        // String selection = DatabaseContract.drinks.COLUMN_NAME_DRINK + " = ?";
        // String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.drinks.COLUMN_NAME_DRINK + " COLLATE NOCASE ASC";

        Cursor c = db_.query(
                DatabaseContract.drinks.TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        String drink;
        ArrayList<String> drinks = new ArrayList<String>();
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            //long itemId = c.getLong(c.getColumnIndexOrThrow(DatabaseContract.drinks._ID));
            drink = c.getString(c.getColumnIndexOrThrow(DatabaseContract.drinks.COLUMN_NAME_DRINK));
            drinks.add(drink);
            c.moveToNext();
        }

        return drinks;
    }

    public ArrayList<Bottle> readBottles() {

        db_ = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.bottles._ID,
                DatabaseContract.bottles.COLUMN_NAME_BOTTLE,
                DatabaseContract.bottles.COLUMN_SIZE_BOTTLE
        };

        // Filter results WHERE "title" = 'My Title'
        // String selection = DatabaseContract.drinks.COLUMN_NAME_DRINK + " = ?";
        // String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.bottles.COLUMN_NAME_BOTTLE + " COLLATE NOCASE ASC";

        Cursor c = db_.query(
                DatabaseContract.bottles.TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<Bottle> bottles = new ArrayList<Bottle>();
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            //long itemId = c.getLong(c.getColumnIndexOrThrow(DatabaseContract.drinks._ID));
            bottles.add(new Bottle(c.getString(c.getColumnIndexOrThrow(DatabaseContract.bottles.COLUMN_NAME_BOTTLE)),c.getString(c.getColumnIndexOrThrow(DatabaseContract.bottles.COLUMN_SIZE_BOTTLE))));
            c.moveToNext();
        }

        return bottles;
    }

    public ArrayList<String> readCustomer() {

        db_ = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.customer._ID,
                DatabaseContract.customer.COLUMN_NAME_CUSTOMER
        };

        // Filter results WHERE "title" = 'My Title'
        // String selection = DatabaseContract.drinks.COLUMN_NAME_DRINK + " = ?";
        // String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.customer.COLUMN_NAME_CUSTOMER + " COLLATE NOCASE ASC";

        Cursor c = db_.query(
                DatabaseContract.customer.TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        String customer;
        ArrayList<String> customers = new ArrayList<String>();
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            //long itemId = c.getLong(c.getColumnIndexOrThrow(DatabaseContract.drinks._ID));
            customer = c.getString(c.getColumnIndexOrThrow(DatabaseContract.customer.COLUMN_NAME_CUSTOMER));
            customers.add(customer);
            c.moveToNext();
        }

        return customers;
    }

    public ArrayList<Order> readOrdersforCustomer(String customer_name) {

        db_ = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.order._ID,
                DatabaseContract.order.COLUMN_NAME_BOTTLE,
                DatabaseContract.order.COLUMN_NAME_DRINK,
                DatabaseContract.order.COLUM_NAME_NUMBER
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.order.COLUMN_NAME_CUSTOMER + " = ?";
        String[] selectionArgs = { customer_name };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.order.COLUMN_NAME_DRINK + " COLLATE NOCASE ASC";

        Cursor c = db_.query(
                DatabaseContract.order.TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<Order> orders = new ArrayList<Order>();
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            long itemId = c.getLong(c.getColumnIndexOrThrow(DatabaseContract.order._ID));
            orders.add(new Order(itemId,
                                 c.getString(c.getColumnIndexOrThrow(DatabaseContract.order.COLUMN_NAME_DRINK)),
                                 c.getString(c.getColumnIndexOrThrow(DatabaseContract.order.COLUMN_NAME_BOTTLE)),
                                 customer_name,
                                 c.getString(c.getColumnIndexOrThrow(DatabaseContract.order.COLUM_NAME_NUMBER))));
            c.moveToNext();
        }

        return orders;
    }

    public ArrayList<String> readOrderOverviewList() {

        db_ = this.getReadableDatabase();

        ArrayList<String> drinks = readDrinks();
        ArrayList<Bottle> bottle_list = readBottles();
        ArrayList<String> bottles = new ArrayList<String>();
        ArrayList<String> order_overview = new ArrayList<String>();
        String string_number;
        int number = 0;

        String[] projection = {
                DatabaseContract.order.COLUMN_NAME_BOTTLE,
                DatabaseContract.order.COLUMN_NAME_DRINK,
                DatabaseContract.order.COLUM_NAME_NUMBER
        };

        String sortOrder =
                DatabaseContract.order.COLUMN_NAME_CUSTOMER + " COLLATE NOCASE ASC";

        String selection = DatabaseContract.order.COLUMN_NAME_DRINK + " = ? and " + DatabaseContract.order.COLUMN_NAME_BOTTLE + " = ?";


        for(int i = 0; i < bottle_list.size(); i++) {
            bottles.add(bottle_list.get(i).convertBottletoString());
        }

        for(String drink: drinks) {

            for(String bottle: bottles) {

                String[] selectionArgs = { drink, bottle };

                Cursor c = db_.query(
                        DatabaseContract.order.TABLE_NAME,       // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                                     // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        sortOrder                                 // The sort order
                );

                if( c.moveToFirst() ) {
                    number = 0;
                    for(int i = 0; i < c.getCount(); i++) {
                        string_number = c.getString(c.getColumnIndexOrThrow(DatabaseContract.order.COLUM_NAME_NUMBER));
                        number += Integer.parseInt(string_number);

                        c.moveToNext();
                    }

                    order_overview.add(drink + " " + bottle + " " + number);
                }
            }

        }
        return order_overview;

    }

}

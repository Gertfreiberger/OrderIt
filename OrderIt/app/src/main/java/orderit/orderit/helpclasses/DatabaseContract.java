package orderit.orderit.helpclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Gert on 29.11.2016.
 */


public final class DatabaseContract {


    private DatabaseContract() {
    }

    /* Inner class that defines the table contents */
    public static class drinks implements BaseColumns {
        public static final String TABLE_NAME = "drinks";
        public static final String COLUMN_NAME_DRINK = "name";
    }

    public static class bottles implements BaseColumns {
        public static final String TABLE_NAME = "bottles";
        public static final String COLUMN_NAME_BOTTLE = "name";
        public static final String COLUMN_SIZE_BOTTLE = "size";
    }

    public static class customer implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_NAME_CUSTOMER = "name";
    }

    public static class order implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_NAME_DRINK = "drink";
        public static final String COLUMN_NAME_CUSTOMER = "customer";
        public static final String COLUMN_NAME_BOTTLE = "bottle";
        public static final String COLUM_NAME_NUMBER = "number";
    }
}

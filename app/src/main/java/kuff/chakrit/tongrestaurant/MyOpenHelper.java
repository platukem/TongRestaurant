package kuff.chakrit.tongrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Platukem on 9/24/2015.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit
    private static final String DATABASE_NAME = "Restaurant.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER = "create table userTABLE (_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name Text);";
    private static final String CREATE_FOOD = "create table foodTABLE (_id integer primary key, " +
            "Food text, " +
            "Source text, " +
            "Price text)";


    public MyOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }   //Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_FOOD);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}      //Main Class

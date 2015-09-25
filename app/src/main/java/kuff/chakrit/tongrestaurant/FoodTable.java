package kuff.chakrit.tongrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Platukem on 9/24/2015.
 */
public class FoodTable {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQSqLiteDatabase, readSqLiteDatabase;

    public static final String TABLE_FOOD = "foodTABLE";
    public static final String COLUMN_ID_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";



    public FoodTable(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        writeSQSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   //Constructor

    //Update Food
    public long addNewFood(String strFood, String strSource, String strPrice) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_FOOD, strFood);
        objContentValues.put(COLUMN_SOURCE, strSource);
        objContentValues.put(COLUMN_PRICE, strPrice);
        return writeSQSqLiteDatabase.insert(TABLE_FOOD, null, objContentValues);

    }


}   //Main Class

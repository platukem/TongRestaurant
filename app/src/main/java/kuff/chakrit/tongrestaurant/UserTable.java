package kuff.chakrit.tongrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Platukem on 9/24/2015.
 */
public class UserTable {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String TABLE_USER = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";


    public UserTable(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();
    }   //Constructor


    //Search User
    public String[] searchUser(String strUser) {
        try {

            String[] strResult = null;
            Cursor objCursor = readSqLiteDatabase.query(TABLE_USER,
                    new String[]{COLUMN_ID_USER, COLUMN_USER, COLUMN_PASSWORD, COLUMN_NAME},
                    COLUMN_USER + "=?",
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);
            if (objCursor != null) {
                if (objCursor.moveToFirst()) {

                    strResult = new String[4];
                    strResult[0] = objCursor.getString(0);
                    strResult[1] = objCursor.getString(1);
                    strResult[2] = objCursor.getString(2);
                    strResult[3] = objCursor.getString(3);

                }   //if
            }   //if

            objCursor.close();
            return strResult;

        } catch (Exception e) {
            return null;
        }

        //return new String[0];
    }


    //Update User
    public long addNewUser(String strUser, String strPassword, String strName) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_NAME, strName);

        return writeSqLiteDatabase.insert(TABLE_USER, null, objContentValues);
    }



}   //Main Class

//add user

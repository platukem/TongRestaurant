package kuff.chakrit.tongrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTable objUserTable;
    private FoodTable objFoodTable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create and Connect SQLite
        createAndConnected();

        //Test add new value
        //testAddNewValue();

        //Delete All Data
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }   //Main Method  onCreate

    private void synJSONtoSQLite() {

        //0. Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTimes = 0;
        while (intTimes <= 1) {

            //Constant
            InputStream objInputStream = null;
            String strJSON = null;


            intTimes += 1;

        }   //while


    }   //synJSONtoSQLite

    private void deleteAllData() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("Restaurant.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("foodTABLE", null, null);
    }

    private void testAddNewValue() {
        objUserTable.addNewUser("testUser", "testPass", "โดรามอน");
        objFoodTable.addNewFood("testFood", "testSource", "4444");
    }

    private void createAndConnected() {
        objUserTable = new UserTable(this);
        objFoodTable = new FoodTable(this);
    }
}   //Main Class

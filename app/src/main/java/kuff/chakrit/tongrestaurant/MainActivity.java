package kuff.chakrit.tongrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTable objUserTable;
    private FoodTable objFoodTable;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();


        //Create and Connect SQLite
        createAndConnected();

        //Test add new value
        //testAddNewValue();

        //Delete All Data
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }   //Main Method  onCreate

    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")) {

            //Have space
            myAlertDialog("มีช่องว่าง","กรุณากรอกทุกช่อง นะคะ");

        } else {

            //No Space
            checkUser();


        }


    }   //clickLogin

    private void checkUser() {
        try {

            String[] strMyResult = objUserTable.searchUser(userString);

            if (passwordString.equals(strMyResult[2])) {

                welcome(strMyResult[3]);

            } else {

                myAlertDialog("Password False", "Please try again Password false");

            }

        } catch (Exception e) {
            myAlertDialog("No User", "No " + userString + " in my database");
        }


    }   //Check User

    private void welcome(String strName) {

    }   //Welcome

    private void myAlertDialog(String strTitle, String strMessage) {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }   //My Alert Dialog

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    private void synJSONtoSQLite() {

        //0. Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTimes = 0;
        while (intTimes <= 1) {

            //Constant
            InputStream objInputStream = null;
            String strJSON = null;
            String strUrlUser = "http://swiftcodingthai.com/24Sep/php_get_data_tong.php";
            String strUrlFood = "http://swiftcodingthai.com/24Sep/php_get_data_food_tong.php";
            HttpPost objHttpPost;

            //1.Create InputStream
            try {
                HttpClient objHttpClient = new DefaultHttpClient();
                if (intTimes != 1) {
                    objHttpPost = new HttpPost(strUrlUser);
                } else {
                    objHttpPost = new HttpPost(strUrlFood);
                }

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("Rest", "InputStream ==>" + e.toString());
            }


            //2.Create strJSON
            try {
                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = objBufferedReader.readLine()) != null ) {
                    objStringBuilder.append(strLine);
                }   //while
                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d("Rest", "strJSON ==>" + e.toString());
            }

            //3.Update SQLite
            try {
                final JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {

                    JSONObject object = objJsonArray.getJSONObject(i);

                    if (intTimes < 1) {

                        // For UserTABLE
                        String strUser = object.getString("User");
                        String strPassword = object.getString("Password");
                        String strName = object.getString("Name");
                        objUserTable.addNewUser(strUser, strPassword, strName);

                    } else {

                        //For foodTABLE
                        String strFood = object.getString("Food");
                        String strSource = object.getString("Source");
                        String strPrice = object.getString("Price");
                        objFoodTable.addNewFood(strFood, strSource, strPrice);

                    }
                }

            } catch (Exception e) {
                Log.d("Rest", "Update SQLite ==>" + e.toString());
            }


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

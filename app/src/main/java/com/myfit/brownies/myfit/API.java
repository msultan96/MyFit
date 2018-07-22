package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 11/16/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class API extends AppCompatActivity {

    private String TAG = API.class.getSimpleName();

    //private ProgressDialog pDialog;
    private ListView lv;
    Calendar calendar;
    Food FoodGroup;
    String string;

    // URL to get contacts JSON
    private String url;

    ChainedHashTable<Food> FoodArray= new ChainedHashTable<>(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_diary);

        lv = (ListView) findViewById(R.id.list);
        new GetFood().execute();
    }

    public String getCurrentDay(){
        String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return daysArray[day];
    }



    /**
     * Async task class to get json by making HTTP call
     */
    private class GetFood extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            /*
            pDialog = new ProgressDialog(API.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            */

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            string = getIntent().getStringExtra("Search");
            url = "https://api.nutritionix.com/v1_1/search/" + string + "?results=0:1&fields=item_name,brand_name,nf_calories,nf_total_carbohydrate,nf_protein,nf_total_fat&appId=5bdd20e0&appKey=6aa652ff3c10decb5776c6bdb5f40930";

            Log.v(string, "Search: ");

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray Food = jsonObj.getJSONArray("hits");

                    for (int i = 0; i < Food.length(); i++) {
                        JSONObject all = Food.getJSONObject(i);
                        JSONObject fields = all.getJSONObject("fields");

                        String itemName = fields.optString("item_name");
                        String BrandName = fields.optString("brand_name");
                        String Calories = fields.optString("nf_calories");
                        String Protein = fields.optString("nf_protein");
                        String Carbs = fields.optString("nf_total_carbohydrate");
                        String Fats = fields.optString("nf_total_fat");

                        FoodGroup = new Food();

                        FoodGroup.setCalories(Calories);
                        FoodGroup.setProtein(Protein);
                        FoodGroup.setCarbs(Carbs);
                        FoodGroup.setFats(Fats);
                        FoodGroup.setBName(BrandName);
                        FoodGroup.setIName(itemName);

                        calendar = Calendar.getInstance();
                        FoodArray.add1(FoodGroup, calendar.get(Calendar.DAY_OF_WEEK) - 1);
                    }

                } catch (final JSONException e) {
                    Log.v(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /*
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            CustomAdapter adapter  = new CustomAdapter(API.this, FoodArray);

            lv.setAdapter(adapter);
        }

    }
}

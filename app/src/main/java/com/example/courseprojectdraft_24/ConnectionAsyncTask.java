package com.example.courseprojectdraft_24;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
public class ConnectionAsyncTask extends AsyncTask<String, String,String> {
    public static List<Car> cars;
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            ((MainActivity) activity).setButtonText("connection failed");
            Toast.makeText(activity, "Faild to connect to the API", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            try {
                ((MainActivity) activity).setButtonText("connected");
                // tost message for the success loading of the cars data in the arraylist
                Toast.makeText(activity, "Success to connect to the API", Toast.LENGTH_SHORT).show();
                cars = CarJasonParser.getObjectFromJason(s);
                ((MainActivity) activity).proceedToLoginAndRegistration(cars);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in parsing JSON data from the API", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // getter method for the cars arraylist that contains the data from the API
    //public static List<Car> getCars() {
     //   return cars;
   // }
}
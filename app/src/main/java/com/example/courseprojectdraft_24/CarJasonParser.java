package com.example.courseprojectdraft_24;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class CarJasonParser {
    public static List<Car> getObjectFromJason(String jason) {
        List<Car> cars;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            cars = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car car = new Car();
                car.setId(jsonObject.getInt("id"));
                car.setType(jsonObject.getString("type"));
                cars.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cars;
    }
}
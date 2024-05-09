package com.example.courseprojectdraft_24;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CarDataBaseHelper extends SQLiteOpenHelper {

    public static Car newCar = new Car();
    public static final String CAR_TABLE_NAME = "Car";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_OFFERS = "offers";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_DISTANCE = "distance";
    private static final String DATABASE_NAME = "carDb";
    private static final int DATABASE_VERSION = 2;
    public CarDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public CarDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private static final String CREATE_SQL_TABLE_CAR = "CREATE TABLE " + CAR_TABLE_NAME + "("
            + COLUMN_ID + " TEXT PRIMARY KEY, "
            + COLUMN_TYPE + " TEXT, "
            + COLUMN_MODEL + " TEXT, "
            + COLUMN_PRICE + " TEXT, "
            + COLUMN_OFFERS + " TEXT, "
            + COLUMN_YEAR + " TEXT, "
            + COLUMN_DISTANCE + " TEXT);";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SQL_TABLE_CAR);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public boolean addCar(Car car) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, car.getId());
        contentValues.put(COLUMN_TYPE, car.getType());
        contentValues.put(COLUMN_MODEL, car.getModel());
        contentValues.put(COLUMN_PRICE, car.getPrice());
        contentValues.put(COLUMN_OFFERS, car.getOffers());
        contentValues.put(COLUMN_YEAR, car.getYear());
        contentValues.put(COLUMN_DISTANCE, car.getDistance());
        newCar = car;
        if (!checkID(car.getId())) {
            sqLiteDatabase.insert(CAR_TABLE_NAME, null, contentValues);
         } else {
            sqLiteDatabase.update(CAR_TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(car.getId())});
         }
        return true;

    }
    public boolean checkID(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + CAR_TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                newCar.setId(cursor.getInt(0));
                newCar.setType(cursor.getString(1));
                newCar.setModel(cursor.getString(2));
                newCar.setPrice(cursor.getDouble(3));
                newCar.setOffers(cursor.getDouble(4));
                newCar.setYear(cursor.getInt(5));
                newCar.setDistance(cursor.getDouble(6));
            }
            return true;
        } else {
            return false;
        }
    }
    public Cursor getCarDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_TYPE + ", " + COLUMN_MODEL + ", " + COLUMN_PRICE + ", " + COLUMN_OFFERS + ", " + COLUMN_YEAR + ", " + COLUMN_DISTANCE + " FROM " + CAR_TABLE_NAME, null);
    }
    public String getCarInformation(int carId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SELECTing Type and Model of the car with id = carId
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TYPE + ", " + COLUMN_MODEL + " FROM " + CAR_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + carId, null);

        String carInformation = "";

        if (cursor != null && cursor.moveToFirst()) {
            // Extracting values from the Cursor
            @SuppressLint("Range") String carType = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") String carModel = cursor.getString(cursor.getColumnIndex(COLUMN_MODEL));

            // Creating a String with car information
            carInformation = carType + " " + carModel;

            // Closing the Cursor
            cursor.close();
        }

        return carInformation;
    }
    public Cursor filterCars(String name, String model, String price) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_TYPE,
                COLUMN_MODEL,
                COLUMN_PRICE,
                COLUMN_OFFERS,
                COLUMN_YEAR,
                COLUMN_DISTANCE
        };

        // Define the selection criteria based on the provided filters
        String selection = "";
        ArrayList<String> selectionArgsList = new ArrayList<>();

        if (!name.isEmpty()) {
            selection += COLUMN_TYPE + " LIKE ? ";
            selectionArgsList.add("%" + name + "%");
        }

        if (!model.isEmpty()) {
            if (!selection.isEmpty()) {
                selection += "AND ";
            }
            selection += COLUMN_MODEL + " LIKE ? ";
            selectionArgsList.add("%" + model + "%");
        }

        if (!price.isEmpty()) {
            if (!selection.isEmpty()) {
                selection += "AND ";
            }
            selection += COLUMN_PRICE + " <= ? ";
            selectionArgsList.add(price);
        }

        String[] selectionArgs = selectionArgsList.toArray(new String[0]);

        return db.query(CAR_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

}

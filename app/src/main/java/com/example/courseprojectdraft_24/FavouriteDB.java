package com.example.courseprojectdraft_24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouriteDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "IsFavourite";
    private static final int DB_VERSION = 3;
    public static final String COL_CAR_ID = "car_id";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_IS_FAVE = "favourite";
    public static final String FAVORITE_CARS_TABLE_NAME = "favorite_cars";
    public static final String COL_FAVORITE_ID = "favorite_id";
    public FavouriteDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String CREATE_SQL_TABLE_CAR = "CREATE TABLE " + FAVORITE_CARS_TABLE_NAME + "("
            + COL_CAR_ID + " TEXT, "
            + COL_USER_EMAIL + " TEXT, "
            + COL_IS_FAVE + " TEXT);";

    // Create the junction table for favorite cars
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL_TABLE_CAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FAVORITE_CARS_TABLE_NAME);
        onCreate(sqLiteDatabase);
        // Handle database upgrades if needed
    }


    public boolean addFavorite(String carId, String userEmail, String isFavourite) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_CAR_ID, carId);
        values.put(COL_USER_EMAIL, userEmail);
        values.put(COL_IS_FAVE, isFavourite);

        long result = sqLiteDatabase.insert(FAVORITE_CARS_TABLE_NAME, null, values);
        // Check if the insertion was successful
        boolean success = result != -1;
        // Close the database
        sqLiteDatabase.close();

        return success;
    }
    public void deleteFavorite( String userEmail,String carId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(FAVORITE_CARS_TABLE_NAME,
                COL_CAR_ID + " = ? AND " + COL_USER_EMAIL + " = ?",
                new String[]{carId, userEmail});
        sqLiteDatabase.close();
    }

    // delete all fav car for this email
    public void deleteAllFavoritesForUser(String userEmail) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(FAVORITE_CARS_TABLE_NAME,
                COL_USER_EMAIL + " = ?",
                new String[]{userEmail});
        sqLiteDatabase.close();
    }
    public boolean isFavorite(String email, String carId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + FAVORITE_CARS_TABLE_NAME +" WHERE " + COL_USER_EMAIL + " = ?" +" AND " + COL_CAR_ID + " = ?" +
                        " AND " + COL_IS_FAVE + " = 'isFav'",
                new String[]{email, carId}
        );

        boolean isFav = cursor.getCount() > 0;

        cursor.close();
        sqLiteDatabase.close();

        return isFav;
    }

}
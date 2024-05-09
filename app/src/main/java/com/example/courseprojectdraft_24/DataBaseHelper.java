package com.example.courseprojectdraft_24;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static User inputUser = new User();
    public static final String USER_TABLE_NAME = "User";
    public static final String COLUMN_EMAIL_ID = "userEmail";
    public static final String COLUMN_FNAME = "firstName";
    public static final String COLUMN_LNAME = "lastName";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CPASSWORD_ = "cpassword";
    public static final String COLUMN_GENDER = "userGender";
    public static final String COLUMN_COUNTRY = "userCountry";
    public static final String COLUMN_CITY = "userCity";
    public static final String COLUMN_PHONE = "userPhone";
    private static final String DATABASE_NAME = "finalProDB";
    private static final int DATABASE_VERSION = 2;

    // create table sql query --> User Table Creation
    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE_NAME + "("
            + COLUMN_EMAIL_ID + " TEXT PRIMARY KEY, "
            + COLUMN_FNAME + " TEXT, "
            + COLUMN_LNAME + " TEXT, "
            + COLUMN_GENDER + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_CPASSWORD_ + " TEXT,"
            + COLUMN_COUNTRY + " TEXT, "
            + COLUMN_CITY + " TEXT, "
            + COLUMN_PHONE + " TEXT);";
    // Constructors
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USERS);
    }
    public boolean addUser(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (!checkEmailSignIn(user.getUserEmail())) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL_ID, user.getUserEmail());
        contentValues.put(COLUMN_FNAME, user.getFirstName());
        contentValues.put(COLUMN_LNAME, user.getLastName());
        contentValues.put(COLUMN_GENDER, user.getUserGender());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_CPASSWORD_, user.getCpassword());
        contentValues.put(COLUMN_COUNTRY, user.getUserCountry());
        contentValues.put(COLUMN_CITY, user.getUserCity());
        contentValues.put(COLUMN_PHONE, user.getUserPhone());

        inputUser = user;
        sqLiteDatabase.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    } else {
        // User with the specified email already exists
        return false;
    }
    }
    // this function to check if the email is already exist in the database.
    public boolean checkEmailSignIn(String email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_EMAIL_ID + " = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                inputUser.setUserEmail(cursor.getString(0));
                inputUser.setFirstName(cursor.getString(1));
                inputUser.setLastName(cursor.getString(2));
                inputUser.setUserGender(cursor.getString(3));
                inputUser.setPassword(cursor.getString(4));
                inputUser.setCpassword(cursor.getString(5));
                inputUser.setUserCountry(cursor.getString(6));
                inputUser.setUserCity(cursor.getString(7));
                inputUser.setUserPhone(cursor.getString(8));
            }
            return true;
        } else {
            return false;
        }
    }
    // this function to check if the email and password are correct.
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+USER_TABLE_NAME+" WHERE "+COLUMN_EMAIL_ID +" = ? AND "+COLUMN_PASSWORD+" = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public boolean updateUserName(String email, String updatedFirstName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FNAME, updatedFirstName);
        int result = sqLiteDatabase.update(USER_TABLE_NAME, values, COLUMN_EMAIL_ID + " = ?", new String[]{email});
        sqLiteDatabase.close();
        return result > 0;
    }
    public boolean updateUserLastName(String email, String updatedLastName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LNAME, updatedLastName);
        int result = sqLiteDatabase.update(USER_TABLE_NAME, values, COLUMN_EMAIL_ID + " = ?", new String[]{email});
        sqLiteDatabase.close();
        return result > 0;
    }
    public boolean updatePhoneNumber(String email, String updatedPhoneNumber) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, updatedPhoneNumber);
        int result = sqLiteDatabase.update(USER_TABLE_NAME, values, COLUMN_EMAIL_ID + " = ?", new String[]{email});
        sqLiteDatabase.close();
        return result > 0;
    }
    public boolean updatePassword(String email, String updatedPassword) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, updatedPassword);
        values.put(COLUMN_CPASSWORD_, updatedPassword);
        int result = sqLiteDatabase.update(USER_TABLE_NAME, values, COLUMN_EMAIL_ID + " = ?", new String[]{email});
        sqLiteDatabase.close();
        return result > 0;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public Cursor getUserDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_EMAIL_ID + ", " + COLUMN_FNAME + ", " + COLUMN_LNAME + " FROM " + USER_TABLE_NAME, null);
    }


    public Boolean checkEmailLogInExist(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " where "+COLUMN_EMAIL_ID+" = ?", new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        sqLiteDatabase.close();
        return exists;
    }

    public boolean deleteCustomers(String email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.delete(USER_TABLE_NAME, COLUMN_EMAIL_ID + " = ?", new String[]{email});
        sqLiteDatabase.close();
        return result > 0;
    }
    public String getCustomerName(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT " + COLUMN_FNAME + ", " + COLUMN_LNAME +
                        " FROM " + USER_TABLE_NAME +
                        " WHERE " + COLUMN_EMAIL_ID + " = ?",
                new String[]{email}
        );

        String customerName = "";

        try {
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FNAME));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LNAME));
                customerName = firstName + " " + lastName;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return customerName;
    }


}
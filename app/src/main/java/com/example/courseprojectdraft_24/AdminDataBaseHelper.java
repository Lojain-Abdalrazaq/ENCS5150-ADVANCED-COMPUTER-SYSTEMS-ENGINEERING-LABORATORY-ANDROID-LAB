package com.example.courseprojectdraft_24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminDataBaseHelper extends SQLiteOpenHelper {
    public static Admin inputAdmin = new Admin();
    public static final String ADMIN_TABLE_NAME = "Admin";
    public static final String COLUMN_EMAIL_ID = "adminEmail";
    public static final String COLUMN_FNAME = "adminFirstName";
    public static final String COLUMN_LNAME = "adminLastName";
    public static final String COLUMN_PASSWORD = "adminPassword";
    public static final String COLUMN_CPASSWORD_ = "adminCpassword";
    public static final String COLUMN_PHONE = "adminPhone";
    public static final String COLUMN_GENDER = "adminGender";
    public static final String COLUMN_COUNTRY = "adminCountry";
    public static final String COLUMN_CITY = "adminCity";
    private static final String DATABASE_NAME = "adminDb";
    private static final int DATABASE_VERSION = 2;

    public AdminDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public AdminDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String CREATE_SQL_TABLE_ADMIN = "CREATE TABLE " + ADMIN_TABLE_NAME + "("
            + COLUMN_EMAIL_ID + " TEXT PRIMARY KEY, "
            + COLUMN_FNAME + " TEXT, "
            + COLUMN_LNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_CPASSWORD_ + " TEXT, "
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_GENDER + " TEXT, "
            + COLUMN_COUNTRY + " TEXT, "
            + COLUMN_CITY + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SQL_TABLE_ADMIN);
    }
    public boolean checkEmailSignIn(String email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ADMIN_TABLE_NAME + " WHERE " + COLUMN_EMAIL_ID + " = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                inputAdmin.setAdminEmail(cursor.getString(0));
                inputAdmin.setAdminFirstName(cursor.getString(1));
                inputAdmin.setAdminLastName(cursor.getString(2));
                inputAdmin.setAdminGender(cursor.getString(3));
                inputAdmin.setAdminPassword(cursor.getString(4));
                inputAdmin.setAdminConfirmPassword(cursor.getString(5));
                inputAdmin.setAdminCountry(cursor.getString(6));
                inputAdmin.setAdminCity(cursor.getString(7));
                inputAdmin.setAdminPhone(cursor.getString(8));
            }
            return true;
        } else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ADMIN_TABLE_NAME + " WHERE " + COLUMN_EMAIL_ID + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public boolean addAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (!checkEmailSignIn(admin.getAdminEmail())) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL_ID, admin.getAdminEmail());
        contentValues.put(COLUMN_FNAME, admin.getAdminFirstName());
        contentValues.put(COLUMN_LNAME, admin.getAdminLastName());
        contentValues.put(COLUMN_GENDER, admin.getAdminGender());
        contentValues.put(COLUMN_PASSWORD, admin.getAdminPassword());
        contentValues.put(COLUMN_CPASSWORD_, admin.getAdminConfirmPassword());
        contentValues.put(COLUMN_COUNTRY, admin.getAdminCountry());
        contentValues.put(COLUMN_CITY, admin.getAdminCity());
        contentValues.put(COLUMN_PHONE, admin.getAdminPhone());

        inputAdmin = admin;
        sqLiteDatabase.insert(ADMIN_TABLE_NAME, null, contentValues);
        return true;
        } else {
            // Admin with the specified email already exists
            return false;
        }
    }

}
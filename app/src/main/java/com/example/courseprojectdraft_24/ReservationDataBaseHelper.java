package com.example.courseprojectdraft_24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReservationDataBaseHelper extends SQLiteOpenHelper {

        public static Reservation newReservation = new Reservation();
        public static final String RESERVATION_TABLE_NAME = "Reservation";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CAR_ID = "car_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_RESERVATION_DATE = "Date";
        public static final String COLUMN_RESERVATION_TIME = "time";
        public static final String COLUMN_PRICE = "price";
        private static final String DATABASE_NAME = "reservationDb";
        private static final int DATABASE_VERSION = 2;
        public ReservationDataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public ReservationDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        private static final String CREATE_SQL_TABLE_RESERVATION = "CREATE TABLE " + RESERVATION_TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY, "
                + COLUMN_CAR_ID + " TEXT, "
                + COLUMN_USER_ID + " TEXT, "
                + COLUMN_RESERVATION_DATE + " TEXT, "
                + COLUMN_RESERVATION_TIME + " TEXT, "
                + COLUMN_PRICE + " TEXT);";

        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_SQL_TABLE_RESERVATION);
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
        public boolean addReservation(Reservation reservation) {

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, reservation.getId());
            contentValues.put(COLUMN_CAR_ID, reservation.getCarId());
            contentValues.put(COLUMN_USER_ID, reservation.getUserId());
            contentValues.put(COLUMN_RESERVATION_DATE, reservation.getReservationDate());
            contentValues.put(COLUMN_RESERVATION_TIME, reservation.getReservationTime());
            contentValues.put(COLUMN_PRICE, reservation.getPrice());

            newReservation = reservation;

            if (!checkCarIsReserved(reservation.getCarId())) {
                sqLiteDatabase.insert(RESERVATION_TABLE_NAME, null, contentValues);
                return true;
            }else{
                return false;
            }
        }
        public boolean checkCarIsReserved(int carId) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + RESERVATION_TABLE_NAME + " WHERE " + COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(carId)});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    newReservation.setId(cursor.getInt(0));
                    newReservation.setCarId(cursor.getInt(1));
                    newReservation.setUserId(cursor.getString(2));
                    newReservation.setReservationDate(cursor.getString(3));
                    newReservation.setReservationTime(cursor.getString(4));
                    newReservation.setPrice(cursor.getDouble(5));
                }
                return true;
            } else {
                return false;
            }
        }
        public Cursor getReservationDetails() {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_CAR_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_RESERVATION_DATE + ", " + COLUMN_RESERVATION_TIME + ", " + COLUMN_PRICE + " FROM " + RESERVATION_TABLE_NAME, null);
        }
        public Cursor getCustomerReservationDetails(String userId) {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_CAR_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_RESERVATION_DATE + ", " + COLUMN_RESERVATION_TIME + ", " + COLUMN_PRICE + " FROM " + RESERVATION_TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});
        }
}

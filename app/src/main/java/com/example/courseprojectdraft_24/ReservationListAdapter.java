package com.example.courseprojectdraft_24;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {
    public ReservationListAdapter(@NonNull Context context, ArrayList<Reservation> dataArrayList) {
        super(context, R.layout.reservation_item, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Reservation reservation = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.reservation_item, parent, false);
        }
        CarDataBaseHelper carDataBaseHelper = new CarDataBaseHelper(getContext());
        String carInfo = carDataBaseHelper.getCarInformation(reservation.getCarId());
        TextView nameTextView = view.findViewById(R.id.reserved_car_make);
        nameTextView.setText(carInfo);

        TextView priceTextView = view.findViewById(R.id.reserved_car_price);
        priceTextView.setText(String.valueOf(reservation.getPrice())+"$");

        TextView dateTextView = view.findViewById(R.id.Reservation_date);
        dateTextView.setText(String.valueOf(reservation.getReservationDate()));

        TextView timeTextView = view.findViewById(R.id.Reservation_time);
        timeTextView.setText(String.valueOf(reservation.getReservationTime()));

        return view;
    }
}

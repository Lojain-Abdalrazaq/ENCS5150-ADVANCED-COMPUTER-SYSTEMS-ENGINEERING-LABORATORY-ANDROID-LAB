package com.example.courseprojectdraft_24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AllReservationAdapter extends ArrayAdapter<Reservation> {
    public AllReservationAdapter(@NonNull Context context, ArrayList<Reservation> dataArrayList) {
        super(context, R.layout.admin_reservation_item, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Reservation reservation = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.admin_reservation_item, parent, false);
        }
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        TextView customerId = view.findViewById(R.id.CustomerName);
        customerId.setText(dataBaseHelper.getCustomerName(reservation.getUserId()));

        CarDataBaseHelper carDataBaseHelper = new CarDataBaseHelper(getContext());
        String carInfo = carDataBaseHelper.getCarInformation(reservation.getCarId());
        TextView reservationId = view.findViewById(R.id.reserved_car_make);
        reservationId.setText(carInfo);

        TextView dateTextView = view.findViewById(R.id.Reservation_date);
        dateTextView.setText(String.valueOf(reservation.getReservationDate()));

        TextView timeTextView = view.findViewById(R.id.Reservation_time);
        timeTextView.setText(String.valueOf(reservation.getReservationTime()));

        TextView reservationPriceTextView = view.findViewById(R.id.reserved_car_price);
        reservationPriceTextView.setText(String.valueOf(reservation.getPrice())+"$");

        return view;
    }
}

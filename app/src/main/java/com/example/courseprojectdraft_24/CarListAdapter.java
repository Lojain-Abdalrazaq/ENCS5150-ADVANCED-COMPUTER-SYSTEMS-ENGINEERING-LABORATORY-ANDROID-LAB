package com.example.courseprojectdraft_24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class CarListAdapter extends ArrayAdapter<Car> {
    public CarListAdapter(@NonNull Context context, ArrayList<Car> dataArrayList) {
        super(context, R.layout.car_item, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Car car = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.car_item, parent, false);
        }
        TextView nameTextView = view.findViewById(R.id.detailsMake);
        nameTextView.setText(car.getType() + " " + car.getModel());

        TextView priceTextView = view.findViewById(R.id.detailsPrice);
        priceTextView.setText(String.valueOf(car.getPrice()));

        TextView offersTextView = view.findViewById(R.id.detailsOffers);
        offersTextView.setText(String.valueOf(car.getOffers()));

        TextView yearTextView = view.findViewById(R.id.detailsYear);
        yearTextView.setText(String.valueOf(car.getYear()));

        TextView distanceTextView = view.findViewById(R.id.detailsDistance);
        distanceTextView.setText(String.valueOf(car.getDistance()));

        return view;
    }
}

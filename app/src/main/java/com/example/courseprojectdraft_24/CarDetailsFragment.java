package com.example.courseprojectdraft_24;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

public class CarDetailsFragment extends Fragment {

    private static final String ARG_CAR = "car";
    private Car selectedCar;
    public CarDetailsFragment() {
        // Required empty public constructor
    }
    public static CarDetailsFragment newInstance(Car car) {
        CarDetailsFragment fragment = new CarDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CAR, car);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCar = (Car) getArguments().getSerializable(ARG_CAR);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_details, container, false);

        // Display car details in the fragment's layout
        if (selectedCar != null) {

            TextView makeTextView = view.findViewById(R.id.detailsMake);
            makeTextView.setText(selectedCar.getType());

            TextView modelTextView = view.findViewById(R.id.detailsModel);
            modelTextView.setText(selectedCar.getModel());

            TextView yearTextView = view.findViewById(R.id.detailsYear);
            yearTextView.setText(String.valueOf(selectedCar.getYear()));

            TextView distanceTextView = view.findViewById(R.id.detailsDistance);
            distanceTextView.setText(String.valueOf(selectedCar.getDistance()));

            TextView priceTextView = view.findViewById(R.id.detailsPrice);
            priceTextView.setText(String.valueOf(selectedCar.getPrice()));

            TextView offersTextView = view.findViewById(R.id.detailsOffers);
            priceTextView.setText(String.valueOf(selectedCar.getOffers()));

        }
        return view;
    }
}
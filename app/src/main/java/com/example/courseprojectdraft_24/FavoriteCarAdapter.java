package com.example.courseprojectdraft_24;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteCarAdapter extends ArrayAdapter<Car> {

    public FavoriteCarAdapter(Context context, ArrayList<Car> favoriteCars) {
        super(context, 0, favoriteCars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car car = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_item, parent, false);
        }

        // Find TextViews in details_item layout
        TextView makeTextView = convertView.findViewById(R.id.detailsMake);

        TextView priceTextView = convertView.findViewById(R.id.detailsPrice);
        TextView offersTextView = convertView.findViewById(R.id.detailsOffers);
        TextView yearTextView = convertView.findViewById(R.id.detailsYear);
        TextView distanceTextView = convertView.findViewById(R.id.detailsDistance);

        // Set values to TextViews
        makeTextView.setText("Make: " + car.getType());

        priceTextView.setText("Price: " + car.getPrice());
        offersTextView.setText("Offers: " + car.getOffers());
        yearTextView.setText("Year: " + car.getYear());
        distanceTextView.setText("Distance: " + car.getDistance());

        return convertView;
    }
}
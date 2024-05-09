package com.example.courseprojectdraft_24;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarmenuFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //  private FragmentCarmenuBinding binding;
    private ListView listViewCars;
    private CarDataBaseHelper carDataBaseHelper;
    private boolean isHeartPressed = false;
    private FavouriteDB favouriteDB;
    private EditText nameEditText;
    private EditText modelEditText;
    private EditText priceEditText;
    private ImageView star1, star2, star3, star4, star5;
    private TextView editTextRate;
    private double averageRating = 0;
    private int totalRatings = 0;
    private int currentCarId = 0;
    public CarmenuFragment() {
        // Required empty public constructor
    }
    public static CarmenuFragment newInstance(String param1, String param2) {
        CarmenuFragment fragment = new CarmenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        favouriteDB = new FavouriteDB(requireContext());

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carmenu, container, false);

        listViewCars = view.findViewById(R.id.listViewCars);
        nameEditText = view.findViewById(R.id.nameEditText);
        modelEditText = view.findViewById(R.id.modelEditText);
        priceEditText = view.findViewById(R.id.priceEditText);

        if (view != null) {
            // Try to find the EditText within the rootView
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView editTextRate = view.findViewById(R.id.editTextRate);

            // Check if editTextRate is not null
            if (editTextRate != null) {
                // Continue with your logic, for example, setting an OnClickListener
                editTextRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle click event
                    }
                });
            } else {
                Log.e("CarmenuFragment", "EditText with ID editTextRate not found in the inflated layout");
            }
        } else {
            Log.e("CarmenuFragment", "Failed to inflate the layout");
        }
        Button filterButton = view.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> {
            // Get filter criteria
            String name = nameEditText.getText().toString();
            String model = modelEditText.getText().toString();
            String price = priceEditText.getText().toString();

            // Filter cars based on criteria
            filterCars(name, model, price);
        });
        carDataBaseHelper = new CarDataBaseHelper(requireContext());
        Cursor cursor = carDataBaseHelper.getCarDetails();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                ArrayList<Car> carList = new ArrayList<>();
                do {
                    Car car;
                    @SuppressLint("Range") String carID = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_ID));
                    @SuppressLint("Range") String carType = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_TYPE));
                    @SuppressLint("Range") String carModel = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_MODEL));
                    @SuppressLint("Range") String carPrice = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_PRICE));
                    @SuppressLint("Range") String carOffers = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_OFFERS));
                    @SuppressLint("Range") String carYear = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_YEAR));
                    @SuppressLint("Range") String carDistance = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_DISTANCE));
                    car = new Car(new Integer(carID),carType,carModel,new Double(carPrice), new Double(carOffers),new Integer(carYear),new Double(carDistance));
                    carList.add(car);
                } while (cursor.moveToNext());
                CarListAdapter adapter = new CarListAdapter(requireContext(), carList);
                listViewCars.setAdapter(adapter);
                // when clicking on one item, the fucntion of fragement replacement will be called
                // listner click
                listViewCars.setOnItemClickListener((parent, view1, position, id) -> {
                    Car selectedCar = (Car) parent.getItemAtPosition(position);
                    showCarDetailsPage(selectedCar);
                });
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return view;
    }
    public void showCarDetailsPage(Car car){
        // starting the implementation of the car's details activity

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_car_details, null);

        ImageView heartImageView = dialogView.findViewById(R.id.heart);

        // Set a click listener for the heart ImageView
        heartImageView.setOnClickListener(v -> {
            toggleHeartState(heartImageView, car);
        });
        star1 = dialogView.findViewById(R.id.rate1);
        star2 = dialogView.findViewById(R.id.rate2);
        star3 = dialogView.findViewById(R.id.rate3);
        star4 = dialogView.findViewById(R.id.rate4);
        star5 = dialogView.findViewById(R.id.rate5);
        editTextRate = dialogView.findViewById(R.id.editTextRate);
        currentCarId = car.getId();
        loadExistingRating();
        setStarClickListener(star1, 1);
        setStarClickListener(star2, 2);
        setStarClickListener(star3, 3);
        setStarClickListener(star4, 4);
        setStarClickListener(star5, 5);

        TextView detailsMake = dialogView.findViewById(R.id.detailsMake);
        detailsMake.setText(car.getType());

        TextView modelTextView = dialogView.findViewById(R.id.detailsModel);
        modelTextView.setText(car.getModel());

        TextView yearTextView = dialogView.findViewById(R.id.detailsYear);
        yearTextView.setText(String.valueOf(car.getYear()));

        TextView distanceTextView = dialogView.findViewById(R.id.detailsDistance);
        distanceTextView.setText(String.valueOf(car.getDistance()));

        TextView priceTextView = dialogView.findViewById(R.id.detailsPrice);
        priceTextView.setText(String.valueOf(car.getPrice()));

        TextView offersTextView = dialogView.findViewById(R.id.detailsOffers);
        offersTextView.setText(String.valueOf(car.getOffers()));

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // now, we will handle the functionalities of the buttons- or the actions of the buttons
        // 1. The reserve button will allow the customer to reserve the car
        Button buttonCarReserve = dialogView.findViewById(R.id.button_reserve_car);
        buttonCarReserve.setOnClickListener(v -> {
            // when the button is pressed, i have to create an object of type reservation and add it to the database
            ReservationDataBaseHelper reservationDataBaseHelper = new ReservationDataBaseHelper(requireContext());
            Reservation reservation = new Reservation();
            reservation.setId(car.getId());
            reservation.setCarId(car.getId());
            reservation.setUserId(Login.emailStr);
            // the current data and time will be added to the reservation
            reservation.setReservationDate(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            reservation.setReservationTime(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            reservation.setPrice(car.getPrice());

            // now, we will check if the car is reserved or not
            if(!reservationDataBaseHelper.checkCarIsReserved(car.getId())) {
                reservationDataBaseHelper.addReservation(reservation);
                Toast.makeText(requireContext(), "Car Reserved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(requireContext(), "Car is already reserved", Toast.LENGTH_SHORT).show();
            }
        });
        // 2. The rate button will allow the customer to rate the car
        Button btnRating = dialogView.findViewById(R.id.button_rate_car);

        dialog.show();
    }
    private void toggleHeartState(ImageView heartImageView, Car car) {
        isHeartPressed = !isHeartPressed;
        if (isHeartPressed) {
            // Heart is pressed, set it to red
            heartImageView.setImageResource(R.drawable.heartred);
            Toast.makeText(requireContext(), "Car Added to Favorites", Toast.LENGTH_SHORT).show();
            // Add the car to favorites
            favouriteDB.addFavorite(String.valueOf(car.getId()), "user@example.com", "isFav");
        } else {
            // Heart is not pressed, set it to white
            heartImageView.setImageResource(R.drawable.heart);
            Toast.makeText(requireContext(), "Car Removed from Favorites", Toast.LENGTH_SHORT).show();
            // Remove the car from favorites
            favouriteDB.deleteFavorite("user@example.com", String.valueOf(car.getId()));
        }
    }
    public void onFilterButtonClick(View view) {
        String name = nameEditText.getText().toString();
        String model = modelEditText.getText().toString();
        String price = priceEditText.getText().toString();
        // Perform filtering based on the provided criteria
        filterCars(name, model, price);
    }

    private void filterCars(String name, String model, String price) {
        Cursor cursor = carDataBaseHelper.filterCars(name, model, price);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                ArrayList<Car> filteredCarList = new ArrayList<>();
                do {
                    @SuppressLint("Range") String carID = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_ID));
                    @SuppressLint("Range") String carType = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_TYPE));
                    @SuppressLint("Range") String carModel = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_MODEL));
                    @SuppressLint("Range") String carPrice = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_PRICE));
                    @SuppressLint("Range") String carOffers = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_OFFERS));
                    @SuppressLint("Range") String carYear = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_YEAR));
                    @SuppressLint("Range") String carDistance = cursor.getString(cursor.getColumnIndex(CarDataBaseHelper.COLUMN_DISTANCE));
                    Car car = new Car(Integer.parseInt(carID), carType, carModel, Double.parseDouble(carPrice),
                            Double.parseDouble(carOffers), Integer.parseInt(carYear),
                            Double.parseDouble(carDistance));
                    filteredCarList.add(car);
                } while (cursor.moveToNext());

                CarListAdapter adapter = new CarListAdapter(requireContext(), filteredCarList);
                listViewCars.setAdapter(adapter);
                listViewCars.setOnItemClickListener((parent, view, position, id) -> {
                    Car selectedCar = (Car) parent.getItemAtPosition(position);
                    showCarDetailsPage(selectedCar);
                });
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private void setStarClickListener(ImageView star, int rating) {
        star.setOnClickListener(v -> {
            // Handle star click
            updateStarRating(rating);
        });
    }
    private void updateStarRating(int rating) {
        // Store the rating for the current car in SharedPreferences
        storeRating(currentCarId, rating);

        // Update the UI
        loadExistingRating();
    }
    private void storeRating(int carId, int rating) {
        // Use SharedPreferences to store ratings for each car
        SharedPreferences preferences = requireContext().getSharedPreferences("car_ratings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Key format: "car_rating_{carId}"
        String key = "car_rating_" + carId;

        // Store the rating
        editor.putInt(key, rating);
        editor.apply();
    }
    private void loadExistingRating() {
        // Use SharedPreferences to load existing ratings for the current car
        SharedPreferences preferences = requireContext().getSharedPreferences("car_ratings", Context.MODE_PRIVATE);

        // Key format: "car_rating_{carId}"
        String key = "car_rating_" + currentCarId;

        // Load the rating for the current car from SharedPreferences
        int existingRating = preferences.getInt(key, 0);

        calculateAndDisplayAverageRating();

        // Update the UI
        updateUI(existingRating);
    }
    private void calculateAndDisplayAverageRating() {
        SharedPreferences preferences = requireContext().getSharedPreferences("car_ratings", Context.MODE_PRIVATE);
        // Iterate through all cars and calculate the total rating and count
        int totalRating = 0;
        int totalCars = 0;
        // Assuming you have a known total number of cars (e.g., 100)
        int totalCarsCount = 100;

        for (int carId = 1; carId <= totalCarsCount; carId++) {
            String key = "car_rating_" + carId;
            int rating = preferences.getInt(key, 0);

            if (rating > 0) {
                totalRating += rating;
                totalCars++;
            }
        }
        averageRating = (double) totalRating / totalCars;
        // Display the average rating in the EditText
        if (editTextRate != null) {
            // Format the average rating to show up to one decimal place
            editTextRate.setText(String.format("%.1f", averageRating));
        } else {
            Log.e("CarmenuFragment", "EditText with ID editTextRate not found");
        }
    }
    private void updateUI(int rating) {
        // Update star images based on the loaded or updated rating
        ImageView[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setImageResource(R.drawable.baseline_star_yellow_24); // Assuming you have a yellow star drawable
            } else {
                stars[i].setImageResource(R.drawable.baseline_star_border_24); // Assuming you have a gray star drawable
            }
        }
    }
}
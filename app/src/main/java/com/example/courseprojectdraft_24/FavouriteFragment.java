package com.example.courseprojectdraft_24;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listViewFav;
    private FavouriteDB favouriteDB;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewallreservesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        listViewFav = view.findViewById(R.id.listViewFav);

        // Get the list of favorite cars from the database
        ArrayList<Car> favoriteCars = getFavoriteCars();

        // Create a custom adapter to display the list of favorite cars using details_item layout
        FavoriteCarAdapter adapter = new FavoriteCarAdapter(requireContext(), favoriteCars);
        // Set the adapter to the ListView
        listViewFav.setAdapter(adapter);

        return view;
    }

    private ArrayList<Car> getFavoriteCars() {
        ArrayList<Car> favoriteCars = new ArrayList<>();
        String userEmail = "user@example.com"; // Replace with actual user email

        Cursor cursor = favouriteDB.getReadableDatabase().rawQuery(
                "SELECT * FROM " + FavouriteDB.FAVORITE_CARS_TABLE_NAME +
                        " WHERE " + FavouriteDB.COL_USER_EMAIL + " = ?" +
                        " AND " + FavouriteDB.COL_IS_FAVE + " = 'isFav'",
                new String[]{userEmail}
        );

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String carID = cursor.getString(cursor.getColumnIndex(FavouriteDB.COL_CAR_ID));
                    // Retrieve other details of the car from your database or API
                    // and create a Car object
                    Car car = new Car(/* set details here */);
                    favoriteCars.add(car);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return favoriteCars;
    }
}
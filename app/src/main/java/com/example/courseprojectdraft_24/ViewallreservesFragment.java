package com.example.courseprojectdraft_24;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewallreservesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView allReservationListView_id;
    private ReservationDataBaseHelper reservationDataBaseHelper;


    private String mParam1;
    private String mParam2;

    public ViewallreservesFragment() {
        // Required empty public constructor
    }

    public static ViewallreservesFragment newInstance(String param1, String param2) {
        ViewallreservesFragment fragment = new ViewallreservesFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewallreserves, container, false);
        allReservationListView_id = view.findViewById(R.id.allReservationListView_id);
        reservationDataBaseHelper = new ReservationDataBaseHelper(getContext());

        Cursor cursor = reservationDataBaseHelper.getReservationDetails();

        try{
            if (cursor != null && cursor.moveToFirst()) {
                ArrayList<Reservation> reservationList = new ArrayList<>();
                do{
                    Reservation reservation;
                    @SuppressLint("Range") String reservesionId = cursor.getString(cursor.getColumnIndex(ReservationDataBaseHelper.COLUMN_ID));
                    @SuppressLint("Range") String carId = cursor.getString(cursor.getColumnIndex(ReservationDataBaseHelper.COLUMN_CAR_ID));
                    @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex(ReservationDataBaseHelper.COLUMN_USER_ID));
                    @SuppressLint("Range") String reservationDate = cursor.getString(cursor.getColumnIndex(ReservationDataBaseHelper.COLUMN_RESERVATION_DATE));
                    @SuppressLint("Range") String reservationTime = cursor.getString(cursor.getColumnIndex(ReservationDataBaseHelper.COLUMN_RESERVATION_TIME));
                    @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(ReservationDataBaseHelper.COLUMN_PRICE));

                    reservation = new Reservation(new Integer(reservesionId), new Integer(carId), userId, reservationDate, reservationTime, new Double(price));
                    reservationList.add(reservation);
                } while (cursor.moveToNext());
                AllReservationAdapter adapter = new AllReservationAdapter(requireContext(), reservationList);
                allReservationListView_id.setAdapter(adapter);
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return view;
    }
}
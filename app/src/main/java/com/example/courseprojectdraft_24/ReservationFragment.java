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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listViewReservations;
    private ReservationDataBaseHelper reservationDataBaseHelper;
    public ReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
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
        View view =  inflater.inflate(R.layout.fragment_reservation, container, false);
        listViewReservations = view.findViewById(R.id.reservation_list_view_id);
        reservationDataBaseHelper = new ReservationDataBaseHelper(requireContext());

        Cursor cursor = reservationDataBaseHelper.getCustomerReservationDetails(Login.emailStr);
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
                ReservationListAdapter adapter = new ReservationListAdapter(requireContext(), reservationList);
                listViewReservations.setAdapter(adapter);
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return view;
    }
}
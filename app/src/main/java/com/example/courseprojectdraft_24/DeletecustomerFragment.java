package com.example.courseprojectdraft_24;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.courseprojectdraft_24.databinding.FragmentDeletecustomerBinding;

import java.util.ArrayList;

public class DeletecustomerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentDeletecustomerBinding binding;
    private EditText editText_EnterEmail;
    private Button button_DeleteCustomer;
    private ListView listViewCustomers;
    private DataBaseHelper dataBaseHelper;
    public DeletecustomerFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static CallUsFragment newInstance(String param1, String param2) {
        CallUsFragment fragment = new CallUsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deletecustomer, container, false);

        editText_EnterEmail = view.findViewById(R.id.editText_EnterEmail);
        button_DeleteCustomer = view.findViewById(R.id.button_DeleteCustomer);
        listViewCustomers = view.findViewById(R.id.listViewCustomers);

        dataBaseHelper = new DataBaseHelper(requireContext());

        Cursor cursor = dataBaseHelper.getUserDetails();

        try {
            if (cursor != null && cursor.moveToFirst()) {
                ArrayList<String> userNames = new ArrayList<>();

                do {
                    @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_EMAIL_ID));
                    @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_FNAME));
                    @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_LNAME));

                    userNames.add(firstName + " " + lastName + " - " + userEmail);
                } while (cursor.moveToNext());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, userNames);
                listViewCustomers.setAdapter(adapter);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        button_DeleteCustomer.setOnClickListener(v -> {
            String email = editText_EnterEmail.getText().toString().trim();
            if (dataBaseHelper.checkEmailLogInExist(email)){
                Toast.makeText(requireContext(), "Please select an item", Toast.LENGTH_SHORT).show();
                dataBaseHelper.deleteCustomers(email);
                updateListView();
            } else {
                Toast.makeText(requireContext(), "There is no Customer with this email", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
        // Inflate the layout for this fragment
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateListView() {
        Cursor cursor = dataBaseHelper.getUserDetails();

        try {
            if (cursor != null && cursor.moveToFirst()) {
                ArrayList<String> userNames = new ArrayList<>();

                do {
                    @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_EMAIL_ID));
                    @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_FNAME));
                    @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_LNAME));

                    userNames.add(firstName + " " + lastName + " - " + userEmail);
                } while (cursor.moveToNext());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, userNames);
                listViewCustomers.setAdapter(adapter);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
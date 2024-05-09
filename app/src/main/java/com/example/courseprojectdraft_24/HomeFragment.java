package com.example.courseprojectdraft_24;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    // Required empty public constructor
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // You can perform additional operations or set up UI components here
        // For example, if you want to handle clicks or update the UI based on data

        // Example: Set a click listener for the ImageView
        ImageView profileImg2 = view.findViewById(R.id.profileImg2);
        profileImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for the profile image
                // You can open another fragment/activity or perform any action here
            }
        });

        // Example: Set text for the description TextView
        TextView description = view.findViewById(R.id.description);
        description.setText("This app is for our car dealing company, with over 20 years of experience and high-quality services.");

        // Example: Set text for the second description TextView
        TextView description2 = view.findViewById(R.id.description2);
        description2.setText("We hope that you will have a good experience with it.");
    }
}

package com.example.courseprojectdraft_24;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseprojectdraft_24.databinding.FragmentAddadminBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddadminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddadminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentAddadminBinding binding;

    String[] gender_options = {"Male", "Female"};
    String[] country_options = {"Palestine", "Morocco", "Algeria", "Tunisia"};
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Spinner gender_optionsSpinner;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner country_optionsSpinner;
    private Spinner city_optionsSpinner;
    private EditText phoneEditText;
    private Button AddAdminButton;
    public static Admin myAdmin = new Admin();
    AdminDataBaseHelper adminDataBaseHelper;
    private Map<String, String> countryToZipCodeMap;
    // for the zip code
    private TextView zipCodeTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_addadmin, container, false);
        View view = inflater.inflate(R.layout.fragment_addadmin, container, false);

        gender_optionsSpinner = (Spinner) view.findViewById(R.id.spinner_Gender);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gender_options);
        gender_optionsSpinner.setAdapter(objGenderArr);
        country_optionsSpinner = (Spinner) view.findViewById(R.id.spinner_Country);
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, country_options);
        country_optionsSpinner.setAdapter(objCountryArr);
        city_optionsSpinner = (Spinner) view.findViewById(R.id.spinner_City);
        emailEditText = view.findViewById(R.id.email);
        firstNameEditText = view.findViewById(R.id.FirstName);
        lastNameEditText = view.findViewById(R.id.lastName);
        passwordEditText = view.findViewById(R.id.password);
        confirmPasswordEditText = view.findViewById(R.id.confirmPassword);
        phoneEditText = view.findViewById(R.id.phoneNumber);
        AddAdminButton = view.findViewById(R.id.AddAdminButton);
        // initializing the map of countries to zip codes
        initializeCountryToZipCodeMap();
        // setting up the spinners
        country_optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCountry = country_options[position];
                populateCitySpinner(selectedCountry);
                String zipCode = countryToZipCodeMap.get(selectedCountry);
                zipCodeTextView = view.findViewById(R.id.zip_code_textView);
                zipCodeTextView.setText(zipCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });



        AddAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String gender_selected = gender_optionsSpinner.getSelectedItem().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String country_selected = country_optionsSpinner.getSelectedItem().toString();
                String city_selected = city_optionsSpinner.getSelectedItem().toString();
                String phone = phoneEditText.getText().toString();

                adminDataBaseHelper = new AdminDataBaseHelper(getContext());
                if (validateInput(email, firstName, lastName, gender_selected, password, confirmPassword, country_selected, city_selected, phone)) {
                    adminDataBaseHelper.addAdmin(myAdmin);
                }
                else {
                    AddAdminButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        return view;
    }


    private void populateCitySpinner(String selectedCountry) {
        List<String> citiesForSelectedCountry = getCitiesForCountry(selectedCountry);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, citiesForSelectedCountry);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_optionsSpinner.setAdapter(cityAdapter);

    }

    private void initializeCountryToZipCodeMap() {
        countryToZipCodeMap = new HashMap<>();
        countryToZipCodeMap.put("Palestine", "00970");
        countryToZipCodeMap.put("Morocco", "00212");
        countryToZipCodeMap.put("Algeria", "00213");
        countryToZipCodeMap.put("Tunisia", "00216");
    }


    private List<String> getCitiesForCountry(String country) {
        List<String> cities = new ArrayList<>();
        if ("Palestine".equals(country)) {
            cities.add("Jerusalem");
            cities.add("Haifa");
            cities.add("Gaza");
        } else if ("Morocco".equals(country)) {
            cities.add("Casablanca");
            cities.add("Rabat");
            cities.add("Fes");
        } else if ("Algeria".equals(country)) {
            cities.add("Algiers");
            cities.add("Oran");
            cities.add("Constantine");
        } else if ("Tunisia".equals(country)) {
            cities.add("Tunis");
            cities.add("Sfax");
            cities.add("Sousse");
        }
        return cities;
    }


    private boolean validateInput(String email, String firstName, String lastName, String gender_selected, String password, String confirmPassword,
                                  String country_selected, String city_selected, String phone) {
        boolean flag = true;
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.endsWith("@admin.com")) {
            emailEditText.setError("Enter a valid email address ends with @admin.com");
            flag = false;
        } else {
            myAdmin.setAdminEmail(email);
        }
        if (firstName.isEmpty() || firstName.length() < 3) {
            firstNameEditText.setError("Enter valid first name");
            flag = false;
        } else {
            myAdmin.setAdminFirstName(firstName);
        }
        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameEditText.setError("Enter valid last name");
            flag = false;
        } else {
            myAdmin.setAdminLastName(lastName);
        }
        // for gender spinner
        myAdmin.setAdminGender(gender_selected);
        if (password.isEmpty() || password.length() < 5 || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$")) {
            passwordEditText.setError("Password must include at least 1 character, 1 number, and 1 special character");
            flag = false;
        } else {
            myAdmin.setAdminPassword(password);
        }
        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            confirmPasswordEditText.setError("Passwords do not match");
            flag = false;
        } else {
            myAdmin.setAdminConfirmPassword(confirmPassword);
        }
        myAdmin.setAdminCountry(country_selected);
        myAdmin.setAdminCity(city_selected);
        String zipCode = countryToZipCodeMap.get(country_selected);

        if (phone.toString().isEmpty() || phone.toString().length() != 9) {
            phoneEditText.setError("Phone number must be 9 digits only");
            flag = false;
        } else {
            String updatedPhoneNumber = "";
            if (zipCode != null){
                updatedPhoneNumber = zipCode + phone.toString();
            }
            myAdmin.setAdminPhone(updatedPhoneNumber);
        }
        return flag;
    }


    public AddadminFragment() {
        // Required empty public constructor
    }


    public static AddadminFragment newInstance(String param1, String param2) {
        AddadminFragment fragment = new AddadminFragment();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
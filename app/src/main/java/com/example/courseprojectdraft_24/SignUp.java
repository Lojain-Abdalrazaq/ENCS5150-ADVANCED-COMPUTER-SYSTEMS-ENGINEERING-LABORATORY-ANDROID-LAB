package com.example.courseprojectdraft_24;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    // spinner options
    String[] gender_options = {"Male", "Female"};
    String[] country_options = {"Palestine", "Morocco", "Algeria","Tunisia"};
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Spinner gender_optionsSpinner;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner country_optionsSpinner;
    private Spinner city_optionsSpinner;
    private EditText phoneEditText;
    private Button signupButton;
    private TextView loginRedirectText;
    public static User myUser = new User();
    DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUp.this);
    private Map<String, String> countryToZipCodeMap;
    // for the zip code
    private TextView zipCodeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        gender_optionsSpinner = (Spinner) findViewById(R.id.spinner_Gender);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gender_options);
        gender_optionsSpinner.setAdapter(objGenderArr);
        country_optionsSpinner = (Spinner) findViewById(R.id.spinner_Country);
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, country_options);
        country_optionsSpinner.setAdapter(objCountryArr);
        city_optionsSpinner = (Spinner) findViewById(R.id.spinner_City);
        emailEditText = findViewById(R.id.email);
        firstNameEditText = findViewById(R.id.FirstName);
        lastNameEditText = findViewById(R.id.lastName);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        phoneEditText = findViewById(R.id.phoneNumber);
        signupButton = findViewById(R.id.signupButton);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        // initializing the map of countries to zip codes
        initializeCountryToZipCodeMap();
        // setting up the spinners
        country_optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCountry = country_options[position];
                populateCitySpinner(selectedCountry);
                String zipCode = countryToZipCodeMap.get(selectedCountry);
                zipCodeTextView = findViewById(R.id.zip_code_textView);
                zipCodeTextView.setText(zipCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
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
                if (validateInput(email, firstName, lastName, gender_selected, password, confirmPassword, country_selected, city_selected, phone)) {
                    dataBaseHelper.addUser(myUser);
                    // User added successfully
                    Toast.makeText(SignUp.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to the signup screen
                }
                else {
                    Toast.makeText(SignUp.this, "Invalid input. Please check your information.", Toast.LENGTH_LONG).show();
                    signupButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }
    private void populateCitySpinner(String selectedCountry) {
        List<String> citiesForSelectedCountry = getCitiesForCountry(selectedCountry);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, citiesForSelectedCountry);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_optionsSpinner.setAdapter(cityAdapter);

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
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            flag = false;
        } else {
            myUser.setUserEmail(email);
        }
        if (firstName.isEmpty() || firstName.length() < 3) {
            firstNameEditText.setError("Enter valid first name");
            flag = false;
        } else {
            myUser.setFirstName(firstName);
        }
        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameEditText.setError("Enter valid last name");
            flag = false;
        } else {
            myUser.setLastName(lastName);
        }
        // for gender spinner
        myUser.setUserGender(gender_selected);
        if (password.isEmpty() || password.length() < 5 || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$")) {
            passwordEditText.setError("Password must include at least 1 character, 1 number, and 1 special character");
            flag = false;
        } else {
            myUser.setPassword(password);
        }
        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            confirmPasswordEditText.setError("Passwords do not match");
            flag = false;
        } else {
            myUser.setCpassword(confirmPassword);
        }
        myUser.setUserCountry(country_selected);
        myUser.setUserCity(city_selected);
        String zipCode = countryToZipCodeMap.get(country_selected);

        if (phone.toString().isEmpty() || phone.toString().length() != 9) {
            phoneEditText.setError("Phone number must be 9 digits only");
            flag = false;
        } else {
            String updatedPhoneNumber = "";
            if (zipCode != null){
                updatedPhoneNumber = zipCode + phone.toString();
            }
            myUser.setUserPhone(updatedPhoneNumber);
        }
        return flag;
    }
    private void initializeCountryToZipCodeMap() {
        countryToZipCodeMap = new HashMap<>();
        countryToZipCodeMap.put("Palestine", "00970");
        countryToZipCodeMap.put("Morocco", "00212");
        countryToZipCodeMap.put("Algeria", "00213");
        countryToZipCodeMap.put("Tunisia", "00216");
    }
}
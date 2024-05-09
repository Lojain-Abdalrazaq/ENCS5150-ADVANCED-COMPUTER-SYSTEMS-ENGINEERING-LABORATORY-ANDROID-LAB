package com.example.courseprojectdraft_24;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    static String emailStr;
    AdminDataBaseHelper adminDataBaseHelper;
    DataBaseHelper sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // definition of the elements in the login page.
        //sqLiteDatabase = new DataBaseHelper(this);
        adminDataBaseHelper =new AdminDataBaseHelper(this);
        Button login = (Button) findViewById(R.id.button_login);
        EditText emailEditText = (EditText) findViewById(R.id.email_input);
        EditText passwordEditText = (EditText) findViewById(R.id.password_input);
        CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
        TextView signupRedirectText = findViewById(R.id.signupRedirectText2);


        Admin admin = new Admin();
        admin.setAdminEmail("alaa@admin.com");
        admin.setAdminFirstName("ala");
        admin.setAdminLastName("mah");
        admin.setAdminPassword("alaa123#");
        admin.setAdminConfirmPassword("alaa123#");
        admin.setAdminPhone("0097123456789");
        admin.setAdminGender("Female");
        admin.setAdminCountry("Palestine");
        admin.setAdminCity("Jerusalem");

        if (!adminDataBaseHelper.checkEmailSignIn(admin.getAdminEmail())) {
            // Admin doesn't exist, add it to the database
            adminDataBaseHelper.addAdmin(admin);
        }



        // Shared preferences to save the user email.
        SharedPrefManager prefs = SharedPrefManager.getInstance(this);
        String savedEmail = prefs.readString("email", "");
        emailEditText.setText(savedEmail);

        // button handlers for the login page.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // Verify email and password against database
                DataBaseHelper dataBaseHelper = new DataBaseHelper(Login.this);
                AdminDataBaseHelper adminDataBaseHelper = new AdminDataBaseHelper(Login.this);
                boolean isAdmin = email.endsWith("@admin.com");
                if (isAdmin == true) {
                    if (adminDataBaseHelper.checkEmailSignIn(email) == true) {
                        if (adminDataBaseHelper.checkEmailPassword(email, password) == true) {
                            emailStr = email;
                            if (rememberMeCheckBox.isChecked()) {
                                prefs.writeString("email", email);
                            }
                            Intent intent3 = new Intent(Login.this, AdminHomePage.class);
                            startActivity(intent3);
                        } else if (adminDataBaseHelper.checkEmailPassword(email, password) == false) {
                            Toast.makeText(Login.this, "Invalid password!", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Login.this, "Invalid admin email!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (dataBaseHelper.checkEmailSignIn(email) == true) {
                        if (dataBaseHelper.checkEmailPassword(email, password) == true) {
                            emailStr = email;
                            if (rememberMeCheckBox.isChecked()) {
                                prefs.writeString("email", email);
                            }
                            Intent intent2 = new Intent(Login.this, CustomerHomePage.class);
                            startActivity(intent2);
                        } else if (dataBaseHelper.checkEmailPassword(email, password) == false) {
                            Toast.makeText(Login.this, "Invalid password!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Invalid user email!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        // handler for the signup redirect text.
        // in case the user deos not have an account, he can click on the signup redirect text to be redirected to the signup page.
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to the signup page.
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
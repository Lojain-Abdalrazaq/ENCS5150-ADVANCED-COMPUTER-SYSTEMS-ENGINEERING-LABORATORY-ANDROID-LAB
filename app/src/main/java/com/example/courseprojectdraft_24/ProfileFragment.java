package com.example.courseprojectdraft_24;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.courseprojectdraft_24.DataBaseHelper;
import com.example.courseprojectdraft_24.Login;
import com.example.courseprojectdraft_24.R;
import com.example.courseprojectdraft_24.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentProfileBinding binding;

    EditText edit_first_name;
    EditText edit_last_name;
    EditText edit_phone_number;
    EditText edit_password;
    EditText edit_confirm_password;
    Button button_update_first_name;
    Button button_update_last_name;
    Button button_update_phone;
    Button button_update_password;
    Button button_save_changes;
    TextView ProfileTitle;
    String email = Login.emailStr;
    DataBaseHelper myDB;
    ImageView img;
    static byte[] imageContent;
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProfileTitle = root.findViewById(R.id.Profile);
        edit_first_name = root.findViewById(R.id.edit_first_name);
        edit_last_name = root.findViewById(R.id.edit_last_name);
        edit_phone_number = root.findViewById(R.id.edit_phone_number);
        edit_password = root.findViewById(R.id.edit_password);
        edit_confirm_password = root.findViewById(R.id.edit_confirm_password);
        img = root.findViewById(R.id.profileImg);

        showUserData();

        button_update_first_name = root.findViewById(R.id.button_update_first_name);
        button_update_last_name = root.findViewById(R.id.button_update_last_name);
        button_update_phone = root.findViewById(R.id.button_update_phone);
        button_update_password = root.findViewById(R.id.button_update_password);
        button_save_changes = root.findViewById(R.id.button_save_changes);

        button_update_first_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_first_name.setEnabled(true);
            }
        });
        button_update_last_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_last_name.setEnabled(true);
            }
        });
        button_update_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_phone_number.setEnabled(true);
            }
        });
        button_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_password.setEnabled(true);
                edit_confirm_password.setEnabled(true);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setVisibility(View.VISIBLE);
                edit_first_name.setVisibility(View.VISIBLE);  // Set to VISIBLE
                edit_last_name.setVisibility(View.VISIBLE);   // Set to VISIBLE
                edit_phone_number.setVisibility(View.VISIBLE); // Set to VISIBLE
                edit_password.setVisibility(View.VISIBLE);     // Set to VISIBLE
                edit_confirm_password.setVisibility(View.VISIBLE); // Set to VISIBLE
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        String email =  Login.emailStr;
        DataBaseHelper myDB = new DataBaseHelper(getActivity());
        // Cursor userInformation = myDB.getUserDetails(email);
        button_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_first_name.isEnabled()==true){
                    // updating the first name of the customer
                    String updatedFirstName = edit_first_name.getText().toString();
                    if(validateInputFirstNameLength(updatedFirstName)==true) {
                        myDB.updateUserName(email, updatedFirstName);
                        edit_first_name.setText(updatedFirstName);
                        ProfileTitle.setText(updatedFirstName);
                        // updating the layout
                        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
                        // NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
                        // updating the visibility of the textfields
                        edit_first_name.setEnabled(false);
                        Toast.makeText(getActivity(), "First Name Changed Successfully!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "First Name must be more than 3 characters ", Toast.LENGTH_SHORT).show();
                    }
                }else if(edit_last_name.isEnabled()==true){
                    String updatedLastName = edit_last_name.getText().toString();
                    if(validateInputLastNameLength(updatedLastName)==true) {
                        myDB.updateUserLastName(email, updatedLastName);
                        edit_last_name.setText(updatedLastName);
                        // updating the layout
                        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
                        // NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
                        // updating the visibility of the textfields
                        edit_last_name.setEnabled(false);
                        Toast.makeText(getActivity(), "Last Name Changed Successfully!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Last Name must be more than 3 characters ", Toast.LENGTH_SHORT).show();
                    }
                }else if(edit_phone_number.isEnabled()==true){
                    String updatedPhoneNumber = edit_phone_number.getText().toString();
                    if(validatePhoneNumber(updatedPhoneNumber) == true){
                        String phoneZipCode=updatePhoneWithZipCode(updatedPhoneNumber);
                        myDB.updatePhoneNumber(email, phoneZipCode);
                        edit_phone_number.setText(phoneZipCode);
                        // updating the layout
                        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
                        // NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
                        // updating the visibility of the textfields
                        edit_phone_number.setEnabled(false);
                        Toast.makeText(getActivity(), "Phone Number Changed Successfully!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(), "Error in updating phone number!", Toast.LENGTH_SHORT).show();
                    }
                }else if(edit_password.isEnabled()==true){
                    String updatedPassword = edit_password.getText().toString();
                    String updatedCPassword = edit_confirm_password.getText().toString();
                    if(validatePassword(updatedPassword,updatedCPassword)==true){
                        myDB.updatePassword(email, updatedPassword);
                        edit_password.setText(updatedPassword);
                        edit_confirm_password.setText(updatedCPassword);
                        // updating the layout
                        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
                        //NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
                        // updating the visibility of the textfields
                        edit_password.setEnabled(false);
                        edit_confirm_password.setEnabled(false);
                        Toast.makeText(getActivity(), "Password Changed Successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Error in updating password number!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Nothing Has Changed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
    private void showUserData() {
        edit_first_name.setText(DataBaseHelper.inputUser.getFirstName());
        edit_last_name.setText(DataBaseHelper.inputUser.getLastName());
        edit_phone_number.setText(DataBaseHelper.inputUser.getUserPhone());
        edit_password.setText(DataBaseHelper.inputUser.getPassword());
        edit_confirm_password.setText(DataBaseHelper.inputUser.getCpassword());
        ProfileTitle.setText(DataBaseHelper.inputUser.getFirstName());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshUserData();
        }
    }
    private void refreshUserData() {
        // Refresh user data
        String firstName = DataBaseHelper.inputUser.getFirstName();
        String lastName = DataBaseHelper.inputUser.getLastName();
        String phoneNumber = DataBaseHelper.inputUser.getUserPhone();
        String password = DataBaseHelper.inputUser.getPassword();
        String cpassword = DataBaseHelper.inputUser.getPassword();
        // Update TextViews
        edit_first_name.setText(firstName);
        edit_last_name.setText(lastName);
        edit_phone_number.setText(phoneNumber);
        edit_password.setText(password);
        edit_confirm_password.setText(cpassword);
        // update the title of the profile
        ProfileTitle.setText(firstName);
    }
    private boolean validateInputFirstNameLength(String input) {
        if (input.length() < 3) {
            edit_first_name.setError("The first name at least 3 chars");
            return false;
        }else{return true;}
    }
    private boolean validateInputLastNameLength(String input) {
        if (input.length() < 3) {
            edit_first_name.setError("The last name at least 3 chars");
            return false;
        }else{return true;}
    }
    private boolean validatePhoneNumber(String phoneNumber){
        if (phoneNumber.isEmpty() || phoneNumber.length()>9 || phoneNumber.length()<9){
            edit_phone_number.setError("Phone number must be 9 digits only");
            return false;
        }else{
            return true;
        }
    }
    private String updatePhoneWithZipCode(String phoneWithoutZip){
        String phoneWithZip ="";
        String userCountry = DataBaseHelper.inputUser.getUserCountry();
        if (userCountry.equals("Palestine")){
            phoneWithZip = "00970" + phoneWithoutZip;
        }else if (userCountry.equals("Morocco")) {
            phoneWithZip = "00212" + phoneWithoutZip;
        }else if (userCountry.equals("Tunisia")) {
            phoneWithZip = "00216" + phoneWithoutZip;
        }else{
            phoneWithZip = "00213" + phoneWithoutZip;
        }
        return phoneWithZip;
    }
    private boolean validatePassword(String updatedPassword, String updatedConfirmPasword){
        if(updatedPassword.matches(updatedConfirmPasword)==false) {
            edit_password.setError("Passwords do not match");
            edit_confirm_password.setError("Passwords do not match");
            return false;
        }else{
            if(updatedPassword.isEmpty()|| updatedPassword.length() < 5||!updatedPassword.matches("^(?=.[A-Za-z])(?=.\\d)(?=.[@$!%#?&])[A-Za-z\\d@$!%*#?&]+$")){
                edit_password.setError("Password must include at least 1 character, 1 number, and 1 special character");
                return false;
            }else{
                return true;
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Picasso.get().load(selectedImageUri).into(img);
            try {
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageContent = stream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
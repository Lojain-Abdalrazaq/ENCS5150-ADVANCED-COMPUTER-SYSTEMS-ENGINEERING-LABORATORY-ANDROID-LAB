package com.example.courseprojectdraft_24;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.courseprojectdraft_24.databinding.ActivityAdminHomePageBinding;
import com.google.android.material.navigation.NavigationView;

public class AdminHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private AppBarConfiguration mAppBarConfiguration1;
    private ActivityAdminHomePageBinding binding;
    AdminDataBaseHelper adminDataBaseHelper = new AdminDataBaseHelper(this);
    Admin admin=new Admin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarPart3.toolbar2);

        DrawerLayout drawer = binding.drawerAdmin;
        NavigationView navigationView = binding.navView2;

        mAppBarConfiguration1 = new AppBarConfiguration.Builder(
                R.id.nav_deletecustomer,
                R.id.nav_addadmin,
                R.id.nav_viewallreserves,
                R.id.nav_logout).
                setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_part2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration1);
        NavigationUI.setupWithNavController(navigationView, navController);
        //setContentView(R.layout.activity_admin_home_page);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_drawer, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_part2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration1)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_deletecustomer) {
            // Intent intent = new Intent(Part2.this , AllFragment.class);
            //startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            logoutmenu1(AdminHomePage.this);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logoutmenu1(AdminHomePage log_out) {
        AlertDialog.Builder builder = new AlertDialog.Builder(log_out);
        builder.setTitle("Logout");
        builder.setMessage("Are You Sure ??");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent2 = new Intent(AdminHomePage.this, Login.class);
                startActivity(intent2);
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
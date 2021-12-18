package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.demo.views.SellerListFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SellerListFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction;
        switch (item.getItemId()) {
            case R.id.item_home:
                fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SellerListFragment.newInstance());
                fragmentTransaction.commit();
                return true;
            case R.id.item_profile:
                fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SellerListFragment.newInstance());
                fragmentTransaction.commit();
                return true;
            case R.id.item_about_us:
                fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SellerListFragment.newInstance());
                fragmentTransaction.commit();
                return true;
            case R.id.item_contact_us:
                fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SellerListFragment.newInstance());
                fragmentTransaction.commit();
                return true;
            case R.id.item_logout:
                Intent i = new Intent(HomeActivity.this, SingInActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return false;
    }
}
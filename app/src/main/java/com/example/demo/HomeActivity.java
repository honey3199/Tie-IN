package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.demo.navigation.NavigationFragment;
import com.example.demo.views.SellerListFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationFragment.Callbacks {

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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navigationView.setNavigationItemSelectedListener(item -> true);

        NavigationFragment navigationFragment = NavigationFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.navigation_view, navigationFragment)
                .commit();

        /*ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, , toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();*/

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SellerListFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMasterItemClicked(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    /*@Override
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
    }*/
}
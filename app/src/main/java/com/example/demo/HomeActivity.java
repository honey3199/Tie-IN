package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo.repository.UserRepository;
import com.example.demo.room.model.User;
import com.example.demo.storage.LocalStorage;
import com.example.demo.views.ContactUsFragment;
import com.example.demo.views.ProfileFragment;
import com.example.demo.views.SellerListFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView navigationHeaderUserName;
    UserRepository userRepository;
    LocalStorage localStorage;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        localStorage = new LocalStorage(this.getApplication());
        userRepository = new UserRepository(this);
        user = userRepository.getUser(localStorage.getPhone());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, SellerListFragment.newInstance()).commit();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction;
            switch (item.getItemId()) {
                case R.id.item_home:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, SellerListFragment.newInstance());
                    fragmentTransaction.commit();
                    break;
                case R.id.item_about_us:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ProfileFragment.newInstance());
                    fragmentTransaction.commit();
                    break;
                case R.id.item_contact_us:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ContactUsFragment.newInstance());
                    fragmentTransaction.commit();
                    break;
                case R.id.item_change_password:
                    Intent i = new Intent(HomeActivity.this, ChangePasswordActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case R.id.item_sign_out:
                    localStorage.setPhone("");
                    localStorage.setPassword("");
                    Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            drawerLayout.close();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
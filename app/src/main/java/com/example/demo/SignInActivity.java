package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.data.Status;
import com.example.demo.repository.UserRepository;
import com.example.demo.storage.LocalStorage;

public class SignInActivity extends AppCompatActivity {

    UserRepository userRepository;
    LocalStorage localStorage;
    Button btnSignIn;
    TextView tvClickToSignUp;
    EditText etMobileNo, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnSignIn = findViewById(R.id.btn_change_password);
        tvClickToSignUp = findViewById(R.id.tv_click_to_signup);
        etMobileNo = findViewById(R.id.et_mobileNo);
        etPassword = findViewById(R.id.et_password);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("phone"))
            etMobileNo.setText(bundle.getString("phone"));

        userRepository = new UserRepository(this);
        localStorage = new LocalStorage(this.getApplication());

        btnSignIn.setOnClickListener(loginButton -> {
            if (etMobileNo.getText().toString().equals("")) {
                Toast.makeText(this, "please enter phone number", Toast.LENGTH_LONG).show();
            } else if (etPassword.getText().toString().equals("")) {
                Toast.makeText(this, "please enter password", Toast.LENGTH_LONG).show();
            } else {
                Status response = userRepository.login(etMobileNo.getText().toString(), etPassword.getText().toString());

                if (response.getCode().equals(Status.SUCCESS.getCode())) {
                    localStorage.setPhone(etMobileNo.getText().toString());
                    localStorage.setPassword(etPassword.getText().toString());
                    Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, "Error: Invalid Credentials!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvClickToSignUp.setOnClickListener(v -> {
            Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(i);
            finish();
        });
    }
}